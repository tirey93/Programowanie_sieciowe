package sieciowe1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.plaf.metal.MetalComboBoxUI.MetalPropertyChangeListener;

public class Server {
	public static ServerSocket welcomeSocket;
	//public static OutputStream socketOutputStream = null;
	public static void main(String[] args) {
		String clientSentence = "";
		byte[] messageByte = new byte[1000]; 
		try {
			welcomeSocket = new ServerSocket(3301);
			while(true) {
				Socket clientSocket = welcomeSocket.accept();
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
					System.out.println("Read: " + clientSentence);
					out.println(clientSentence);
					out.flush();
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
