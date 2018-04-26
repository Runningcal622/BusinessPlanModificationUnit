package GuiTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import Client.Client;
import Server.BusinessEntity;
import Server.Server;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.moznion.random.string.RandomStringGenerator;
import s4.Main;
import s4.EditView.EditViewController;

@ExtendWith(ApplicationExtension.class)
public class EditViewControllerTest // extend ApplicationTest

{
	BorderPane viewGoesHere = new BorderPane();
	Main main = new Main();
	Stage stage;
	EditViewController cont;
	String id;
	RandomStringGenerator gener = new RandomStringGenerator(7);

	@Start
	public void onStart(Stage stage) throws IOException
	{
		this.stage = stage;

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("EditView/EditView.fxml"));
		try
		{
			viewGoesHere.setCenter(loader.load());
			cont = loader.getController();
			Server server = new Server();
			Client client = new Client(server);
			server.readDisk();
			client.login("Caleb", "4C");
			client.requestBusinessPlan("Math", 2016);
			cont.setMain(main, client);
			cont.setUp();
			Scene s = new Scene(viewGoesHere);
			stage.setScene(s);
			stage.show();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("here");
	}

	@Test
	public void please(FxRobot robo)
	{
		// assertThat();
		// stage.getScene().getRoot();
		TreeView<BusinessEntity> head = cont.tree;
		TreeItem<BusinessEntity> topLayer = head.getTreeItem(0);
		ArrayList<TreeItem<BusinessEntity>> treeItems = new ArrayList<TreeItem<BusinessEntity>>();
		getAllChildren(topLayer, treeItems);
		// run through and see all nodes and test each
		for (TreeItem<BusinessEntity> item : treeItems)
		{
			id = item.getValue().toString();
			System.out.println(id + 3);
			robo.clickOn(id);
			if (cont.textArea.isVisible())
			{
				// change the statement
				testChangestatement(robo);
			}
		}
		int sizeOfArray = treeItems.size();
		TreeItem toolItem = treeItems.get(treeItems.size() - 1);
		String tool = treeItems.get(treeItems.size() - 1).getValue().toString();
		robo.clickOn(tool);
		robo.clickOn("#delCompButton");
		/// clear the tree to get the updated tree
		treeItems.clear();
		getAllChildren(cont.tree.getRoot(), treeItems);
		assertEquals(treeItems.size(), (sizeOfArray - 1));
		tool = treeItems.get(treeItems.size() - 1).getValue().toString();
		robo.clickOn(tool);
		robo.clickOn("#addCompButton");
		TreeItem<BusinessEntity> ran = cont.tree.getTreeItem(3);
		robo.clickOn(ran.getValue().toString());
		robo.clickOn("#addCompButton");

		treeItems.clear();
		getAllChildren(cont.tree.getRoot(), treeItems);
		for (TreeItem<BusinessEntity> item : treeItems)
		{
			id = item.getValue().toString();
			robo.clickOn(item.getValue().toString());
			changeEntityTitle(robo);
		}
	}

	private void changeEntityTitle(FxRobot robo)
	{
		String s = cont.entityTitleField.getText();
		if (s.length() != 0)
		{
			robo.clickOn("#entityTitleField");

			robo.push(KeyCode.SHIFT, KeyCode.END);
			robo.push(KeyCode.CONTROL, KeyCode.BACK_SPACE);
			robo.push(KeyCode.CONTROL, KeyCode.BACK_SPACE);
			robo.push(KeyCode.CONTROL, KeyCode.BACK_SPACE);

			String random = gener.generateByRegex("\\w\\w\\w\\w\\w\\w");

			robo.write(random);
			robo.clickOn("#changeTitleButton");
			cont.tree.setShowRoot(true);
			robo.clickOn(random);
			// assertEquals(random, cont.entityTitleField.getText());
		}

	}

	private void getAllChildren(TreeItem<BusinessEntity> topLayer, ArrayList<TreeItem<BusinessEntity>> arrayList)
	{

		arrayList.add(topLayer);
		/// recursive call the subentities
		for (int i = 0; i < topLayer.getChildren().size(); i++)
		{
			getAllChildren(topLayer.getChildren().get(i), arrayList);
		}

	}

	@Test
	public void testChangestatement(FxRobot robo)
	{
		String s = cont.textArea.getText();
		if (s.length() != 0)
		{
			robo.clickOn("#textArea");
			robo.type(KeyCode.A);
			robo.clickOn("#saveStatementButton");
			robo.clickOn(id);
			assertEquals(s + "a", cont.textArea.getText());
		}
	}

}
