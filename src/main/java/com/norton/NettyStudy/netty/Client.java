package com.norton.NettyStudy.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class Client {

	public static void main(String[] args) throws Exception {
		//线程池
		EventLoopGroup group=new NioEventLoopGroup(1);
		//辅助启动类
		Bootstrap b=new Bootstrap();
		try {
			ChannelFuture f=b.group(group)
			     //指定nio非阻塞
			     .channel(NioSocketChannel.class)
			     .handler(new ClientChannelInitializer())
			     .connect("localhost",8889);
			f.addListener(new ChannelFutureListener() {
				
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					// TODO Auto-generated method stub
					if(!future.isSuccess()) {
						System.out.println("not success!");
					}else {
						System.out.println("connected!");
					}
				}
			});
			f.sync();
			
			System.out.println("........");
			 f.channel().closeFuture().sync();    
		}finally {
			group.shutdownGracefully();
		}
	}
}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ch.pipeline().addLast(new ClientHandler());
	}
	
}
class ClientHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//channel 第一次连上可用，写出一个字符串 Direct Memory
		ByteBuf buf=Unpooled.copiedBuffer("hello".getBytes());
		ctx.writeAndFlush(buf);
				
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf=null; 
		try {
		buf=(ByteBuf) msg;
		byte[] bytes=new byte[buf.readableBytes()];
		buf.getBytes(buf.readerIndex(), bytes);
		System.out.println(bytes);
		//System.out.println(buf);
		//System.out.println(buf.refCnt());
		}finally {
			//if(buf!=null) ReferenceCountUtil.release(buf);
			//System.out.println(buf.refCnt());
		}
		
	}
	
}
