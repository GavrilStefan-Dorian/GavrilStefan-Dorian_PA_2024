package org.example;
import java.io.*;
import java.net.*;

public class QuizClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 8000;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_IP, PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String userInputLine;
            while ((userInputLine = userInput.readLine()) != null) {
                out.println(userInputLine);
            }
        } catch (UnknownHostException e) {
            System.err.println("Adresa IP a serverului este incorectă: " + SERVER_IP);
        } catch (IOException e) {
            System.err.println("Nu se poate conecta la serverul de la adresa " + SERVER_IP + " și portul " + PORT);
        }
    }
}
