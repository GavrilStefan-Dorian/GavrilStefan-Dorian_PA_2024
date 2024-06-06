package org.example;
import java.io.*;
import java.net.*;

public class QuizServer {
    private static final int PORT =8000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serverul a pornit pe portul " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client conectat: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
