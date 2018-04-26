package businessPlanTools.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import Server.*;

import java.util.*;

class VMOSAPlanFactoryTest
{

	VMOSAPlanFactory businessPlanFactory;
	EntityFactory objectiveFactory;
	EntityFactory strategyFactory;
	EntityFactory actionPlanFactory;

	@Test
	void testVMOSAPlanFactory()
	{
		businessPlanFactory = new VMOSAPlanFactory();

		// Get references to all factories for future reference
		objectiveFactory = businessPlanFactory.getSubFactory();
		strategyFactory = objectiveFactory.getSubFactory();
		actionPlanFactory = strategyFactory.getSubFactory();

		// Check the entityTitles of all factories.
		testEntityTitles();

		// Check the default statements for each factory
		testDefaultStatements();

		// Create an instance of the Centre Business Plan to test
		BusinessEntity VMOSABusinessPlan = businessPlanFactory.nextLayer(null);

		// Replace the Institutional Mission Statement
		VMOSABusinessPlan.replaceStatement("Vision Statement", "Wen is the GOAT");
		assertEquals(VMOSABusinessPlan.getStatement("Vision Statement").getStatement(), "Wen is the GOAT");
		// Check that the old entity statement is stored correctly
		EntityStatement oldStatement = VMOSABusinessPlan.getStatement("Vision Statement").getOldStatement();
		assertEquals(oldStatement.getStatement(), "Enter in a Vision Statement!");

		// Create a department, goal, SLO, and tool to ensure that they can be created
		// correctly
		BusinessEntity objective = VMOSABusinessPlan.createNewSubentity();
		assertEquals(objective.getEntityTitle(), "Objective");
		assertEquals(objective.getParentEntity(), VMOSABusinessPlan);

		BusinessEntity strategy = objective.createNewSubentity();
		assertEquals(strategy.getEntityTitle(), "Strategy");
		assertEquals(strategy.getParentEntity(), objective);
	}

	// Tests the entityTitles of each factory
	@Test
	void testEntityTitles()
	{
		assertEquals(businessPlanFactory.getEntityTitle(), "VMOSA Business Plan");
		assertEquals(objectiveFactory.getEntityTitle(), "Objective");
		assertEquals(strategyFactory.getEntityTitle(), "Strategy");
		assertEquals(actionPlanFactory.getEntityTitle(), "ActionPlan");
	}

	// Tests the default statements of each factory
	@Test
	void testDefaultStatements()
	{
		ArrayList<EntityStatement> VMOSADefault = businessPlanFactory.getDefaultStatements();
		ArrayList<EntityStatement> objectiveDefault = objectiveFactory.getDefaultStatements();
		ArrayList<EntityStatement> strategyDefault = strategyFactory.getDefaultStatements();
		ArrayList<EntityStatement> actionPlanDefault = actionPlanFactory.getDefaultStatements();

		// Test VMOSADefault
		assertEquals(VMOSADefault.size(), 2);
		assertEquals(VMOSADefault.get(0).getName(), "Vision Statement");
		assertEquals(VMOSADefault.get(0).getStatement(), "Enter in a Vision Statement!");
		assertNotEquals(VMOSADefault.get(0).getDate(), null);
		assertEquals(VMOSADefault.get(0).getOldStatement(), null);

		assertEquals(VMOSADefault.get(1).getName(), "Mission Statement");
		assertEquals(VMOSADefault.get(1).getStatement(), "Enter in a Mission Statement!");
		assertNotEquals(VMOSADefault.get(1).getDate(), null);
		assertEquals(VMOSADefault.get(1).getOldStatement(), null);

		// Test objectiveDefault
		assertEquals(objectiveDefault.size(), 1);
		assertEquals(objectiveDefault.get(0).getName(), "ObjectiveStatement");
		assertEquals(objectiveDefault.get(0).getStatement(), "Enter in a Statement for you objective!");
		assertNotEquals(objectiveDefault.get(0).getDate(), null);
		assertEquals(objectiveDefault.get(0).getOldStatement(), null);

		// Test strategyDefault
		assertEquals(strategyDefault.size(), 1);
		assertEquals(strategyDefault.get(0).getName(), "StrategyStatement");
		assertEquals(strategyDefault.get(0).getStatement(), "Enter what your Strategy is!");
		assertNotEquals(strategyDefault.get(0).getDate(), null);
		assertEquals(strategyDefault.get(0).getOldStatement(), null);

		// Test keyResultobjectiveDefault
		assertEquals(actionPlanDefault.size(), 1);
		assertEquals(actionPlanDefault.get(0).getName(), "ActionPlanStatement");
		assertEquals(actionPlanDefault.get(0).getStatement(), "Enter what your action plan is!");
		assertNotEquals(actionPlanDefault.get(0).getDate(), null);
		assertEquals(actionPlanDefault.get(0).getOldStatement(), null);
	}

}
