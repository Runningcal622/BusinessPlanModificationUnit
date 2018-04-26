package GuiTest;
import static org.junit.Assert.assertEquals;
//imports
import static org.junit.Assert.assertThat;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;

import Client.Client;
import Server.BP_Node;
import Server.Server;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import s4.Main;
import s4.MainView.MainViewController;
import s4.HomeView.HomeViewController;
import org.testfx.framework.junit5.*;
@ExtendWith(ApplicationExtension.class)
public class HomeViewTest implements ViewInterface // extend ApplicationTest

{
	BorderPane viewGoesHere = new BorderPane();
	Stage stage;
	Client client;
	Main main = new Main();
	int calledHome;
	int calledAddUser;
	int calledEditor;
	int calledClone;
	int calledClose;
	int calledSetStatus;
	@Start
	public void onStart(Stage stage) throws IOException
	{
		this.stage = stage;

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("HomeView/HomeView.fxml"));
		try
		{
			//Server server = new Server();
			//MainViewController cont2 = loader.getController();
			//cont2.setServer(server);
			
			viewGoesHere.setCenter(loader.load());
			HomeViewController cont = loader.getController();
			cont.setMain(new Main());
			Server server = new Server();
			client = new Client(server);
			client.proxy.readDisk();
			client.login("Caleb", "4C");
			cont.setUp(client);
			cont.setMain(main);
			//cont.setServer(server);
			
			Scene s = new Scene(viewGoesHere);
			stage.setScene(s);
			stage.show();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("here");
	}
	public void LoginTest(String ButtonId, String text, FxRobot robo) {
		robo.clickOn(ButtonId);
				//TextInputControls.clearTextIn("#moneyInput");
		robo.write(text);
	}
	@Before
	public void initMocks()
	{
		 calledHome=0;
		 calledAddUser=0;
		 calledEditor=0;
		 calledClone=0;
		 calledSetStatus=0;
		 calledClose=0;
	}
	

	@Test
	public void please(FxRobot robo)
	{
		// assertThat();
        robo.clickOn("#selectYear");
        robo.type(KeyCode.DOWN);
       
        robo.type(KeyCode.ENTER);
        robo.clickOn("#editButton");
       
        assertEquals(calledHome,0);
		assertEquals(calledAddUser,0);
		assertEquals(calledEditor,1);
		assertEquals(calledClone,0);
		assertEquals(calledSetStatus,0);
		calledEditor=0;
        
        
        //CHECK deleteButton
        //should delete plan 
        robo.clickOn("#selectYear");
        robo.type(KeyCode.DOWN);
        robo.type(KeyCode.DOWN);
        robo.type(KeyCode.DOWN);
        robo.type(KeyCode.ENTER);
        robo.clickOn("#deleteButton");
        assertEquals(calledHome,0);
		assertEquals(calledAddUser,0);
		assertEquals(calledEditor,0);
		assertEquals(calledClone,0);
		assertEquals(calledSetStatus,0);
        
        
        
        //CHECK cloneButton
        //should go to clone page 
        robo.clickOn("#selectYear");
        robo.type(KeyCode.DOWN);
        robo.type(KeyCode.DOWN);
        robo.type(KeyCode.ENTER);
        robo.clickOn("#cloneButton");
        assertEquals(calledHome,0);
		assertEquals(calledAddUser,0);
		assertEquals(calledEditor,0);
		assertEquals(calledClone,1);
		assertEquals(calledSetStatus,0);
		calledClone=0;
        
        
        //CHECK newPlanButton
        //should go to blank plan screen 
        robo.clickOn("#newPlanButton");
        assertEquals(calledHome,0);
		assertEquals(calledAddUser,0);
		assertEquals(calledEditor,1);
		assertEquals(calledClone,0);
		assertEquals(calledSetStatus,0);
		calledEditor=0;
        
        //CHECK newUserButton;
        //Should go to newUser 
        robo.clickOn("#newUser");
        assertEquals(calledHome,0);
		assertEquals(calledAddUser,1);
		assertEquals(calledEditor,0);
		assertEquals(calledClone,0);
		assertEquals(calledSetStatus,0);
		calledAddUser=0;
        
        //CHECK setStatusButton 
        //EDITIBLE 
        robo.clickOn("#selectYear");
        robo.type(KeyCode.DOWN);
        robo.type(KeyCode.ENTER);
        robo.clickOn("#setStatusButton");
        assertEquals(calledHome,0);
		assertEquals(calledAddUser,0);
		assertEquals(calledEditor,0);
		assertEquals(calledClone,0);
		assertEquals(calledSetStatus,1);
		calledSetStatus=0;
        
		
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

}