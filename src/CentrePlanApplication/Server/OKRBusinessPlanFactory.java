package CentrePlanApplication.Server;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

public class OKRBusinessPlanFactory extends EntityFactory implements Serializable
{

	private static final long serialVersionUID = 5L;

	public OKRBusinessPlanFactory()
	{
		// Construct the factories
		super(new String[] { "OKR Business Plan", "Long Term Goal", "Short Term Goal", "Objective", "Key Result" });

		// Construct the default statements
		ArrayList<EntityStatement> defaultOKRStatement = new ArrayList<EntityStatement>();
		defaultOKRStatement
				.add(new EntityStatement("Vision Statement", "Enter in a Vision Statement!", new Date(), null));
		defaultOKRStatement
				.add(new EntityStatement("Mission Statement", "Enter in a Mission Statement!", new Date(), null));

		ArrayList<EntityStatement> defaultLongTermGoalStatement = new ArrayList<EntityStatement>();
		defaultLongTermGoalStatement
				.add(new EntityStatement("Long Term Goal", "Enter in a Long Term Goal!", new Date(), null));

		ArrayList<EntityStatement> defaultShortTermGoalStatement = new ArrayList<EntityStatement>();
		defaultShortTermGoalStatement
				.add(new EntityStatement("Short Term Goal", "Enter in a Short Term Goal!", new Date(), null));

		ArrayList<EntityStatement> defaultObjectiveStatement = new ArrayList<EntityStatement>();
		defaultObjectiveStatement
				.add(new EntityStatement("Objective", "Enter what your Objective is!", new Date(), null));

		ArrayList<EntityStatement> defaultKeyResultStatement = new ArrayList<EntityStatement>();
		defaultKeyResultStatement
				.add(new EntityStatement("Key Result", "Enter what your Key Result is!", new Date(), null));

		// Store the default statements in an array
		ArrayList<ArrayList<EntityStatement>> newDefaultStatements = new ArrayList<ArrayList<EntityStatement>>();
		newDefaultStatements.add(defaultOKRStatement);
		newDefaultStatements.add(defaultLongTermGoalStatement);
		newDefaultStatements.add(defaultShortTermGoalStatement);
		newDefaultStatements.add(defaultObjectiveStatement);
		newDefaultStatements.add(defaultKeyResultStatement);

		// Set the default statements of all of the factories
		setAllDefaultStatements(newDefaultStatements);
	}
}
