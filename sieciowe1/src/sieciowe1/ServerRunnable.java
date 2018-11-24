package sieciowe1;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRunnable{
	public static ServerSocket welcomeSocket;
	public static void main(String[] args) throws IOException {
		welcomeSocket = new ServerSocket(3301);
		while(true) {
			Socket so = welcomeSocket.accept();
			Thread th = new Thread(new ServerThreads(so));
			th.start();
		}
	}
}
