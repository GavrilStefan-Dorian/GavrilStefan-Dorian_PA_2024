import java.util.Arrays;

/**
 * Models the allocation of clients to vehicles over a given problem instance
 */
public class Solution {
    private Problem problem;
    private Tour[] tours;

    public Solution(Problem problem) {
        this.problem = problem;
    }

    /**
     * Allocate clients in a greedy manner.
     * Iterate the clients, choose the vehicle with the shortest distance to it
     */
    public void greedyAlgorithm() {
        int tourCount = problem.getVehicles().length;
        int tourIndex = 0;

        /* instantiate all tours and set the respective vehicle
         */
        tours = new Tour[tourCount];
        for(int i = 0; i < tourCount; i++)
            tours[i] = new Tour(problem.getVehicles()[i]);

         /* keeping track of where a vehicle currently is
            -1 indicates it's in depot, otherwise index of client
             */
        int[] vehiclePosition = new int[problem.getVehicles().length];
        Arrays.fill(vehiclePosition, -1);


        int[][] timesDepotsToClients = problem.getTimesDepotsToClients();
        int[][] timesClientsToClients = problem.getTimesClientsToClients();
        int indexClient = -1;
        boolean clientIsVisited[] = new boolean[problem.getClients().length];

        /* iterate through all clients, greedily pick a vehicle to visit it
         */
        for(Client client : problem.getClients()) {
            indexClient++;

            int minTime = Integer.MAX_VALUE;
            int indexMinTime = -1;
            int indexVehicle = -1;
            int indexDepot = -1;

            for(Depot depot : problem.getDepots()) {
                indexDepot++;
                for (Vehicle vehicle : depot.getVehicles()) {
                    indexVehicle++;
                    // if vehicle in depot
                    if (vehiclePosition[indexVehicle] == -1) {
                        if (timesDepotsToClients[indexDepot][indexClient] < minTime) {
                            minTime = timesDepotsToClients[indexDepot][indexClient];
                            indexMinTime = indexVehicle;
                        }
                    }
                    // if vehicle is at a client
                    else {
                        if (timesClientsToClients[vehiclePosition[indexVehicle]][indexClient] < minTime) {
                            minTime = timesClientsToClients[vehiclePosition[indexVehicle]][indexClient];
                            indexMinTime = indexVehicle;
                        }
                    }
                }
            }
            if(indexMinTime != -1) {
                tours[indexMinTime].addClient(client);
                vehiclePosition[indexMinTime] = indexClient;
                clientIsVisited[indexClient] = true;
            }
        }
    }
    public Problem getProblem() {
        return problem;
    }
    public Tour[] getTours() {
        return tours;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "problem=" + problem.getName() +
                ", tours=[" + Arrays.toString(tours) +
                "]}";
    }
}
