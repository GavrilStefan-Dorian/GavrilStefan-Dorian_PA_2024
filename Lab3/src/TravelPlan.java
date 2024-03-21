import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class TravelPlan {
    private Map<LocalDate, List<Attraction>> attractionPlan;
    private Trip trip;

    public TravelPlan (Trip trip){
        this.trip = trip;
        attractionPlan = new HashMap<>();
    };

    public Map<LocalDate, List<Attraction>> getAttractionPlan() {
        return attractionPlan;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setAttractionPlan(Map<LocalDate, List<Attraction>> attractionPlan) {
        this.attractionPlan = attractionPlan;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public void setAttractionDate(LocalDate date, Attraction attraction) {
        this.attractionPlan.get(date).add(attraction);
    }

    public void addDate(LocalDate date) {
        this.attractionPlan.put(date, new ArrayList<>());
    }

    @Override
    public String toString() {
        return "TravelPlan{" +
                "attractionPlan=" + attractionPlan +
                ", trip=" + trip +
                '}';
    }
}
