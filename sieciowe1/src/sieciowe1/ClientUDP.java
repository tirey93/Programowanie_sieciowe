package sieciowe1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class ClientUDP {
	public static Scanner scanner = null;
	public static void main(String[] args) throws Exception{
		MulticastSocket clientSocketSend = new MulticastSocket(3301);
		MulticastSocket clientSocketReceive = new MulticastSocket(3301);
		InetAddress group = InetAddress.getByName("239.255.42.99");
		clientSocketSend.joinGroup(group);
        String sentence = "";  
        String name = "";
        String myName = "";
        //clientSocket.setLoopbackMode(true);
        clientSocketSend.setReuseAddress(true);
        clientSocketSend.setSoTimeout(1000);
        
        clientSocketReceive.joinGroup(group);
        //clientSocket.setLoopbackMode(true);
        clientSocketReceive.setReuseAddress(true);
        clientSocketReceive.setSoTimeout(1000);
        
        scanner = new Scanner(System.in);
        DatagramPacket packet;
		myName = setNick(clientSocketSend, group);
        Thread.sleep(1000);
        DatagramPacket response = new DatagramPacket(new byte[1000], 1000);
        try{
        	clientSocketReceive.receive(response);
        	String message = new String(response.getData(), 0,  response.getLength(), "utf8");
        	System.out.println("X: " + message);
        	clientSocketReceive.receive(response);
        	while(message.contains("Busy")) {
        		System.out.println("Choose another nick: ");
        		myName = setNick(clientSocketSend, group);
        	}
		}catch (IOException e){
	        //System.out.println("Nie otrzyma³em odpowiedzi");
	    }
        System.out.println("Nick is registered.");
        ClientUDPThread cut = new ClientUDPThread(clientSocketReceive, clientSocketSend, myName);
        cut.isThreadBusy = true;
        Thread th = new Thread(cut);
        th.start();
        while(!(sentence = scanner.nextLine()).equals("quit")) {
        	cut.isThreadBusy = false;
        	sentence = "MSG " + myName.substring(6) + " :" + sentence;
        	packet = new DatagramPacket(
        			sentence.getBytes(), 
        			sentence.getBytes().length,
        			group,
        			3301);
	        clientSocketSend.send(packet);
	        
		}

    }
	private static String setNick(MulticastSocket clientSocketSend, InetAddress group) throws IOException {
		String myName;
		System.out.println("Choose nick: ");

        myName = scanner.nextLine();
        myName = "NICK: " + myName;
        DatagramPacket packet = new DatagramPacket(
        		myName.getBytes(), 
        		myName.getBytes().length,
    			group,
    			3301);
        clientSocketSend.send(packet);
		return myName;
	}
}
