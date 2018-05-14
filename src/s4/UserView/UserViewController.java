package s4.UserView;

import java.util.ArrayList;
import java.util.Arrays;

import Client.Client;
import Server.Person;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import s4.Main;

public class UserViewController
{

	@FXML
	private TableView<Person> tableViewer;

	@FXML
	private TextField usernameBox;

	@FXML
	private TextField passBox;

	@FXML
	private CheckBox Admin;

	@FXML
	private Button addButton;

	@FXML
	private Button delButton;

	Main main;
	Client client;

	@FXML
	void deleteButtonAction(ActionEvent event)
	{
		
		if (tableViewer.getSelectionModel().getSelectedItem()!= null)
		{
			Person deletee = tableViewer.getSelectionModel().getSelectedItem();
			client.proxy.getPeople().remove(deletee);
			client.proxy.writeDisk();
			client.proxy.readDisk();
			main.showSeeUser(client);
		}
	}

	@FXML
	void backAction(ActionEvent event)
	{
		client.proxy.writeDisk();
		main.showHome(client);
	}

	public void setMain(Main main, Client client)
	{
		this.main = main;
		this.client = client;
	}

	/// add a user
	@FXML
	public void addButtonAction(ActionEvent event)
	{

		String user = usernameBox.getText();
		String pass = passBox.getText();
		boolean aStatus = Admin.isSelected();

		if (user.length() > 0 && pass.length() > 0)
		{
			System.out.println("add");
			client.addPeople(user, pass, client.person.getDepartment(), aStatus);
			client.proxy.writeDisk();
			main.showSeeUser(client);
		}

	}

	public void setUp()
	{
		ArrayList<Person> people = new ArrayList<Person>(client.getPeople());
		for (int j = 0; j < people.size(); j++)
		{
			if (!people.get(j).department.equals(client.person.getDepartment()))
			{
				people.remove(j);
				j--;
			}
		}

		ObservableList<Person> depPeople = FXCollections.observableArrayList(people);
		;

		tableViewer.getItems().clear();
		tableViewer.setItems(depPeople);
		TableColumn<Person, String> name_Column = new TableColumn<Person, String>("Username");
		name_Column.setCellValueFactory(new Callback<CellDataFeatures<Person, String>, ObservableValue<String>>()
		{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Person, String> n)
			{
				return new ReadOnlyStringWrapper(n.getValue().getUsername());
			}
		});

		TableColumn<Person, String> dep_Column = new TableColumn<Person, String>("Department");
		dep_Column.setCellValueFactory(new Callback<CellDataFeatures<Person, String>, ObservableValue<String>>()
		{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Person, String> n)
			{
				return new ReadOnlyStringWrapper(n.getValue().getDepartment());
			}
		});

		TableColumn<Person, String> admin_Column = new TableColumn<Person, String>("Username");
		admin_Column.setCellValueFactory(new Callback<CellDataFeatures<Person, String>, ObservableValue<String>>()
		{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Person, String> n)
			{
				if (n.getValue().isAdmin())
				{
					return new ReadOnlyStringWrapper("Yes");
				} else
				{
					return new ReadOnlyStringWrapper("No");
				}
			}
		});

		tableViewer.getColumns().addAll(Arrays.asList(name_Column, dep_Column, admin_Column));

		// Deselect the row if the row has already been clicked
		tableViewer.setRowFactory(new Callback<TableView<Person>, TableRow<Person>>()
		{
			@Override
			public TableRow<Person> call(TableView<Person> plan_table_row)
			{
				final TableRow<Person> row = new TableRow<>();
				row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>()
				{
					@Override
					public void handle(MouseEvent event)
					{
						final int index = row.getIndex();
						if (index >= 0 && index < tableViewer.getItems().size()
								&& tableViewer.getSelectionModel().isSelected(index))
						{
							tableViewer.getSelectionModel().clearSelection();
							event.consume();
						}
					}
				});
				return row;
			}
		});
	}

}