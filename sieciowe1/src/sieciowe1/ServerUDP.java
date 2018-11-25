package sieciowe1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

public class ServerUDP {

	public static void main(String[] args) throws Exception{

        InetAddress group = InetAddress.getByName("239.255.42.99");
        MulticastSocket serverSocket = new MulticastSocket(3301);
        serverSocket.joinGroup(group);
        serverSocket.setReuseAddress(true);
        //serverSocket.setLoopbackMode(true);
        try{
            while (true){
                DatagramPacket recv = new DatagramPacket(new byte[1000], 1000);
                serverSocket.receive(recv);
                String stringMsg = new String(recv.getData(), 0, recv.getLength(), "utf8") + "+";
                System.out.println("Od: " +  recv.getSocketAddress() + " W: \"" + stringMsg + "\"");
//                DatagramSocket responseSocket = new DatagramSocket();
                DatagramPacket response = new DatagramPacket(
                		stringMsg.getBytes(), 
                		stringMsg.getBytes().length,
                		group,
                		3301);

                serverSocket.send(response);
                Thread.sleep(1000);
            }
        }finally {
            serverSocket.close();
        }
    }

}
