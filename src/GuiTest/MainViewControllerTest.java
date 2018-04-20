package GuiTest;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit.ApplicationTest;

import Client.Client;
import javafx.scene.Scene;
import javafx.stage.Stage;
import s4.Main;
import s4.MainView.MainViewController;

class MainViewControllerTest extends ApplicationTest
{
	Main main;
	Client client;

	@Override
	public void start(Stage stage)
	{
		main.showLogin();
		main.viewGoesHere.setId("#BorderPane");
		Scene s = new Scene(main.viewGoesHere);
		stage.setScene(s);
		stage.show();
	}

	@Test
	void test()
	{
		clickOn("#username");
		write("Caleb");
		
	}

}
