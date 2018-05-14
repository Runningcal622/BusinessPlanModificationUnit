package businessPlanTools.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

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
import s4.MainView.MainViewController;
import s4.NewUserView.NewUserViewController;

public class TestNewUserView extends ApplicationTest
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
		
		this.stage = stage;

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("MainView/MainView.fxml"));
		try
		{
			viewGoesHere = loader.load();
		} catch (IOException e)
		{
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

		showNewUser( client);
		// showClone(BP_Node node, Client client, ArrayList<BP_Node> dep_plans, boolean
		// new_plan)
	}
	
	public void showNewUser(Client client)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("NewUserView/NewUserView.fxml"));
		try
		{
			viewGoesHere.setCenter(loader.load());
			NewUserViewController cont = loader.getController();
			cont.setMain(new Main(), client);
			cont.initialize(null, null);;
				
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	void test()
	{
		clickOn("#usernameField");
		write("john");
		clickOn("#passField");
		write("king");
		clickOn("#depField");
		write("Math");
		sleep(1000);
		clickOn("#AdminBox").sleep(1000).type(KeyCode.UP).type(KeyCode.ENTER);
		sleep(1000);
		moveTo("#okButton");
		
	}
	
	@Test
	void testCancel()
	{
		Button cancel = lookup("#cancelButton").query();
		assertNotNull(cancel);
	}
	
	@Test
	void testok()
	{
		Button ok = lookup("#okButton").query();
		assertNotNull(ok);
	}

}
