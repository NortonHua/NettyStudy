package com.norton.NettyStudy.netty;
/**
 * 窗口通信的客户端
 * @author Artificial Intellige
 *
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class FrameClient {

	private Channel channel=null;
	
	public void connect() {
		//线程池
		EventLoopGroup group =new NioEventLoopGroup(1);
		
		Bootstrap b=new Bootstrap();
		
		try {
		
		ChannelFuture f=b.group(group).channel(NioSocketChannel.class).handler(new ClientChannelInitializer())
				.connect("localhost",8888);
		f.addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				// TODO Auto-generated method stub
				if(!future.isSuccess()) {
					System.out.println("not connected!");
				}else {
					System.out.println("connected!");
					channel=future.channel();
				}
			}
		});
		f.sync();
		
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			group.shutdownGracefully();
		}
	}
	
	public void send(String msg) {
		ByteBuf buf=Unpooled.copiedBuffer(msg.getBytes());
		channel.writeAndFlush(buf);
	}
}


