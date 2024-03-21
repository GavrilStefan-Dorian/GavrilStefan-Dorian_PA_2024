import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Church extends Attraction implements Visitable {

 private Map<LocalDate, TimeInterval> timeTable;

 public Church(String name) {
  super(name);
 }

 @Override
 public Map<LocalDate, TimeInterval> getTimeTable() {
  return timeTable;
 }

 @Override
 public void setTimeTable(Map<LocalDate, TimeInterval> timeTable) {
      this.timeTable = timeTable;
 }

 @Override
 public LocalTime getOpeningHour(LocalDate date) {
  return Visitable.super.getOpeningHour(date);
 }
}
