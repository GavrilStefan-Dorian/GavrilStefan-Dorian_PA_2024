import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public interface Visitable {
    Map<LocalDate, TimeInterval> timeTable = new HashMap<LocalDate, TimeInterval>();

    Map<LocalDate, TimeInterval> getTimeTable();

    void setTimeTable(Map<LocalDate, TimeInterval> timeTable);
    default LocalTime getOpeningHour(LocalDate date) {
        return timeTable.get(date).start;
    }
}
