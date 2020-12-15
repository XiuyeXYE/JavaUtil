package com.xiuye.util.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * @author Dell
 */
public class UdpClientServer {

    public static void main(String[] args) throws IOException {


        DatagramSocket clientServer = new DatagramSocket(8888);
        byte[] msg = "Hello,I am client".getBytes(StandardCharsets.UTF_8);
        DatagramPacket request = new DatagramPacket(msg, msg.length, InetAddress.getByName("106.13.163.111"), 8888);
        clientServer.send(request);

        //udp 两端都 close的话 udp打洞 就关闭了NAT端口会话，就无法打洞了！
//        clientServer.close();

        System.out.println("client server open,running...");
        while (true) {
            clientServer = new DatagramSocket(8888);
            byte[] data = new byte[1024];
            DatagramPacket receiveData = new DatagramPacket(data, data.length);
            clientServer.receive(receiveData);
            String info = new String(data, 0, receiveData.getLength());
            System.out.println("received other peer's data:" + info);
            InetAddress address = receiveData.getAddress();
            int port = receiveData.getPort();
            System.out.println("other peer's address:" + address + ",port:" + port);
            byte[] response = "好的，我已经收到了你的请求，udp打洞成功。主要还是NAT（网络地址转换）协议OK呢！嘿嘿。".getBytes(StandardCharsets.UTF_8);
            DatagramPacket responseData = new DatagramPacket(response, response.length, address, port);
            clientServer.send(responseData);
//        clientServer.close();

        }


    }

}
