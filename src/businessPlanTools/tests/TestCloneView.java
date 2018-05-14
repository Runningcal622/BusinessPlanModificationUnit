package businessPlanTools.tests;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.ComboBoxMatchers;

import Client.Client;
import Server.BP_Node;
import Server.Server;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import s4.Main;
import s4.CloneView.CloneViewController;
import s4.HomeView.HomeViewController;
import s4.MainView.MainViewController;

public class TestCloneView<T> extends ApplicationTest
{

	BorderPane viewGoesHere = new BorderPane();
	ComboBox<Integer> plans;
	public Stage stage;
	Server server;
	ArrayList<BP_Node> dep_plans;
	Client client;

	@Start
	public void start(Stage stage)
	{
		//
		// server = new Server();
		// CentrePlanFactory centreHead1 = new CentrePlanFactory();
		// BusinessEntity centreplan1 = centreHead1.nextLayer(null);
		// BusinessEntity mission_statement1 = centreplan1.createNewSubentity();
		// BusinessEntity department_statement1 =
		// mission_statement1.createNewSubentity();
		// BusinessEntity goal1 = department_statement1.createNewSubentity();
		// BusinessEntity SLO1 = goal1.createNewSubentity();
		// SLO1.createNewSubentity();
		// BP_Node plan1 = new BP_Node(centreplan1, 2016, "Math", true);
		//
		// CentrePlanFactory centreHead2 = new CentrePlanFactory();
		// BusinessEntity centreplan2 = centreHead2.nextLayer(null);
		// BusinessEntity mission_statement2 = centreplan1.createNewSubentity();
		// BusinessEntity department_statement2 =
		// mission_statement2.createNewSubentity();
		// BusinessEntity goal2 = department_statement2.createNewSubentity();
		// BusinessEntity SLO2 = goal2.createNewSubentity();
		// SLO2.createNewSubentity();
		// BP_Node plan2 = new BP_Node(centreplan2, 2015, "Math", true);
		//
		// CentrePlanFactory centreHead3 = new CentrePlanFactory();
		// BusinessEntity centreplan3 = centreHead3.nextLayer(null);
		// BusinessEntity mission_statement3 = centreplan3.createNewSubentity();
		// BusinessEntity department_statement3 =
		// mission_statement3.createNewSubentity();
		// BusinessEntity goal3 = department_statement3.createNewSubentity();
		// BusinessEntity SLO3 = goal3.createNewSubentity();
		// SLO3.createNewSubentity();
		// BP_Node plan3 = new BP_Node(centreplan3, 2014, "Math", true);
		//
		// server.addBP_Node(plan1);
		// server.addBP_Node(plan2);
		// server.addBP_Node(plan3);
		//
		// CentrePlanFactory centreHead4 = new CentrePlanFactory();
		// BusinessEntity centreplan4 = centreHead4.nextLayer(null);
		// BusinessEntity mission_statement4 = centreplan4.createNewSubentity();
		// BusinessEntity department_statement4 =
		// mission_statement4.createNewSubentity();
		// BusinessEntity goal4 = department_statement4.createNewSubentity();
		// BusinessEntity SLO4 = goal4.createNewSubentity();
		// SLO4.createNewSubentity();
		// BP_Node plan4 = new BP_Node(centreplan4, 2016, "Computer Science", true);
		//
		// CentrePlanFactory centreHead5 = new CentrePlanFactory();
		// BusinessEntity centreplan5 = centreHead5.nextLayer(null);
		// BusinessEntity mission_statement5 = centreplan5.createNewSubentity();
		// BusinessEntity department_statement5 =
		// mission_statement5.createNewSubentity();
		// BusinessEntity goal5 = department_statement5.createNewSubentity();
		// BusinessEntity SLO5 = goal5.createNewSubentity();
		// SLO5.createNewSubentity();
		// BP_Node plan5 = new BP_Node(centreplan5, 2015, "Computer Science", true);
		//
		// CentrePlanFactory centreHead6 = new CentrePlanFactory();
		// BusinessEntity centreplan6 = centreHead6.nextLayer(null);
		// BusinessEntity mission_statement6 = centreplan6.createNewSubentity();
		// BusinessEntity department_statement6 =
		// mission_statement6.createNewSubentity();
		// BusinessEntity goal6 = department_statement6.createNewSubentity();
		// BusinessEntity SLO6 = goal6.createNewSubentity();
		// SLO6.createNewSubentity();
		// BP_Node plan6 = new BP_Node(centreplan6, 2014, "Computer Science", true);
		//
		// server.addBP_Node(plan4);
		// server.addBP_Node(plan5);
		// server.addBP_Node(plan6);
		//
		// Person caleb = new Person("Caleb", "4C", "Math", true);
		// Person alex = new Person("Alex", "Beta", "Computer Science", true);
		//
		// server.addPerson(caleb);
		// server.addPerson(alex);
		//
		// server.writeDisk();
		this.stage = stage;

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("MainView/MainView.fxml"));
		try
		{
			viewGoesHere = loader.load();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MainViewController cont = loader.getController();
		cont.setMain(new Main());
		server = new Server();
		cont.setServer(server);
		client = new Client(server);
		client.login("Alex", "Beta");

		Scene s = new Scene(viewGoesHere);
		stage.setScene(s);
		stage.setTitle("Clone");
		stage.show();
		client = new Client(server);
		client.login("Caleb", "4C");
		dep_plans = new ArrayList<BP_Node>();
		plans = new ComboBox<Integer>();
		plans.getItems().add(2014);
		List<BP_Node> allPlans = client.getBP();
		for (BP_Node node : allPlans)
		{
			if (node.department.equals(client.person.department))
			{
				plans.getItems().add(node.year);
				System.out.println(plans.getItems().get(1).intValue());
				dep_plans.add(node);
			}
		}
		plans.getItems().remove(0);
		// plans.getItems().add(2014);
		// plans.getItems().add(2015);
		// plans.getItems().add(2016);
		// getLists(plans, dep_plans);
		showClone(client.business, client, dep_plans, true);
		// showClone(BP_Node node, Client client, ArrayList<BP_Node> dep_plans, boolean
		// new_plan)
	}

