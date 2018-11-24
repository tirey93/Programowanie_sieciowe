package sieciowe1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

public class ServerUDP {

	public static void main(String[] args) throws Exception{

        InetAddress group = InetAddress.getByName("239.255.42.99");
        MulticastSocket s = new MulticastSocket(3301);
        s.joinGroup(group);

        try{
            while (true){
                DatagramPacket recv = new DatagramPacket(new byte[1000], 1000);
                s.receive(recv);
                String stringMsg = new String(recv.getData(), 0, recv.getLength(), "utf8");
                System.out.println("Odebra³em wiadomoœæ: \"" + stringMsg + "\"");
                DatagramSocket responseSocket = new DatagramSocket();
                DatagramPacket response = new DatagramPacket(stringMsg.getBytes(), stringMsg.getBytes().length);
                response.setAddress(recv.getAddress());
                response.setPort(recv.getPort());

                responseSocket.send(response);
            }
        }finally {
            s.leaveGroup(group);
        }
    }

}
