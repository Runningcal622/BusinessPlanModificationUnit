package s4.SetEditStatusView;

import Client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import s4.ViewInterface;

public class SetEditStatusViewController
{
	private Client client;
	private ViewInterface main;
	private int year;
	
	@FXML
	private Button okButton;

	@FXML
	private Button cancelButton;
	
	@FXML
	private ComboBox<String> selectStatus;
	
	public void setMain(ViewInterface main, Client client, int year)
	{
		this.main = main;
		this.client = client;
		this.year = year;
		
		selectStatus.getItems().clear();
		selectStatus.getItems().addAll("editable","not editable");
	}

	@FXML
	void OnCancel(ActionEvent event)
	{
		main.showHome(client);
	}

	@FXML
	void OnOk(ActionEvent event)
	{
		if(selectStatus.getValue()!="")
		{
			client.requestBusinessPlan(client.person.getDepartment(), year);
			boolean editable = false;
			if(selectStatus.getValue().equals("editable"))
			{
				editable = true;
			}
			client.business.setEditable(editable);
		}
		client.proxy.writeDisk();
		main.showHome(client);
	}

}
