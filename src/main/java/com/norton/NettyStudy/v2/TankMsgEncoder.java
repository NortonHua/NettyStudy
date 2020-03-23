package com.norton.NettyStudy.v2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class TankMsgEncoder extends MessageToByteEncoder<TankMsg>{

	@Override
	protected void encode(ChannelHandlerContext ctx, TankMsg msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		out.writeInt(msg.x);
		out.writeInt(msg.y);
	}

}
