package GuiTest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import Client.Client;
import Server.BP_Node;
import Server.Server;
import Server.starter;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import s4.Main;
import s4.ViewInterface;
import s4.HomeView.HomeViewController;


@ExtendWith(ApplicationExtension.class)
public class HomeViewTest implements ViewInterface // extend ApplicationTest
{
	BorderPane viewGoesHere = new BorderPane();
	Stage stage;
	Client client;
	HomeViewController cont;

	int calledHome;
	int calledAddUser;
	int calledEditor;
	int calledClone;
	int calledClose;
	int calledSetStatus;

	ObservableList<Node> table_rows;
	@BeforeAll
	public static void StartemUp()
	{
		starter.main(null);
	}

	@Start
	public void onStart(Stage stage) throws IOException
	{
		this.stage = stage;

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("HomeView/HomeView.fxml"));
		try
		{
			viewGoesHere.setCenter(loader.load());
			cont = loader.getController();
			Server server = new Server();
			client = new Client(server);
			client.proxy.readDisk();
			client.login("Caleb", "4C");
			cont.setUp(client);
			cont.setMain(this);

			Scene s = new Scene(viewGoesHere);
			stage.setScene(s);
			stage.show();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void LoginTest(String ButtonId, String text, FxRobot robo)
	{
		robo.clickOn(ButtonId);
		// TextInputControls.clearTextIn("#moneyInput");
		robo.write(text);
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
	public void please(FxRobot robo)
	{
		robo.clickOn("#selectYear");
		robo.type(KeyCode.DOWN);

		robo.type(KeyCode.ENTER);
		robo.clickOn("#editButton");

		assertEquals(calledHome, 0);
		assertEquals(calledAddUser, 0);
		assertEquals(calledEditor, 1);
		assertEquals(calledClone, 0);
		assertEquals(calledSetStatus, 0);
		calledEditor = 0;

		// CHECK deleteButton
		// should delete plan
		robo.clickOn("#selectYear");
		robo.type(KeyCode.DOWN);
		robo.type(KeyCode.DOWN);
		robo.type(KeyCode.ENTER);
		robo.clickOn("#deleteButton");
		assertEquals(calledHome, 0);
		assertEquals(calledAddUser, 0);
		assertEquals(calledEditor, 0);
		assertEquals(calledClone, 0);
		assertEquals(calledSetStatus, 0);

		// CHECK cloneButton
		// should go to clone page
		robo.clickOn("#selectYear");
		robo.type(KeyCode.DOWN);
		robo.type(KeyCode.ENTER);
		robo.clickOn("#cloneButton");
		assertEquals(0, calledHome);
		assertEquals(0, calledAddUser);
		assertEquals(0, calledEditor);
		assertEquals(1, calledClone);
		assertEquals(0, calledSetStatus);
		calledClone = 0;

		// CHECK newPlanButton
		// should go to blank plan
		robo.clickOn("#selectYear");
		robo.type(KeyCode.DOWN);
		robo.type(KeyCode.ENTER);
		robo.clickOn("#newPlanButton");
		assertEquals(calledHome, 0);
		assertEquals(calledAddUser, 0);
		assertEquals(calledEditor, 0);
		assertEquals(calledClone, 1);
		assertEquals(calledSetStatus, 0);
		calledClone = 0;

		// CHECK newUserButton;
		// Should go to newUser
		robo.clickOn("#newUserButton");
		assertEquals(calledHome, 0);
		assertEquals(calledAddUser, 1);
		assertEquals(calledEditor, 0);
		assertEquals(calledClone, 0);
		assertEquals(calledSetStatus, 0);
		calledAddUser = 0;

		// CHECK setStatusButton
		// EDITIBLE
		robo.clickOn("#selectYear");
		robo.type(KeyCode.DOWN);
		robo.type(KeyCode.ENTER);
		robo.clickOn("#setStatusButton");
		assertEquals(calledHome, 0);
		assertEquals(calledAddUser, 0);
		assertEquals(calledEditor, 0);
		assertEquals(calledClone, 0);
		calledSetStatus = 0;
	}

	private ArrayList<BP_Node> get_DepPlans(LinkedList<BP_Node> plans)
	{
		ArrayList<BP_Node> dep_plans = new ArrayList<BP_Node>();
		List<BP_Node> allPlans = client.getBP();
		for (BP_Node node : allPlans)
		{
			if (node.department.equals(client.person.department))
			{
				dep_plans.add(node);
			}
		}
		return dep_plans;
	}

	@Test
	public void TestTableEdit(FxRobot robo)
	{
		ArrayList<BP_Node> dep_plans = get_DepPlans(client.getBP());
		for (int i = 0; i < dep_plans.size(); i++)
		{
			// test edit
			robo.clickOn(String.valueOf(dep_plans.get(i).getYear()));
			robo.clickOn("#editButton");

			assertEquals(calledHome, 0);
			assertEquals(calledAddUser, 0);
			assertEquals(calledEditor, 1);
			assertEquals(calledClone, 0);
			assertEquals(calledSetStatus, 0);
			calledEditor = 0;
		}

		dep_plans = get_DepPlans(client.getBP());
	}

	//@Test
	public void TestTableSetStatus(FxRobot robo)
	{
		ArrayList<BP_Node> dep_plans = get_DepPlans(client.getBP());

		// CHECK setStatusButton
		// EDITIBLE
		robo.clickOn(String.valueOf(dep_plans.get(0).getYear()));
		robo.clickOn("#setStatusButton");

		assertEquals(dep_plans.get(0).isEditable(), true);
		assertEquals(calledHome, 0);
		assertEquals(calledAddUser, 0);
		assertEquals(calledEditor, 0);
		assertEquals(calledClone, 0);
		assertEquals(calledSetStatus, 1);
		calledSetStatus = 0;
	}

	@Test
	public void TestTableDelete(FxRobot robo)
	{
		ArrayList<BP_Node> dep_plans = get_DepPlans(client.getBP());

		// CHECK deleteButton
		// should delete plan
		robo.clickOn(String.valueOf(dep_plans.get(0).getYear()));
		robo.clickOn("#deleteButton");
		assertEquals(calledHome, 0);
		assertEquals(calledAddUser, 0);
		assertEquals(calledEditor, 0);
		assertEquals(calledClone, 0);
		assertEquals(calledSetStatus, 0);
	}

	@Test
	public void TestTableClone(FxRobot robo)
	{
		ArrayList<BP_Node> dep_plans = get_DepPlans(client.getBP());
		
		// CHECK cloneButton
		// should go to clone page
		robo.clickOn(String.valueOf(dep_plans.get(0).getYear()));

		robo.clickOn("#cloneButton");
		assertEquals(calledHome, 0);
		assertEquals(calledAddUser, 0);
		assertEquals(calledEditor, 0);
		assertEquals(calledClone, 1);
		assertEquals(calledSetStatus, 0);
		calledClone = 0;
	}
	
	@Test
	public void TestStatus(FxRobot robo) 
	{
		ArrayList<BP_Node> dep_plans = get_DepPlans(client.getBP());
		robo.clickOn(String.valueOf(dep_plans.get(0).getYear()));
		BP_Node plan = dep_plans.get(0);
		if (plan.isEditable())
		{
			robo.clickOn("#setStatusButton");
			dep_plans = get_DepPlans(client.getBP());
			plan = dep_plans.get(0);
			assertEquals(false , plan.isEditable());
		}
		else
		{
			robo.clickOn("#setStatusButton");
			dep_plans = get_DepPlans(client.getBP());
			plan = dep_plans.get(0);
			assertEquals(true , plan.isEditable());
		}
	}

	@Override
	public void showClone(BP_Node node, Client client, ArrayList<BP_Node> dep_plans, boolean new_plan)
	{
		// TODO Auto-generated method stub
		calledClone++;
	}

	@Override
	public void showNewUser(Client client)
	{
		// TODO Auto-generated method stub
		calledAddUser++;
	}

	@Override
	public void showEdit(Client client)
	{
		// TODO Auto-generated method stub
		calledEditor++;
	}

	@Override
	public void showSetStatus(Client client, int year)
	{
		// TODO Auto-generated method stub
		calledSetStatus++;
	}

	@Override
	public void showHome(Client client)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void showLogin()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void showSeeUser(Client client)
	{
		// TODO Auto-generated method stub
		
	}
}