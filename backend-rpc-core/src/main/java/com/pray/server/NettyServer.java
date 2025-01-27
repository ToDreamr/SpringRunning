package com.pray.server;


import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RuntimeUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import java.util.concurrent.TimeUnit;

/**
 * NettyServer
 *
 * @author Cotton Eye Joe
 * @since 2024/11/21 18:08
 */
public class NettyServer extends AbstractRpcServer{

    /**
     *
     */
    @Override
    public void start(int port) {
        //使用Netty的事件循环组具体来实现交流的代码
        NioEventLoopGroup handleConnectionGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        // 里面的参数是线程数，这里是处理消息的线程数,false是指定线程工厂是否是守护线程
        DefaultEventExecutorGroup serviceHandlerGroup = new DefaultEventExecutorGroup(
                RuntimeUtil.getProcessorCount() * 2,
                ThreadUtil.newNamedThreadFactory("service-handler-group", false)
        );

        try {
            /**
             * boss线程组用于处理连接工作，worker线程组用于数据处理
             * 依次的结构是 group -> channel -> childHandler -> handler
             * group 用于处理连接，channel 用于处理数据，childHandler 用于处理连接的数据，handler 用于处理数据的
             * 所属关系:一个 group 可以有多个 channel，一个 channel 可以有多个 childHandler，一个 childHandler 可以有多个 handler
             * 一个 channel 只能有一个 childHandler，一个 childHandler 可以有多个 handler
             */
            ServerBootstrap b = new ServerBootstrap();
            b.group(handleConnectionGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.TRACE))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
//                                    .addLast(new HttpServerCodec())
//                                    .addLast(new HttpObjectAggregator(65536))
//                                    .addLast(new ChunkedWriteHandler())
                                    // 30之内没有收到客户端请求，就会触发IdleStateHandler的userEventTriggered方法
                                    .addLast(new IdleStateHandler(30,0,0, TimeUnit.SECONDS))
                                    .addLast(serviceHandlerGroup, new LoggingHandler());
//                                    .addLast(new TestNettyHandler());
                            // todo 接收消息，将消息先编码，然后解码成ZMessage格式，最后交由NettyHttpServerHandler处理
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            System.out.println("Server is now listening on port " + port);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            workGroup.shutdownGracefully();
            handleConnectionGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyServer().start(8081);
    }
}
