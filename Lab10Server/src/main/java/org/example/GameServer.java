package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class GameServer {
    private int port;
    private ServerSocket serverSocket;
    private boolean flag;
    private Map<String, Game> games;
    private Map<String, ClientThread> clients;

    public GameServer(int port) {
        this.port = port;
        this.flag = true;
        this.games = new HashMap<>();
        this.clients = new HashMap<>();
    }

    public static void main(String[] args) {
        GameServer gameServer = new GameServer(8000);
        gameServer.start();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Game server started on port " + port);
            while (flag) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                ClientThread clientThread = new ClientThread(clientSocket, this);
                clientThread.start();
            }
        } catch (IOException e) {
            if (flag) {
                System.err.println("Error starting server on port " + port + ": " + e.getMessage());
            }
        } finally {
            stop();
            System.out.println("Server has been closed.");
        }
    }

    public synchronized void stop() {
        flag = false;
        try {

            for (ClientThread clientThread : clients.values()) {
                clientThread.sendResponse("Server is shutting down. Goodbye!");
                clientThread.exit();
            }

            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error stopping server on port " + port + ": " + e.getMessage());
        }
    }

    public synchronized void addClient(String clientId, ClientThread clientThread) {
        clients.put(clientId, clientThread);
    }

    public synchronized void removeClient(String clientId) {
        clients.remove(clientId);
    }

    public synchronized void addGame(String gameId, Game game) {
        games.put(gameId, game);
    }

    public synchronized Game getGame(String gameId) {
        return games.get(gameId);
    }

    public synchronized void removeGame(String gameId) {
        games.remove(gameId);
    }
}
