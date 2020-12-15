package com.xiuye.util.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author Dell
 */
public class TcpServerToClient {

    //    private static final int REMOTE_PORT = 18888;
    private static final int LOCAL_PORT = 18889;

    public static void main(String[] args) throws IOException, InterruptedException {

//        System.out.println("等待udp打洞的消息,用于打洞和建立连接");
//        //udp server bind port
//        DatagramSocket udpClient = new DatagramSocket(LOCAL_PORT);
//        byte[] receivedData = new byte[1024];
//        //udp服务器接收客户端服务器数据，用于保持短期NAT 地址端口映射 会话！！！
//        DatagramPacket establishConnectionPacket = new DatagramPacket(receivedData, receivedData.length);
//        udpClient.receive(establishConnectionPacket);
//        System.out.println("'client (to) server' request:" + new String(establishConnectionPacket.getData(), StandardCharsets.UTF_8));
//
//        //让服务器成为客户端，请求客户端(to)服务器。
//        //这个地址是经过NAT转换的公网地址
//        System.out.println("服务器NAT中间地址:" + establishConnectionPacket.getSocketAddress());
//
//        byte []responseBytes = "你好，我是服务器端，正在响应的客户端你，建立连接吧，保持短期NAT会话端口，让服务器的我反过来成为你的客户端吧。（使用udp打洞）".getBytes(StandardCharsets.UTF_8);
//        DatagramPacket responseData = new DatagramPacket(responseBytes,responseBytes.length,establishConnectionPacket.getAddress(),establishConnectionPacket.getPort());
//        udpClient.send(responseData);

        //不要关闭，否则NAT短期内的会话公网地址端口映射就会失效，从而无法建立以客户端为服务器的tcp连接
        //udpClient.close();

        System.out.println("等待tcp打洞的消息,用于打洞和建立连接");

        ServerSocket server = new ServerSocket(LOCAL_PORT);
        Socket client = server.accept();

        System.out.println("local地址信息:" + client.getLocalAddress());
        System.out.println("local端口信息:" + client.getLocalPort());
        System.out.println("remote地址信息:" + client.getRemoteSocketAddress());
        System.out.println("client地址信息:" + client.getInetAddress());
        System.out.println("client端口信息:" + client.getPort());


        InetAddress address = client.getInetAddress();
        int port = client.getPort();
//
//        client.close();
//        server.close();

        System.out.println("服务器正在等待客户端的服务器启动...");
        //让客户端做好准备 启动服务器，要有反应的时间啊
        Thread.sleep(3000);

        //循环处理客户端服务器请求
//        Socket serverClient = new Socket(establishConnectionPacket.getAddress(), establishConnectionPacket.getPort());
        //客户端经过NAT转换后公网ip和端口
        Socket serverClient = new Socket(address, port);
        String networkInfo = "\tLocalSocketAddress:"
                + serverClient.getLocalSocketAddress() + " ;\n\t"
                + "LocalAddress:" + serverClient.getLocalAddress() + " ;\n\t"
                + "LocalPort:" + serverClient.getLocalPort() + " ;\n\t"
                + "RemoteSocketAddress:" + serverClient.getRemoteSocketAddress() + " ;\n\t"
                + "InetAddress:" + serverClient.getInetAddress() + " ;\n\t"
                + "Port:" + serverClient.getPort() + " ;\n";
        System.out.println("Get address info:\n" + networkInfo);

        try {
            serverClient.setSendBufferSize(1024);
            long count = 0;

            for (; ; ) {

                //如果执行了10次就退出
                if (count > 3) {
                    break;
                }

                BufferedOutputStream writer = new BufferedOutputStream(serverClient.getOutputStream());
                BufferedInputStream reader = new BufferedInputStream(serverClient.getInputStream());

                writer.write("\tHello,'client (to) server',I am 'server (to) client'.I received your message!\n".getBytes(StandardCharsets.UTF_8));
                writer.write(("\t服务器(to)客户端 udp打洞成功!---" + count).getBytes(StandardCharsets.UTF_8));
                writer.flush();
                System.out.println("A 'client (to) server' response：\n\t");
//                if (!serverClient.isOutputShutdown()) {
//                    serverClient.shutdownOutput();
//                }
                byte[] readMsg = new byte[1024];
                int len = 0;
//                while ((len = reader.read(readMsg)) != -1) {
//                    System.out.print(new String(readMsg, 0, len, StandardCharsets.UTF_8));
//                }
//                System.out.println();
                len = reader.read(readMsg);
                System.out.println(new String(readMsg, 0, len, StandardCharsets.UTF_8));
//                if (!serverClient.isInputShutdown()) {
//                    serverClient.shutdownInput();
//                }

                count++;
            }
            serverClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
