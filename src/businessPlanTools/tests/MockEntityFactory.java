package businessPlanTools.tests;

import java.util.ArrayList;

import Server.EntityFactory;
import Server.EntityStatement;

//This exists purely so that we can test the purely the EntityFactory
public class MockEntityFactory extends EntityFactory
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5414430206612594817L;

	public MockEntityFactory(String[] layers)
	{
		super(layers);
	}

	public MockEntityFactory(EntityFactory subFactory, String entityTitle)
	{
		super(subFactory, entityTitle);
	}

	// Make protected methods visible to outside classes
	@Override
	public void setDefaultStatements(ArrayList<EntityStatement> defaultStatements)
	{
		this.defaultStatements = defaultStatements;
	}

	@Override
	public void setEntityTitle(String entityTitle)
	{
		this.entityTitle = entityTitle;
	}

	@Override
	public EntityFactory getFactoryFromIndex(int index)
	{
		return super.getFactoryFromIndex(index);
	}

	@Override
	public void setAllDefaultStatements(ArrayList<ArrayList<EntityStatement>> allDefaultStatements)
	{
		super.setAllDefaultStatements(allDefaultStatements);
	}
}
