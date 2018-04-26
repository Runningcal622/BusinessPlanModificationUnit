
package s4.MainView;

import Client.Client;
import Server.Server;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import s4.Main;
import s4.ViewInterface;

public class MainViewController 
{
	Client client;
	//public int calledLogin;
	//public Main main;
	String port;
	Stage stage;
	public ViewInterface main;
	
	@FXML
	private PasswordField pass;

	@FXML
	private RadioButton local;

	@FXML
	private RadioButton selectHost;
     @FXML
    private Button cancel;

    @FXML
    private Button loginButton;

    
	@FXML
	private TextField username;

	@FXML
	private TextField portNum;


	
	public void setMain(ViewInterface m)
	{
		this.main=m;
	}
	
	@FXML
	void login()
	{
		client.login(username.getText(), pass.getText());
		if (client.person != null)
		{
			main.login(client);
		}

	}

	@FXML
	void localAction(ActionEvent event)
	{
		selectHost.setSelected(false);
		portNum.setVisible(false);
		
	}
	
	@FXML 
	void selectHostAction(ActionEvent event) {
		local.setSelected(false);
		portNum.setVisible(true);
	}
	
	public void setServer(Server server)
	{
		client = new Client(server);
		client.proxy.readDisk();
		//local.setOnAction(e -> radioClicked(local));
		local.setSelected(true);
		portNum.setVisible(false);
	}
	 public void cancelLogin() 
    {
    	username.setPromptText("Enter username...");
		pass.setPromptText("Enter password...");
		//main.stage.close();
    }

	

}
