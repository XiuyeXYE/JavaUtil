package com.xiuye.util.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;

/**
 * @author Dell
 */
public class MulticastServer {


    public static void main(String[] args) throws IOException {

        System.out.println("server:");

        MulticastSocket server = new MulticastSocket(8888);
//        server.setLoopbackMode(false);
        server.setTimeToLive(32);

        InetAddress address = InetAddress.getByName("224.0.0.9");
        server.joinGroup(address);

        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        server.receive(packet);
        System.out.println("receive:" + new String(packet.getData(), StandardCharsets.UTF_8));

        server.leaveGroup(address);
        server.close();


    }

}
