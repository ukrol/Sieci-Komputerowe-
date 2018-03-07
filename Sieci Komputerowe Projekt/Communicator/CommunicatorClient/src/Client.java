import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Client extends Thread {
	private Socket clientSocket = null;
	private PrintStream os = null;
	private DataInputStream is = null;
	private int port = 2222;
	private String host = null;
	private boolean isConnected;
	private TextArea chatTextArea = null;
	private String username;
	private TextField messageTextField;
	private BufferedReader reader = null;
	String response; 
	
	public Client(String host, String username, TextField messageTextField, TextArea chatTextArea) {
			this.host=host;
			this.username = username;
			this.messageTextField = messageTextField;
			this.chatTextArea = chatTextArea;
	}
	
	private Thread readMessageThread = new Thread(new Runnable() {
		@Override
		public void run() {
			while(isConnected){
				try {
					if((response = reader.readLine()+"\n") != null){
						chatTextArea.appendText(response);
					}
				} catch (IOException e) {
					break;
				}
				if(response.equals("Stopped")){
					break;
				}
			}
		}
	});
	
	@Override
	public void run(){
		try {
			clientSocket = new Socket(host, port);
			os = new PrintStream(clientSocket.getOutputStream());
		    is = new DataInputStream(clientSocket.getInputStream()); 
		    reader = new BufferedReader(new InputStreamReader(is));
		    os.println(username);
		    os.flush();
		    System.out.println("Client Connected!");
		} catch (UnknownHostException e) {
			System.err.println("Cannot connect!");
		} catch (IOException e) {
			System.err.println("Cannot connect!");
		}
		if(clientSocket != null && os != null && is != null && reader != null)
		{
			isConnected = true;
			readMessageThread.start();
		}
	}
	public void disconnect() throws IOException{
		try{
			os.println("disconnected");
			isConnected = false;
			System.out.println("Client Disconnected!");
			os.flush();
			reader.close();
			os.close();
			is.close();
			clientSocket.close();
			}catch(NullPointerException e){
				System.out.println("Disconnection error!");
			}
	}
	
	public void send(){
		try{
			os.println(": " + messageTextField.getText());
		}catch(Exception ex){
			chatTextArea.appendText("Failed to send message! \n");
		}
	}
}
