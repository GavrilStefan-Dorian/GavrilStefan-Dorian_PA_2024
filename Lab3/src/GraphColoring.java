import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

/**
 * Encapsulates the graph, travel plan and offers methods of allocating days/colors
 */
public class GraphColoring {
    private int[][] adjacencyMatrix;
    private int n;
    private TravelPlan travelPlan;
    /**
     * -1 represents uncolored
     */
    private int[] allocatedColor; //Map<Attraction, Integer>
    private Map<Attraction, Set<Integer>> availableColors;

    public GraphColoring(Trip trip) {

        n = trip.getAttractions().size();
        adjacencyMatrix = new int[n][n];
        availableColors = new HashMap<Attraction, Set<Integer>>();

        /*
         Fill graph
         */
        int i = 0, j = 0;
        for (Attraction attraction : trip.getAttractions()) {
            j = 0;
            for (Attraction otherAttraction : trip.getAttractions()) {
                if (((attraction instanceof Statue) && (otherAttraction instanceof Statue) && attraction.compareTo(otherAttraction) != 0)
                        || ((attraction instanceof Church) && (otherAttraction instanceof Church) && attraction.compareTo(otherAttraction) != 0)
                        || ((attraction instanceof Concert) && (otherAttraction instanceof Concert) && attraction.compareTo(otherAttraction) != 0))
                    adjacencyMatrix[i][j] = adjacencyMatrix[j][i] = 1;

                ++j;
            }

            if (attraction instanceof Visitable) {
                Set<Integer> colorSet = new HashSet<>();

                for (LocalDate date : ((Visitable) attraction).getTimeTable().keySet()) {
                    colorSet.add(trip.getStart().until(date).getDays());
                }
                availableColors.put(attraction, colorSet);
            }
            /*
            Non-Visitable can be seen/attended/visited any day of the trip
             */
            else {
                Set<Integer> colorSet = new HashSet<>();
                for (int k = 0; k < trip.getDaysCount(); k++) {
                    colorSet.add(k);
                }
                availableColors.put(attraction, colorSet);

            }

            ++i;
        }

        travelPlan = new TravelPlan(trip);

        /*
         * Fill days map and their attractions
         */
        for (LocalDate date = trip.getStart(); !date.isAfter(trip.getEnd()); date = date.plusDays(1)) {
            travelPlan.addDate(date);
        }
    }

    public void colorStaticDescendingDegree() {
        int maxDegree = -1;
        int[] degrees = new int[n];

        /*
        Find max degree, to limit the number of necessary colors
         */
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                degrees[i] += adjacencyMatrix[i][j];
            }
            if (maxDegree < degrees[i])
                maxDegree = degrees[i];
        }

        List<Integer> nodes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            nodes.add(i);
        }
        /*
        Sorted descending order of degree
         */
        nodes.sort((v1, v2) -> Integer.compare(nodes.get(v2), nodes.get(v1)));

//        int[] arrayMaximumColors = new int[maxDegree];
        allocatedColor = new int[n];
        Arrays.fill(allocatedColor, -1);

        for (int node : nodes) {
            allocatedColor[node] = findAvailableColor(node);
        }

        updateTravelPlan();
    }

    private int findAvailableColor(int node) {

        for (int color : availableColors.get(travelPlan.getTrip().getAttractions().get(node))) {
            if (isValidColor(node, color, allocatedColor)) {
                return color;
            }
        }
        return -1;
    }

    private void updateTravelPlan() {
        Trip trip = travelPlan.getTrip();
        travelPlan = new TravelPlan(trip);

        /*
         * Fill days map and their attractions
         */
        for (LocalDate date = trip.getStart(); !date.isAfter(trip.getEnd()); date = date.plusDays(1)) {
            travelPlan.addDate(date);
        }

        for (int i = 0; i < n; i++) {
            Attraction attraction = travelPlan.getTrip().getAttractions().get(i);
            LocalDate date = null;

            if (allocatedColor[i] != -1)
                travelPlan.setAttractionDate(travelPlan.getTrip().getStart().plusDays(allocatedColor[i]), attraction);
        }
    }

    private boolean isValidColor(int node, int color, int[] allocatedColor) {
        for (int i = 0; i < n; i++) {
            if (adjacencyMatrix[i][node] == 1
                    && allocatedColor[i] == color) {
                return false;
            }
        }
        return true;
    }

    public void colorBySaturation() {
        Arrays.fill(allocatedColor, -1);

        int[] saturation = new int[n];
        Arrays.fill(saturation, 0);
        int maxDegree = -1;
        int currentNode = -1;
        int[] degrees = new int[n];
        /*
        Find max degree, to limit the number of necessary colors
         */
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                degrees[i] += adjacencyMatrix[i][j];
            }
            if (maxDegree < degrees[i]) {
                maxDegree = degrees[i];
                currentNode = i;
            }
        }

        boolean foundUncolored = true;

        while(foundUncolored) {
            foundUncolored = false;
            int maxSaturation = 0;
            //Assign to max node
            allocatedColor[currentNode] = availableColors.get(travelPlan.getTrip().getAttractions().get(currentNode)).iterator().next();
            degrees[currentNode] = 0;
            //Reduce options
            for (int neighbor : getNeighbors(currentNode)) {
                if(allocatedColor[neighbor] == -1) {
                    degrees[neighbor]--;
                    saturation[neighbor]++;
                    availableColors.get(travelPlan.getTrip().getAttractions().get(neighbor)).remove(allocatedColor[currentNode]);
                }
                }

            for (int i = 0; i < n; i++) {
                if (allocatedColor[i] == -1) {
//                    System.out.println(i + " " + Arrays.toString(saturation));
                    foundUncolored = true;
                    if (saturation[i] > maxSaturation || (saturation[i] == maxSaturation && degrees[i] > degrees[currentNode])) {
                        currentNode = i;
                        maxSaturation = saturation[i];
                    }
                }
            }
        }
        updateTravelPlan();

    }
    private List<Integer> getNeighbors(int node) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (adjacencyMatrix[node][i] == 1) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }
    public void printMatrix() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.print(adjacencyMatrix[i][j] + " ");
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return "GraphColoring{" +
                "\n travelPlan=" + travelPlan +
                ",\n allocatedColor=" + Arrays.toString(allocatedColor) +
                ",\n availableColors=" + availableColors +
                '}';
    }
}