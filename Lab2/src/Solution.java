import java.util.Arrays;

public class Solution {
    private Problem problem;
    private Tour[] tours;

    public Solution(Problem problem) {
        this.problem = problem;

        int tourCount = 0;
        for(Depot depot : problem.getDepots())
            tourCount += depot.getVehicles().length;

        tours = new Tour[tourCount];

        int tourIndex = 0;
        int depotIndex = 0;
        int currentIndex = 0;

        for(Depot depot : problem.getDepots()) {
            for (Vehicle vehicle : depot.getVehicles()) {
              tours[tourIndex] = new Tour(vehicle);

              int minTime = Integer.MAX_VALUE;
              int minTimeIndex = 0;
              int[][] timesDepotsToClients = problem.getTimesDepotsToClients();
              int[][] timesClientsToClients = problem.getTimesClientsToClients();

              for(int i = 0; i < timesDepotsToClients.length; i++)
                  if(timesDepotsToClients[depotIndex][i] < minTime) {
                      minTime = timesDepotsToClients[depotIndex][i];
                      minTimeIndex = i;
                  }

              tours[tourIndex].addClient(problem.getClients()[minTimeIndex]);

              boolean tourComplete = false;
              boolean[] isVisited = new boolean[problem.getClients().length];

              while(!tourComplete) {
                  minTime = Integer.MAX_VALUE;
                  currentIndex = minTimeIndex;
                  minTimeIndex = -1;

                  for (int i = 0; i < timesClientsToClients.length; i++)
                      if (!isVisited[i] && timesClientsToClients[currentIndex][i] < minTime) {
                          minTime = timesClientsToClients[currentIndex][i];
                          minTimeIndex = i;
                      }

                  if (minTimeIndex == -1)
                      tourComplete = true;
                  else {
                      tours[tourIndex].addClient(problem.getClients()[minTimeIndex]);
                      isVisited[minTimeIndex] = true;
                  }
              }
                  tourIndex++;
            }

            depotIndex++;
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
                ", tours=" + Arrays.toString(tours) +
                '}';
    }
}
