import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class Church extends Attraction implements Visitable {

 private HashMap<LocalDate, TimeInterval> timeTable;

 public Church(String name) {
  super(name);
 }

 @Override
 public HashMap<LocalDate, TimeInterval> getTimeTable() {
  return timeTable;
 }

 @Override
 public void setTimeTable(HashMap<LocalDate, TimeInterval> timeTable) {
      this.timeTable = timeTable;
 }

 @Override
 public LocalTime getOpeningHour(LocalDate date) {
  return Visitable.super.getOpeningHour(date);
 }
}
