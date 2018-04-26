package Server;

import java.util.*;
import java.io.Serializable;

public class CentrePlanFactory extends EntityFactory implements Serializable
{

	private static final long serialVersionUID = 2L;

	public CentrePlanFactory()
	{
		// Construct the Entity Factory
		super(new String[]
		{ "Centre Plan Title", "Centre Mission Statement", "Department", "Goal", "SLO", "Tool" });

		// Construct the default statements for each factory
		ArrayList<EntityStatement> defaultTitleStatement = new ArrayList<EntityStatement>();
		defaultTitleStatement.add(new EntityStatement("Plan Title",
				"Enter description of the plan.", new Date(), null));

		ArrayList<EntityStatement>defaultCentreStatement = new ArrayList<EntityStatement>();
		defaultCentreStatement.add(new EntityStatement("Institutional Mission Statement",
				"Enter in an Institutional Mission Statement!", new Date(), null));

		ArrayList<EntityStatement> defaultDepartmentStatement = new ArrayList<EntityStatement>();
		defaultDepartmentStatement
				.add(new EntityStatement("DepartmentName", "Enter in a Name for your Department!", new Date(), null));
		defaultDepartmentStatement.add(new EntityStatement("DepartmentMissionStatement",
				"Enter in a Mission Statement for you Department!", new Date(), null));

		ArrayList<EntityStatement> defaultGoalStatement = new ArrayList<EntityStatement>();
		defaultGoalStatement.add(new EntityStatement("GoalStatement", "Enter what your Goal is!", new Date(), null));

		ArrayList<EntityStatement> defaultSLOStatement = new ArrayList<EntityStatement>();
		defaultSLOStatement.add(new EntityStatement("SLOStatement", "Enter what your Student Learning Objective is!",
				new Date(), null));

		ArrayList<EntityStatement> defaultToolStatement = new ArrayList<EntityStatement>();
		defaultToolStatement
				.add(new EntityStatement("ToolDescription", "Enter a description for your tool!", new Date(), null));

		defaultToolStatement.add(new EntityStatement("ToolTarget", "Enter the target of your tool!", new Date(), null));

		defaultToolStatement.add(
				new EntityStatement("ToolResult", "Enter what the result from your tool will be!", new Date(), null));

		// Store the default statements in an array
		ArrayList<ArrayList<EntityStatement>> newDefaultStatements = new ArrayList<ArrayList<EntityStatement>>();
		newDefaultStatements.add(defaultTitleStatement);
		newDefaultStatements.add(defaultCentreStatement);
		newDefaultStatements.add(defaultDepartmentStatement);
		newDefaultStatements.add(defaultGoalStatement);
		newDefaultStatements.add(defaultSLOStatement);
		newDefaultStatements.add(defaultToolStatement);

		// Set the Default statements of every factory
		setAllDefaultStatements(newDefaultStatements);
	}

}
