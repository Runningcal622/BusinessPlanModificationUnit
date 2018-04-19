package s4.HomeView;

import java.util.ArrayList;
import java.util.List;

import Client.Client;
import Server.BP_Node;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import s4.Main;

public class HomeViewController
{
	private Client client;
	private Main main;
	
	@FXML
	private Label departmentLabel;

	@FXML
	private Button logoutButton;

	@FXML
	private ComboBox<Integer> selectYear;

	@FXML
	private Button editButton;

	@FXML
	private Button deleteButton;

	@FXML
	private Button cloneButton;

	@FXML
	private Button newPlanButton;

	@FXML
	private TableView<BP_Node> planTable;

	
	public void setMain(Main main)
	{
		this.main = main;
	}
	
	public void setUp(Client client)
	{
		this.client = client;
		ArrayList<BP_Node> dep_plans = new ArrayList<BP_Node>();
		getLists(selectYear, dep_plans);

		// table
		planTable = new TableView<BP_Node>();
		ObservableList<BP_Node> business_plans = FXCollections.observableArrayList(dep_plans);
		planTable.setItems(business_plans);

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

		planTable.getColumns().addAll(title_column, year_column, status_column);
	}
	
	
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
	
	
	@FXML
	void OnClone(ActionEvent event)
	{

	}

	@FXML
	void OnDelete(ActionEvent event)
	{

	}

	@FXML
	void OnEdit(ActionEvent event)
	{

	}

	@FXML
	void OnLogout(ActionEvent event)
	{

	}

	@FXML
	void OnMakeNewPlan(ActionEvent event)
	{

	}

	@FXML
	void OnTableRowClick(MouseEvent event)
	{

	}

}