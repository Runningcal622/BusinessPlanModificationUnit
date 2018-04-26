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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import s4.Main;

public class HomeViewController
{
	private Client client;
	private ArrayList<BP_Node> dep_plans;
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
	private Button newUserButton;

	@FXML
	private Button setStatusButton;

	@FXML
	private TableView<BP_Node> planTable;

	public void setMain(Main main)
	{
		this.main = main;
	}

	public void setUp(Client client)
	{
		this.client = client;
		departmentLabel.setText(client.person.getDepartment());

		dep_plans = new ArrayList<BP_Node>();
		getLists(selectYear, dep_plans);

		ObservableList<BP_Node> business_plans = FXCollections.observableArrayList(dep_plans);
		planTable.getItems().clear();
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

		// Deselect the row if the row has already been clicked
		planTable.setRowFactory(new Callback<TableView<BP_Node>, TableRow<BP_Node>>()
		{
			@Override
			public TableRow<BP_Node> call(TableView<BP_Node> plan_table_row)
			{
				final TableRow<BP_Node> row = new TableRow<>();
				row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>()
				{
					@Override
					public void handle(MouseEvent event)
					{
						final int index = row.getIndex();
						if (index >= 0 && index < planTable.getItems().size()
								&& planTable.getSelectionModel().isSelected(index))
						{
							planTable.getSelectionModel().clearSelection();
							event.consume();
						}
					}
				});
				return row;
			}
		});
		
		if(!client.person.isAdmin())
		{
			newUserButton.setVisible(false);
			setStatusButton.setVisible(false);
		}
	}

	private void getLists(ComboBox<Integer> plans, ArrayList<BP_Node> dep_plans)
	{
		List<BP_Node> allPlans = client.getBP();
		for (BP_Node node : allPlans)
		{
			if (node.department.equals(client.person.department))
			{
				plans.getItems().add(node.year);
				System.out.println(plans.getItems().get(1).intValue());
				dep_plans.add(node);
			}
		}
	}

	@FXML
	void OnClone(ActionEvent event)
	{
		int clone_year = -1;
		if (selectYear.getValue() == null) // if they didn't use the dropdown
		{
			clone_year = onEdit(); // get the highlighted table row
		} else // otherwise, get the dropdown value
		{
			clone_year = selectYear.getValue();
		}

		if (clone_year != -1)
		{
			client.requestBusinessPlan(client.person.getDepartment(), clone_year);
			main.showClone(client.business, client, dep_plans, false);
		}
	}

	private int onEdit()
	{
		int year = -1;
		// check the table's selected item and get selected item
		if (planTable.getSelectionModel().getSelectedItem() != null)
		{
			BP_Node selected_plan = planTable.getSelectionModel().getSelectedItem();
			year = selected_plan.getYear();
		}
		return year;
	}

	@FXML
	void OnDelete(ActionEvent event)
	{
		int delete_year = -1;
		if (selectYear.getValue() == null)
		{
			delete_year = onEdit();
		} else
		{
			delete_year = selectYear.getValue();
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
					selectYear.getItems().remove(index);
					dep_plans.remove(index);
				} else if (node.getDepartment().equalsIgnoreCase(client.person.getDepartment()))
				{
					index++;
				}
			}
		}
	}

	private void deletePlan(int ye, ArrayList<BP_Node> dep_plans)
	{
		for (BP_Node plan : dep_plans)
		{
			if (plan.year == ye)
			{
				client.deleteBP(plan);
			}
		}
		main.login(client);
	}

	@FXML
	void OnEdit(ActionEvent event)
	{
		int year = -1;
		if (selectYear.getValue() == null)
		{
			year = onEdit();
		} else
		{
			year = selectYear.getValue();
		}

		if (year != -1)
		{
			client.requestBusinessPlan(client.person.getDepartment(), year);
			main.showEdit(client);
		}
	}

	@FXML
	void OnLogout(ActionEvent event)
	{
		main.showLogin();
	}

	@FXML
	void OnMakeNewPlan(ActionEvent event)
	{
		main.showClone(client.business, client, dep_plans, true);
	}

	@FXML
	void OnSetStatus(ActionEvent event)
	{
		// set up the set status and add new user pages

		int year = -1;
		if (selectYear.getValue() == null)
		{
			year = onEdit();
		} else
		{
			year = selectYear.getValue();
		}
		
		if (year != -1)
		{
			main.showSetStatus(client,year);
		}
	}

	@FXML
	void OnAddNewUser(ActionEvent event)
	{
		main.showNewUser(client);
	}
}