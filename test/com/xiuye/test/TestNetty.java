package com.xiuye.test;

import org.junit.Test;

import com.xiuye.test.TestNetty.DiscardServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestNetty {

	static class DiscardServerHandler implements ChannelHandler {

		@Override
		public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
			// TODO Auto-generated method stub

		}

		@Override
		public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
			// TODO Auto-generated method stub

		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			// TODO Auto-generated method stub

		}

	}

	static class DiscardServer {
		private int port;

		public DiscardServer(int port) {
			this.port = port;
		}

		public void run() throws InterruptedException {
			EventLoopGroup bossGroup = new NioEventLoopGroup();
			EventLoopGroup workerGroup = new NioEventLoopGroup();
			try {

				ServerBootstrap b = new ServerBootstrap();

				b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
						.childHandler(new ChannelInitializer<SocketChannel>() {

							@Override
							protected void initChannel(SocketChannel ch) throws Exception {
								ch.pipeline().addLast(new DiscardServerHandler());
							}

						}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
				ChannelFuture f = b.bind(port).sync();

				f.channel().closeFuture().sync();

			} 
			finally{
				bossGroup.shutdownGracefully();
				workerGroup.shutdownGracefully();
			}
		}
	}

	@Test
	public void test1() {
		int port = 9999;
		new DiscardServer(port);
	}

}
