package Server;

public class starter
{
	/// this creates 6 plans and 2 users
	public static void main(String[] args)
	{
		Server server = new Server();
		CentrePlanFactory centreHead1 = new CentrePlanFactory();
		BusinessEntity centreplan1 = centreHead1.nextLayer(null);
		BusinessEntity mission_statement1 = centreplan1.createNewSubentity();
		BusinessEntity department_statement1 = mission_statement1.createNewSubentity();
		BusinessEntity goal1 = department_statement1.createNewSubentity();
		BusinessEntity SLO1 = goal1.createNewSubentity();
		SLO1.createNewSubentity();
		BP_Node plan1 = new BP_Node(centreplan1,2016,"Math",true);
		
		CentrePlanFactory centreHead2 = new CentrePlanFactory();
		BusinessEntity centreplan2 = centreHead2.nextLayer(null);
		BusinessEntity mission_statement2 = centreplan1.createNewSubentity();
		BusinessEntity department_statement2 = mission_statement2.createNewSubentity();
		BusinessEntity goal2 = department_statement2.createNewSubentity();
		BusinessEntity SLO2 = goal2.createNewSubentity();
		SLO2.createNewSubentity();
		BP_Node plan2 = new BP_Node(centreplan2,2015,"Math",true);
		
		CentrePlanFactory centreHead3 = new CentrePlanFactory();
		BusinessEntity centreplan3 = centreHead3.nextLayer(null);
		BusinessEntity mission_statement3 = centreplan3.createNewSubentity();
		BusinessEntity department_statement3 = mission_statement3.createNewSubentity();
		BusinessEntity goal3 = department_statement3.createNewSubentity();
		BusinessEntity SLO3 = goal3.createNewSubentity();
		SLO3.createNewSubentity();
		BP_Node plan3 = new BP_Node(centreplan3,2014,"Math",true);
		
		server.addBP_Node(plan1);
		server.addBP_Node(plan2);
		server.addBP_Node(plan3);
		
		CentrePlanFactory centreHead4 = new CentrePlanFactory();
		BusinessEntity centreplan4 = centreHead4.nextLayer(null);
		BusinessEntity mission_statement4 = centreplan4.createNewSubentity();
		BusinessEntity department_statement4 = mission_statement4.createNewSubentity();
		BusinessEntity goal4 = department_statement4.createNewSubentity();
		BusinessEntity SLO4 = goal4.createNewSubentity();
		SLO4.createNewSubentity();
		BP_Node plan4 = new BP_Node(centreplan4,2016,"Computer Science",true);
		
		CentrePlanFactory centreHead5 = new CentrePlanFactory();
		BusinessEntity centreplan5 = centreHead5.nextLayer(null);
		BusinessEntity mission_statement5 = centreplan5.createNewSubentity();
		BusinessEntity department_statement5 = mission_statement5.createNewSubentity();
		BusinessEntity goal5 = department_statement5.createNewSubentity();
		BusinessEntity SLO5 = goal5.createNewSubentity();
		SLO5.createNewSubentity();
		BP_Node plan5 = new BP_Node(centreplan5,2015,"Computer Science",true);
		
		CentrePlanFactory centreHead6 = new CentrePlanFactory();
		BusinessEntity centreplan6 = centreHead6.nextLayer(null);
		BusinessEntity mission_statement6 = centreplan6.createNewSubentity();
		BusinessEntity department_statement6 = mission_statement6.createNewSubentity();
		BusinessEntity goal6 = department_statement6.createNewSubentity();
		BusinessEntity SLO6 = goal6.createNewSubentity();
		SLO6.createNewSubentity();
		BP_Node plan6 = new BP_Node(centreplan6,2014,"Computer Science",true);
		
		server.addBP_Node(plan4);
		server.addBP_Node(plan5);
		server.addBP_Node(plan6);
		
		Person caleb = new Person("Caleb","4C","Math",true);
		Person alex = new Person("Alex" ,"Beta","Computer Science",true);
		
		plan1.addWatcher(caleb);
		plan2.addWatcher(caleb);
		plan3.addWatcher(caleb);
		System.out.println(plan1.getObservers().get(0));
		assert(plan1.getObservers().contains(caleb));
		
		
		plan4.addWatcher(alex);
		plan5.addWatcher(alex);
		plan6.addWatcher(alex);
		server.addPerson(caleb);
		server.addPerson(alex);
		
		server.writeDisk();
		server.readDisk();
		System.out.println(plan1.getObservers().get(0));

	}
}
