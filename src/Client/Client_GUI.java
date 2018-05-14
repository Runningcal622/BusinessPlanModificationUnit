package Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import Server.BP_Node;
import Server.BusinessEntity;
import Server.CentrePlanFactory;
import Server.Person;
import Server.Server;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Client_GUI extends Application
{
	/// get the process roling
	// same as all javafx applications
	public static void main(String[] args)
	{
		launch(args);
	}

	/// to referance and change scenes no matter where in the program
	Stage mainStage;
	Scene login;
	Scene home;
	Scene edit;
	Scene addUser;

	Client client = new Client(new Server());

	// Clone Data for if the client wants to clone a plan
	ArrayList<String> data = new ArrayList<String>();
	// current text area that you can edit
	TextArea cur;
	// centre plan factory used to create new entities
	CentrePlanFactory centreHead1 = new CentrePlanFactory();
	// table view of all the business plans
	TableView<BP_Node> plan_table;

	public Client_GUI()
	{
	}

	// LOGIN PAGE METHODS
	/// login screen
	private void makeLogin(GridPane mainBorder)
	{
		/// logo goes here
		/// use png and include it in your project
//		Image logo = new Image("logo.png");
//		ImageView logoView = new ImageView();
//		logoView.setPreserveRatio(true);
//		logoView.setFitHeight(400);
//		logoView.setImage(logo);

		//GridPane.setColumnIndex(logoView, 0);
		GridPane logSide = new GridPane();

		GridPane.setColumnIndex(logSide, 1);
		mainBorder.getChildren().addAll(/*logoView,*/ logSide);

		// grid pane on the right
		Text header = new Text("Login");
		TextField username = new TextField();
		username.setPromptText("Enter username...");
		PasswordField pass = new PasswordField();
		pass.setPromptText("Enter password...");
		TextField portNum = new TextField();
		portNum.setPromptText("Enter port number...");

		HBox radioButtons = new HBox();
		RadioButton local = new RadioButton("Localhost");
		local.setSelected(true);
		RadioButton selectHost = new RadioButton("Select a host");
		// only select one radio button at a time
		local.setOnAction(e -> selectHost.setSelected(false));
		selectHost.setOnAction(e -> local.setSelected(false));
		radioButtons.getChildren().addAll(local, selectHost);

		HBox log_in = new HBox();
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e -> cancelAction(username, pass));
		Button logMe = new Button("Login");
		logMe.setOnAction(
				e -> loginAction(username.getText(), pass.getText(), local.isSelected(), selectHost.isSelected()));

		log_in.getChildren().addAll(cancel, logMe);
		GridPane.setRowIndex(header, 0);
		GridPane.setRowIndex(username, 1);
		GridPane.setRowIndex(pass, 2);
		GridPane.setRowIndex(radioButtons, 3);
		GridPane.setRowIndex(portNum, 4);
		GridPane.setRowIndex(log_in, 5);
		logSide.getChildren().addAll(header, username, pass, radioButtons, portNum, log_in);

	}

	/// logs in user, otherwise no change
	private void loginAction(String username, String password, boolean local, boolean selectHost)
	{
		client.login(username, password);
		if (client.person != null)
		{
			/// makes homepage
			makeHome(client.person);
			mainStage.setScene(home);
			mainStage.show();
		}

	}

	// HOME PAGE METHODS
	// cancel button on login screen resets prompt text
	private void cancelAction(TextField username, TextField pass)
	{
		username.setPromptText("Enter username...");
		pass.setPromptText("Enter password...");
	}

	private void editPlan(int ye, ArrayList<BP_Node> dep_plans)
	{
		for (BP_Node plan : dep_plans)
		{
			if (plan.year == ye)
			{
				client.business = plan;
			}
		}
		makeEditScene();
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
		makeHome(client.person);
		mainStage.setScene(home);
		mainStage.show();
	}

	private int clonePlan(int ye, String department, ArrayList<BP_Node> dep_plans)
	{
		// make dialog box for getting the new year and status of the plan
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(mainStage);

		// vBox for the two inputs
		VBox dialogVbox = new VBox();
		TextField new_year_field = new TextField();
		new_year_field.setPromptText("New year...");

		ComboBox<String> editable = new ComboBox<String>();
		editable.setPromptText("edit status...");
		editable.getItems().addAll("editable", "not editable");

		HBox buttons = new HBox();

		Button ok = new Button("Ok");
		ok.setOnAction(e ->
		{
			data = getCloneData(new_year_field, editable);
			dialog.close();
		});

		Button cancel = new Button("Cancel");
		cancel.setOnAction(e -> dialog.close());

		buttons.getChildren().addAll(ok, cancel);

		// add everything to the vbox
		dialogVbox.getChildren().addAll(new_year_field, editable, buttons);

		// set the scene
		Scene dialogScene = new Scene(dialogVbox, 300, 200);
		dialog.setScene(dialogScene);
		dialog.showAndWait();

		int new_year;
		if (data.get(0).length() == 0)
		{
			new_year = -1;
		} else
		{
			try {
			new_year = Integer.parseInt(data.get(0));
			}
			catch (Exception e)
			{
				new_year=-1;
			}
		}
		if (new_year<1819 || new_year>2050)
		{
			new_year=-1;
		}
		String edit = data.get(1);
		if (ye != new_year && new_year != -1)
		{
			if (ye != -1)
			{
				for (BP_Node plan : dep_plans)
				{
					if (plan.year == ye)
					{
						boolean set_editable = false;
						if (edit == "editable" || edit == null)
						{
							set_editable = true;
						}
						client.make_CloneBP(ye, department, set_editable, new_year);
					}
				}
			}

			else
			{
				boolean set_editable = false;
				if (edit == "editable" || edit == null)
				{
					set_editable = true;
				}
				client.make_BlankBP(new_year, department, set_editable);
			}
		}
		return new_year;
	}

	private ArrayList<String> getCloneData(TextField new_year, ComboBox<String> editable)
	{
		ArrayList<String> data = new ArrayList<String>();
		data.add(new_year.getText());
		data.add(editable.getValue());
		return data;
	}

	private void set_BPStatus(int ye, String department, ArrayList<BP_Node> dep_plans)
	{
		// make dialog box for getting the new year and status of the plan
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(mainStage);

		VBox dialogVbox = new VBox();
		// combo box for editable status
		ComboBox<String> editable = new ComboBox<String>();
		editable.setPromptText("edit status...");
		editable.getItems().addAll("editable", "not editable");

		HBox buttons = new HBox();

		Button ok = new Button("Ok");
		ok.setOnAction(e ->
		{
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
		makeHome(client.person);
		mainStage.setScene(home);
		mainStage.show();
	}

	/// adding a user
	private void addAction(String name, String pass, String dep, String admin)
	{
		boolean isAdmin;
		if (admin.toLowerCase().equals("admin") || admin.toLowerCase().equals("y"))
		{
			isAdmin = true;
			client.addPeople(name, pass, dep, isAdmin);
			mainStage.setScene(home);
		} else if (admin.toLowerCase().equals("general member"))
		{
			isAdmin = false;
			client.addPeople(name, pass, dep, isAdmin);
			mainStage.setScene(home);
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
				dep_plans.add(node);
			}
		}
	}

	/// put all the subentities in a tree viewer
	private void makeEditScene()
	{
		BusinessEntity plan = client.business.getEntity();
		BP_Node currentNode = client.business;
		AnchorPane edits = new AnchorPane();
		Button back = new Button("Back");
		back.setOnAction(e -> backAction(plan));

		// text of what plan you are editing/viewing
		Text top = new Text(
				plan.getEntityTitle() + " for " + client.business.department + " in " + client.business.year);
		/// this is how the plan component visual display is organized
		TreeItem<BusinessEntity> first = new TreeItem<BusinessEntity>(plan);
		// make the tree always fully expanded for convienence
		first.setExpanded(true);
		// the view
		TreeView<BusinessEntity> planparts = new TreeView<BusinessEntity>(showPlan(plan.getSubentity(0), first));
		/// determining which element of the view is selected
		planparts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,
				newValue) -> clickedOn(newValue, getEntity(newValue, plan), edits, planparts, currentNode.editable));
		TextArea curText = new TextArea();
		edits.getChildren().addAll(planparts, curText, back, top);

		// this organizes the scene
		AnchorPane.setRightAnchor(back, (double) 50);
		AnchorPane.setTopAnchor(back, (double) 50);
		AnchorPane.setLeftAnchor(planparts, (double) 10);
		AnchorPane.setRightAnchor(curText, (double) 10);
		AnchorPane.setTopAnchor(top, (double) 40);
		AnchorPane.setRightAnchor(top, (double) 200);
		if (currentNode.editable == false)
		{
			// if not editable no saving of changes
			back.setOnAction(e -> goBack());
		}

		curText.setTranslateY(150);
		edit = new Scene(edits, 1000, 800);
		mainStage.setScene(edit);
	}

	/// returns the businessEntity being edited, to know which entity to make
	/// changes to
	private BusinessEntity getEntity(TreeItem<BusinessEntity> newValue, BusinessEntity plan)
	{
		// Finds when the entity titles are equal
		if (plan != null && newValue.getValue() != null)
		{
			// each treeitem id is a unique identifier of each BusinessEntity
			if (plan.getTreeItemID() == newValue.getValue().getTreeItemID())
			{
				return plan;
			}
			ArrayList<BusinessEntity> children = plan.getSubentities();
			BusinessEntity res = null;
			/// recursive call the subentities
			for (int i = 0; res == null && i < children.size(); i++)
			{
				res = getEntity(newValue, children.get(i));
			}
			return res;
		}
		return null;
	}

	// making the home screen once a user has been logged in
	private void makeHome(Person person)
	{
		// creates the home page for the user

		// make outer border pane
		BorderPane outer_border = new BorderPane();
		HBox outer_topPanel = new HBox();
		outer_border.setTop(outer_topPanel);

		// outer top panel
		HBox outer_topPanel_info = new HBox();
		Text home_text = new Text("Home > ");
		Text department_text = new Text(client.person.getDepartment());

		// Logout button
		Button logout = new Button("Logout");
		logout.setOnAction(e -> mainStage.setScene(login));
		AnchorPane logout_anchor = new AnchorPane();
		logout_anchor.getChildren().addAll(logout);
		AnchorPane.setTopAnchor(logout, (double) 0);
		AnchorPane.setRightAnchor(logout, (double) 10);

		outer_topPanel_info.getChildren().addAll(home_text, department_text);

		outer_topPanel.getChildren().addAll(outer_topPanel_info, logout_anchor);
		HBox.setHgrow(outer_topPanel_info, Priority.ALWAYS);
		HBox.setHgrow(logout, Priority.ALWAYS);

		// Set up inner panel
		BorderPane inner_border = new BorderPane();
		HBox inner_topPanel = new HBox();
		inner_border.setTop(inner_topPanel);

		// Selection box for plans
		ComboBox<Integer> plans = new ComboBox<Integer>();
		plans.setPromptText("Select year...");
		ArrayList<BP_Node> dep_plans = new ArrayList<BP_Node>();
		getLists(plans, dep_plans);

		// inner inner border holds the add new user button in top panel and table of
		// plans in center
		BorderPane inner_inner_border = new BorderPane();
		HBox inner_inner_topPanel = new HBox();
		inner_inner_border.setTop(inner_inner_topPanel);

		inner_border.setCenter(inner_inner_border);

		// table
		plan_table = new TableView<BP_Node>();
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

		plan_table.getColumns().addAll(Arrays.asList(title_column, year_column, status_column));
		inner_inner_border.setCenter(plan_table);

		// Edit/View Button
		Button editPlan = new Button("Edit/View");

		editPlan.setOnAction(e ->
		{
			int year = -1;
			if (plans.getValue() == null)
			{
				year = onEdit();
			} else
			{
				year = plans.getValue();
			}

			if (year != -1)
			{
				editPlan(year, dep_plans);
			}
		});

		// Delete Button: deletes the indicated plan
		Button deletePlan = new Button("Delete");
		deletePlan.setOnAction(e ->
		{
			int delete_year = -1;
			if (plans.getValue() == null)
			{
				delete_year = onEdit();
			} else
			{
				delete_year = plans.getValue();
			}

			if (delete_year != -1)
			{
				List<BP_Node> allPlans = client.getBP();
				int index = 0;
				for (BP_Node node : allPlans)
				{
					if (node.getDepartment().equalsIgnoreCase(client.person.getDepartment())
							&& node.year == delete_year)
					{
						deletePlan(delete_year, dep_plans);
						plans.getItems().remove(index);
						dep_plans.remove(index);
					} else if (node.getDepartment().equalsIgnoreCase(client.person.getDepartment()))
					{
						index++;
					}
				}
			}
		});

		// Clone Button: clones a business plan
		Button clonePlan = new Button("Clone/Make Plan");
		clonePlan.setOnAction(e ->
		{
			int clone_year = -1;
			if (plans.getValue() == null) // if they didn't use the dropdown
			{
				clone_year = onEdit(); // get the highlighted table row
			} else // otherwise, get the dropdown value
			{
				clone_year = plans.getValue();
			}

			// make a new plan if no plan was selected, or clone the plan selected
			int new_year = clonePlan(clone_year, client.person.getDepartment(), dep_plans);

			int index = 0;
			boolean found = false;
			while (index < dep_plans.size() && !found)
			{
				if (dep_plans.get(index).getYear() == new_year)
				{
					found = true;
				} else
				{
					index++;
				}
			}
			// if the new year doesn't exist and was actually inputed by the user
			if (!found && new_year != -1)
			{
				// edit the plan
				client.requestBusinessPlan(client.person.getDepartment(), new_year);
				dep_plans.add(client.business);
				editPlan(new_year, dep_plans);
			} else
			{
				System.out.println("Error cloning plan");
			}
		});

		inner_topPanel.getChildren().add(plans);

		if (person.admin)
		{
			// set up the set status and add new user pages
			Button set_status = new Button("Set Status");
			set_status.setOnAction(e ->
			{
				int year = -1;
				if (plans.getValue() == null)
				{
					year = onEdit();
				} else
				{
					year = plans.getValue();
				}

				if (year != -1)
				{
					set_BPStatus(year, client.person.getDepartment(), dep_plans);
				}
			});
			inner_topPanel.getChildren().add(set_status);

			Button addnew = new Button("Add new user");
			addnew.setOnAction(e -> newUser());
			inner_inner_topPanel.getChildren().add(addnew);
		}

		// set inner border as the center for outer border
		inner_topPanel.getChildren().addAll(editPlan, deletePlan, clonePlan);
		outer_border.setCenter(inner_border);
		home = new Scene(outer_border, 500, 500);
	}

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

	/// new user scene
	private void newUser()
	{
		// set up the new user info
		BorderPane aUser = new BorderPane();
		VBox info = new VBox();
		/// details for a new user
		TextField name = new TextField();
		name.setPromptText("Username...");
		TextField pass = new TextField();
		pass.setPromptText("Password...");
		TextField dep = new TextField();
		dep.setPromptText("Department...");
		ComboBox<String> admin = new ComboBox<String>();
		admin.setPromptText("Member Status...");
		admin.getItems().add("Admin");
		admin.getItems().add("General Member");
		Button add = new Button("Add");
		add.setOnAction(e -> addAction(name.getText(), pass.getText(), dep.getText(), admin.getValue()));

		info.getChildren().addAll(name, pass, dep, admin, add);
		aUser.setCenter(info);
		addUser = new Scene(aUser, 300, 500);
		mainStage.setScene(addUser);
	}

	// EDIT PLAN METHODS

	/// this takes a tree view and a business entity and recursively goes through
	/// the plan until it
	// has all components
	private TreeItem<BusinessEntity> showPlan(BusinessEntity plan, TreeItem<BusinessEntity> treeItem)
	{
		TreeItem<BusinessEntity> smallest = new TreeItem<BusinessEntity>(plan);
		smallest.setExpanded(true);
		treeItem.getChildren().add(smallest);
		if (smallest.getValue() != null && smallest.getValue().getSubentities().size() > 0)
		{
			for (BusinessEntity subs : smallest.getValue().getSubentities())
			{
				showPlan(subs, smallest);
			}
		}

		return treeItem;
	}

	private void clickedOn(TreeItem<BusinessEntity> newValue, BusinessEntity plan, AnchorPane edits,
			TreeView<BusinessEntity> planparts, boolean editable)
	{
		if (plan != null)
		{
			setUpForSelect(newValue, plan, edits, planparts, editable);
		}
	}

	/// this sets up the buttons on each choice
	private void setUpForSelect(TreeItem<BusinessEntity> newValue, BusinessEntity plan, AnchorPane edits,
			TreeView<BusinessEntity> planparts, boolean editable)
	{
		// text area to type in
		cur = (TextArea) edits.getChildren().get(1);
		cur.setText(plan.getStatement(0).getStatement());
		// buttons and actions
		Button save = new Button("Save Statement");
		save.setOnAction(e -> saveAction(plan, cur.getText()));
		Button cancel = new Button("Cancel");

		cancel.setOnAction(e -> cancelAction(plan, cur.getText()));
		TextField eTitle = new TextField(plan.getEntityTitle());
		Button changeTitle = new Button("Click to change");
		changeTitle.setOnAction(e -> changeTitle(plan, edits, planparts, eTitle.getText()));

		Button addNode = new Button("Add new component descending");
		Button remNode = new Button("Remove this component");
		addNode.setOnAction(e -> addNew(newValue, plan, edits, planparts));
		remNode.setOnAction(e -> RemoveComp(plan, edits, planparts));
		edits.getChildren().addAll(save, cancel, addNode, remNode, eTitle, changeTitle);

		if (editable == false)
		{
			// if not editable, no saving
			addNode.setDisable(true);
			remNode.setDisable(true);
			changeTitle.setDisable(true);
			save.setDisable(true);
			cancel.setOnAction(e -> goBack());

		}
		/// orient buttons
		AnchorPane.setBottomAnchor(cancel, (double) 50);
		AnchorPane.setLeftAnchor(cancel, (double) 150);
		AnchorPane.setBottomAnchor(save, (double) 225);
		AnchorPane.setRightAnchor(save, (double) 25);

		AnchorPane.setBottomAnchor(addNode, (double) 225);
		AnchorPane.setRightAnchor(addNode, (double) 300);
		AnchorPane.setBottomAnchor(remNode, (double) 225);
		AnchorPane.setRightAnchor(remNode, (double) 150);
		AnchorPane.setTopAnchor(eTitle, (double) 100);
		AnchorPane.setRightAnchor(eTitle, (double) 250);
		AnchorPane.setTopAnchor(changeTitle, (double) 100);
		AnchorPane.setRightAnchor(changeTitle, (double) 150);

	}

	private void goBack()
	{
		// merely goes back to home scene
		mainStage.setScene(home);
	}

	private void changeTitle(BusinessEntity plan, AnchorPane edits, TreeView<BusinessEntity> planparts,
			String newEntityTitle)
	{
		// new title
		plan.setEntityTitle(newEntityTitle);
		/// need the root of the tree
		BusinessEntity head = getHead(plan);
		client.business.entity = head;
		/// redraw the scene
		makeEditScene();
	}

	/// removes the component selected
	private void RemoveComp(BusinessEntity plan, AnchorPane edits, TreeView<BusinessEntity> planparts)
	{
		// takes component out of parent entities subentities
		if (plan.getParentEntity() != null)
		{
			/// remove current entity from parents children
			ArrayList<BusinessEntity> entities = plan.getParentEntity().getSubentities();
			entities.remove(plan);
			// get root
			BusinessEntity head = getHead(plan);
			// set parent to not have the current node as a child
			plan.getParentEntity().setSubentities(entities);
			// new reevie without the node
			planparts = new TreeView<BusinessEntity>(showPlan(plan, new TreeItem<BusinessEntity>()));
			makeEditScene();
			client.business.entity = head;
		}

	}

	/// this returns the top of the business plan given a node anywher in the tree
	private BusinessEntity getHead(BusinessEntity plan)
	{
		if (plan.getParentEntity() == null)
		{
			return plan;
		}
		return getHead(plan.getParentEntity());
	}

	private void addNew(TreeItem<BusinessEntity> newValue, BusinessEntity plan, AnchorPane edits,
			TreeView<BusinessEntity> planparts)
	{
		BusinessEntity new_plan = newValue.getValue().createNewSubentity();
		// set the factory to be the same depth as the factory in centreplanfactory
		new_plan.setEntityFactory(centreHead1.getFactoryFromIndex(new_plan.getTree_level() + 1));
		/// new tree view
		planparts = new TreeView<BusinessEntity>(showPlan(new_plan, newValue));
		makeEditScene();
		client.business.entity = getHead(plan);
	}

	private void backAction(BusinessEntity plan)
	{
		// same action as other back action
		makeHome(client.person);
		mainStage.setScene(home);
	}

	private void cancelAction(BusinessEntity plan, String text)
	{
		// dialog box to ask if you want those changes saved
		Dialog<?> sure = new Dialog<>();
		sure.setTitle("Cancel");
		sure.setContentText("Do you want to save changes?");
		ButtonType yes = new ButtonType("Yes");
		ButtonType no = new ButtonType("No");
		sure.getDialogPane().getButtonTypes().addAll(yes, no);
		// close button
		sure.getDialogPane().getButtonTypes().add(new ButtonType("Close", ButtonData.CANCEL_CLOSE));
		sure.setOnCloseRequest(e -> sure.close());
		// wait for answer
		Optional<ButtonType> result = (Optional<ButtonType>) sure.showAndWait();
		// answer
		if (result.get() == yes)
		{
			/// if yes save the plan
			plan = client.business.entity;
			saveAction(plan, text);
			client.uploadBP(client.business);
			client.proxy.writeDisk();
			client.proxy.readDisk();
			makeHome(client.person);
			mainStage.setScene(home);
		} else if (result.get() == no)
		{
			// if no just change the scene to home
			makeHome(client.person);
			mainStage.setScene(home);
		}

	}

	private void saveAction(BusinessEntity plan, String newStatement)
	{
		// set the statement equal to the changed statement
		plan.getStatement(0).setStatement(newStatement);
		// upload
		client.uploadBP(client.business);
		// write to server and read
		client.proxy.writeDisk();
		client.proxy.readDisk();
		makeEditScene();
	}

	/// must have reads from disk and then sets first scene
	@Override
	public void start(Stage stage) throws Exception
	{
		// read the disk to start
		client.proxy.readDisk();
		// main pane
		GridPane mainBorder = new GridPane();

		// first screen
		login = new Scene(mainBorder, 900, 400);
		/// makes login page
		makeLogin(mainBorder);
		/// stage finalization
		mainStage = stage;
		mainStage.setScene(login);
		mainStage.setTitle("Login");
		// no resizing the window
		mainStage.setResizable(false);
		mainStage.show();

	}

}
