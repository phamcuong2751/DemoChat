package org.example.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientChat {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Thread receiverThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("Received from server: " + serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiverThread.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = reader.readLine()) != null) {
                out.println(userInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
