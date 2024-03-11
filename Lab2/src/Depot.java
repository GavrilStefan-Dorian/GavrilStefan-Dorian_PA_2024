import java.util.Arrays;
import java.util.Objects;

public class Depot {
    private String name;
    private Vehicle[] vehicles;

    private Problem problem;
    public Depot(String name, Vehicle ... vehicles) {
        this.vehicles = new Vehicle[0];
        this.setVehicles(vehicles);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVehicles(Vehicle... vehicles) {
        for(Vehicle v : vehicles)
            this.addVehicle(v);
//
//        for (Depot d : problem.getDepots())
//            outer:
//                    for (Vehicle vToAdd : d.vehicles) {
//                        for (Vehicle v : this.vehicles)
//                            if (v.equals(vToAdd))
//                                continue outer;
//                        Vehicle[] newVehicles = new Vehicle[this.vehicles.length + 1];
//                        System.arraycopy(this.vehicles, 0, newVehicles, 0, this.vehicles.length);
//                        newVehicles[newVehicles.length - 1] = vToAdd;
//                        vToAdd.setDepot(this);
//                        this.vehicles = newVehicles;
//                    }
    }

    public void addVehicle(Vehicle vehicle) {
        for(Vehicle v : this.vehicles)
            if(vehicle.equals(v))
                return;

        Vehicle[] newVehicle = new Vehicle[this.vehicles.length + 1];
        System.arraycopy(this.vehicles, 0, newVehicle, 0, this.vehicles.length);
        newVehicle[newVehicle.length - 1] = vehicle;
        this.vehicles = newVehicle;

        vehicle.setDepot(this);

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
