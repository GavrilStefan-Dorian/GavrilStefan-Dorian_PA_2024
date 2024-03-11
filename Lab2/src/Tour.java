import java.util.Arrays;

public class Tour {
    private Vehicle vehicle;
    private Client[] clients;

    public Tour(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.clients = new Client[0];
    }
    public void addClient(Client client) {
        Client[] newClient = new Client[this.clients.length + 1];
        System.arraycopy(this.clients, 0, newClient, 0, this.clients.length);
        newClient[newClient.length - 1] = client;
        this.clients = newClient;
    }

    @Override
    public String toString() {
        StringBuilder tour = new StringBuilder();
        tour.append(this.vehicle.getDepot().getName());
        tour.append(", ");

        for(Client c : clients) {
            tour.append(c.getName());
            tour.append(", ");
        }

        tour.append(this.vehicle.getDepot().getName());
        tour.append('\n');
        return tour.toString();
    }
}
