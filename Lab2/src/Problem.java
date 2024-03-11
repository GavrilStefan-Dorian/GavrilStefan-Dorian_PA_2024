import java.lang.reflect.Array;
import java.security.interfaces.RSAPrivateCrtKey;
import java.util.Arrays;

public class Problem {
    private String name;
    private Depot[] depots;
    private Client[] clients;
    private Vehicle[] vehicles;
    private int[][] timesDepotsToClients;
    private int[][] timesClientsToClients;

    public Problem(String name, Depot[] depots, Client[] clients, Vehicle[] vehicles) {
        this.name = name;
        timesDepotsToClients = new int [depots.length][clients.length];
        timesClientsToClients = new int [clients.length][clients.length];
        this.depots = new Depot[0];
        this.clients = new Client[0];
        this.vehicles = new Vehicle[0];

        for(int i = 0; i < timesDepotsToClients.length; i++) {
            timesDepotsToClients[i] = new int[clients.length];
            for (int j = 0; j < timesClientsToClients.length; j++)
                timesDepotsToClients[i][j] = (int) (Math.random() * 200);
        }

        for(int i = 0; i < timesClientsToClients.length; i++) {
            timesClientsToClients[i] = new int[clients.length];
            for (int j = 0; j < timesClientsToClients.length; j++)
                timesClientsToClients[i][j] = (int) (Math.random() * 200);
        }

        this.setDepots(depots);
        this.setClients(clients);
        this.setVehicles(vehicles);
    }

    public void addDepot(Depot depot) {
        for(Depot d : this.depots)
            if(depot.equals(d))
                return;

        Depot[] newDepot = new Depot[this.depots.length + 1];
        System.arraycopy(this.depots, 0, newDepot, 0, this.depots.length);
        newDepot[newDepot.length - 1] = depot;
        this.depots = newDepot;
    }

    public void addClient(Client client) {
        for(Client c : this.clients)
            if(client.equals(c))
                return;

        Client[] newClient = new Client[this.clients.length + 1];
        System.arraycopy(this.clients, 0, newClient, 0, this.clients.length);
        newClient[newClient.length - 1] = client;
        this.clients = newClient;
    }

    public void addVehicle(Vehicle vehicle) {
        for(Vehicle v : this.vehicles)
            if(vehicle.equals(v))
                return;

        Vehicle[] newVehicle = new Vehicle[this.vehicles.length + 1];
        System.arraycopy(this.vehicles, 0, newVehicle, 0, this.vehicles.length);
        newVehicle[newVehicle.length - 1] = vehicle;
        this.vehicles = newVehicle;
    }
    public void setDepots(Depot... depots) {
        for(Depot d : depots)
            this.addDepot(d);
    }

    public void setClients(Client... clients) {
        for(Client c : clients)
            this.addClient(c);
    }

    public void setVehicles(Vehicle ... vehicles) {
        for(Vehicle v : vehicles)
            this.addVehicle(v);
    }

    public Depot[] getDepots() {
        return depots;
    }
    public Client[] getClients() {
        return clients;
    }

    public int[][] getTimesDepotsToClients() {
        return timesDepotsToClients;
    }

    public int[][] getTimesClientsToClients() {
        return timesClientsToClients;
    }

    public String getName() {
        return name;
    }

    public Vehicle[] getVehicles() {
        int vehicleCount = 0;
        for (Depot d : depots)
            vehicleCount += d.getVehicles().length;

        Vehicle[] vehicles = new Vehicle[vehicleCount];
        int i = 0;
        for (Depot d : depots)
            for (Vehicle v : d.getVehicles())
                vehicles[i++] = v;

        return vehicles;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "\nname='" + name + '\'' +
                ",\n depots=" + Arrays.toString(depots) +
                ",\n clients=" + Arrays.toString(clients) +
                ",\n vehicles=" + Arrays.toString(vehicles) +
                ",\n timesDepotsToClients=" + Arrays.deepToString(timesDepotsToClients) +
                ",\n timesClientsToClients=" + Arrays.deepToString(timesClientsToClients) +
                "\n}";
    }
}
