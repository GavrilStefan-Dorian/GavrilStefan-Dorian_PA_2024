import java.util.Arrays;
import java.util.Objects;

public class Depot {
    private String name;
    private Vehicle[] vehicles;

    public Depot() {
    }

    public Depot(String name) {
        this.name = name;
    }

    public Depot(Vehicle[] vehicles) {
        this.vehicles = vehicles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVehicles(Vehicle ... vehicles) {
        this.vehicles = vehicles;
        for(Vehicle v : vehicles) {
            v.setDepot(this);
        }
    }

    public String getName() {
        return name;
    }

    public Vehicle[] getVehicles() {
        return vehicles;
    }

    @Override
    public String toString() {
        return "Depot{" +
                "name='" + name + '\'' +
                ", vehicles=" + Arrays.toString(vehicles) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Depot depot = (Depot) o;
        return Objects.equals(name, depot.name) && Arrays.equals(vehicles, depot.vehicles);
    }
}
