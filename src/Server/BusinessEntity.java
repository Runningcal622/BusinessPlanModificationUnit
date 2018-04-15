package Server;

import java.util.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.Serializable;

public class BusinessEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	String entityTitle;
	ArrayList<EntityStatement> statements;
	ArrayList<BusinessEntity> subentities;
	BusinessEntity parentEntity;
	int tree_level;
	int component_number;
	// This must be public for serialization to work
	public EntityFactory subentityFactory;

	public BusinessEntity(String entityTitle, ArrayList<EntityStatement> statements,
			ArrayList<BusinessEntity> subentities, BusinessEntity parentEntity, EntityFactory subentityFactory)
	{
		super();
		this.entityTitle = entityTitle;
		this.statements = statements;
		this.subentities = subentities;
		this.parentEntity = parentEntity;
		this.subentityFactory = subentityFactory;
		tree_level = 0;
		component_number = 0;
	}

	// Need a default constructor for serialization
	public BusinessEntity()
	{
	}

	// Returns a new BusinessEntity, which has been added as a child to this node
	// and is stored in the "subentites" attribute
	public BusinessEntity createNewSubentity()
	{
		BusinessEntity newEntity = subentityFactory.nextLayer(this);
		newEntity.component_number = newEntity.parentEntity.getSubentities().size();
		newEntity.tree_level = newEntity.parentEntity.tree_level+1;
		subentities.add(newEntity);
		return newEntity;
	}

	public String getEntityTitle()
	{
		return entityTitle;
	}

	public void setEntityTitle(String entityTitle)
	{
		this.entityTitle = entityTitle;
	}

	public ArrayList<EntityStatement> getStatements()
	{
		return statements;
	}

	public void setStatements(ArrayList<EntityStatement> statements)
	{
		this.statements = statements;
	}

	public ArrayList<BusinessEntity> getSubentities()
	{
		return subentities;
	}

	public void setSubentities(ArrayList<BusinessEntity> subentities)
	{
		this.subentities = subentities;
	}

	public BusinessEntity getParentEntity()
	{
		return parentEntity;
	}

	public void setParentEntity(BusinessEntity parentEntity)
	{
		this.parentEntity = parentEntity;
	}

	public EntityFactory getSubentityFactory()
	{
		return subentityFactory;
	}

	public void setEntityFactory(EntityFactory subFactory)
	{
		this.subentityFactory = subFactory;
	}
	
	/**
	 * @return the tree_level
	 */
	public int getTree_level()
	{
		return tree_level;
	}

	/**
	 * @param tree_level the tree_level to set
	 */
	public void setTree_level(int tree_level)
	{
		this.tree_level = tree_level;
	}

	/**
	 * @return the component_number
	 */
	public int getComponent_number()
	{
		return component_number;
	}

	/**
	 * @param component_number the component_number to set
	 */
	public void setComponent_number(int component_number)
	{
		this.component_number = component_number;
	}
	
	public int getTreeItemID()
	{
		String id_string = Integer.toString(tree_level) + Integer.toString(component_number);
		System.out.println(tree_level);
		System.out.println(component_number);
		return Integer.parseInt(id_string);
	}

	// Returns the EntityStatement at the specified index. The index is determined
	// by the order in which EntityStatements were added.
	public EntityStatement getStatement(int index)
	{
		if (index < statements.size() && index >= 0)
		{
			return statements.get(index);
		}

		else
		{
			return null;
		}
	}

	// Retrieves and returns the EntityStatement with the given title
	public EntityStatement getStatement(String title)
	{
		EntityStatement statement = null;

		for (EntityStatement currStatement : statements)
		{
			if (currStatement.getName().equals(title))
			{
				statement = currStatement;
				break;
			}
		}

		return statement;
	}

	// Returns the subentity at the index provided in the ArrayList of subentities.
	// This list is sorted by the order in which entities were created
	public BusinessEntity getSubentity(int index)
	{
		if (index < subentities.size() && index >= 0)
		{
			return subentities.get(index);
		} else
		{
			return null;
		}
	}

	// Takes an old entityStatement that is currently in statements and replaces
	// that with a new entityStatement that is given
	// The old statement is set to be the "oldStatement" attribute in the new
	// entityStatement so it can be referenced later.
	// This creates a chronologically ordered linked list of statements.
	public void replaceStatement(EntityStatement oldStatement, EntityStatement newStatement)
	{
		newStatement.setOldStatement(oldStatement);

		// Look through statements for the old statement
		for (EntityStatement statement : statements)
		{
			if (statement == oldStatement)
			{
				// Replace the reference of the old statement with the new one
				statement = newStatement;
				break;
			}
		}
	}

	// Replaces the current entityStatement that has the given title with the new
	// entityStatement
	public void replaceStatement(String oldTitle, EntityStatement newStatement)
	{
		EntityStatement oldStatement = getStatement(oldTitle);

		newStatement.setOldStatement(oldStatement);

		// Look through statements for the old statement
		EntityStatement statement;

		for (int i = 0; i < statements.size(); i++)
		{
			statement = statements.get(i);
			if (statement == oldStatement)
			{
				// Replace the reference of the old statement with the new one
				statements.set(i, newStatement);
				break;
			}
		}
	}

	// Takes a string that is the title of the EntityStatement to be replaced and
	// creates a new EntityStatement using the
	// second given string, and then replaces the old EntityStatement.
	public void replaceStatement(String oldTitle, String newStatementString)
	{
		EntityStatement oldStatement = getStatement(oldTitle);

		EntityStatement newStatement = new EntityStatement(oldTitle, newStatementString, new Date(), oldStatement);

		// Look through statements for the old statement
		EntityStatement statement;

		for (int i = 0; i < statements.size(); i++)
		{
			statement = statements.get(i);
			if (statement == oldStatement)
			{
				// Replace the reference of the old statement with the new one
				statements.set(i, newStatement);
				break;
			}
		}

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return entityTitle;
	}
	
	

}
