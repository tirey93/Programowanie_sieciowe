package sieciowe1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static ServerSocket welcomeSocket;
	public static OutputStream socketOutputStream = null;
	public static void main(String[] args) {
		String clientSentence = "";
		try {
			welcomeSocket = new ServerSocket(3301);
			while(true) {
				Socket connectionSocket = welcomeSocket.accept();
				System.out.println("Connected to: " + connectionSocket.getRemoteSocketAddress().toString());
				//BufferedReader inFromServer = 
				//		new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				DataInputStream inFromServer = new DataInputStream(connectionSocket.getInputStream());
				clientSentence = inFromServer.readUTF();
				System.out.println("Readed: " + clientSentence);
				socketOutputStream =  connectionSocket.getOutputStream();
				socketOutputStream.write(clientSentence.getBytes());
				connectionSocket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void closeConnection() {
		try {
			welcomeSocket.close();
			socketOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
