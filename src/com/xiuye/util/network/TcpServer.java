package com.xiuye.util.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.xiuye.util.log.XLog;

public class TcpServer {

	public static void main(String[] args) throws IOException {
		
		ServerSocket server = new ServerSocket(8888);
		XLog.lg("Server Open");
		Socket client = server.accept();
		
		InputStream is = client.getInputStream();
		
//		BufferedInputStream bis = new BufferedInputStream(is);
		
		
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
		pw.println("{\"code\":200,\"msg\":\"OK\"}");
		
		pw.flush();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		char []data = new char[1024];
		int len = -1;
		while((len=reader.read(data, 0, 1024)) != -1) {
			XLog.lg("Data length:",len);
			XLog.lg(String.valueOf(data,0,len));
		}
		
		
		
		client.close();
		
		server.close();
		
		
		XLog.lg("Server End");
	}
}
