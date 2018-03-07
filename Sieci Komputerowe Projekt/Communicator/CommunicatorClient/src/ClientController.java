import java.io.IOException;
import java.io.PrintStream;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientController {

	private Client client;
	private PrintStream os = null;
	private String host = null;
	private String username = null;
	private String message;
	private boolean isConnected;
	
	@FXML
	Button sendButton;
	@FXML
	Button connectButton;
	@FXML
	TextArea chatTextArea;
	@FXML
	TextField messageTextField;
	@FXML
	Button disconnectButton;
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField hostTextField;
	
	@FXML
	public void initialize(){
		chatTextArea.setEditable(false);
		messageTextField.setDisable(true);
		sendButton.setDisable(true);
		disconnectButton.setDisable(true);
	}
	
	@FXML
	public void handleConnect() {
		host = hostTextField.getText();
		username = usernameTextField.getText();
		client = new Client(host, username, messageTextField, chatTextArea);
		client.start();
		connectButton.setDisable(true);
		messageTextField.setDisable(false);
		sendButton.setDisable(false);
		disconnectButton.setDisable(false);
	}
	

	@FXML
	public void handleSend(){
		client.send();
		message = messageTextField.getText();
		chatTextArea.appendText(username+": " +message+"\n");
		if(os != null){
			os.println(username+": "+message);
			os.flush();
		}
		messageTextField.clear();
	}

	@FXML
	public void handleDisconnect() throws IOException {
		client.disconnect();
		isConnected = false;
		sendButton.setDisable(true);
		disconnectButton.setDisable(true);
		messageTextField.setDisable(true);
	}

}

