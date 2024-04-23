package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Game {
    private final Bag bag = new Bag(4);
    private final List<Player> players = new ArrayList<>();
    private boolean isRunning = true;
    private Player foundWinner = null;

    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        game.addPlayer(new Player("Player 1", true));
//        game.addPlayer(new Player("Player 2", true));
//        game.addPlayer(new Player("Player 3", true));
        game.play();
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
    }

    public void play() throws InterruptedException {
        List<Thread> playerThreads = new ArrayList<>();

        for (Player player : players) {
            Thread playerThread = new Thread(player);
            playerThreads.add(playerThread);
            playerThread.start();
        }

        Thread timekeeper = getTimekeeper();
        timekeeper.start();

        while (isRunning()) {
            synchronized (getBag()) {
                if (getBag().getTiles().isEmpty() || getFoundWinner() != null) {
                    setRunning(false);
                    getBag().notifyAll();
                    break;
                }
                getBag().notifyAll();
            }
        }
        for (Thread playerThread : playerThreads) {
            playerThread.join();
        }

        for (Player player : players) {
            System.out.println(player.getName() + "'s tiles are: " + player.getTiles());
        }

        if (foundWinner == null) {
            List<Player> sortedPlayers = players.stream()
                    .sorted(Comparator.comparing(Player::getScore))
                    .toList();
            System.out.println("The winner with longest sequence is: " + sortedPlayers.get(getPlayers().size() - 1).getName());
        }
        else {
            System.out.println("The winner with a complete sequence is: " + foundWinner.getName());
        }
    }

    private Thread getTimekeeper() {
        Thread timekeeper = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            long timeLimit = 5000;
            long lastPrintTime = startTime;

            while (isRunning) {
                if (System.currentTimeMillis() - lastPrintTime >= 100) {
                    lastPrintTime = System.currentTimeMillis();
                    System.out.println("Running time: " + (float)(lastPrintTime - startTime) / 1000 + " seconds");
                }

                if (System.currentTimeMillis() - startTime >= timeLimit) {
                    isRunning = false;
                    synchronized (bag) {
                        bag.notifyAll();
                    }
                    System.out.println("Time's up! Game over.");
                }
            }
            System.out.println("Final running time: " + (float)(System.currentTimeMillis() - startTime) / 1000 + " seconds");
        });
        timekeeper.setDaemon(true);
        return timekeeper;
    }


    public synchronized boolean isRunning() {
        return isRunning;
    }

    public synchronized void setRunning(boolean running) {
        this.isRunning = running;
    }

    public Bag getBag() {
        return bag;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setFoundWinner(Player foundWinner) {
        this.foundWinner = foundWinner;
    }

    public Player getFoundWinner() {
        return foundWinner;
    }
}
