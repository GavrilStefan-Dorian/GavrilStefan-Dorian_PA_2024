package org.example;

import java.util.List;

public class Ship {
    private List<Integer> positions;

    public Ship(List<Integer> positions) {
        this.positions = positions;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void removePosition(int position) {
        positions.remove(Integer.valueOf(position));
    }

    public boolean isSunk() {
        return positions.isEmpty();
    }
}
