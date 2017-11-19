package io.nagging.network.nio;

import sun.applet.Main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private SelectionKey selectionKey;

    private MainServer(InetSocketAddress socketAddress) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        selector = Selector.open();
        selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        serverSocketChannel.bind(socketAddress);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                loop();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    static Map<SelectionKey, ClientSession> clientMap = new ConcurrentHashMap<>();

    private void loop() throws IOException {
        selector.selectNow();

        for (SelectionKey key : selector.selectedKeys()) {
            try {
                if (!key.isValid()) {
                    continue;
                }

                if (key == selectionKey) {
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    if (socketChannel == null)
                        continue;

                    socketChannel.configureBlocking(false);
                    SelectionKey readkey = socketChannel.register(selector, SelectionKey.OP_READ);

                    clientMap.put(readkey, new ClientSession(readkey, socketChannel));
                    System.out.println("New Client IP = " + socketChannel.getRemoteAddress() + ", Total Clients = " + clientMap.size());
                }

                if (key.isReadable()) {
                    ClientSession session = clientMap.get(key);

                    if (session == null) {
                        continue;
                    }

                    session.read();
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        selector.selectedKeys().clear();
    }


    public static void main(String[] args) throws IOException {
        new MainServer(new InetSocketAddress("localhost", 10086));
    }


}
