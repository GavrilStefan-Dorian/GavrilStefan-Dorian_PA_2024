package org.example;

public class Tile{
    private int first;
    private int second;
    Tile (int numberOne, int numberTwo){
        this.first =numberOne;
        this.second =numberTwo;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }
    @Override
    public String toString() {
        return "[" + first + ", " + second + "]";
    }
}