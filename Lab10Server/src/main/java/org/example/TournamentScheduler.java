package org.example;

import com.gurobi.gurobi.*;
import org.graph4j.*;
import org.graph4j.support.Tournament;

import java.util.Random;

public class TournamentScheduler {
    Digraph g;
    GRBVar[][][] x;
    int n,d,p;

    public TournamentScheduler(int n, int d, int p) {
        this.n = n;
        this.d = d;
        this.p = p;
        g = GraphBuilder.empty().buildDigraph();
    }

    public String getScheduleSolution() {
//        int n = 10; // Number of players
//        int d = 3; // Number of days
//        int p = 3; // Maximum games per day

        try {

            // TODO: Create constraint for d days!!!
            GRBEnv env = new GRBEnv("tournament.log");
            GRBModel model = new GRBModel(env);
            model.set(GRB.StringAttr.ModelName, "ILP Tournament");

            Random random = new Random();

            x = new GRBVar[n][n][d];
            for (int i = 0; i < n; i++) {
                g.addVertex(i );
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < d; k++) {
                        x[i][j][k] = model.addVar(0, 1, 0, GRB.BINARY, "x_" + i + "_" + j + "_" + k);
                    }
                }
            }

            // Symmetry constraint: X_i_j_k = X_j_i_k
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

            // Play once constraint: sum_k X_i_j_k = 1 for each i and j
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    GRBLinExpr uniqueMatchExpr = new GRBLinExpr();
                    // Form sum over k
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
                    // Analog for j
                    for (int j = 0; j < n; j++) {
                        if (i != j) {
                            dailyLimitExpr.addTerm(1, x[i][j][k]);
                        }
                    }
                    model.addConstr(dailyLimitExpr, GRB.LESS_EQUAL, p, "daily_limit_" + i + "_" + k);
                }
            }

//            // Ignore primary diagonal ( equal indexes )
            for (int i = 0; i < n; i++) {
                for(int k = 0; k < d; k++) {
                    GRBLinExpr ignorePrimaryExpr = new GRBLinExpr();
                    ignorePrimaryExpr.addTerm(1, x[i][i][k]);
                    model.addConstr(ignorePrimaryExpr, GRB.EQUAL, 0, "ignore_primary_" + i + "_" + k);
                }
            }


            // Solves
            model.optimize();

            // Do i need objective ?

            StringBuilder builder = new StringBuilder();

            for (int k = 0; k < d; k++) {
                builder.append("Day ").append(k + 1).append(":\n");
                for (int i = 0; i < n; i++) {
                    for (int j = i + 1; j < n; j++) {
                        if (x[i][j][k].get(GRB.DoubleAttr.X) == 1.0) {
                            builder.append("    Player ").append(i).append(" vs Player ").append(j).append('\n');
                            if(random.nextBoolean())
                                g.addEdge(i, j); // From i -> j
                            else
                                g.addEdge(j, i);
                        }
                    }
                }
            }

            model.dispose();
            env.dispose();

            return builder.toString();
        } catch (GRBException e) {
            System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
        }
        return "";
    }

    public String findSequenceForTournament() throws GRBException {
        var tournament = new Tournament(g);
        System.out.println(g);
        return tournament.getHamiltonianPath().toString();
    }

}
