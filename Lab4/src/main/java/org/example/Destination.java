package org.example;

public class Destination implements Comparable<Destination>{
    private String name;

    public Destination(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Destination o) {
            return o.getName().compareTo(this.getName());
    }
}
