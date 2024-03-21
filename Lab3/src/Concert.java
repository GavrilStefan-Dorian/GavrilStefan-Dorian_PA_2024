import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Concert
        extends Attraction
        implements Visitable, Payable {
    private Map<LocalDate, TimeInterval> timetable;
    private double ticketPrice = 20;

    public Concert(String name) {
        super(name);
    }

    @Override
    public Map<LocalDate, TimeInterval> getTimeTable() {
        return timetable;
    }

    @Override
    public void setTimeTable(Map<LocalDate, TimeInterval> timeTable) {
        this.timetable = timeTable;
    }

    @Override
    public LocalTime getOpeningHour(LocalDate date) {
        return Visitable.super.getOpeningHour(date);
    }
    @Override
    public double getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public void setTicketPrice(int i) {
        ticketPrice = i;
    }
}