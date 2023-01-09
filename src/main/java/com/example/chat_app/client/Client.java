package com.example.chat_app.client;

import com.example.chat_app.ChatController;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientName;

    public Client(Socket socket, String clientName) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.clientName = clientName;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        System.out.println(message);
        try {
            /*bufferedWriter.write(clientName);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Scanner scanner = new Scanner(System.in);*/
                //String message = scanner.nextLine();
                bufferedWriter.write(clientName+" : " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listenToMessage(VBox vBox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String messageFromChat = bufferedReader.readLine();
                        System.out.println(messageFromChat);
                        ChatController.showReceivedMessages(messageFromChat, vBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }

    /*public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter name");
        String username = scanner.nextLine();
        Socket socket;
        try {
            socket = new Socket("127.0.0.1", 1000);
            Client client = new Client(socket, username);
            VBox vBox = new VBox();
            client.listenToMessage(vBox);
            client.sendMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
}
