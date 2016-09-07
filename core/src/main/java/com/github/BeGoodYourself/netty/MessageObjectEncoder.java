package com.github.BeGoodYourself.netty;

import com.github.BeGoodYourself.util.ProtobufSerializeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Administrator on 2016/9/7.
 */
public class MessageObjectEncoder extends MessageToByteEncoder<Object>{
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        byte[] bytes = ProtobufSerializeUtil.serialize(msg);
        byte[] clsNameBytes = msg.getClass().getName().getBytes();
        ByteBuf buf = Unpooled.buffer(bytes.length + clsNameBytes.length + 5);
        buf.writeInt(bytes.length + clsNameBytes.length + 1);
        buf.writeByte(clsNameBytes.length);
        buf.writeBytes(clsNameBytes);
        buf.writeBytes(bytes);
        out.writeBytes(buf);
    }
}
