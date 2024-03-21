import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Main {
    public static void main(String[] args) {
        Concert concert1 = new Concert("Concert 1");
        Concert concert2 = new Concert("Concert 2");
        Concert concert3 = new Concert("Concert 3");
        Concert concert4 = new Concert("Concert 4");
        Church church1 = new Church("Church 1");
        Church church2 = new Church("Church 2");
        Statue statue1 = new Statue("Statue 1");
        Statue statue2 = new Statue("Statue 2");

        Map<LocalDate, TimeInterval> concert1Schedule = new HashMap<>();
        concert1Schedule.put(LocalDate.now(), new TimeInterval(LocalTime.NOON, LocalTime.MIDNIGHT));
        concert1.setTimeTable(concert1Schedule);

        Map<LocalDate, TimeInterval> concert2Schedule = new HashMap<>();
        concert2Schedule.put(LocalDate.now().plusDays(1), new TimeInterval(LocalTime.of(8, 0), LocalTime.of(12, 30)));
        concert2.setTimeTable(concert2Schedule);

        Map<LocalDate, TimeInterval> concert3Schedule = new HashMap<>();
        concert3Schedule.put(LocalDate.now().plusDays(2), new TimeInterval(LocalTime.of(9, 0), LocalTime.of(11, 0)));
        concert3.setTimeTable(concert3Schedule);

        Map<LocalDate, TimeInterval> concert4Schedule = new HashMap<>();
        concert4Schedule.put(LocalDate.now().plusDays(4), new TimeInterval(LocalTime.of(12, 0), LocalTime.of(15, 40)));
        concert4.setTimeTable(concert4Schedule);

        Map<LocalDate, TimeInterval> church1Schedule = new HashMap<>();
        church1Schedule.put(LocalDate.now(), new TimeInterval(LocalTime.of(9, 0), LocalTime.of(17, 0)));
        church1.setTimeTable(church1Schedule);

        Map<LocalDate, TimeInterval> church2Schedule = new HashMap<>();
        church2Schedule.put(LocalDate.now().plusDays(1), new TimeInterval(LocalTime.of(10, 0), LocalTime.of(16, 0)));
        church2.setTimeTable(church2Schedule);

        List<Attraction> attractions = new ArrayList<>();
        attractions.add(concert1);
        attractions.add(concert2);
        attractions.add(concert3);
        attractions.add(concert4);
        attractions.add(church1);
        attractions.add(church2);
        attractions.add(statue1);
        attractions.add(statue2);

        Trip trip = new Trip("Iasi", LocalDate.now(), LocalDate.now().plusDays(5), attractions);

        System.out.println(trip);

        TravelPlan travelPlan = new TravelPlan(trip);

        for (int i = 0; i < trip.getDaysCount(); i++) {
            travelPlan.addDate(trip.getStart().plusDays(i));
        }

        GraphColoring graphColoring = new GraphColoring(trip);
        graphColoring.colorStaticDescendingDegree();

        System.out.println();
        System.out.println(graphColoring);

        graphColoring.colorBySaturation();

        System.out.println();
        System.out.println(graphColoring);
    }
}
