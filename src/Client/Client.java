package Client;

import Server.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.rmi.server.RemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;

public class Client // extends RemoteObject
{

	/**
	 * 
	 * 
	 * private static final long serialVersionUID = 1L;
	 */

	public ServerInterface proxy;
	public Person person;
	public BP_Node business;

	public Client(ServerInterface proxy)
	{
		this.proxy = proxy;

	}

	public void login(String username, String password) // Takes a username and password (to be given by GUI in future)
	{ // call get person on server with user and pass, set person variable

		person = proxy.getPerson(username, password);

	}

	public void requestBusinessPlan(String department, int year) // Same as login, but for the business plan
	{
		business = proxy.getBP_Node(department, year);
	}

	public void writeLocalBP(BP_Node business) // write BP_Node to the disk
	{
		XMLEncoder encoder = null;
		try
		{
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("businessPlan.xml")));
		} catch (FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: While Creating or Opening the File businessPlan.xml");
		}
		encoder.writeObject(business);
		encoder.close();

	}

	public void readLocalBP() // Read from disk
	{
		XMLDecoder decoder = null;
		try
		{
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("businessPlan.xml")));
		} catch (FileNotFoundException e)
		{
			System.out.println("ERROR: File businessPlan.xml not found");
		}
		business = (BP_Node) decoder.readObject();
		decoder.close();
	}

	public void addPeople(String username, String password, String department, Boolean admin) // add person to list on
																								// server
	{
		if (person.isAdmin())// Check if person is admin before allowing
		{
			Person newPerson = new Person(username, password, department, admin); // makes new person based on
																					// parameters
			proxy.addPerson(newPerson);
		} else
		{
			System.out.println("You definitely don't have permission to do this.");
		}
	}

	public void make_BlankBP(int year, String department, Boolean editable) // make a BP_Node with blank business plan
	{
		//String entityTitle, ArrayList<EntityStatement> statements,
		//ArrayList<BusinessEntity> subentities, BusinessEntity parentEntity, EntityFactory subentityFactory
		
		/*String entity_title = "Centre Business Plan";
		EntityFactory centre_plan = new CentrePlanFactory();
		BusinessEntity newEntity = new BusinessEntity(entity_title,new ArrayList<EntityStatement>(),new ArrayList<BusinessEntity>(),null,centre_plan);
		BusinessEntity department_statement = newEntity.createNewSubentity();
		BusinessEntity goal = department_statement.createNewSubentity();
		BusinessEntity SLO = goal.createNewSubentity();
		SLO.createNewSubentity();
		System.out.println(newEntity.getSubentities().get(0).getEntityTitle());*/
		proxy.make_BlankBP(year,department,editable);
		business = proxy.getBP_Node(department,year);
		//uploadBP(business);
		writeLocalBP(business);
	}

	public void make_CloneBP(int year, String department, Boolean editable, int new_year)
	{
		/*BusinessEntity oldEntity = proxy.getBP_Node(department, year).getEntity();

		String new_entity_title = oldEntity.getEntityTitle();
		ArrayList<EntityStatement> old_statements = oldEntity.getStatements();
		ArrayList<EntityStatement> new_statements = new ArrayList<EntityStatement>();
		for (int i = 0; i < old_statements.size(); i++)
		{
			new_statements.add(old_statements.get(i));
		}

		ArrayList<BusinessEntity> old_entities = oldEntity.getSubentities();
		ArrayList<BusinessEntity> new_entities = new ArrayList<BusinessEntity>();

		for (int i = 0; i < old_entities.size(); i++)
		{
			new_entities.add(old_entities.get(i));
		}
		BusinessEntity new_parent = oldEntity.getParentEntity();
		EntityFactory new_subentity_factory = oldEntity.getSubentityFactory();

		BusinessEntity newEntity = new BusinessEntity(new_entity_title, new_statements, new_entities, new_parent,
				new_subentity_factory);
		BP_Node newPlan = new BP_Node(newEntity, new_year, department, editable);
		business = newPlan;
		uploadBP(newPlan);
		writeLocalBP(newPlan);*/
		proxy.make_CloneBP(year,department,editable,new_year);
		business = proxy.getBP_Node(department,new_year);
		//uploadBP(business);
		writeLocalBP(business);
	}

	public void setBPStatus(BP_Node businessPlan, boolean editable)
	{
		proxy.setEditStatus(businessPlan, editable);
	}

	public void uploadBP(BP_Node businessPlan)
	{
		proxy.addBP_Node(businessPlan);
	}

	public void deleteBP(BP_Node businessPlan)
	{
		proxy.deleteBP_Node(businessPlan);
	}

	public LinkedList<BP_Node> getBP()
	{
		return proxy.getBP();
	}

	public LinkedList<Person> getPeople()
	{
		return proxy.getPeople();
	}

}
