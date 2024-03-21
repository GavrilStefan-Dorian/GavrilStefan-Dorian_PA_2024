public abstract class Attraction
        implements Comparable<Attraction> {
    private String name;
    public Attraction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Attraction{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Attraction other) {
        if(other == null) throw new NullPointerException();
        return this.name.compareTo(other.name);
    }
}