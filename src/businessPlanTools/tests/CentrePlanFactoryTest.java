package businessPlanTools.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import Server.*;

import java.util.*;

class CentrePlanFactoryTest
{

	CentrePlanFactory businessPlanFactory;
	EntityFactory departmentFactory;
	EntityFactory goalFactory;
	EntityFactory SLOFactory;
	EntityFactory toolFactory;

	@Test
	void testCentrePlanFactory()
	{
		businessPlanFactory = new CentrePlanFactory();

		// Get references to all factories for future reference
		departmentFactory = businessPlanFactory.getSubFactory();
		goalFactory = departmentFactory.getSubFactory();
		SLOFactory = goalFactory.getSubFactory();
		toolFactory = SLOFactory.getSubFactory();

		// Check the entityTitles of all factories.
		testEntityTitles();

		// Check the default statements for each factory
		testDefaultStatements();

		// Create an instance of the Centre Business Plan to test
		BusinessEntity centreBusinessPlan = businessPlanFactory.nextLayer(null);

		// Replace the Institutional Mission Statement
		centreBusinessPlan.replaceStatement("Institutional Mission Statement", "Wen is the GOAT");
		assertEquals(centreBusinessPlan.getStatement("Institutional Mission Statement").getStatement(),
				"Wen is the GOAT");
		// Check that the old entity statement is stored correctly
		EntityStatement oldStatement = centreBusinessPlan.getStatement("Institutional Mission Statement")
				.getOldStatement();
		assertEquals(oldStatement.getStatement(), "Enter in an Institutional Mission Statement!");

		// Create a department, goal, SLO, and tool to ensure that they can be created
		// correctly
		BusinessEntity department = centreBusinessPlan.createNewSubentity();
		assertEquals(department.getEntityTitle(), "Department");
		assertEquals(department.getParentEntity(), centreBusinessPlan);

		BusinessEntity goal = department.createNewSubentity();
		assertEquals(goal.getEntityTitle(), "Goal");
		assertEquals(goal.getParentEntity(), department);
	}

	// Tests the entityTitles of each factory
	@Test
	void testEntityTitles()
	{
		assertEquals(businessPlanFactory.getEntityTitle(), "Centre Business Plan");
		assertEquals(departmentFactory.getEntityTitle(), "Department");
		assertEquals(goalFactory.getEntityTitle(), "Goal");
		assertEquals(SLOFactory.getEntityTitle(), "SLO");
		assertEquals(toolFactory.getEntityTitle(), "Tool");
	}

	// Tests the default statements of each factory
	@Test
	void testDefaultStatements()
	{
		ArrayList<EntityStatement> centreDefault = businessPlanFactory.getDefaultStatements();
		ArrayList<EntityStatement> departmentDefault = departmentFactory.getDefaultStatements();
		ArrayList<EntityStatement> goalDefault = goalFactory.getDefaultStatements();
		ArrayList<EntityStatement> SLODefault = SLOFactory.getDefaultStatements();
		ArrayList<EntityStatement> toolDefault = toolFactory.getDefaultStatements();

		// Test centreDefault
		assertEquals(centreDefault.size(), 1);
		assertEquals(centreDefault.get(0).getName(), "Institutional Mission Statement");
		assertEquals(centreDefault.get(0).getStatement(), "Enter in an Institutional Mission Statement!");
		assertNotEquals(centreDefault.get(0).getDate(), null);
		assertEquals(centreDefault.get(0).getOldStatement(), null);

		// Test departmentDefault
		assertEquals(departmentDefault.size(), 2);
		assertEquals(departmentDefault.get(0).getName(), "DepartmentName");
		assertEquals(departmentDefault.get(0).getStatement(), "Enter in a Name for your Department!");
		assertNotEquals(departmentDefault.get(0).getDate(), null);
		assertEquals(departmentDefault.get(0).getOldStatement(), null);

		assertEquals(departmentDefault.get(1).getName(), "DepartmentMissionStatement");
		assertEquals(departmentDefault.get(1).getStatement(), "Enter in a Mission Statement for you Department!");
		assertNotEquals(departmentDefault.get(1).getDate(), null);
		assertEquals(departmentDefault.get(1).getOldStatement(), null);

		// Test goalDefault
		assertEquals(goalDefault.size(), 1);
		assertEquals(goalDefault.get(0).getName(), "GoalStatement");
		assertEquals(goalDefault.get(0).getStatement(), "Enter what your Goal is!");
		assertNotEquals(goalDefault.get(0).getDate(), null);
		assertEquals(goalDefault.get(0).getOldStatement(), null);

		// Test SLODefault
		assertEquals(SLODefault.size(), 1);
		assertEquals(SLODefault.get(0).getName(), "SLOStatement");
		assertEquals(SLODefault.get(0).getStatement(), "Enter what your Student Learning Objective is!");
		assertNotEquals(SLODefault.get(0).getDate(), null);
		assertEquals(SLODefault.get(0).getOldStatement(), null);

		// Test departmentDefault
		assertEquals(toolDefault.size(), 3);
		assertEquals(toolDefault.get(0).getName(), "ToolDescription");
		assertEquals(toolDefault.get(0).getStatement(), "Enter a description for your tool!");
		assertNotEquals(toolDefault.get(0).getDate(), null);
		assertEquals(toolDefault.get(0).getOldStatement(), null);

		assertEquals(toolDefault.get(1).getName(), "ToolTarget");
		assertEquals(toolDefault.get(1).getStatement(), "Enter the target of your tool!");
		assertNotEquals(toolDefault.get(1).getDate(), null);
		assertEquals(toolDefault.get(1).getOldStatement(), null);

		assertEquals(toolDefault.get(2).getName(), "ToolResult");
		assertEquals(toolDefault.get(2).getStatement(), "Enter what the result from your tool will be!");
		assertNotEquals(toolDefault.get(2).getDate(), null);
		assertEquals(toolDefault.get(2).getOldStatement(), null);
	}

}
