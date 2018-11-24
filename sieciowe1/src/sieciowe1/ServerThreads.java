package sieciowe1;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThreads implements Runnable {
	public static String clientSentence = "";
	public static byte[] messageByte = new byte[1000]; 
	public Socket clientSocket = null;
	public ServerThreads(Socket socket) {
		clientSocket = socket;
	}
	@Override
	public void run() {
		try {
			System.out.println("Connected to: " + clientSocket.getRemoteSocketAddress().toString());
			//BufferedReader inFromServer = 
			//		new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataInputStream in = new DataInputStream(clientSocket.getInputStream());
			PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			boolean running = true;
			while(running) {
				int bytesRead = in.read(messageByte);
				if(bytesRead == -1) {
					clientSocket.close();
					System.out.println("Client: " + clientSocket.getInetAddress() + ";" + clientSocket.getPort() + " has stopped.");
					break;
				}
				clientSentence = new String(messageByte, 0, bytesRead);
				System.out.println("Read " + clientSocket.getPort() + ": " + clientSentence);
				out.println(clientSentence);
				out.flush();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
