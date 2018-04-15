package s4.HomeView;

import java.util.ArrayList;
import java.util.List;
import Client.Client;
import Server.BP_Node;
import Server.Server;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import s4.Main;

public class HomeViewController
{

	ArrayList<String> data = new ArrayList<String>();
	Main main;
	@FXML
	private Text departmentTitle;

	@FXML
	private ComboBox<Integer> plansBox;

	@FXML
	private Button statusButton;

	@FXML
	private Button editButton;

	@FXML
	private Button deleteButton;

	@FXML
	private Button cloneButton;

	@FXML
	private Button newUserButton;

	private ArrayList<BP_Node> dep_plans;
	@FXML
	private TableView<BP_Node> plan_table;

	private Client client;

	// sets the "model"
	public void setMain(Main m)
	{
		this.main = m;
	}

	public void setUp(Client c)
	{
		this.client = c;
		departmentTitle.setText(client.person.department);
		dep_plans = new ArrayList<BP_Node>();
		getLists(plansBox, dep_plans);
		makeTable(dep_plans);
		if (client.person.isAdmin() == false)
		{
			cloneButton.setVisible(false);
			statusButton.setVisible(false);
			newUserButton.setVisible(false);
		} else
		{
			statusButton.setOnAction(e -> setStatus());
			cloneButton.setOnAction(e -> cloneStart());
			newUserButton.setOnAction(e -> main.showNewUser(client));
		}
		editButton.setOnAction(e -> editThePlan());
		deleteButton.setOnAction(e -> deleteAction());

	}

	
	
	public void cloneStart()
	{
		int clone_year = -1;
		if (plansBox.getValue() == null) // if they didn't use the dropdown
		{
			clone_year = onEdit(); // get the highlighted table row
		} else // otherwise, get the dropdown value
		{
			clone_year = plansBox.getValue();
		}
		
		if (clone_year != -1)
		{
			List<BP_Node> allPlans = client.getBP();
			for ( BP_Node node : allPlans)
			{
				if (node.department.equals(client.person.department)
						&& node.year==clone_year)
				{
					main.showClone(node,client,dep_plans);
				}
			}
		}
	}

	/// find plan to delete and delete it
	private void deleteAction()
	{
		int delete_year = -1;
		if (plansBox.getValue() == null)
		{
			delete_year = onEdit();
		} else
		{
			delete_year = plansBox.getValue();
		}

		if (delete_year != -1)
		{
			List<BP_Node> allPlans = client.getBP();
			int index = 0;
			for (BP_Node node : allPlans)
			{
				if (node.getDepartment().equalsIgnoreCase(client.person.getDepartment()) && node.year == delete_year)
				{
					deletePlan(delete_year, dep_plans);
					plansBox.getItems().remove(index);
					dep_plans.remove(index);
				} else if (node.getDepartment().equalsIgnoreCase(client.person.getDepartment()))
				{
					index++;
				}
			}
		}
	}

	// delete and redraw
	private void deletePlan(int ye, ArrayList<BP_Node> dep_plans)
	{
		for (BP_Node plan : dep_plans)
		{
			if (plan.year == ye)
			{
				client.deleteBP(plan);
			}
		}
		setUp(client);
	}

	/// get the bp node and make the edit page
	private void editPlan(int ye, ArrayList<BP_Node> dep_plans)
	{
		for (BP_Node plan : dep_plans)
		{
			if (plan.year == ye)
			{
				client.business = plan;
			}
		}
		main.showEdit(client);
	}

	private void editThePlan()
	{
		int year = -1;
		if (plansBox.getValue() == null)
		{
			year = onEdit();
		} else
		{
			year = plansBox.getValue();
		}

		if (year != -1)
		{
			editPlan(year, dep_plans);
		}
	}

	/// change status of a plan
	private void setStatus()
	{
		int year = -1;
		if (plansBox.getValue() == null)
		{
			year = onEdit();
		} else
		{
			year = plansBox.getValue();
		}

		if (year != -1)
		{
			set_BPStatus(year, client.person.getDepartment(), dep_plans);
		}
	}

