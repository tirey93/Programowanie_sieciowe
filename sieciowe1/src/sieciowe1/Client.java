package sieciowe1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import rw.TCPServer;

public class Client {
	public static Socket clientSocket = null;
	public static OutputStream socketOutputStream = null;
	public static Scanner scanner = null;
	public static void main(String[] args) {
		String sentence = "";
		try {
			clientSocket = new Socket("127.0.0.1", 3301);
			BufferedReader inFromServer = 
					new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			socketOutputStream =  clientSocket.getOutputStream();
			scanner = new Scanner(System.in);
			while(!(sentence = scanner.nextLine()).equals("q")) {
				socketOutputStream.write(sentence.getBytes());
				System.out.println("SERVER: " + inFromServer.readLine());
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
	}
	public static void closeConnection() {
		try {
			clientSocket.close();
			socketOutputStream.close();
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
