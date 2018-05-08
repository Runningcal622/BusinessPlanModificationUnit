package GuiTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import Client.Client;
import Server.BP_Node;
import Server.BusinessEntity;
import Server.CentrePlanFactory;
import Server.Person;
import Server.Server;
import Server.starter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import s4.Main;
import s4.ViewInterface;
import s4.CloneView.CloneViewController;
import s4.MainView.MainViewController;
import s4.NewUserView.NewUserViewController;

class CloneViewTest extends ApplicationTest implements ViewInterface
{

	Stage stage;
	BorderPane mainView;
	int calledHome;
	int calledAddUser;
	int calledEditor;
	int calledClone;
	int calledClose;
	int calledSetStatus;
	// Main main=new Main();
	Client client;
	Server server;
	BP_Node plan3;

	@Override
	public void start(Stage stage)
	{

		server = new Server();
		CentrePlanFactory centreHead1 = new CentrePlanFactory();
		BusinessEntity centreplan1 = centreHead1.nextLayer(null);
		BusinessEntity mission_statement1 = centreplan1.createNewSubentity();
		BusinessEntity department_statement1 = mission_statement1.createNewSubentity();
		BusinessEntity goal1 = department_statement1.createNewSubentity();
		BusinessEntity SLO1 = goal1.createNewSubentity();
		SLO1.createNewSubentity();
		BP_Node plan1 = new BP_Node(centreplan1, 2016, "Math", true);

		CentrePlanFactory centreHead2 = new CentrePlanFactory();
		BusinessEntity centreplan2 = centreHead2.nextLayer(null);
		BusinessEntity mission_statement2 = centreplan1.createNewSubentity();
		BusinessEntity department_statement2 = mission_statement2.createNewSubentity();
		BusinessEntity goal2 = department_statement2.createNewSubentity();
		BusinessEntity SLO2 = goal2.createNewSubentity();
		SLO2.createNewSubentity();
		BP_Node plan2 = new BP_Node(centreplan2, 2015, "Math", true);

		CentrePlanFactory centreHead3 = new CentrePlanFactory();
		BusinessEntity centreplan3 = centreHead3.nextLayer(null);
		BusinessEntity mission_statement3 = centreplan3.createNewSubentity();
		BusinessEntity department_statement3 = mission_statement3.createNewSubentity();
		BusinessEntity goal3 = department_statement3.createNewSubentity();
		BusinessEntity SLO3 = goal3.createNewSubentity();
		SLO3.createNewSubentity();
		plan3 = new BP_Node(centreplan3, 2014, "Math", true);

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
		BP_Node plan4 = new BP_Node(centreplan4, 2016, "Computer Science", true);

		CentrePlanFactory centreHead5 = new CentrePlanFactory();
		BusinessEntity centreplan5 = centreHead5.nextLayer(null);
		BusinessEntity mission_statement5 = centreplan5.createNewSubentity();
		BusinessEntity department_statement5 = mission_statement5.createNewSubentity();
		BusinessEntity goal5 = department_statement5.createNewSubentity();
		BusinessEntity SLO5 = goal5.createNewSubentity();
		SLO5.createNewSubentity();
		BP_Node plan5 = new BP_Node(centreplan5, 2015, "Computer Science", true);

		CentrePlanFactory centreHead6 = new CentrePlanFactory();
		BusinessEntity centreplan6 = centreHead6.nextLayer(null);
		BusinessEntity mission_statement6 = centreplan6.createNewSubentity();
		BusinessEntity department_statement6 = mission_statement6.createNewSubentity();
		BusinessEntity goal6 = department_statement6.createNewSubentity();
		BusinessEntity SLO6 = goal6.createNewSubentity();
		SLO6.createNewSubentity();
		BP_Node plan6 = new BP_Node(centreplan6, 2014, "Computer Science", true);

		server.addBP_Node(plan4);
		server.addBP_Node(plan5);
		server.addBP_Node(plan6);

		Person caleb = new Person("Caleb", "4C", "Math", true);
		Person alex = new Person("Alex", "Beta", "Computer Science", true);

		server.addPerson(caleb);
		server.addPerson(alex);

		server.writeDisk();
		this.stage = stage;

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("CloneView/CloneView.fxml"));
		try
		{
			mainView = loader.load();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client = new Client(server);
		client.login("Caleb", "4C");
		// BP_Node plan3 = new BP_Node(centreplan3,2014,"Math",true);
		CloneViewController cont = loader.getController();
		ArrayList<BP_Node> dep_plans = new ArrayList<BP_Node>();
		List<BP_Node> allPlans = client.getBP();
		for (BP_Node node : allPlans)
		{
			dep_plans.add(node);
		}
		cont.setMain(this, client, plan3, dep_plans, true);
		Scene s = new Scene(mainView);
		stage.setScene(s);
		stage.show();

	}

