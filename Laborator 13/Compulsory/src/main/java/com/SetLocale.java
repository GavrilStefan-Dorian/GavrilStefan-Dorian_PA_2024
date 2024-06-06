package com;

import java.util.Locale;
import java.util.ResourceBundle;

public class SetLocale {
    public void execute(String localeTag) {
        Locale locale = Locale.forLanguageTag(localeTag);
        Locale.setDefault(locale);
        ResourceBundle messages = ResourceBundle.getBundle("res.Messages");
        String currentLocale = Locale.getDefault().toString();
        String message = messages.getString("locale.set");
        System.out.println(message.replace("{0}", currentLocale));
    }
}
