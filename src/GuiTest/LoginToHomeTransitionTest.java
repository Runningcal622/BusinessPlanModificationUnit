package GuiTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import Client.Client;
import Server.Server;
import Server.starter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import s4.Main;
import s4.ViewInterface;
import s4.MainView.MainViewController;

class LoginTest  extends ApplicationTest implements ViewInterface
{

	Stage stage;
	BorderPane mainView;
	Server server;
	int calledHome;
	int calledAddUser;
	int calledEditor;
	int calledLogin;
	int calledClone;
	int calledClose;
	//Main main=new Main();
	
	@Override
	public void start(Stage stage)
	{
		
		starter starter = new starter();
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
		server = new Server();
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
		 calledLogin=0;
		 calledClone=0;
	}
	
	@Test
	void testLogin()
	{
		clickOn("#username");
		write("Caleb");
		clickOn("#pass");
		write("4C");
		clickOn("#loginButton");
		assertEquals(calledHome,1);
	}

	@Override
	public void login(Client client)
	{
		calledHome++;
	}

}