	// gets the plan and changes the status of that plan
	private void set_BPStatus(int ye, String department, ArrayList<BP_Node> dep_plans)
	{
		// make dialog box for getting the new year and status of the plan
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		// dialog.initOwner(mainStage);

		VBox dialogVbox = new VBox();
		// combo box for editable status
		ComboBox<String> editable = new ComboBox<String>();
		editable.setPromptText("edit status...");
		editable.getItems().addAll("editable", "not editable");

		HBox buttons = new HBox();

		Button ok = new Button("Ok");
		ok.setOnAction(e -> {
			data = new ArrayList<String>();
			data.add(editable.getValue());
			dialog.close();
		});

		Button cancel = new Button("Cancel");
		cancel.setOnAction(e -> dialog.close());

		buttons.getChildren().addAll(ok, cancel);

		// add everything to the vbox
		dialogVbox.getChildren().addAll(editable, buttons);

		// set the scene
		Scene dialogScene = new Scene(dialogVbox, 300, 200);
		dialog.setScene(dialogScene);

		dialog.showAndWait();

		String edit = data.get(0);
		for (BP_Node plan : dep_plans)
		{
			if (plan.year == ye)
			{
				boolean set_editable = false;
				if (edit == "editable")
				{
					set_editable = true;
				}
				client.setBPStatus(plan, set_editable);
			}
		}
		// redraw
		setUp(client);
	}

	/// this gets the year of the plan you want
	private int onEdit()
	{
		int year = -1;
		// check the table's selected item and get selected item
		if (plan_table.getSelectionModel().getSelectedItem() != null)
		{
			BP_Node selected_plan = plan_table.getSelectionModel().getSelectedItem();
			year = selected_plan.getYear();
		}
		return year;
	}

	/// takes a list of plans and makes the table
	public void makeTable(ArrayList<BP_Node> dep_plans)
	{
		// this allows for the redrawing not creating 3 more columns
		plan_table.getColumns().clear();

		ObservableList<BP_Node> business_plans = FXCollections.observableArrayList(dep_plans);
		plan_table.setItems(business_plans);
		// title column
		TableColumn<BP_Node, String> title_column = new TableColumn<BP_Node, String>("Plan Title");
		title_column.setCellValueFactory(new Callback<CellDataFeatures<BP_Node, String>, ObservableValue<String>>()
		{
			@Override
			public ObservableValue<String> call(CellDataFeatures<BP_Node, String> n)
			{
				return new ReadOnlyStringWrapper(n.getValue().getEntity().getEntityTitle());
			}
		});

		// year column
		TableColumn<BP_Node, String> year_column = new TableColumn<BP_Node, String>("Year");
		year_column.setCellValueFactory(new Callback<CellDataFeatures<BP_Node, String>, ObservableValue<String>>()
		{
			@Override
			public ObservableValue<String> call(CellDataFeatures<BP_Node, String> n)
			{
				return new ReadOnlyStringWrapper(String.valueOf(n.getValue().getYear()));
			}
		});

		// status column
		TableColumn<BP_Node, String> status_column = new TableColumn<BP_Node, String>("Plan Status");
		status_column.setCellValueFactory(new Callback<CellDataFeatures<BP_Node, String>, ObservableValue<String>>()
		{
			@Override
			public ObservableValue<String> call(CellDataFeatures<BP_Node, String> n)
			{
				String toReturn = "view only";
				if (n.getValue().isEditable())
				{
					toReturn = "edit/view";
				}
				return new ReadOnlyStringWrapper(toReturn);
			}
		});
		plan_table.getColumns().addAll(title_column, year_column, status_column);
	}

	/// this adds the appropriate plans to the comboBox
	private void getLists(ComboBox<Integer> plans, ArrayList<BP_Node> dep_plans)
	{
		List<BP_Node> allPlans = client.getBP();
		for (BP_Node node : allPlans)
		{
			if (node.department.equals(client.person.department))
			{
				plans.getItems().add(node.year);
				dep_plans.add(node);
			}
		}
	}
}
