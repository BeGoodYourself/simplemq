package com.github.BeGoodYourself.netty;

import com.github.BeGoodYourself.util.ProtobufSerializeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class MessageObjectDecoder extends ByteToMessageDecoder{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBuf buf = Unpooled.copiedBuffer(in);
        int clsLen = buf.readByte() & 0xffff;
        if(clsLen >buf.readableBytes()){
            return ;
        }
        byte[] claBytes = new byte[clsLen];
        buf.readBytes(claBytes, 0, clsLen);
        byte[] val = new byte[buf.readableBytes()];
        buf.readBytes(val, 0, buf.readableBytes());
        out.add(ProtobufSerializeUtil.derialize(Class.forName(new String(claBytes)), val));
    }
}
