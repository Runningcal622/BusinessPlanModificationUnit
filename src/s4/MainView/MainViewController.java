
package s4.MainView;

import Client.Client;
import Server.Server;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import s4.Main;

public class MainViewController
{
	Client client;
	Main main;
	String port;
	
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


	
	public void setMain(Main m)
	{
		this.main=m;
	}
	@FXML
	void login()
	{
		client.login(username.getText(), pass.getText());
		if (client.person != null)
		{
			main.showHome(client);
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
		main.stage.close();
    }
}
