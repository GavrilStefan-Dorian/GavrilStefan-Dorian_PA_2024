package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BattleshipBoard {
    private static final int SIZE = 8;
    private static final char EMPTY = '-';
    private static final char HIT = 'X';
    private static final char MISS = 'O';
    private char[][] shipsBoard = new char[SIZE][SIZE];
    private char[][] movesBoard = new char[SIZE][SIZE];
    private int[][] SHIPSCONFIGS = {{3, 2}, {2, 3}, {2, 4}, {1, 5}};

    private Map<Character, Ship> ships;
    private Game game;



    public BattleshipBoard(Game game) {
        this.game = game;
        initializeBoard();
        placeShips();
    }

    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                shipsBoard[i][j] = EMPTY;
                movesBoard[i][j] = EMPTY;
            }
        }
    }

    private void placeShips() {
        ships = new HashMap<>();
        Random random = new Random();
        int[][] shipConfigs = SHIPSCONFIGS;
        int shipId = 1;
        for (int[] shipConfig : shipConfigs) {
            int count = shipConfig[0];
            int length = shipConfig[1];
            for (int i = 0; i < count; i++) {
                boolean placed = false;
                while (!placed) {
                    placed = placeShip(random, length, shipId);
                }
                shipId++;
            }
        }
    }

    private boolean placeShip(Random random, int length, int shipId) {
        boolean horizontal = random.nextBoolean();
        int row, col;
        if (horizontal) {
            row = random.nextInt(SIZE);
            col = random.nextInt(SIZE - length + 1);
            for (int i = 0; i < length; i++) {
                if (shipsBoard[row][col + i] != EMPTY) {
                    return false;
                }
            }
            List<Integer> positions = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                shipsBoard[row][col + i] = (char) ('0' + shipId);
                positions.add(row * SIZE + col + i);
            }
            ships.put((char) ('0' + shipId), new Ship(positions));
        } else {
            row = random.nextInt(SIZE - length + 1);
            col = random.nextInt(SIZE);
            for (int i = 0; i < length; i++) {
                if (shipsBoard[row + i][col] != EMPTY) {
                    return false;
                }
            }
            List<Integer> positions = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                shipsBoard[row + i][col] = (char) ('0' + shipId);
                positions.add((row + i) * SIZE + col);
            }
            ships.put((char) ('0' + shipId), new Ship(positions));
        }
        return true;
    }

    public boolean makeMove(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || movesBoard[row][col] != EMPTY) {
            return false;
        }

        char target = game.getPlayers()[1 - game.getCurrentPlayer()].getBoard().shipsBoard[row][col];
        boolean hit = target != EMPTY && target != HIT && target != MISS;

        movesBoard[row][col] = hit ? HIT : MISS;

        game.getPlayers()[1 - game.getCurrentPlayer()].getBoard().markHit(row, col, hit);

        if (hit) {
            game.checkForWin(game.getPlayers()[1 - game.getCurrentPlayer()]);
        }

        return hit;
    }

    public void markHit(int row, int col, boolean isHit) {
        char shipId = shipsBoard[row][col];
        shipsBoard[row][col] = isHit ? HIT : MISS;
        if (isHit) {
            int position = row * SIZE + col;
            Ship ship = ships.get(shipId);
            if (ship.getPositions().contains(position)) {
                ship.removePosition(position);
                if (ship.isSunk()) {
                    ships.remove(shipId);
                    game.announceShipSunk();
                }
            }
        }
    }

    public String getBoardState() {
        StringBuilder boardState = new StringBuilder();
        boardState.append(" |");

        for (int i = 0; i < SIZE; i++) {
            boardState.append((char) ('A' + i)).append(" ");
        }

        boardState.append("               ");
        boardState.append(" |");

        for (int i = 0; i < SIZE; i++) {
            boardState.append((char) ('A' + i)).append(" ");
        }
        boardState.append('\n');

        for (int i = 0; i < SIZE; i++) {
            boardState.append(i + 1).append("|");
            for (int j = 0; j < SIZE; j++) {
                boardState.append(shipsBoard[i][j]).append(" ");
            }
            boardState.append("               ");
            boardState.append(i + 1).append("|");
            for (int j = 0; j < SIZE; j++) {
                boardState.append(movesBoard[i][j]).append(" ");
            }
            boardState.append('\n');
        }
        return boardState.toString();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean hasShips() {
        return !ships.isEmpty();
    }
}
