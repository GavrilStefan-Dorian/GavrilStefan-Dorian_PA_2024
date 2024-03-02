package lab1;

import java.util.Scanner;

public class Lab1 {
    //author: Gavril Stefan-Dorian
    public static void main(String[] args) {
        Lab1 lab1 = new Lab1();
        lab1.compulsory();
        lab1.homework(args);
        lab1.bonus();
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

        System.out.println("Hello world!");
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
        System.out.printf("%1$d-reducible numbers in [%2$d, %3$d]: " + identified_numbs, k, a, b);
        System.out.println("\nApplication runtime: " + (endTime - startTime));

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

    void bonus() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the vertex count: ");

        int n = scanner.nextInt();

        scanner.close();

        boolean[][] adjacencyMatrix = new boolean[n][n];

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

        int count = 1 + (n-1) * (n-2);
        System.out.println();
        System.out.print("Count of all cycles: " + count);
    }

}
