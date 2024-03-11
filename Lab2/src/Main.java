import javax.swing.*;
import java.time.LocalTime;


public class Main {
    public static void main(String[] args) {

        Client c1 = new Client();
        c1.setName("Client 1");
        c1.setMinTime(LocalTime.of(8, 0));
        c1.setMaxTime(LocalTime.of(12, 30));

        System.out.println(c1.getName());

        Client c2 = new Client("Client 2");
        c2.setMinTime(LocalTime.of(5, 0));
        c2.setMaxTime(LocalTime.of(10, 0));

        System.out.println(c2);

        Client c3 = new Client("Client 3",
                LocalTime.NOON, LocalTime.MIDNIGHT);

        System.out.println(c3);

        Client c4 = new Client("Client 4", LocalTime.of(4,0),
                LocalTime.of(10,0));

        Client c5 = new Client("Client 5", LocalTime.of(3,0),
                LocalTime.of(8,0));

        Client c6 = new Client("Client 6", LocalTime.of(7,0),
                LocalTime.of(9,0));

        Client c7 = new Client("Client 7", ClientType.PREMIUM);

        System.out.println(c7.getName() + " type=" + c7.getType());

        Depot d1 = new Depot("Depot 1");

        System.out.println(d1);

        Truck t1 = new Truck("Truck 1");

        System.out.println(t1);

        t1.setDepot(d1);

        System.out.println(t1);

        c7.setMinTime(LocalTime.of(6,0));
        c7.setMaxTime(LocalTime.of(11, 0));

        Truck t2 = new Truck("Truck 2");
        Drone dn1 = new Drone("Drone 1");
        Drone dn2 = new Drone("Drone 2");

        Depot d2 = new Depot("Depot 2", t2, dn1);
        Depot d3 = new Depot("Depot 3", dn2);

        Depot[] depots = {d1, d2, d3};
        Client[] clients = {c1, c2, c3, c4, c5, c6, c7};
        Vehicle[] vehicles = {dn1, dn2, t1, t2};

        Problem p1 = new Problem("Problem 1", depots, clients, vehicles);

        System.out.println(p1);

        Solution s1 = new Solution(p1);

        System.out.println(s1);
    }
}