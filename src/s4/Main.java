package s4;

import java.io.IOException;
import java.util.ArrayList;

import Client.Client;
import Server.BP_Node;
import Server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import s4.CloneView.CloneViewController;
import s4.EditView.EditViewController;
import s4.HomeView.HomeViewController;
import s4.MainView.MainViewController;
import s4.NewUserView.NewUserViewController;
import s4.SetEditStatusView.SetEditStatusViewController;
import s4.UserView.UserViewController;

public class Main extends Application implements ViewInterface
{
	public Stage stage;
	Server server;
	public BorderPane viewGoesHere=new BorderPane();;
	ViewInterface v;



	@Override
	public void start(Stage stage) throws Exception
	{
		this.stage=stage;		
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("MainView/MainView.fxml"));
		viewGoesHere = loader.load();
		
		MainViewController cont = loader.getController();
		cont.setMain(this);
		server = new Server();
		cont.setServer(server);	
		
		showLogin();
		Scene s = new Scene(viewGoesHere);
		stage.setScene(s);
		stage.setTitle("Business plan modification unit");
		stage.show();
		
	}
	
	public Stage getStage()
	{
		return stage;
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
			cont.setMain(this);
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public void showEdit(Client client)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("EditView/EditView.fxml"));
		try
		{
			viewGoesHere.setCenter(loader.load());
			EditViewController cont = loader.getController();
			cont.setMain(this,client);
			cont.setUp();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	
	public void showLogin()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("MainView/MainView.fxml"));
		try
		{
			viewGoesHere.setCenter(loader.load());
			MainViewController cont = loader.getController();
			cont.setMain(this);
			server = new Server();
			cont.setServer(server);
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
				
	}

	public static void main(String[] args)
	{
		
		launch(args);
	}


	public void showNewUser(Client client)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("NewUserView/NewUserView.fxml"));
		try
		{
			viewGoesHere.setCenter(loader.load());
			NewUserViewController cont = loader.getController();
			cont.setMain(this, client);
			cont.initialize(null, null);;
				
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	public void showClone(BP_Node node, Client client, ArrayList<BP_Node> dep_plans,boolean new_plan)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("CloneView/CloneView.fxml"));
		try
		{
			viewGoesHere.setCenter(loader.load());
			CloneViewController cont = loader.getController();
			cont.setMain(this, client, node, dep_plans,new_plan);
			cont.initialize(null, null);	
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void showSetStatus(Client client,int year)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("SetEditStatusView/SetEditStatusView.fxml"));
		try
		{
			viewGoesHere.setCenter(loader.load());
			SetEditStatusViewController cont = loader.getController();
			cont.setMain(this,client,year);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void showSeeUser(Client client)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("UserView/UserView.fxml"));
		try
		{
			viewGoesHere.setCenter(loader.load());
			UserViewController cont = loader.getController();
			cont.setMain(this,client);
			cont.setUp();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
	
}
