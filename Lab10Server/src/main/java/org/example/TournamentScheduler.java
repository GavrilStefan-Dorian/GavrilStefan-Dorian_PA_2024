package org.example;

import gurobi.*;

public class TournamentScheduler {

   public static void main(String[] args) {
       int n = 4; // Number of players
       int d = 3; // Number of days
       int p = 2; // Maximum games per day

       try {
           // Create a new environment
           GRBEnv env = new GRBEnv("tournament.log");
           // Create a new model
           GRBModel model = new GRBModel(env);

           // Add variables
           GRBVar[][][] x = new GRBVar[n][n][d];
           for (int i = 0; i < n; i++) {
               for (int j = 0; j < n; j++) {
                   for (int k = 0; k < d; k++) {
                       x[i][j][k] = model.addVar(0, 1, 0, GRB.BINARY, "x_" + i + "_" + j + "_" + k);
                   }
               }
           }

           // Symmetry constraint: x[i][j][k] = x[j][i][k]
           for (int i = 0; i < n; i++) {
               for (int j = i + 1; j < n; j++) {
                   for (int k = 0; k < d; k++) {
                       GRBLinExpr symmetryExpr = new GRBLinExpr();
                       symmetryExpr.addTerm(1, x[i][j][k]);
                       symmetryExpr.addTerm(-1, x[j][i][k]);
                       model.addConstr(symmetryExpr, GRB.EQUAL, 0, "symmetry_" + i + "_" + j + "_" + k);
                   }
               }
           }

           // Unique match constraint: sum_k x[i][j][k] = 1 for each i < j
           for (int i = 0; i < n; i++) {
               for (int j = i + 1; j < n; j++) {
                   GRBLinExpr uniqueMatchExpr = new GRBLinExpr();
                   for (int k = 0; k < d; k++) {
                       uniqueMatchExpr.addTerm(1, x[i][j][k]);
                   }
                   model.addConstr(uniqueMatchExpr, GRB.EQUAL, 1, "unique_" + i + "_" + j);
               }
           }

           // Daily game limit constraint: sum_j x[i][j][k] <= p for each i and k
           for (int i = 0; i < n; i++) {
               for (int k = 0; k < d; k++) {
                   GRBLinExpr dailyLimitExpr = new GRBLinExpr();
                   for (int j = 0; j < n; j++) {
                       if (i != j) {
                           dailyLimitExpr.addTerm(1, x[i][j][k]);
                       }
                   }
                   model.addConstr(dailyLimitExpr, GRB.LESS_EQUAL, p, "daily_limit_" + i + "_" + k);
               }
           }

           // Total game limit constraint: sum_{i,j,k} x[i][j][k] = n*(n-1)/2
           int totalGames = n * (n - 1) / 2;
           GRBLinExpr totalGamesExpr = new GRBLinExpr();
           for (int i = 0; i < n; i++) {
               for (int j = i + 1; j < n; j++) {
                   for (int k = 0; k < d; k++) {
                       totalGamesExpr.addTerm(1, x[i][j][k]);
                   }
               }
           }
           model.addConstr(totalGamesExpr, GRB.EQUAL, totalGames, "total_games");

           // Optimize the model
           model.optimize();

           // Print the results
           for (int k = 0; k < d; k++) {
               System.out.println("Day " + (k + 1) + ":");
               for (int i = 0; i < n; i++) {
                   for (int j = 0; j < n; j++) {
                       if (i != j && x[i][j][k].get(GRB.DoubleAttr.X) == 1.0) {
                           System.out.println("Player " + i + " vs Player " + j);
                       }
                   }
               }
           }

           // Dispose of the model and environment
           model.dispose();
           env.dispose();
       } catch (GRBException e) {
           System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
       }
   }
}
