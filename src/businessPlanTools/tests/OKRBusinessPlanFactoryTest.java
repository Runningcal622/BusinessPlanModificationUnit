package businessPlanTools.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import Server.*;

import java.util.*;

class OKRBusinessPlanFactoryTest
{

	OKRBusinessPlanFactory businessPlanFactory;
	EntityFactory longTermGoalFactory;
	EntityFactory shortTermGoalFactory;
	EntityFactory objectiveFactory;
	EntityFactory keyResultFactory;

	@Test
	void testOKRBusinessPlanFactory()
	{
		businessPlanFactory = new OKRBusinessPlanFactory();

		// Get references to all factories for future reference
		longTermGoalFactory = businessPlanFactory.getSubFactory();
		shortTermGoalFactory = longTermGoalFactory.getSubFactory();
		objectiveFactory = shortTermGoalFactory.getSubFactory();
		keyResultFactory = objectiveFactory.getSubFactory();

		// Check the entityTitles of all factories.
		testEntityTitles();

		// Check the default statements for each factory
		testDefaultStatements();

		// Create an instance of the Centre Business Plan to test
		BusinessEntity OKRBusinessPlan = businessPlanFactory.nextLayer(null);

		// Replace the Institutional Mission Statement
		OKRBusinessPlan.replaceStatement("Vision Statement", "Wen is the GOAT");
		assertEquals(OKRBusinessPlan.getStatement("Vision Statement").getStatement(), "Wen is the GOAT");
		// Check that the old entity statement is stored correctly
		EntityStatement oldStatement = OKRBusinessPlan.getStatement("Vision Statement").getOldStatement();
		assertEquals(oldStatement.getStatement(), "Enter in a Vision Statement!");

		// Create a department, goal, SLO, and tool to ensure that they can be created
		// correctly
		BusinessEntity department = OKRBusinessPlan.createNewSubentity();
		assertEquals(department.getEntityTitle(), "Long Term Goal");
		assertEquals(department.getParentEntity(), OKRBusinessPlan);

		BusinessEntity goal = department.createNewSubentity();
		assertEquals(goal.getEntityTitle(), "Short Term Goal");
		assertEquals(goal.getParentEntity(), department);
	}

	// Tests the entityTitles of each factory
	@Test
	void testEntityTitles()
	{
		assertEquals(businessPlanFactory.getEntityTitle(), "OKR Business Plan");
		assertEquals(longTermGoalFactory.getEntityTitle(), "Long Term Goal");
		assertEquals(shortTermGoalFactory.getEntityTitle(), "Short Term Goal");
		assertEquals(objectiveFactory.getEntityTitle(), "Objective");
		assertEquals(keyResultFactory.getEntityTitle(), "Key Result");
	}

	// Tests the default statements of each factory
	@Test
	void testDefaultStatements()
	{
		ArrayList<EntityStatement> OKRDefault = businessPlanFactory.getDefaultStatements();
		ArrayList<EntityStatement> longTermDefault = longTermGoalFactory.getDefaultStatements();
		ArrayList<EntityStatement> shortTermDefault = shortTermGoalFactory.getDefaultStatements();
		ArrayList<EntityStatement> objectiveDefault = objectiveFactory.getDefaultStatements();
		ArrayList<EntityStatement> keyResultDefault = keyResultFactory.getDefaultStatements();

		// Test OKRDefault
		assertEquals(OKRDefault.size(), 2);
		assertEquals(OKRDefault.get(0).getName(), "Vision Statement");
		assertEquals(OKRDefault.get(0).getStatement(), "Enter in a Vision Statement!");
		assertNotEquals(OKRDefault.get(0).getDate(), null);
		assertEquals(OKRDefault.get(0).getOldStatement(), null);

		assertEquals(OKRDefault.get(1).getName(), "Mission Statement");
		assertEquals(OKRDefault.get(1).getStatement(), "Enter in a Mission Statement!");
		assertNotEquals(OKRDefault.get(1).getDate(), null);
		assertEquals(OKRDefault.get(1).getOldStatement(), null);

		// Test longTermDefault
		assertEquals(longTermDefault.size(), 1);
		assertEquals(longTermDefault.get(0).getName(), "Long Term Goal");
		assertEquals(longTermDefault.get(0).getStatement(), "Enter in a Long Term Goal!");
		assertNotEquals(longTermDefault.get(0).getDate(), null);
		assertEquals(longTermDefault.get(0).getOldStatement(), null);

		// Test shortTermDefault
		assertEquals(shortTermDefault.size(), 1);
		assertEquals(shortTermDefault.get(0).getName(), "Short Term Goal");
		assertEquals(shortTermDefault.get(0).getStatement(), "Enter in a Short Term Goal!");
		assertNotEquals(shortTermDefault.get(0).getDate(), null);
		assertEquals(shortTermDefault.get(0).getOldStatement(), null);

		// Test objectiveDefault
		assertEquals(objectiveDefault.size(), 1);
		assertEquals(objectiveDefault.get(0).getName(), "Objective");
		assertEquals(objectiveDefault.get(0).getStatement(), "Enter what your Objective is!");
		assertNotEquals(objectiveDefault.get(0).getDate(), null);
		assertEquals(objectiveDefault.get(0).getOldStatement(), null);

		// Test keyResultDefault
		assertEquals(keyResultDefault.size(), 1);
		assertEquals(keyResultDefault.get(0).getName(), "Key Result");
		assertEquals(keyResultDefault.get(0).getStatement(), "Enter what your Key Result is!");
		assertNotEquals(keyResultDefault.get(0).getDate(), null);
		assertEquals(keyResultDefault.get(0).getOldStatement(), null);
	}

}
