package com.pray.netty.singlereactor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 * Reactor
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/7/13 17:00
 */
public class Reactor implements Closeable, Runnable {
    private final Logger logger = LoggerFactory.getLogger(Reactor.class);
    private final ServerSocketChannel serverChannel;
    private final Selector selector;

    public Reactor() throws IOException{
        serverChannel = ServerSocketChannel.open();
        selector = Selector.open();
    }

    /**
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        serverChannel.close();
        selector.close();
    }

    /**
     *
     */
    @Override
    public void run() {
        // 建立连接并选择对应的处理器
        try {
            serverChannel.bind(new InetSocketAddress(8080));
            //要使用选择器进行操作，必须使用非阻塞的方式，这样才不会像阻塞IO那样卡在accept()，而是直接通过，让选择器去进行下一步操作
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT, new Acceptor(serverChannel, selector));
            while (true) {
                int count = selector.select();
                System.out.println("监听到 "+count+" 个事件");
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    this.dispatch(iterator.next());   //通过dispatch方法进行分发
                    iterator.remove();
                }
            }
        }catch (IOException e){
            logger.error(e.getMessage());
        }
    }

    //通过此方法进行分发
    private void dispatch(SelectionKey key){
        Object att = key.attachment();   //获取attachment，ServerSocketChannel和对应的客户端Channel都添加了的
        if(att instanceof Runnable) {
            ((Runnable) att).run();   //由于Handler和Acceptor都实现自Runnable接口，这里就统一调用一下
        }   //这样就实现了对应的时候调用对应的Handler或是Acceptor了
    }

    public static void main(String[] args) {
        //创建Reactor对象，启动，完事
        try (Reactor reactor = new Reactor()){
            reactor.run();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
