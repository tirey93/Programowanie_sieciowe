package sieciowe1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ClientUDPThread implements Runnable{
	private MulticastSocket s;
	private MulticastSocket clientSocketSend;
	private String myName;
	public boolean isThreadBusy = false;
	public ClientUDPThread(MulticastSocket s2, MulticastSocket send, String name) {
		s = s2;
		myName = name.substring(6);
		clientSocketSend = send;
	}
	@Override
	public void run() {
		while(true) {
			DatagramPacket response = new DatagramPacket(new byte[1000], 1000);
	        try{
	            s.receive(response);
	            String message = new String(response.getData(), 0,  response.getLength(), "utf8");
	            if(message.contains("NICK: ") && !isThreadBusy) {
	            	if(myName.equals(message.substring(6))) {
	            		System.out.println("Busy");
	            		DatagramPacket packet = new DatagramPacket(
	                			("Nick " + message.substring(6) +" Busy").getBytes(), 
	                			("Nick " + message.substring(6) +" Busy").getBytes().length,
	                			InetAddress.getByName("239.255.42.99"),
	                			3301);
	        	        clientSocketSend.send(packet);
	            	}
	            }
	            else {
	            	System.out.println(message);
	            }
	        }catch (IOException e){
	            //System.out.println("Nie otrzyma³em odpowiedzi");
	        }
		}
		
	}

}