	@Before
	public void initMocks()
	{
		calledHome = 0;
		calledAddUser = 0;
		calledEditor = 0;
		calledClone = 0;
		calledSetStatus = 0;
		calledClose = 0;
	}

	@Test
	void testClone()
	{
		clickOn("#newYearField");
		write("2019");
		clickOn("#statusBox").clickOn("#statusBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
		sleep(1000);
		clickOn("#okButton");
		assertEquals(calledHome, 1);
		assertEquals(calledAddUser, 0);
		assertEquals(calledEditor, 0);
		assertEquals(calledClone, 0);
		assertEquals(calledClose, 0);
		calledHome = 0;
		assertEquals(2019, server.getBP_Node(client.person.department, 2019).year);
		// assertEquals(true,server.getBP_Node(client.person.department,
		// 2019).editable);
		assertEquals(client.person.department, server.getBP_Node(client.person.department, 2019).department);
		assertEquals(true, server.getBP_Node(client.person.department, 2019).isEditable());
		assertEquals(server.getBP_Node(client.person.department, 2019).department, plan3.department);
		assertEquals(server.getBP_Node(client.person.department, 2019).isEditable(), plan3.isEditable());
		assertEquals(server.getBP_Node(client.person.department, 2019).entity.getEntityTitle(), plan3.entity.getEntityTitle());
		assertEquals(server.getBP_Node(client.person.department, 2019).entity.getSubentityFactory().getEntityTitle(), plan3.entity.getSubentityFactory().getEntityTitle());
	}

	@Test
	void testFailedClone1()
	{
		moveTo("#okButton");
		clickOn("#okButton");
		assertEquals(calledHome, 0);
		assertEquals(calledAddUser, 0);
		assertEquals(calledEditor, 0);
		assertEquals(calledClone, 0);
		assertEquals(calledClose, 0);
		calledHome = 0;
	}

	@Test
	void testFailedClone()
	{
		clickOn("#newYearField");
		clickOn("#statusBox").clickOn("#statusBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
		moveTo("#okButton");
		clickOn("#okButton");
		assertEquals(calledHome, 0);
		assertEquals(calledAddUser, 0);
		assertEquals(calledEditor, 0);
		assertEquals(calledClone, 0);
		assertEquals(calledClose, 0);
		calledHome = 0;
	}

	@Test
	void testCancel()
	{
		clickOn("#cancelButton");
		assertEquals(calledHome, 1);
		assertEquals(calledAddUser, 0);
		assertEquals(calledEditor, 0);
		assertEquals(calledClone, 0);
		assertEquals(calledClose, 0);
		calledHome = 0;
	}

	@Override
	public void showEdit(Client client)
	{
		// TODO Auto-generated method stub
		calledEditor++;
	}

	@Override
	public void showLogin()
	{
		// TODO Auto-generated method stub
		calledHome++;
	}

	@Override
	public void showHome(Client client)
	{
		// TODO Auto-generated method stub
		calledHome++;
	}

	@Override
	public void showNewUser(Client client)
	{
		// TODO Auto-generated method stub
		calledAddUser++;
	}

	@Override
	public void showClone(BP_Node node, Client client, ArrayList<BP_Node> dep_plans, boolean new_plan)
	{
		// TODO Auto-generated method stub
		calledClone++;
	}

	@Override
	public void showSetStatus(Client client, int year)
	{
		// TODO Auto-generated method stub
		calledSetStatus++;
	}

	@Override
	public void showSeeUser(Client client)
	{
		// TODO Auto-generated method stub
		
	}

}
