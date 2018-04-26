package businessPlanTools.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import Server.*;

public class MainTest
{
	public static void main(String[] args)
	{
		// Test the EntityFactory class
		CentrePlanFactoryTest centreTest = new CentrePlanFactoryTest();
		centreTest.testCentrePlanFactory();

		// Test each business plan
		OKRBusinessPlanFactoryTest OKRTest = new OKRBusinessPlanFactoryTest();
		OKRTest.testOKRBusinessPlanFactory();

		VMOSAPlanFactoryTest vmosaTest = new VMOSAPlanFactoryTest();
		vmosaTest.testVMOSAPlanFactory();

		EntityFactoryTest factoryTest = new EntityFactoryTest();
		factoryTest.test();

		MainTest mainTest = new MainTest();
		mainTest.testSerializability();

	}

	@Test
	public void testSerializability()
	{

		String fileName = "CentrePlan.xml";

		Encoder encoder = new Encoder(fileName);
		Decoder decoder = new Decoder(fileName);

		CentrePlanFactory businessPlanFactory = new CentrePlanFactory();
		BusinessEntity centrePlan = businessPlanFactory.nextLayer(null);
		BusinessEntity department = centrePlan.createNewSubentity();
		department.getStatement("DepartmentName").setStatement("CS Department");
		BusinessEntity goal = department.createNewSubentity();
		goal.getStatement("GoalStatement").setStatement("Do a CS.");
		BusinessEntity SLO = goal.createNewSubentity();
		BusinessEntity tool = SLO.createNewSubentity();

		try
		{
			encoder.serialize(centrePlan);
		} catch (Exception e)
		{

		}

		BusinessEntity deserializedPlan = decoder.deserialize();
		BusinessEntity deserializedDepartment = deserializedPlan.getSubentity(0);
		BusinessEntity deserializedGoal = deserializedDepartment.getSubentity(0);
		BusinessEntity deserializedSLO = deserializedGoal.getSubentity(0);
		BusinessEntity deserializedTool = deserializedSLO.getSubentity(0);

		// Ensure that the entityTitle, subentityFactory, statements, subentities, and
		// parentEntity are all the same when saved and loaded
		assertEquals(centrePlan.getEntityTitle(), deserializedPlan.getEntityTitle());
		assertEquals(centrePlan.getSubentityFactory().getEntityTitle(),
				deserializedPlan.getSubentityFactory().getEntityTitle());
		assertEquals(centrePlan.getStatement(0).getName(), deserializedPlan.getStatement(0).getName());
		assertEquals(centrePlan.getStatement(0).getStatement(), deserializedPlan.getStatement(0).getStatement());
		assertEquals(centrePlan.getStatement(0).getOldStatement(), deserializedPlan.getStatement(0).getOldStatement());
		assertEquals(centrePlan.getSubentity(0).getEntityTitle(), deserializedPlan.getSubentity(0).getEntityTitle());
		assertEquals(centrePlan.getSubentities().size(), deserializedPlan.getSubentities().size());
		assertEquals(centrePlan.getParentEntity(), centrePlan.getParentEntity());

		// Ensure that subentities maintain the proper parentEntity
		assertEquals(department.getParentEntity().getEntityTitle(),
				deserializedDepartment.getParentEntity().getEntityTitle());
		assertEquals(goal.getParentEntity().getEntityTitle(), deserializedGoal.getParentEntity().getEntityTitle());
		assertEquals(SLO.getParentEntity().getEntityTitle(), deserializedSLO.getParentEntity().getEntityTitle());
		assertEquals(tool.getParentEntity().getEntityTitle(), deserializedTool.getParentEntity().getEntityTitle());

		// Explicitly ensure that the overall structure of the tree is the same
		assertEquals(department.getEntityTitle(), deserializedDepartment.getEntityTitle());
		assertEquals(goal.getEntityTitle(), deserializedGoal.getEntityTitle());
		assertEquals(SLO.getEntityTitle(), deserializedSLO.getEntityTitle());
		assertEquals(tool.getEntityTitle(), deserializedTool.getEntityTitle());

	}
}