import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	private Stage stage;
	private Scene scene;
	
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Chat.fxml"));
			Parent root = loader.load();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("*Communicator* Author: Urszula Król");
			stage.show();
			stage.setResizable(false);
		}
		catch(Exception e){
			System.out.println("Cannot load client scene!");
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

