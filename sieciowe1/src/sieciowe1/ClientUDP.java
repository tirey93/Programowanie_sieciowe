package sieciowe1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class ClientUDP {
	public static Scanner scanner = null;
	public static void main(String[] args) throws Exception{
        DatagramSocket s = new DatagramSocket();
        String sentence = "";
        byte[] message = "Test".getBytes("utf8");

        
        DatagramPacket packet = new DatagramPacket(message, message.length);
        packet.setPort(3301);
        packet.setAddress(InetAddress.getByName("239.255.42.99"));

        s.setSoTimeout(1000);
        scanner = new Scanner(System.in);
        while(!(sentence = scanner.nextLine()).equals("quit")) {
			packet.setData(sentence.getBytes());
	        s.send(packet);
	        DatagramPacket response = new DatagramPacket(new byte[1000], 1000);
	        try{
	            s.receive(response);
	            System.out.println("Odpowiedü: ");
	            System.out.println(new String(response.getData(), 0,  response.getLength(), "utf8"));
	        }catch (SocketTimeoutException e){
	            System.out.println("Nie otrzyma≥em odpowiedzi");
	        }
		}

    }
}
