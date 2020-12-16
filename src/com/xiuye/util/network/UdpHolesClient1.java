package com.xiuye.util.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * @author Dell
 */
public class UdpHolesClient1 {
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("client1");

        DatagramSocket client1 = new DatagramSocket(8888);

        byte[] msg = "Hello,I am client1.".getBytes(StandardCharsets.UTF_8);
        DatagramPacket request = new DatagramPacket(msg, msg.length, InetAddress.getByName("106.13.163.111"), 7777);
        client1.send(request);

//        Thread.sleep(3000);
        //因为要阻塞所以新建一个线程,异步
        new Thread(() -> {
            try {
                byte[] data = new byte[1024];
                DatagramPacket response = new DatagramPacket(data, data.length);
                client1.receive(response);
                System.out.println("client1 received msg:" + new String(response.getData(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        for (; ; ) {

//            msg = "Hello,I am client1.".getBytes(StandardCharsets.UTF_8);
            //已经知道公网地址了,所以直接写地址不用服务器返回
            request = new DatagramPacket(msg, msg.length, InetAddress.getByName("223.95.114.216"), 9999);
            client1.send(request);
            System.out.println("client1 sent");

            Thread.sleep(1000);
        }

    }
}
