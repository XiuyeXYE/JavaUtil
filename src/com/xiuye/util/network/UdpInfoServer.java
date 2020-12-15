package com.xiuye.util.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;


/**
 * @author Dell
 */
public class UdpInfoServer {

    public static void main(String[] args) throws IOException {

        DatagramSocket udpServer = new DatagramSocket(8888);
        System.out.println("My local address:" + udpServer.getLocalSocketAddress());
        System.out.println("My remote address:" + udpServer.getRemoteSocketAddress());
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        System.out.println("info server open,running...");
        udpServer.receive(packet);
        String info = new String(data, 0, packet.getLength());
        System.out.println("received client data:" + info);

        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        System.out.println("client address:" + address + ",port:" + port);

        byte[] response = "Hello,I am server.For address info.".getBytes(StandardCharsets.UTF_8);
        DatagramPacket responseData = new DatagramPacket(response, response.length, address, port);
        udpServer.send(responseData);
        udpServer.close();


    }

}
