package s4.NewUserView;

import java.util.ArrayList;

import Client.Client;
import Server.BP_Node;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import s4.Main;

public class NewUserViewController
{

	@FXML
	private TextField usernameField;

	@FXML
	private TextField passField;

	@FXML
	private TextField depField;

	@FXML
	private ComboBox<Boolean> AdminBox;

	@FXML
	private Button okButton;

	private Main main;
	private Client client;

	public void setMain(Main m, Client c)
	{
		this.main = m;
		this.client = c;
	}

	public void setUp()
	{
		AdminBox.getItems().addAll(true, false);
		okButton.setOnAction(e -> okAction());
	}

	private void okAction()
	{
		String username = usernameField.getText();
		String password = passField.getText();
		String department = depField.getText();
		Boolean admin = AdminBox.getValue();

		if (username.length() != 0 && password.length() != 0 && department.length() != 0 && admin != null)
		{
			client.addPeople(username, password, department, admin);
			main.showHome(client);
		}

	}

}
