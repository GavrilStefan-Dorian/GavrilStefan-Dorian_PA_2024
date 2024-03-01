import java.util.Arrays;
import java.util.Vector;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Lab1 {
    private int n;
    private int count = 0;

    private boolean[][] adjacencyMatrix;

    public static void main(String[] args) {
        Lab1 lab1 = new Lab1();
        lab1.compulsory();
        lab1.homework(args);
        //lab1.bonus(args);
    }

    void compulsory() {
        String[] languages = {"C", "C++",
                "C#", "Python", "Go", "Rust",
                "JavaScript", "PHP", "Swift", "Java"};
        int n = (int) (Math.random() * 1_000_000);

        int result = n * 3;
        result += 0b10101;
        result += 0xFF;
        result *= 6;

        int i = 0;

        do {
            while (result > 0) {
                i += result % 10;
                result /= 10;
            }
            result = i;
            i = 0;
        }
        while (result > 9);

        System.out.println("Willy-nilly, this semester I will learn " + languages[result]);
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
    }

    void homework(String[] args) {
        long startTime = System.nanoTime();

        if (args.length < 3) {
            System.out.println(
                    "Usage: lower-bound, upper-bound, k to reduce to");
            System.exit(-1);
        }
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);

        if (a > b) {
            System.out.println(
                    "Incorrect interval!");
            System.exit(-1);

        }
        StringBuilder identified_numbs = getIdentified_numbs(a, b, k);

        long endTime = System.nanoTime();
        System.out.println(identified_numbs);
        System.out.println("Application runtime: " + (endTime - startTime));

    }

    private static StringBuilder getIdentified_numbs(int a, int b, int k) {
        int i = 0;
        int result;
        int iterator = a;
        int[] digitMet = new int[10];


        StringBuilder identified_numbs = new StringBuilder();

        for (; iterator <= b; iterator++) {
            result = iterator;
            do {
                if(result < 10) {
                    if (digitMet[result] == 1) {
                        break;
                    }
                }

                while (result > 0) {
                    i += (result % 10) * (result % 10);
                    result /= 10;
                }
                result = i;
                i = 0;

                if(result < 10) {
                    digitMet[result] = 1;
                }
            }
            while (result != 1);

            if (result == k) {
                identified_numbs.append(iterator).append(" ");
            }

        }
        return identified_numbs;
    }

    void bonus(String[] args) {
        //Create the adjacency matrix of a wheel graph Wn.
        //Display on the screen a textual representation of the matrix. 36 - 18 = 18 + 3= 21
        //Write an algorithm that finds all the cycles of a wheel graph. Verify that their number is n^2 - 3n + 3.

        if (args.length < 1) {
            System.out.println(
                    "Usage: n == vertex count");
            System.exit(-1);
        }
        n = Integer.parseInt(args[0]);

        adjacencyMatrix = new boolean[n][n];

        for (int i = 0; i < n - 1; i++) {
            adjacencyMatrix[i][n - 1] = true;
            adjacencyMatrix[i][(i + 1) % n] = true;
            adjacencyMatrix[i][(i - 2 + n) % (n - 1)] = true;
            adjacencyMatrix[n - 1][i] = true;
            adjacencyMatrix[(i + 1) % n][i] = true;
            adjacencyMatrix[(i - 2 + n) % (n - 1)][i] = true;

        }

        System.out.printf("\nMatrix of a W%1$d graph\n", n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.print((adjacencyMatrix[i][j] ? 1 : 0) + " ");
            System.out.println();
        }

        System.out.println();
        int[] coloring = new int[n];
        int[] parents = new int[n];
        cyclesCount();
        System.out.print("Count of all cycles: " + count);
    }

    private void cyclesCount() {
        boolean[] visited = new boolean[n];

        for (int v = 0; v < n; v++) {
            Arrays.fill(visited, false);
            Vector<Integer> path = new Vector<>();
            DFS(v, v, visited, path);
        }
    }

    private void DFS(int root, int current, boolean[] visited, Vector<Integer> path) {
        visited[current] = true;
        path.add(current);

        for (int v = 0; v < n; v++) {
            if (adjacencyMatrix[current][v]) {
                if (!visited[v]) {
                    DFS(root, v, visited, path);
                } else if (v == root && path.size() > 2) {
                    count++;
                }
            }
        }
        path.remove(path.size() - 1);
        visited[current] = false;
    }
}

