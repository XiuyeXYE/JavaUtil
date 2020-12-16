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

    public static void main(String[] args) throws IOException, InterruptedException {


        DatagramSocket clientServer = new DatagramSocket(8888);
        for (; ; ) {
//        clientServer.setReuseAddress(true);
            byte[] msg = "Hello,I am client sss".getBytes(StandardCharsets.UTF_8);
            DatagramPacket request = new DatagramPacket(msg, msg.length, InetAddress.getByName("106.13.163.111"), 8889);
//        DatagramPacket request = new DatagramPacket(msg, msg.length, InetAddress.getByName("223.95.114.216"), 8889);
            clientServer.send(request);
//        clientServer.setBroadcast(true);


            byte[] data = new byte[1024];
            DatagramPacket response = new DatagramPacket(data, data.length);
            clientServer.receive(response);
            System.out.println("received msg:" + new String(response.getData(), StandardCharsets.UTF_8));

            Thread.sleep(3000);
        }


        //udp 两端都 close的话 udp打洞 就关闭了NAT端口会话，就无法打洞了！
//        clientServer.close();
//        Thread.sleep(2000);


        //2020 12 15 可以重复绑定端口，现在没法用了，昨天 网络也被限制了，估计是网络有问题，才能重复绑定，
        //一般端口占用是不能重复绑定的！！！
        //而且 公网IP /223.95.114.216,port:8888 那边出现了网络限制！
        //所有昨天是网络上出现了一个bug吧
//        System.out.println("client server open,running...");
//        while (true) {
//            clientServer = new DatagramSocket(8888);
//            byte[] data = new byte[1024];
//            DatagramPacket receiveData = new DatagramPacket(data, data.length);
//            clientServer.receive(receiveData);
//            String info = new String(data, 0, receiveData.getLength());
//            System.out.println("received other peer's data:" + info);
//            InetAddress address = receiveData.getAddress();
//            int port = receiveData.getPort();
//            System.out.println("other peer's address:" + address + ",port:" + port);
//            byte[] response = "好的，我已经收到了你的请求，udp打洞成功。主要还是NAT（网络地址转换）协议OK呢！嘿嘿。".getBytes(StandardCharsets.UTF_8);
//            DatagramPacket responseData = new DatagramPacket(response, response.length, address, port);
//            clientServer.send(responseData);
////        clientServer.close();
//
//        }


    }

}
