package com.kursovaya;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputCheck {
    
    public static boolean validateEmail(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validateName(String name) {
        String regexPattern = "^[A-Za-zА-Яа-я]* [A-Za-zА-Яа-я]+$";

        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    public static boolean validateMakeID(String name) {
        String regexPattern = "^[A-Za-zА-Яа-я0-9]* [0-9]+$";

        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean validateMakeModel(String name) {
        String regexPattern = "^[A-Za-zА-Яа-я0-9]* [A-Za-zА-Яа-я0-9]+$";

        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
