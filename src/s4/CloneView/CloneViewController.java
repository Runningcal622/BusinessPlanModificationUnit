package s4.CloneView;

import java.net.URL;
import java.util.ArrayList;
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

public class CloneViewController implements Initializable
{

	@FXML
	private TextField newYearField;

	@FXML
	private ComboBox<String> statusBox;

	@FXML
	private Button okButton;

	private Client client;
	private ViewInterface main;
	private BP_Node plan;
	private ArrayList<BP_Node> allPlans;
	private boolean new_plan;

	public void setMain(ViewInterface m, Client c, BP_Node node, ArrayList<BP_Node> all, boolean new_plan)
	{
		this.main = m;
		this.client = c;
		this.plan = node;
		this.allPlans = all;
		this.new_plan = new_plan;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		statusBox.getItems().clear();
		statusBox.getItems().addAll("Editable", "Not Editable");
	}

	@FXML
	private void cancelAction()
	{

		main.showHome(client);
	}

	@FXML
	private void okAction()
	{
		if (!(newYearField.getText().equals("")))
		{
			String toYear = newYearField.getText();

			boolean edit = false;
			if (statusBox.getValue().equals("Editable"))
			{
				edit = true;
			}

			// int yearToBeCloned = plan.year;
			int new_year;
			if (toYear.length() == 0)
			{
				new_year = -1;
			} else
			{
				try
				{
					new_year = Integer.parseInt(toYear);
				} catch (Exception e)
				{
					new_year = -1;
				}
			}
			if (new_year < 1819 || new_year > 2050)
			{
				new_year = -1;
			}
			if (new_year != -1)
			{
				if (new_plan == false)
				{
					int yearToBeCloned = plan.year;
					boolean duplicate = false;
					for (BP_Node node : allPlans)
					{
						if (node.year == yearToBeCloned)
						{
							duplicate = true;
						}
					}
					if (duplicate == false)
					{
						client.make_BlankBP(new_year, client.person.department, edit);
						
					} else
					{
						client.make_CloneBP(yearToBeCloned, client.person.department, edit, new_year);
					}
				} else
				{
					client.make_BlankBP(new_year, client.person.department, edit);		
				}
				client.proxy.writeDisk();
				client.proxy.readDisk();
				for (Person p : client.proxy.getPeople())
				{
					client.requestBusinessPlan(client.person.department, new_year);
					client.business.addWatcher(p);
				}
			}
			
			main.showHome(client);
		}
	}
}
