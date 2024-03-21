import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Trip {
    private String city;
    private LocalDate start, end;

    private int daysCount;
    private List<Attraction> attractions = new ArrayList<>();

    public Trip(String city, LocalDate start, LocalDate end, List<Attraction> attractions) {
        this.city = city;
        this.start = start;
        this.end = end;
        this.attractions = attractions;
        daysCount = start.until(end).getDays();
    }

    public List<Attraction> getVisitableNonPayable(LocalDate date) {
        return this.attractions.stream().filter(attraction -> {
            return attraction instanceof Visitable && !(attraction instanceof Payable);
        }).sorted(Comparator.comparing(attraction -> {
            LocalTime open = ((Visitable) attraction).getOpeningHour(date);
            return open != null ? open : LocalTime.MAX;
        })).collect(Collectors.toList());
    }

    public String getCity() {
        return city;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public int getDaysCount() {
        return daysCount;
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }

    @Override
    public String toString() {
        return "Trip{" + "city='" + city + '\'' + ", start=" + start + ", end=" + end + ", attractions=" + attractions + '}';
    }
}
