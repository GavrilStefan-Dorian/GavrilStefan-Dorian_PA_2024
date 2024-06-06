package com;

import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class Info {
    public void execute(String localeTag) {
        Locale locale = Locale.forLanguageTag(localeTag);
        ResourceBundle messages = ResourceBundle.getBundle("res.Messages");
        String message = messages.getString("info");
        System.out.println(message.replace("{0}", localeTag));
        Locale[] locales = Locale.getAvailableLocales();
        String country = "";
        for (Locale loc : locales) {
            if (loc.getLanguage().equals(locale.getLanguage())) {
                country = loc.getDisplayCountry();
                break;
            }
        }

        System.out.println("Country: " + country);
        System.out.println("Language: " + locale.getDisplayLanguage(locale));

        try {
            Currency currency = Currency.getInstance(locale);
            System.out.println("Currency: " + currency.getCurrencyCode() +
                    " (" + currency.getDisplayName() + ")");
        } catch (IllegalArgumentException e) {
            System.out.println("Currency information not available for this locale.");
        }

        System.out.println("Week Days: " + String.join(", ", DateFormatSymbols.getInstance(locale).getWeekdays()));
        System.out.println("Months: " + String.join(", ", DateFormatSymbols.getInstance(locale).getMonths()));
        DateTimeFormatter formatter=DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(locale);
        System.out.println("Today: " + LocalDateTime.now().format(formatter));
        System.out.println("Locale tag: " + localeTag);
        System.out.println("Locale display country: " + locale.getDisplayCountry());

    }
}
