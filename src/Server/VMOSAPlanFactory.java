package Server;

import java.util.*;
import java.io.Serializable;

public class VMOSAPlanFactory extends EntityFactory implements Serializable
{

	private static final long serialVersionUID = 6L;

	public VMOSAPlanFactory()
	{
		// Construct the Entity Factory
		super(new String[] { "VMOSA Business Plan", "Objective", "Strategy", "ActionPlan" });

		// Construct the default statements for each factory
		ArrayList<EntityStatement> defaultVMOSAStatement = new ArrayList<EntityStatement>();

		defaultVMOSAStatement
				.add(new EntityStatement("Vision Statement", "Enter in a Vision Statement!", new Date(), null));

		defaultVMOSAStatement
				.add(new EntityStatement("Mission Statement", "Enter in a Mission Statement!", new Date(), null));

		ArrayList<EntityStatement> defaultObjectiveStatement = new ArrayList<EntityStatement>();
		defaultObjectiveStatement.add(
				new EntityStatement("ObjectiveStatement", "Enter in a Statement for you objective!", new Date(), null));

		ArrayList<EntityStatement> defaultStrategyStatement = new ArrayList<EntityStatement>();
		defaultStrategyStatement
				.add(new EntityStatement("StrategyStatement", "Enter what your Strategy is!", new Date(), null));

		ArrayList<EntityStatement> defaultActionPlanStatement = new ArrayList<EntityStatement>();
		defaultActionPlanStatement
				.add(new EntityStatement("ActionPlanStatement", "Enter what your action plan is!", new Date(), null));

		// Store the default statements in an array
		ArrayList<ArrayList<EntityStatement>> newDefaultStatements = new ArrayList<ArrayList<EntityStatement>>();
		newDefaultStatements.add(defaultVMOSAStatement);
		newDefaultStatements.add(defaultObjectiveStatement);
		newDefaultStatements.add(defaultStrategyStatement);
		newDefaultStatements.add(defaultActionPlanStatement);

		// Set the Default statements of every factory
		setAllDefaultStatements(newDefaultStatements);
	}

}
