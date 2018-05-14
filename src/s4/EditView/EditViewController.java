package s4.EditView;

import java.util.ArrayList;
import java.util.LinkedList;

import Client.Client;
import Server.BP_Node;
import Server.BusinessEntity;
import Server.CentrePlanFactory;
import Server.Person;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldListCell;
import s4.ViewInterface;

public class EditViewController
{

	@FXML
	private Button addComButton;
	@FXML
	private Button remComButton;
	@FXML
	public TreeView<BusinessEntity> tree;

	@FXML
	public TextArea textArea;

	@FXML
	private Button backButton;

	@FXML
	private Button logoutButton;

	@FXML
	public TextField entityTitleField;

	@FXML
	private Button changeTitleButton;

	@FXML
	private Button addCompButton;

	@FXML
	private Button delCompButton;

	@FXML
	private Button saveStatementButton;

	@FXML
	private ListView<String> commentView;

	private Client client;
	private ViewInterface main;

	// centre plan factory used to create new entities
	CentrePlanFactory centreHead1 = new CentrePlanFactory();

	public void setMain(ViewInterface m, Client c)
	{
		this.main = m;
		this.client = c;

	}

	private void hide()
	{
		textArea.setVisible(false);
		entityTitleField.setVisible(false);
		changeTitleButton.setVisible(false);
		saveStatementButton.setVisible(false);
		addCompButton.setVisible(false);
		delCompButton.setVisible(false);
		addComButton.setVisible(false);
		remComButton.setVisible(false);
		commentView.setVisible(false);

		/// this is how the plan component visual display is organized
		TreeItem<BusinessEntity> first = new TreeItem<BusinessEntity>(client.business.entity);
		// make the tree always fully expanded for convienence
		first.setExpanded(true);
		// the view
		tree.setRoot(showPlan(client.business.entity.getSubentity(0), first));

	}

