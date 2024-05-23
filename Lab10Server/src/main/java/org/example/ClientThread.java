package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private GameServer server;
    private String clientId;
    private String gameId;
    private String clientName;
    private Game game;
    private BattleshipBoard board;

    public ClientThread(Socket clientSocket, GameServer server) {
        this.clientSocket = clientSocket;
        this.server = server;
        this.clientId = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();
        this.board = new BattleshipBoard(game);
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error creating input/output streams for client socket: " + e.getMessage());
        }
    }

    public String getClientId() {
        return clientId;
    }

    public PrintWriter getOut() {
        return out;
    }

    public BattleshipBoard getBoard() {
        return board;
    }

    @Override
    public void run() {
        server.addClient(clientId, this);
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received command from client: " + inputLine);
                String[] commandParts = inputLine.split(" ");
                if (!isValidCommand(commandParts)) {
                    sendResponse(getErrorMessage(commandParts[0]));
                    continue;
                }
                switch (commandParts[0]) {
                    case "exit":
                        exit();
                        break;
                    case "stop":
                        server.stop();
                        sendResponse("Server stopped");
                        break;
                    case "create":
                        gameId = commandParts[1];
                        Game newGame = new Game(gameId);
                        getBoard().setGame(newGame);
                        server.addGame(gameId, newGame);
                        sendResponse("Game created with ID: " + gameId);
                        break;
                    case "join":
                        gameId = commandParts[1];
                        clientName = commandParts[2];
                        game = server.getGame(gameId);
                        getBoard().setGame(game);
                        if (game != null && game.addPlayer(this)) {
                            sendResponse("Joined game: " + gameId);
                        } else {
                            sendResponse("Failed to join game: " + gameId);
                        }
                        break;
                    case "move":
                        if (commandParts[1].toUpperCase().charAt(0) >= 'A'
                                && commandParts[1].toUpperCase().charAt(0) <= 'H'
                                && commandParts[2].charAt(0) >= '1'
                                && commandParts[2].charAt(0) <= '8') {
                            gameId = game.getGameId();
                            int col = (int) (commandParts[1].toUpperCase().charAt(0) - 'A');
                            int row = Integer.parseInt(commandParts[2]) - 1;
                            game = server.getGame(gameId);
                            if (game != null && game.isStarted()) {
                                if (game.getPlayers()[game.getCurrentPlayer()] == this) {
                                    boolean hit = game.makeMove(this, row, col);
                                    sendResponse("Move result: " + (hit ? "hit" : "miss"));
                                } else {
                                    sendResponse("It's not your turn.");
                                }
                            } else {
                                sendResponse("Invalid game or game not started: " + gameId);
                            }
                        } else {
                            sendResponse("Invalid position! Learn how to read --help");
                        }
                        break;
                    case "help":
                        sendResponse("""
                                1.Setting Up the Game Board:
                                    The game is played on an 8x8 grid.
                                    Rows are numbered from 1 to 8, and columns are labeled with letters from A to H.
                                2.Placing the Ships:
                                    You need to place the following types of ships:
                                        3 ships of size 2
                                        2 ships of size 3
                                        2 ships of size 4
                                        1 ship of size 5
                                    Ships can be placed either horizontally (along the X-axis) or vertically (along the Y-axis).
                                    The game board is described as follows:
                                    “-” represents water (a location where no move has been made).
                                    “1-8” represents your own ships.
                                    “X” represents a successful hit on an enemy ship.
                                    “O” represents a miss.
                                3.Gameplay:
                                  Each player has two game boards:
                                    One shows their own ships.
                                    The other shows the opponent’s board, including attacked locations.
                                  A valid move is in the format “submit move L N,” where:
                                    “L” is an uppercase letter from A to H (column).
                                    “N” is a digit from 1 to 8 (row).
                                  Example of a valid move: “submit move A5.”
                                4.Available Commands for Players:
                                  “create <game>” to create a game.
                                  “join <game> <nickname>” to join a game (where “<nickname>” is your name).
                                  “move <L> <N>” to make a move.
                                  “exit” to leave the game and disconnect from the server.
                                  “help” to view the instructions.
                                  “stop” to stop the server.
                                5.Ending the Game:
                                  The game ends when you see the message “The winner is: 'X'!”
                                Enjoy the game!""");
                        break;
                    default:
                        sendResponse("Unknown command: " + commandParts[0]);
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            server.removeClient(clientId);
        }
    }

    public void sendResponse(String response) {
        out.println(response);
        out.flush();
    }

    public String getClientName() {
        return clientName;
    }

    private boolean isValidCommand(String[] commandParts) {
        return switch (commandParts[0]) {
            case "create" -> commandParts.length == 2;
            case "join" -> commandParts.length == 3;
            case "move" -> commandParts.length == 3;
            case "stop", "exit" -> true;
            default -> false;
        };
    }

    private String getErrorMessage(String command) {
        return switch (command) {
            case "create" -> "Error: 'create' command requires a game ID. Usage: create <game>";
            case "join" -> "Error: 'join' command requires a game ID and a nickname. Usage: join <game> <nickname>";
            case "move" -> "Error: 'move' command requires positions. Usage: move <L> <N>";
            default -> "Error: Unknown command or incorrect usage.";
        };
    }
    public void exit() {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error disconnecting client: " + e.getMessage());
        }
    }

}