	public void showHome(Client client)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("HomeView/HomeView.fxml"));
		try
		{
			viewGoesHere.setCenter(loader.load());
			HomeViewController cont = loader.getController();
			cont.setUp(client);
			cont.setMain(new Main());

		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	// @Test
	// public void testClone() {
	// client = new Client(server);
	// client.login("Caleb","4C");
	// ArrayList<BP_Node> dep_plans = new ArrayList<BP_Node>();
	//
	// HomeViewController.getLists(2014, dep_plans);
	// showClone(client.business, client, dep_plans,true);
	// }
	//
	public void showLogin()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("MainView/MainView.fxml"));
		try
		{
			viewGoesHere.setCenter(loader.load());
			MainViewController cont = loader.getController();
			cont.setMain(new Main());
			server = new Server();
			cont.setServer(server);

		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public void getLists(ComboBox<Integer> plans, ArrayList<BP_Node> dep_plans)
	{
		List<BP_Node> allPlans = client.getBP();
		for (BP_Node node : allPlans)
		{
			if (node.department.equals(client.person.department))
			{
				plans.getItems().add(node.year);
				System.out.println(plans.getItems().get(1).intValue());
				dep_plans.add(node);
			}
		}
	}

	public void showClone(BP_Node node, Client client, ArrayList<BP_Node> dep_plans, boolean new_plan)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("CloneView/CloneView.fxml"));
		try
		{
			viewGoesHere.setCenter(loader.load());
			CloneViewController cont = loader.getController();
			cont.setMain(new Main(), client, node, dep_plans, new_plan);
			cont.initialize(null, null);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// public <T extends Node> T find(String id)
	// {
	// return (T) lookup(id).queryAll().iterator().next();
	// }
	// @Test
	// void testLogin()
	// {
	// clickOn("#username");
	// write("Caleb");
	// clickOn("#pass");
	// write("4C");
	// moveTo("#loginButton");
	// assertEquals(1, 1);
	//
	// }

	// @Test
	// void TestCount() {
	// assertThat(plans, ComboBoxMatchers.hasItems(3));
	// }

	// @Test
	// void TestContent() {
	// assertThat(plans, ComboBoxMatchers.containsExactlyItems(2014,2015,2016));
	// }

	@Test
	void testWrite() throws IOException
	{

		clickOn("#newYearField");
		write("2018");
		clickOn("#statusBox").sleep(1000).type(KeyCode.DOWN).type(KeyCode.DOWN).sleep(1000).type(KeyCode.ENTER);
		moveTo("#okButton");
		assertThat(plans, ComboBoxMatchers.containsExactlyItems(2014, 2015, 2016, 2018));
		assertThat(plans, ComboBoxMatchers.hasItems(4));
//		plans.getItems().remove(3);
//		for (BP_Node plan : dep_plans)
//		{
//			if (plan.year == 2018)
//			{
//				client.deleteBP(plan);
//			}
//		}

	}

	
	
	@Test
	void testCancel()
	{
		Button cancel = lookup("#cancelButton").query();
		assertNotNull(cancel);
	}

	@Test
	void testOK()
	{
		Button ok = lookup("#okButton").query();
		assertNotNull(ok);
	}

	@Test
	void testOptions()
	{
		ComboBox<Integer> box = lookup("#statusBox").query();
		assertThat(box, ComboBoxMatchers.containsExactlyItems("Editable", "Not Editable"));
	}
}
