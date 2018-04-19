package CentrePlanApplication;

import java.io.IOException;

import CentrePlanApplication.homeView.HomeViewController;
import CentrePlanApplication.mainView.MainViewController;
import CentrePlanApplication.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application
{

	Model model = new Model();

	Stage stage;
	BorderPane mainView;

	public Main()
	{
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("mainView/MainView.fxml"));
		mainView = loader.load();

		MainViewController cont = loader.getController();
		cont.setMain(this);
		
		showHome();
		
		Scene s = new Scene(mainView);
		stage.setScene(s);
		stage.show();

	}
	
	public void showHome()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("homeView/HomeView.fxml"));
		try {
			mainView.setCenter(loader.load());
			HomeViewController cont = loader.getController();
			cont.setModel(model);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}

}