	public void setUp()
	{
		BP_Node currentNode = client.business;
		BusinessEntity plan = currentNode.entity;
		textArea.setVisible(false);
		entityTitleField.setVisible(false);
		changeTitleButton.setVisible(false);
		saveStatementButton.setVisible(false);
		addCompButton.setVisible(false);
		delCompButton.setVisible(false);
		addComButton.setVisible(false);
		remComButton.setVisible(false);
		commentView.setVisible(false);
		commentView.setEditable(true);

		commentView.setCellFactory(TextFieldListCell.forListView());

		/// this is how the plan component visual display is organized
		TreeItem<BusinessEntity> first = new TreeItem<BusinessEntity>(plan);

		// make the tree always fully expanded for convienence
		first.setExpanded(true);
		// the view
		tree.setRoot(showPlan(plan.getSubentity(0), first));
		tree.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> clickedOn(newValue, getEntity(newValue, plan), tree));
		backButton.setOnAction(e -> backAction());
		logoutButton.setOnAction(e -> logout());

	}

	private void logout()
	{
		for (BP_Node plan : client.getBP())
		{
			if (plan.department.equals(client.person.getDepartment()))
			{
				client.person.unUpdate(plan);
			}
		}
		client.proxy.writeDisk();
		main.showLogin();
	}

	/// saves plan and goes back to home
	private void backAction()
	{
		client.writeLocalBP(client.business);
		client.proxy.writeDisk();
		main.showHome(client);
	}

	// when a component of the plan is selected
	private void clickedOn(TreeItem<BusinessEntity> newValue, BusinessEntity plan, TreeView<BusinessEntity> comp)
	{

		if (client.business.editable)
		{
			textArea.setVisible(true);
			entityTitleField.setVisible(true);
			changeTitleButton.setVisible(true);
			saveStatementButton.setVisible(true);
			addCompButton.setVisible(true);
			delCompButton.setVisible(true);
			addComButton.setVisible(true);
			remComButton.setVisible(true);
			// commentTree.setVisible(true);

			// set up
			if (newValue != null)
			{
				System.out.println(newValue.getValue().getStatement(0).getStatement());
				System.out.println(newValue.getValue().getTreeItemID());
				textArea.setText(newValue.getValue().getSentence());
				entityTitleField.setText(newValue.getValue().getEntityTitle());
				changeTitleButton.setOnAction(e -> changeETitle(newValue, entityTitleField.getText()));
				saveStatementButton.setOnAction(e -> saveStatement(newValue, textArea.getText()));
				addCompButton.setOnAction(e -> addComp(newValue));
				delCompButton.setOnAction(e -> delComp(newValue));
				commentView.setVisible(true);
				commentView.getItems().clear();
				commentView.getItems().addAll(newValue.getValue().getComments());
				addComButton.setOnAction(e -> addComment(newValue.getValue()));
				remComButton.setOnAction(e -> remComment(newValue.getValue()));
				commentView.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>()
				{
					@Override
					public void handle(ListView.EditEvent<String> t)
					{
						commentView.getItems().set(t.getIndex(), client.person.getUsername()+": "+t.getNewValue());
						newValue.getValue().getComments().remove(t.getIndex());
						newValue.getValue().getComments().add(t.getIndex(), client.person.getUsername()+": "+t.getNewValue());
						client.proxy.writeDisk();
						client.proxy.readDisk();
						System.out.println("setOnEditCommit");
					}

				});
			} else
			{
				hide();
			}
		} else
		{
			textArea.setVisible(true);
			entityTitleField.setVisible(true);
			textArea.setText(newValue.getValue().getSentence());
			entityTitleField.setText(newValue.getValue().getEntityTitle());

		}
		
		

	}

	private void action()
	{
		System.out.println(commentView.getSelectionModel().selectedIndexProperty().getValue());
	}

	private void addComment(BusinessEntity entity)
	{
		int indexToAdd = commentView.getSelectionModel().getSelectedIndex();
		if (indexToAdd != -1)
		{
			entity.getComments().add(indexToAdd+1, "New comment here");
		}
		updatePeople(client.business);
		commentView.getItems().clear();
		commentView.getItems().addAll(entity.getComments());
		
		client.proxy.writeDisk();
	}

	private void remComment(BusinessEntity entity)
	{
		int indexToAdd = commentView.getSelectionModel().getSelectedIndex();
		if (indexToAdd != -1)
		{
			entity.getComments().remove(indexToAdd);
		}
		updatePeople(client.business);
		commentView.getItems().clear();
		commentView.getItems().addAll(entity.getComments());
		client.proxy.writeDisk();
	}

	private void delComp(TreeItem<BusinessEntity> newValue)
	{
		ArrayList<BusinessEntity> subs;
		BusinessEntity parent = newValue.getValue().getParentEntity();
		if (parent != null)
		{
			subs = parent.getSubentities();
			subs.remove(newValue.getValue());
			parent.setSubentities(subs);
		}
		updatePeople(client.business);
		client.proxy.writeDisk();
		hide();
	}

	// updates all users who should be
	private void updatePeople(BP_Node node)
	{
		LinkedList<Person> aList = client.proxy.getPeople();
		for (Person p : aList)
		{
			if (p.department.equals(client.person.department) && !p.getUsername().equals(client.person.getUsername()))
			{
				p.update(node);
			}
		}
	}

	private void addComp(TreeItem<BusinessEntity> newValue)
	{
		if (newValue.getValue().getSubentityFactory() != null)
		{
			BusinessEntity new_plan = newValue.getValue().createNewSubentity();
			// set the factory to be the same depth as the factory in centreplanfactory
			new_plan.setEntityFactory(centreHead1.getFactoryFromIndex(new_plan.getTree_level() + 1));
			/// new tree view
			BusinessEntity plan = client.business.entity;
			client.business.entity = getHead(plan);
			updatePeople(client.business);
			client.proxy.writeDisk();
			client.proxy.readDisk();
			hide();
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

	private void saveStatement(TreeItem<BusinessEntity> newValue, String text)
	{
		System.out.println("Called");
		System.out.println(newValue.getValue().getSentence());
		BusinessEntity current = newValue.getValue();
		if (newValue.getValue().getTreeItemID() == (current.getTreeItemID()))
		{

			current.setSentence(text);
			updatePeople(client.business);
			client.proxy.writeDisk();
			client.proxy.readDisk();
		}
		hide();
	}

	private void changeETitle(TreeItem<BusinessEntity> newValue, String text)
	{
		BusinessEntity current = newValue.getValue();
		current.setEntityTitle(text);
		updatePeople(client.business);
		client.proxy.writeDisk();
		client.proxy.readDisk();
		hide();
	}

	/// returns the businessEntity being edited, to know which entity to make
	/// changes to
	private BusinessEntity getEntity(TreeItem<BusinessEntity> newValue, BusinessEntity plan)
	{
		if (newValue != null)
		{
			// Finds when the entity titles are equal
			if (plan != null && newValue.getValue() != null)
			{
				// each treeitem id is a unique identifier of each BusinessEntity
				if (plan.getTreeItemID() == newValue.getValue().getTreeItemID())
				{
					System.out.println(plan.getTreeItemID());
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
		}
		return null;
	}

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

}
