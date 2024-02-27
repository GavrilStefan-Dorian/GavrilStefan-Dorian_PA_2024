package Lab01.src;

import static java.lang.Integer.parseInt;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
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

        System.out.print("Hello world!\n");
        System.out.printf("Willy-nilly, this semester I will learn " + languages[result]);
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
    }
}