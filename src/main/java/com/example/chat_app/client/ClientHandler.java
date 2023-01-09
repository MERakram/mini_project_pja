package com.example.chat_app.client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientName;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.clientName = bufferedReader.readLine();
            clientHandlers.add(this);
//            broadCastMessage("server :"+ clientName+ "has entred chat");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        String messageFromClient;
        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                broadCastMessage(messageFromClient);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void broadCastMessage(String messageToSent) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientName.equals(clientName)) {
                    clientHandler.bufferedWriter.write(messageToSent);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadCastMessage(clientName+ " has logout");
    }
}
