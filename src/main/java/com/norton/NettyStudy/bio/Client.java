package com.norton.NettyStudy.bio;

import java.io.IOException;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws Exception {
		Socket s=new Socket("127.0.0.1", 8888);
		//半双工，阻塞式IO
		s.getOutputStream().write("HellowServer".getBytes());
		s.getOutputStream().flush();
		System.out.println("write over,waiting for msg back....");
		byte[] bytes =new byte[1024];
		int len=s.getInputStream().read(bytes);
		System.out.println(new String(bytes,0,len));
		s.close();
	}
}
