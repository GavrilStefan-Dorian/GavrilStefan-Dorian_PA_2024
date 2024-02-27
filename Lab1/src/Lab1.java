import static java.lang.Integer.parseInt;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Lab1 {
    public static void main(String[] args) {
        Lab1 lab1 = new Lab1();
        lab1.compulsory();
        lab1.homework(args);
    }
    void compulsory(){
        String[] languages = {"C", "C++",
                "C#", "Python", "Go", "Rust",
                "JavaScript", "PHP", "Swift", "Java"};
        int n = (int) (Math.random() * 1_000_000);

        int result = n * 3;
        result += parseInt(String.valueOf(0b10101));
        result += parseInt(String.valueOf(0xFF));
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
        int i = 0;
        int result = a;
        int iterator = a;

        StringBuilder identified_numbs = new StringBuilder();

        for(; iterator <= b; iterator++) {
            result = iterator;
            do {
                while (result > 0) {
                    i += (result % 10) * (result % 10);
                    result /= 10;
                }
                result = i;
                i = 0;
            }
            while (result > 9);

            if(result == k) {
                identified_numbs.append(iterator + " ");
            }

        }

        long endTime = System.nanoTime();
        System.out.println(identified_numbs);
        System.out.println("Application runtime: " + (endTime - startTime));

    }

    void bonus() {
        //
    }
}

