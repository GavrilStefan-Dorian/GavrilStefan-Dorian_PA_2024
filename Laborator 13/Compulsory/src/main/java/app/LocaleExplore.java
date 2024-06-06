package app;

import com.DisplayLocales;
import com.Info;
import com.SetLocale;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LocaleExplore {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DisplayLocales displayLocales = new DisplayLocales();
        SetLocale setLocale = new SetLocale();
        Info info = new Info();
        ResourceBundle messages = ResourceBundle.getBundle("res.Messages", Locale.getDefault());

        while (true) {
            System.out.print(messages.getString("prompt"));
            String command = scanner.nextLine();

            if (command.equals("locales")) {
                displayLocales.execute();
            } else if (command.equals("setlocale")) {
                System.out.print("Enter the locale tag (e.g., en-US, ro-RO): ");
                String localeTag = scanner.nextLine();
                setLocale.execute(localeTag);
                messages = ResourceBundle.getBundle("res.Messages", Locale.getDefault());
            } else if (command.equals("info")) {
                System.out.print("Enter the locale tag (e.g., en-US, ro-RO): ");
                String localeTag = scanner.nextLine();
                info.execute(localeTag);
            } else if (command.equals("exit")) {
                break;
            } else {
                System.out.println(messages.getString("invalid"));
            }

            System.out.println();
        }
    }
}
