package Server;

import java.util.ArrayList;
import java.io.Serializable;

//This class is used to return instances of BusinessEntites that will be used as subentites for the owner of the factory.
//In other words, in terms of VMOSA, an Objective will call its factory (an instance of this), which will return Strategies.
public class EntityFactory implements Serializable
{
	
	private static final long serialVersionUID = 3L;
	
	//These variables are used for the creation of a new BusinessEntities.
	//subFactory is used to provide entities with the EntityFactory they need to create subentities.
	//entityTitle is the title of the entity being created.
	private EntityFactory subFactory;
	protected String entityTitle;
	protected ArrayList<EntityStatement> defaultStatements;
	
	
	//nextLayer() is the method that is actually used to return a new BusinessEntity.
	//Subclasses of EntityFactory provide the subFactory and entityTitle, and then this method is called by entities to make subentities.
	public BusinessEntity nextLayer(BusinessEntity parentEntity)
	{
		return new BusinessEntity(entityTitle, defaultStatements, new ArrayList<BusinessEntity>(), parentEntity, subFactory);
	}
	
	//This constructor takes a LinkedList of strings and returns an EntityFactory where the LinkedList of factories (illustrated in our 
	//design doc) is automatically created.
	//
	//strings should use its order to lay out how the factories should be ordered.
	//For instance, in the Centre Business Plan example of our design doc, the linked list should be {Centre, Department, Goal, SLO, Tool}
	//(because these are the names of the different layers)
	//
	//This makes it so that you can create a custom format by just providing this constructor with a list of strings.
	//This is the recommended method of creating factories, as it only requires one class per plan.

	public EntityFactory(String[] strings)
	{
		//If there is more than one string in the array, then we create the linked list.
		if(strings.length > 1)
		{
			//Copy all strings except the first one into a new array.
			String[] newStrings = new String[strings.length - 1];
			
			for(int i = 1; i < strings.length; i++)
			{
				newStrings[i-1] = strings[i];
			}
			
			//Set entityTitle to the first element in the array and recursively add a new factory.
			entityTitle = strings[0];
			subFactory = new EntityFactory(newStrings);
		}
		
		//If there is only one string in the array, then we are at the last factory.
		else if (strings.length == 1)
		{
			subFactory = null;
			entityTitle = strings[0];
		}
		
		//If they do not pass in any strings, then we don't give the Factory a subFactory or entityTitle.
		else
		{
			subFactory = null;
			entityTitle = null;
		}
	}

	//This constructor is implemented so that subclasses can set subFactory and entityTitle.
	//This is implemented in case developers want to their own hierarchy rather than use the automatic method above.
	public EntityFactory(EntityFactory subFactory, String entityTitle)
	{
		this.subFactory = subFactory;
		this.entityTitle = entityTitle;
	}
	
	//You need a default constructor for serializability
	public EntityFactory() {}
	
	//Basic getters and setters
	public EntityFactory getSubFactory() 
	{
		return subFactory;
	}

	public ArrayList<EntityStatement> getDefaultStatements() 
	{
		return defaultStatements;
	}
	
	public String getEntityTitle()
	{
		return entityTitle;
	}
	
	public void setEntityTitle(String entityTitle)
	{
		this.entityTitle = entityTitle;
	}

	public void setDefaultStatements(ArrayList<EntityStatement> defaultStatements) 
	{
		this.defaultStatements = defaultStatements;
	}
	
	//Returns the factory at the index specified. 0 is this factory.
	public EntityFactory getFactoryFromIndex(int index)
	{
		if(index == 0)
		{
			return this;
		}
		else if(subFactory != null && index > 0)
		{
			index -= 1;
			return subFactory.getFactoryFromIndex(index);
		}
		else
		{
			return null;
		}
	}
	
	//This method takes an array of ArrayLists of EntityStatements that sets the defaultStatements attribute for all factories below this 
	//one in the list of factories.
	//IMPORTANT: If you use this to set the default statements of all attributes, make sure you put in an empty ArrayList if you want to 
	//			 skip over a level.
	//			 For instance, if you want to set the default statements of departments and not the Centre level in the Centre Business 
	//			 Plan, insert an empty ArrayList for Centre (or whatever was there before) and put that in the array before the department
	//		     statements.
	protected void setAllDefaultStatements(ArrayList<ArrayList<EntityStatement>> allDefaultStatements)
	{
		//If there are no more default statements, don't do anything.
		if(allDefaultStatements.size() > 0)
		{
			
			//Copy all elements except the first one into a new array.
			ArrayList<ArrayList<EntityStatement>> newStatements = new ArrayList<ArrayList<EntityStatement>>();
		
			for(int i = 1; i < allDefaultStatements.size(); i++)
			{
				newStatements.add(allDefaultStatements.get(i));
			}
			
			//Assign defaultStatements to the first element in the array and, if subFactory exists, recursively assign defaultStatements.
			defaultStatements = allDefaultStatements.get(0);
		
			if(subFactory != null)
			{
				subFactory.setAllDefaultStatements(newStatements);
			}
		}
	}
	
}

