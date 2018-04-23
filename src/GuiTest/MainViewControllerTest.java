package GuiTest;

import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;

import Server.Server;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import s4.Main;
import s4.MainView.MainViewController;
import org.testfx.framework.junit5.*;
@ExtendWith(ApplicationExtension.class)
public class MainViewControllerTest // extend ApplicationTest

{
	BorderPane viewGoesHere = new BorderPane();
	Stage stage;

	@Start
	public void onStart(Stage stage) throws IOException
	{
		this.stage = stage;

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("MainView/MainView.fxml"));
		try
		{
			viewGoesHere.setCenter(loader.load());
			MainViewController cont = loader.getController();
			cont.setMain(new Main());
			Server server = new Server();
			cont.setServer(server);
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
		//stage.getScene().getRoot();
		robo.clickOn("#username");
	}

}
