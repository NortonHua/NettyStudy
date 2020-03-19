package com.norton.NettyStudy.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class Server {

	public static void main(String[] args) {
		EventLoopGroup bossGroup =new NioEventLoopGroup(1);
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
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf=null; 
		try {
		buf=(ByteBuf) msg;
		byte[] bytes=new byte[buf.readableBytes()];
		buf.getBytes(buf.readerIndex(), bytes);
		System.out.println(bytes);
		ctx.writeAndFlush(msg);
		//System.out.println(buf);
		//System.out.println(buf.refCnt());
		}finally {
			//if(buf!=null) ReferenceCountUtil.release(buf);
			//System.out.println(buf.refCnt());
		}
	}
	
}