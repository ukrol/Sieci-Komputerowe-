import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientThread extends Thread {

	private String username = null;
	private Socket clientSocket = null;
	private DataInputStream is = null;
	private PrintStream os = null;
	private BufferedReader reader = null;
	private List<ClientThread> usersList = new ArrayList<>();
	private boolean isConnected = true;

	public ClientThread(Socket clientSocket, List<ClientThread> usersList) {
		this.clientSocket = clientSocket;  
		this.usersList = usersList;
	}
	
	@Override
	public void run() {
	    isConnected=true;
	    String nickname = null;
	    try {
	    	is = new DataInputStream(clientSocket.getInputStream());
	    	os = new PrintStream(clientSocket.getOutputStream());
	    	reader = new BufferedReader(new InputStreamReader(is));
	    	nickname = reader.readLine();
	    	
	    	for(ClientThread clientThread: usersList){
				if(clientThread != null && clientThread == this){
					username = nickname;
					break;
				}
			}
			for(ClientThread clientThread: usersList){
				if(clientThread != null && clientThread != this){
					clientThread.os.println("New user " + username +" entered the chat!");
					clientThread.os.flush();			
				}
			}
			if(nickname != null){
				os.println("Welcome " + username + " to our chat!");
				os.flush();
			}
	    	
	    	while (true) {
	    		String message = reader.readLine();
	    		if(message != null){
	    			if (message.startsWith("disconnected") || (isConnected==false)) {
	    				break;
	    			}
	    			for(ClientThread clientThread: usersList){
	    				if(clientThread != null && clientThread != this && clientThread.username != null ){
	    					clientThread.os.println(username + message);
	    					clientThread.os.flush();
	    				}
	    			}
	    		}
	    	}     
	    	for(ClientThread clientThread: usersList){
				if(clientThread != null && clientThread != this && clientThread.username != null ){
					clientThread.os.println("User " + username + " is leaving the chat!");
					clientThread.os.flush();			
				}
			}

	    	if(isConnected==false) {
	    		os.print("Stopped");
	    		os.flush();
	    	}
	    	else{
	    		os.println("You are disconnected!");
	    		os.flush();
	    	}
	    	
	    	is.close();
	    	os.close();
	    	reader.close();
	    	clientSocket.close();
	    	
	    } catch (IOException e) {
	    	System.out.println("Lost connection!");
	    	
	    }
	    finally {
	    	usersList.remove(this);
	    }
	  }
}