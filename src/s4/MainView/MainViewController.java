
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
	private PasswordField pass;

	@FXML
	private RadioButton local;

	@FXML
	private RadioButton selectHost;

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
		if (buttonPushed.textProperty().getValue().equals("local"))
		{
			if (selectHost.isSelected())
			{
				selectHost.setSelected(false);
			}
			local.setSelected(true);
		}
		else
		{
			if (local.isSelected())
			{
				local.setSelected(false);
			}
			selectHost.setSelected(true);
		}
	}
	
	public void setServer(Server server)
	{
		client = new Client(server);
		client.proxy.readDisk();
		local.setOnAction(e -> radioClicked(local));
		selectHost.setOnAction(e -> radioClicked(selectHost));
	
	}
}
