package com.xiuye.util.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

/**
 * @author Dell
 */
public class UdpHolesServer {
    public static void main(String[] args) throws IOException {

        System.out.println("Sever");

        DatagramSocket server = new DatagramSocket(7777);

        for (; ; ) {

            DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
            server.receive(request);
            System.out.println(new String(request.getData(), StandardCharsets.UTF_8));
            System.out.println("address:" + request.getSocketAddress());
        }

    }
}
