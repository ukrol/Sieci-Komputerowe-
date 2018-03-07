import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {

	private ServerSocket serverSocket = null;
	private Socket stopSocket = null;
	private Socket clientSocket = null;
	private final int port = 2222;
	private List<ClientThread> usersList = new ArrayList<>();
	private boolean isConnected;
	
	
	public Server() {
	}

	public void stopServer() throws IOException {
		isConnected = false;
		stopSocket = new Socket("localhost", port);
		stopSocket.close();
		System.out.println("Server stopped!");
	}
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server is working!");
			System.out.println("Now using port number: " + port);
			isConnected = true;
	    } catch (IOException e) {
	    	isConnected = false;
	    } 

		while (isConnected) {
			try {
				clientSocket = serverSocket.accept();
				if(isConnected){
					ClientThread user = new ClientThread(clientSocket, usersList);
					usersList.add(user);
					usersList.get(usersList.size()-1).start();
				} 
				else {
					clientSocket.close();
				}
			} catch (IOException e) {
				System.out.println(e);
	        }
	    }
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
		usersList.clear();
		}
	   }
	
}
