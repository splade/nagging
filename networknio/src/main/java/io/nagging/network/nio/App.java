package io.nagging.network.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.Socket;

public class App {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 10086);
        OutputStream out = socket.getOutputStream();

        new Thread(() -> {
            long timestamp = System.currentTimeMillis();
            while (true) {
                try {
                    out.write(0xBB);
                    out.write(0xAA);
                    out.write(0x10);
                    out.write(0x34);
                    out.flush();

                    if (System.currentTimeMillis() - timestamp > 10 * 1000) { // force quit
                        socket.close();
                        break;
                    }

                    Thread.sleep(1000);
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        InputStream in = socket.getInputStream();
        int amount;

        try {
            while ((amount = in.read()) > 0) { // blocking until can read one byte
                System.out.println(Integer.toHexString(amount));
            }
        } catch (IOException e) {
            /*ignore*/
        }

    }
}
