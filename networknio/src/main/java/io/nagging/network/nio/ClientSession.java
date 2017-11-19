package io.nagging.network.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

class ClientSession {

    private SelectionKey selectionKey;
    private SocketChannel socketChannel;
    private ByteBuffer byteBuffer;

    ClientSession(SelectionKey selectionKey, SocketChannel socketChannel) throws IOException {
        this.selectionKey = selectionKey;
        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false); // non-blocking

        this.byteBuffer = ByteBuffer.allocate(64);
    }

    private void disconnect() {
        MainServer.clientMap.remove(selectionKey);

        try {
            if (selectionKey != null) {
                selectionKey.cancel();
            }

            if (socketChannel != null) {
                System.out.println("Byte: " + socketChannel.getRemoteAddress());
                socketChannel.close();
            }
        } catch (Throwable t) {
            /*just ignore*/
        }
    }

    void read() {

        try {
            int amout = -1;
            byteBuffer.clear();
            try {
                amout = socketChannel.read(byteBuffer);
            } catch (Throwable t) {
                /*ignore*/
            }

            if (amout == -1) {
                disconnect();
            }

            if (amout < 1) {
                return;
            }

            System.out.println("Send back: " + byteBuffer.position() + " bytes");
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        } catch (Throwable t) {
            disconnect();
            t.printStackTrace();
        }
    }
}
