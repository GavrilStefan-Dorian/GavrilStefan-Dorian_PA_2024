import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class Concert
        extends Attraction
        implements Visitable, Payable {
    private HashMap<LocalDate, TimeInterval> timetable;
    private double ticketPrice = 20;

    public Concert(String name) {
        super(name);
    }

    @Override
    public HashMap<LocalDate, TimeInterval> getTimeTable() {
        return timetable;
    }

    @Override
    public void setTimeTable(HashMap<LocalDate, TimeInterval> timeTable) {
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