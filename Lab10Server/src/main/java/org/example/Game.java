package org.example;

import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private String gameId;
//    private ClientThread player1;
//    private ClientThread player2;
    private ClientThread[] players;
    private boolean isStarted;
    private int currentPlayer;
    private static final long TURN_TIME_LIMIT = 30000;
    private long currentPlayerRemainingTime = TURN_TIME_LIMIT;
    private Timer timer;

    public Game(String gameId) {
        this.gameId = gameId;
        this.isStarted = false;
        players = new ClientThread[2];
        currentPlayer = 0;
    }

    public String getGameId() {
        return gameId;
    }

    public synchronized boolean addPlayer(ClientThread player) {
        if (players[currentPlayer] == null) {
            players[currentPlayer] = player;
            return true;
        } else if (players[1 - currentPlayer] == null) {
            players[1 - currentPlayer] = player;
            isStarted = true;
            currentPlayer = 0;

            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (currentPlayerRemainingTime % 10000 == 0 && currentPlayerRemainingTime != 0)
                        players[currentPlayer].sendResponse("It's your turn! You have " + currentPlayerRemainingTime / 1000 + " seconds left!");

                    currentPlayerRemainingTime -= 1000;

                    if (currentPlayerRemainingTime <= 0) {
                        players[currentPlayer].sendResponse("Time's up! You lost!");
                        players[1 - currentPlayer].sendResponse(players[currentPlayer].getClientName() + "'s time is up! You won!");
                        endGame();
                        timer.cancel();
                        timer.purge();
                    }

                    if(!isStarted) {
                        timer.cancel();
                        timer.purge();
                    }
                }
            }, 1000, 1000);

            announceBoardStates();
            return true;
        } else {
            return false;
        }
    }

    public ClientThread[] getPlayers() {
        return players;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isStarted() {
        return isStarted;
    }


    public synchronized boolean makeMove(ClientThread player, int row, int col) {
        if (!isStarted) return false;
        if (player != players[currentPlayer]) return false;

        currentPlayerRemainingTime = TURN_TIME_LIMIT;
        boolean hit;
        hit = players[currentPlayer].getBoard().makeMove(row, col);

        if(!hit)
            currentPlayer = 1 - currentPlayer;

        announceBoardStates();
        checkForWin(players[1 - currentPlayer]);
        return hit;
    }

    public void announceBoardStates() {
        players[currentPlayer].sendResponse(players[currentPlayer].getBoard().getBoardState());
        players[1 - currentPlayer].sendResponse(players[1 - currentPlayer].getBoard().getBoardState());
    }

    public void announceShipSunk() {
        if(currentPlayer == 0){
            players[currentPlayer].sendResponse("You sank a ship of " + players[1 - currentPlayer].getClientName() + "'s!");
            players[1 - currentPlayer].sendResponse(players[currentPlayer].getClientName() + " has sunk one of your ships!");
        }
        else {
            players[1 - currentPlayer].sendResponse("You sank a ship of " + players[currentPlayer].getClientName() + "'s!");
            players[currentPlayer].sendResponse(players[1 - currentPlayer].getClientName() + " has sunk one of your ships!");
        }
    }

    public void checkForWin(ClientThread player) {
        if (!player.getBoard().hasShips()) {
            players[currentPlayer].sendResponse("The winner is: " + (players[currentPlayer] == player ? players[1 - currentPlayer].getClientName() : players[currentPlayer].getClientName()) + "!");
            players[1 - currentPlayer].sendResponse("The winner is: " + (players[1 - currentPlayer] == player ? players[currentPlayer].getClientName() : players[1 - currentPlayer].getClientName()) + "!");
            endGame();
        }
    }


    private void endGame() {
        isStarted = false;
    }
}
