import java.time.LocalTime;


public class Main {
    public static void main(String args[]) {
        Client c1 = new Client();
        c1.setName("Client 1");
        c1.setMinTime(LocalTime.of(8, 0));
        c1.setMaxTime(LocalTime.of(12, 30));

        System.out.println(c1.getName());

        Client c2 = new Client("Client 2");
        System.out.println(c2);

        Client c3 = new Client("Client 3",
                LocalTime.NOON, LocalTime.MIDNIGHT);
        System.out.println(c3);

        Client c4 = new Client("Client 4", ClientType.PREMIUM);
        System.out.println(c4.getName() + " type=" +  c4.getType());
    }
}