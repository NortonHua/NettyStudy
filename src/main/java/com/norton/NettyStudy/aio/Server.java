package com.norton.NettyStudy.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

import io.netty.channel.socket.SocketChannel;

public class Server {

	public static void main(String[] args) throws Exception {
		ServerSocketChannel ssc=ServerSocketChannel.open();
		ssc.socket().bind(new InetSocketAddress("127.0.0.1",8888));
		ssc.configureBlocking(false);
		
		System.out.println("server started,listening on:"+ssc.getLocalAddress());
		Selector sel=Selector.open();
		ssc.register(sel, SelectionKey.OP_ACCEPT);
		
		while(true) {
			sel.select();
			Set<SelectionKey> keys=sel.selectedKeys();
			Iterator<SelectionKey> it=keys.iterator();
			while(it.hasNext()) {
				SelectionKey key=it.next();
				it.remove();
				handle(key);
			}
		}
	}

	private static void handle(SelectionKey key) throws Exception {
		// TODO Auto-generated method stub
		if(key.isAcceptable()) {
			ServerSocketChannel ssc=(ServerSocketChannel) key.channel();
			java.nio.channels.SocketChannel sc=ssc.accept();
			sc.configureBlocking(false);
			sc.register(key.selector(), SelectionKey.OP_READ);
		
		}else if(key.isReadable()) {
			SocketChannel sc=null;
			
			sc=(SocketChannel) key.channel();
			ByteBuffer buffer=ByteBuffer.allocate(512);
			buffer.clear();
			
		}
	}
	
}
