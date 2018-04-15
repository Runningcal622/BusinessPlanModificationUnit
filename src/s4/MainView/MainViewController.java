
package s4.MainView;

import Client.Client;
import Server.Server;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import s4.Main;

public class MainViewController
{
	Client client;
	Main main;
	@FXML
	private PasswordField passField;

	@FXML
	private RadioButton radioLocalHost;

	@FXML
	private RadioButton radioForeignHost;

	@FXML
	private TextField userField;

	@FXML
	private TextField foreginHostname;

	public void setMain(Main m)
	{
		this.main=m;
	}
	@FXML
	void login()
	{
		String password = passField.getText();
		String username = userField.getText();
		System.out.println("here 1");
		client.login(username, password);
		if (client.person != null)
		{
			main.showHome(client);
		}
		
	}

	
	void radioClicked(RadioButton buttonPushed)
	{
		if (buttonPushed.textProperty().getValue().equals("Localhost"))
		{
			if (radioForeignHost.isSelected())
			{
				radioForeignHost.setSelected(false);
			}
			radioLocalHost.setSelected(true);
		}
		else
		{
			if (radioLocalHost.isSelected())
			{
				radioLocalHost.setSelected(false);
			}
			radioForeignHost.setSelected(true);
		}
	}
	
	public void setServer(Server server)
	{
		client = new Client(server);
		client.proxy.readDisk();
		radioLocalHost.setOnAction(e -> radioClicked(radioLocalHost));
		radioForeignHost.setOnAction(e -> radioClicked(radioForeignHost));
	
	}
}
