import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ServerController {
	
	@FXML
	public void initialize() {
		stopButton.setDisable(true);
	}
	@FXML
	private Button startButton;
	@FXML
	private Button stopButton;
	
	private Server server = new Server();
	
	@FXML
	public void handleStart(){
		server.start();
		startButton.setDisable(true);
		stopButton.setDisable(false);
	}
	@FXML
	public void handleStop() throws IOException {
		server.stopServer();
		stopButton.setDisable(true);
	}
}
