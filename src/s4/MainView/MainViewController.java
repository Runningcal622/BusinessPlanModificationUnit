
package s4.MainView;

import Client.Client;
import Server.Server;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import s4.Main;

public class LoginController
{
	Client client;
	Main main;
	@FXML
	private PasswordField pass;

	@FXML
	private RadioButton localHost;

	@FXML
	private RadioButton otherHost;

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

	
	void radioClicked(RadioButton buttonPushed)
	{
		if (buttonPushed.textProperty().getValue().equals("Localhost"))
		{
			if (otherHost.isSelected())
			{
				otherHost.setSelected(false);
			}
			localHost.setSelected(true);
		}
		else
		{
			if (localHost.isSelected())
			{
				localHost.setSelected(false);
			}
			otherHost.setSelected(true);
		}
	}
	
	public void setServer(Server server)
	{
		client = new Client(server);
		client.proxy.readDisk();
		localHost.setOnAction(e -> radioClicked(localHost));
		otherHost.setOnAction(e -> radioClicked(otherHost));
	
	}
}
