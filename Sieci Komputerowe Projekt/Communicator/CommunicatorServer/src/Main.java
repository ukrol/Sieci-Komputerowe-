import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Server.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("*Server* Author: Urszula Król");
			stage.show();

		}catch(Exception e){
			System.out.println("Cannot load server scene!");
			System.exit(0);
		}

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
