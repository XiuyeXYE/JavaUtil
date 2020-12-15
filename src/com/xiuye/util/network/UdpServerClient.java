package com.xiuye.util.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * @author Dell
 */
public class UdpServerClient {

    public static void main(String[] args) throws IOException {


        DatagramSocket serverClient = new DatagramSocket(8888);
        byte[] msg = "Hello,I am server client.我正在从服务器请求客服端，你想请求数据额！".getBytes(StandardCharsets.UTF_8);
        DatagramPacket request = new DatagramPacket(msg, msg.length, InetAddress.getByName("223.95.114.216"), 8888);
        serverClient.send(request);

        byte[] requestData = new byte[1024];
        DatagramPacket requestMsg = new DatagramPacket(requestData, requestData.length);
        serverClient.receive(requestMsg);
        System.out.println("我是服务器客户端，接收到数据:" + new String(requestMsg.getData(), StandardCharsets.UTF_8));

        System.out.println("udp打洞成功！");

        serverClient.close();


    }

}
