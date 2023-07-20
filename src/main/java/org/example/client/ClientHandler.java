package org.example.client;

import org.example.server.ServerChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable  {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                System.out.println("Received from client: " + clientMessage);
                ServerChat.broadcastMessage(clientMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendMessage(String message) {
        out.println(message);
    }
}
