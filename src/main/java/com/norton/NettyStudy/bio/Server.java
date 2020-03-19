package com.norton.NettyStudy.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws Exception {
		ServerSocket ss=new ServerSocket();
		ss.bind(new InetSocketAddress(8888));
		while(true) {
			Socket s=ss.accept();//阻塞方法
			new Thread(()->{
				handle(s);
			}).start();
		}
	}
	
	static void handle(Socket s) {
		byte[] bytes=new byte[1024];
		try {
			int len=s.getInputStream().read(bytes);
			System.out.println(new String(bytes,0,len));
			
			s.getOutputStream().write(bytes,0,len);
			s.getOutputStream().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
