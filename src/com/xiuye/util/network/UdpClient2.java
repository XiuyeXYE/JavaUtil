package com.xiuye.util.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UdpClient2 {

    public static void main(String[] args) throws IOException, InterruptedException {

        DatagramSocket client2 = new DatagramSocket(8889);
//        client2.setReuseAddress(true);
        for (; ; ) {
            byte[] msg = "Hello,I am client 2".getBytes(StandardCharsets.UTF_8);
            DatagramPacket request = new DatagramPacket(msg, msg.length, InetAddress.getByName("223.95.114.216"), 8888);
            client2.send(request);
            System.out.println("sent");
            Thread.sleep(1000);
        }

//        client2.setBroadcast(true);


//        byte[]data = new byte[1024];
//        DatagramPacket response = new DatagramPacket(data,data.length);
//        client2.receive(response);
//
//        System.out.println("received msg:"+new String(request.getData(),StandardCharsets.UTF_8));
    }
}
