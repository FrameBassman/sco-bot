package tech.romashov.healthcheck;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.StringJoiner;

public class Main {
    public static void main(String... argv) {
        try (ServerSocket server = new ServerSocket(3000)) {
            server.setSoTimeout(1000);
            while (true) {
                try (Socket socket = server.accept()) {
                    try (InputStream input = socket.getInputStream();
                         OutputStream output = socket.getOutputStream()) {
                        byte[] buffer = new byte[10000];
                        int total = input.read(buffer);
                        String request = new String(Arrays.copyOfRange(buffer, 0, total));
                        output.write(combineResponse());
                    }
                } catch (SocketTimeoutException ex) {
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] combineResponse() {
        StringJoiner builder = new StringJoiner("\r\n");
        builder.add("HTTP/1.1 200 OK");
        builder.add("Content-Type: application/json");
        builder.add("Connection: keep-alive");
        builder.add("");
        builder.add("{\"status\": \"ok\"}");
        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }
}
