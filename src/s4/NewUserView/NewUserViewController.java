package s4.NewUserView;

import java.net.URL;
import java.util.ResourceBundle;

import Client.Client;
import Server.BP_Node;
import Server.Person;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import s4.ViewInterface;

public class NewUserViewController implements Initializable
{

	@FXML
	private TextField usernameField;

	@FXML
	private TextField passField;

	@FXML
	private TextField depField;

	@FXML
	private ComboBox<String> AdminBox;

	@FXML
	private Button okButton;

	@FXML
	private Button cancelButton;

	public ViewInterface main;
	private Client client;

	public void setMain(ViewInterface m, Client c)
	{
		this.main = m;
		this.client = c;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		AdminBox.getItems().removeAll(AdminBox.getItems());
		AdminBox.getItems().addAll("Admin", "Non-Admin");
		AdminBox.getSelectionModel().select("Non-Admin");
		okButton.setOnAction(e -> okAction());
		cancelButton.setOnAction(e -> cancelAction());
	}

	@FXML
	private void okAction()
	{
		String username = usernameField.getText();
		String password = passField.getText();
		String department = depField.getText();
		Boolean admin = false;
		if (AdminBox.getValue().equals("Non-Admin"))
		{
			admin = false;
		} else if (AdminBox.getValue().equals("Admin"))
		{
			admin = true;
		}
		if (username.length() != 0 && password.length() != 0 && department.length() != 0 && admin != null)
		{
			client.addPeople(username, password, department, admin);
			Person newPerson = client.proxy.getPerson(username, password);
			for (BP_Node plan : client.proxy.getBP())
			{
				if (plan.getDepartment().equals(newPerson.getDepartment()))
				{
					plan.addWatcher(newPerson);
				}
			}
			client.proxy.writeDisk();
			main.showHome(client);
		}

	}

	@FXML
	private void cancelAction()
	{

		main.showHome(client);
	}

	// fxml code for dropdown box
	// <items>
	// <FXCollections fx:factory="observableArrayList">
	// <String fx:value="Admin" />
	// <String fx:value="Non-Admin" />
	// </FXCollections>
	// </items>
	// </ComboBox>

}
