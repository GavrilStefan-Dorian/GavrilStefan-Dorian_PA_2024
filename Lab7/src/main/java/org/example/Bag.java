package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bag {
    private int size;
    private final List<Tile> tiles=new ArrayList<>();
    public Bag(int n){
        size = n;
        for (int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                if (i!=j){
                    tiles.add(new Tile(i,j));
                }
            }
        }
    }
    public synchronized List<Tile> extractTile(int howMany) {
        Random random=new Random();
        List<Tile> extracted = new ArrayList<>();

        for (int i = 0; i < howMany; i++) {
            if (tiles.isEmpty()) {
                break;
            }
            int randomRandom= random.nextInt(tiles.size());
            extracted.add(tiles.get(randomRandom));
            tiles.remove(tiles.get(randomRandom));
        }
        return extracted;
    }

    public int getSize() {
        return size;
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}