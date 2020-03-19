package com.norton.NettyStudy.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {

	//保存所有已连接的客户端
	public static ChannelGroup clients=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	public static void main(String[] args) {
		//大管家
		EventLoopGroup bossGroup =new NioEventLoopGroup(1);
		//餐馆伙计
		EventLoopGroup workGroup =new NioEventLoopGroup(2);
		
		try {    
		ServerBootstrap b=new ServerBootstrap();
		
		ChannelFuture f=b.group(bossGroup,workGroup)
			        .channel(NioServerSocketChannel.class)
			        .childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// TODO Auto-generated method stub
							ChannelPipeline cp=ch.pipeline();
							cp.addLast(new ServerChildHandler());
						}
					})
			        .bind(8889)
			        .sync();
			System.out.println("server started!");
			
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		
	}
}
class ServerChildHandler extends ChannelInboundHandlerAdapter{

	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Server.clients.add(ctx.channel());
	}
	
	

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}



	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf=null; 
		try {
		buf=(ByteBuf) msg;
		byte[] bytes=new byte[buf.readableBytes()];
		buf.getBytes(buf.readerIndex(), bytes);
		System.out.println(new String(bytes));
		
		//实现向所有客户端的消息转发
		Server.clients.writeAndFlush(msg);
		//ctx.writeAndFlush(msg);
		
		//System.out.println(buf);
		//System.out.println(buf.refCnt());
		}finally {
			//if(buf!=null) ReferenceCountUtil.release(buf);
			//System.out.println(buf.refCnt());
		}
	}
	
}