//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String[] languages = {"C", "C++",
                "C#", "Python", "Go", "Rust",
                "JavaScript", "PHP", "Swift", "Java"};
        int n = (int)(Math.random() * 1_000_000);

        int result = n * 3;
        n += 0b10101;
        n += 0xFF;
        n *= 6;

        int  i = 0;

        do {
            while (n > 0) {
                i += n % 10;
                n /= 10;
            }
        }
        while(i > 9);

        System.out.printf("Willy-nilly, this semester I will learn " + languages[result]);
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.print("Hello world!");
    }
}