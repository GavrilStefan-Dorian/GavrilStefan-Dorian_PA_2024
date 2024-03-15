import org.w3c.dom.Attr;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String arggs[]) {
        Concert concert = new Concert("Concert 1");
        TimeInterval timeInterval = new TimeInterval(LocalTime.NOON, LocalTime.MIDNIGHT);

        HashMap<LocalDate, TimeInterval> timeTable = new HashMap<LocalDate, TimeInterval>();

        timeTable.put(LocalDate.now(), timeInterval);

        concert.setTimeTable(timeTable);
        concert.setTicketPrice(20);
        Church church = new Church("Church 1");
        church.setTimeTable(timeTable);

        Statue statue = new Statue("Statue 1");

        Visitable[] arr = {concert, church, concert};

        List<Attraction> list = new ArrayList<Attraction>();

        list.add(concert);
        list.add(statue);
        list.add(church);

        Trip trip = new Trip("Iasi", LocalDate.now(), LocalDate.of(2024, 10, 5), list);

        System.out.println(trip);
    }
}