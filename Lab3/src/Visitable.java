import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public interface Visitable {
    HashMap<LocalDate, TimeInterval> timeTable = null;

    HashMap<LocalDate, TimeInterval> getTimeTable();

    void setTimeTable(HashMap<LocalDate, TimeInterval> timeTable);
    default LocalTime getOpeningHour(LocalDate date) {
        return timeTable.get(date).first;
    }
}
