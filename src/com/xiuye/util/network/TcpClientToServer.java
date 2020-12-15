package com.xiuye.util.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author Dell
 */
public class TcpClientToServer {

    private static final int LOCAL_PORT = 18888;
    private static final int REMOTE_PORT = 18889;
    private static final String REMOTE_IP = "106.13.163.111";
//    private static final String REMOTE_IP = "localhost";

    public static void main(String[] args) throws IOException {

//        System.out.println("发送udp打洞消息,用于打洞和建立连接");
//        //request remote server to establish connection for udp holes
//        DatagramSocket udpClient = new DatagramSocket(LOCAL_PORT);
//        byte[] requestData = "你好，我是客户端，正在请求服务器端的你，建立连接吧，保持短期NAT会话端口，让客户端的我反过来成为你的服务器吧。（使用udp打洞）".getBytes(StandardCharsets.UTF_8);
//        //网服务器发送数据，NAT建立短期会话，保持端口号映射，然后在短时间内，发送请求，客户端作为tcp服务器。
//        DatagramPacket establishConnectionPacket = new DatagramPacket(requestData, requestData.length, InetAddress.getByName(REMOTE_IP), REMOTE_PORT);
//        udpClient.send(establishConnectionPacket);
//
//        DatagramPacket serverData = new DatagramPacket(new byte[1024], 1024);
//        udpClient.receive(serverData);
//        System.out.println("'server (to) client' response:" + new String(serverData.getData(), StandardCharsets.UTF_8));


        //不要关闭，否则NAT短期内的会话公网地址端口映射就会失效，从而无法建立以客户端为服务器的tcp连接
        //udpClient.close();

//        Executor threadPool = Executors.newFixedThreadPool(10);


        System.out.println("发送tcp打洞消息,用于打洞和建立连接");
//        Socket client = new Socket(REMOTE_IP,REMOTE_PORT);
        Socket client = new Socket();

        client.setReuseAddress(true);
        InetSocketAddress address = new InetSocketAddress(LOCAL_PORT);
        client.bind(address);
        client.connect(new InetSocketAddress(REMOTE_IP, REMOTE_PORT));
        System.out.println("local地址信息:" + client.getLocalAddress());
        System.out.println("local端口信息:" + client.getLocalPort());
        System.out.println("remote地址信息:" + client.getRemoteSocketAddress());
        System.out.println("client地址信息:" + client.getInetAddress());
        System.out.println("client端口信息:" + client.getPort());


        int port = client.getLocalPort();

        InputStream in = client.getInputStream();
//        in.read();

        client.close();

        //让客户端成为服务器，监听来自服务器的数据。
        //bind port 18888
//        ServerSocket clientServer = new ServerSocket(LOCAL_PORT);
        ServerSocket clientServer = new ServerSocket();
        clientServer.setReuseAddress(true);
        clientServer.bind(address);

        System.out.println("服务器地址:" + clientServer.getLocalSocketAddress());
        System.out.println("tcp打洞，客户端作为服务器监听中...");

        //循环处理客户端服务器请求
        //服务器只处理一次就行了，不需要使用循环和线程去分任务处理了！
//        for (; ; ) {
        Socket tcpConnection = clientServer.accept();
//            threadPool.execute(() -> {
        String networkInfo = "\tLocalSocketAddress:"
                + tcpConnection.getLocalSocketAddress() + " ;\n\t"
                + "LocalAddress:" + tcpConnection.getLocalAddress() + " ;\n\t"
                + "LocalPort:" + tcpConnection.getLocalPort() + " ;\n\t"
                + "RemoteSocketAddress:" + tcpConnection.getRemoteSocketAddress() + " ;\n\t"
                + "InetAddress:" + tcpConnection.getInetAddress() + " ;\n\t"
                + "Port:" + tcpConnection.getPort() + " ;\n";
        System.out.println("Get address info:\n" + networkInfo);

        try {
            tcpConnection.setSendBufferSize(1024);
            long count = 0;

            for (; ; ) {

                //如果执行了10次就退出
                if (count > 3) {
                    break;
                }


                BufferedInputStream reader = new BufferedInputStream(tcpConnection.getInputStream());
                BufferedOutputStream writer = new BufferedOutputStream(tcpConnection.getOutputStream());

                System.out.println("A 'server (to) client' request：\n\t");

                byte[] readMsg = new byte[1024];
                int len = 0;
                //再循环中 read 读不到 -1 是不会结束的！！！
//                        while ((len = reader.read(readMsg)) != -1) {
//                            System.out.print(new String(readMsg, 0, len, StandardCharsets.UTF_8));
//                        }
//                        System.out.println();
                //所以读一次算了
                len = reader.read(readMsg);
                System.out.println(new String(readMsg, 0, len, StandardCharsets.UTF_8));
                //不要用shutdown会关闭流的下次就不能使用了！！！
//                        if (!tcpConnection.isInputShutdown()) {
//                            tcpConnection.shutdownInput();
//                        }
//			reader.close();

                writer.write("\tHello,'server (to) client',I am 'client (to) server' .I received your message!\n".getBytes(StandardCharsets.UTF_8));
                writer.write(("\t客户端(to)服务器 打洞成功!---" + count).getBytes(StandardCharsets.UTF_8));
                writer.flush();
                count++;
//			writer.close();
//                        if (!tcpConnection.isOutputShutdown()) {
//                            tcpConnection.shutdownOutput();
//                        }
//			tcpConnection.close();
            }
            tcpConnection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        clientServer.close();

//            });
//        }

    }
}
