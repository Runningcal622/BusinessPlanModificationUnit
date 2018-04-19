package CentrePlanApplication.homeView;

import java.util.ArrayList;
import java.util.List;

import CentrePlanApplication.Client.Client;
import CentrePlanApplication.Server.BP_Node;
import CentrePlanApplication.Server.CentrePlanFactory;
import CentrePlanApplication.model.Model;
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

public class HomeViewController
{
	private Model model = new Model();

	@FXML
	private Label departmentLabel;

	@FXML
	private ComboBox<Integer> selectYear;

	@FXML
	private Button editButton;

	@FXML
	private Button deleteButton;

	@FXML
	private Button cloneButton;

	@FXML
	private Button makeNewPlanButton;

	@FXML
	private TableView<BP_Node> planTable;
	

	private void getLists(ComboBox<Integer> plans, ArrayList<BP_Node> dep_plans)
	{
		List<BP_Node> allPlans = model.getClient().getBP();
		for (BP_Node node : allPlans)
		{
			if (node.department.equals(model.getClient().person.department))
			{
				plans.getItems().add(node.year);
				dep_plans.add(node);
			}
		}
	}
	
	public void setModel(Model model)
	{
		this.model = model;
		
		client.login("Alex", "Beta");

		// Selection box for plans
		ArrayList<BP_Node> dep_plans = new ArrayList<BP_Node>();
		getLists(selectYear, dep_plans);

		// table
		planTable = new TableView<BP_Node>();
		ObservableList<BP_Node> business_plans = FXCollections.observableArrayList(dep_plans);
		planTable.setItems(business_plans);
		
		System.out.println(business_plans.size());

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

	@FXML
	void OnCloneButton(ActionEvent event)
	{

	}

	@FXML
	void OnDeleteButton(ActionEvent event)
	{

	}

	@FXML
	void OnEditButton(ActionEvent event)
	{

	}

	@FXML
	void OnMakeNewPlanButton(ActionEvent event)
	{

	}

	@FXML
	void OnSelectPlanFromTable(MouseEvent event)
	{

	}

}
