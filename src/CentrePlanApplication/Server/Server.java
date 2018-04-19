package CentrePlanApplication.Server;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Server implements ServerInterface 
{
	LinkedList<Person> people;
	LinkedList<BP_Node> businessPlans;

	public Server() 
	{
		businessPlans = new LinkedList<BP_Node>();
		people = new LinkedList<Person>();
	}


	@Override
	public void addPerson(Person person) //takes person handed to it and adds it to the list
	{									 //admin rights handled in client
		people.add(person);
		writeDisk();
	}
	
	public void make_BlankBP(int year, String department, Boolean editable)
	{	
		EntityFactory centre_factory = new CentrePlanFactory();
		BusinessEntity centre_plan = centre_factory.nextLayer(null);
		BusinessEntity mission_statement = centre_plan.createNewSubentity();
		BusinessEntity department_statement = mission_statement.createNewSubentity();
		BusinessEntity goal = department_statement.createNewSubentity();
		BusinessEntity SLO = goal.createNewSubentity();
		SLO.createNewSubentity();
		addBP_Node(new BP_Node(centre_plan, year, department, editable));
	}
	
	public void make_CloneBP(int year, String department, Boolean editable, int new_year)
	{
		BusinessEntity oldEntity = getBP_Node(department, year).getEntity();

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
		addBP_Node(new BP_Node(newEntity, new_year, department, editable));
	}

	@Override
	public void addBP_Node(BP_Node node)  //takes node given and adds it to the list if it doesn't exist
	{
		int index = 0;
		boolean found = false;
		while(!found && index<businessPlans.size())
		{
			BP_Node curr_node = businessPlans.get(index);
			if(curr_node.getYear() == node.getYear() && curr_node.getDepartment().equalsIgnoreCase(node.getDepartment()))
			{	
				found = true;
			}
			else
			{
				index++;
			}
		}
		if(found) //if the business plan already exists, replace with the updated node
		{
			businessPlans.remove(index);
			businessPlans.add(index, node);
		}
		else
		{
			businessPlans.add(node);
		}
		writeDisk();
		readDisk();
	}
	
	public void deleteBP_Node(BP_Node node)
	{
		int index = 0;
		boolean found = false;
		while(!found && index<businessPlans.size())
		{
			BP_Node curr_node = businessPlans.get(index);
			if(curr_node.getYear() == node.getYear() && curr_node.getDepartment().equalsIgnoreCase(node.getDepartment()))
			{	
				found = true;
			}
			else
			{
				index++;
			}
		}
		if(found) //if the business plan exists, delete it
		{
			businessPlans.remove(index);
		}
		writeDisk();
		readDisk();
	}
	
	public void setEditStatus(BP_Node node, boolean edit_status)
	{
		int index = 0;
		boolean found = false;
		while(!found && index<businessPlans.size())
		{
			BP_Node curr_node = businessPlans.get(index);
			if(curr_node.getYear() == node.getYear() && curr_node.getDepartment().equalsIgnoreCase(node.getDepartment()))
			{	
				found = true;
			}
			else
			{
				index++;
			}
		}
		
		if(found)
		{
			businessPlans.get(index).setEditable(edit_status);
		}
		writeDisk();
		readDisk();
	}

	@Override
	public Person getPerson(String username, String password) //iterates through the list and gets the person with the given username and password
	{														  //return null if not there
		for (int i = 0; i < people.size(); i++)
		{
			Person currentPerson = people.get(i);
			if (currentPerson.username.equals(username) && currentPerson.password.equals(password))
			{
				return currentPerson;
			}
		}
		return null;
	}

	@Override
	public BP_Node getBP_Node(String department, int year) //Same as get person, iterate the list and return the node with the matching dept and year
	{
		for (int i = 0; i < businessPlans.size(); i++)
		{
			BP_Node currentBP = businessPlans.get(i);
			if (currentBP.department.equals(department) && currentBP.year == year)
			{
				return currentBP;
			}
		}
		return null;
	}
	
	public void readDisk() //Reads data from disk and sets lists accordingly
	{
		XMLDecoder decoder=null; //Read the list from disk, set the to list in server
		try {
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream("businessPlans.xml")));
		} catch (FileNotFoundException e) {
			
		}
		businessPlans= (LinkedList<BP_Node>)decoder.readObject();
		
		try {
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream("people.xml")));
		} catch (FileNotFoundException e) {
			
		}
		people = (LinkedList<Person>)decoder.readObject();
		decoder.close();
	}
	
	public void writeDisk() //Write each person and BP_Node in the lists to the disk
	{
		XMLEncoder encoder1=null;
		
		try{
			encoder1=new XMLEncoder(new BufferedOutputStream(new FileOutputStream("businessPlans.xml")));
			}catch(FileNotFoundException fileNotFound){
				System.out.println("ERROR: While Creating or Opening the File businessPlans.xml");
			}
		encoder1.writeObject(businessPlans);
		encoder1.close();
		
		XMLEncoder encoder2=null;
		
		try{
			encoder2=new XMLEncoder(new BufferedOutputStream(new FileOutputStream("people.xml")));
			}catch(FileNotFoundException fileNotFound){
				System.out.println("ERROR: While Creating or Opening the File businessPlans.xml");
			}
		encoder2.writeObject(people);
		encoder2.close();
	}
	
	public LinkedList<Person> getPeople()
	{
		return this.people;
	}

	public LinkedList<BP_Node> getBP()
	{
		return this.businessPlans;
	}
	
	public static void main(String[] args)//main method reads disk at startup, writes every 2 minutes
	{
		Server server = new Server();
		server.readDisk();
		Boolean isDoneRunning = false;
		while(isDoneRunning == false)
		{
			try {
				TimeUnit.MINUTES.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			server.writeDisk();
		}
	}

}
