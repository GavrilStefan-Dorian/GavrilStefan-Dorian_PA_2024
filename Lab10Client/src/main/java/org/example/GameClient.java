package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClient {
    public void start() {
        String serverAddress = "127.0.0.1";
        int PORT = 8000;
        try (Socket socket = new Socket(serverAddress, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            // Create a new thread for reading server messages
            Thread readerThread = new Thread(() -> {
                try {
                    String responseLine;
                    while ((responseLine = in.readLine()) != null) {
                        if(!responseLine.isEmpty())
                            System.out.println(responseLine);
                    }
                } catch (Exception e) {
                    System.out.println("Error reading from server: " + e.getMessage());
                }

            });
            readerThread.start();

            while (true) {
                System.out.println("Enter command: ");
                String command = console.readLine();
                out.println(command);
                if (command.equalsIgnoreCase("exit")) {
                    break;
                }
            }

            // Interrupt and join the reader thread when exiting
            readerThread.interrupt();
            readerThread.join();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
