package GuiTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import Client.Client;
import Server.BP_Node;
import Server.Server;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import s4.Main;
import s4.ViewInterface;
import s4.MainView.MainViewController;

class LoginToHomeTransitionTest  extends ApplicationTest implements ViewInterface
{

	Stage stage;
	BorderPane mainView;
	Server server;
	int calledHome;
	int calledAddUser;
	int calledEditor;
	int calledClone;
	int calledClose;
	int calledSetStatus;
	//Main main=new Main();
	
	@Override
	public void start(Stage stage)
	{
		
		this.stage = stage;
		server = new Server();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("MainView/MainView.fxml"));
		try
		{
			mainView = loader.load();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MainViewController cont = loader.getController();
		cont.setMain(this);

		cont.setServer(server);
		Scene s = new Scene(mainView);
		stage.setScene(s);
		stage.show();

	}
	
	@Before
	public void initMocks()
	{
		 calledHome=0;
		 calledAddUser=0;
		 calledEditor=0;
		// calledLogin=0;
		 calledClone=0;
		 calledSetStatus=0;
	}
	
	@Test
	void testSuccessLogin()
	{
		clickOn("#username");
		write("Caleb");
		clickOn("#pass");
		write("4C");
		clickOn("#loginButton");
		assertEquals(calledHome,1);
		calledHome=0;
	}

	
	@Test
	void testFailedLogin1()
	{
		clickOn("#username");
		write("Caleb");
		clickOn("#pass");
		write("wrong password");
		clickOn("#loginButton");
		assertEquals(calledHome,0);
	}
	
	@Test
	void testFailedLogin2()
	{
		assertEquals(calledHome,0);
	}
	
//	@Test
//	void testFailedLogin3()
//	{
//		clickOn("#username");
//		write("Caleb");
//		clickOn("#pass");
//		write("4C");
//		clickOn("#selectHost");
//		clickOn("#loginButton");
//		assertEquals(calledHome,0);
//	}
	
	@Test
	void testPortLogin()
	{
		clickOn("#username");
		write("Caleb");
		clickOn("#pass");
		write("4C");
		clickOn("#selectHost");
		clickOn("#portNum");
		write("2869");
		clickOn("#loginButton");
		assertEquals(calledHome,1);
		calledHome=0;
	}
	
	@Override
	public void showHome(Client client)
	{
		calledHome++;
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
