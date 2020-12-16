package com.xiuye.util.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;

/**
 * @author Dell
 */
public class MulticaseClient {

    public static void main(String[] args) throws IOException {

        System.out.println("client:");

        MulticastSocket client = new MulticastSocket();
//        client.setLoopbackMode(true);
        client.setTimeToLive(32);
        InetAddress address = InetAddress.getByName("224.0.0.9");
//        client.joinGroup(address);

        byte[] msg = "Hello,I am client.".getBytes(StandardCharsets.UTF_8);
        client.send(new DatagramPacket(msg, msg.length, address, 8888));

//        client.leaveGroup(address);
        client.close();

    }
}
