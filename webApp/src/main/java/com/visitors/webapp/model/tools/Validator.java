package com.visitors.webapp.model.tools;

import java.util.regex.Pattern;

public class Validator {
    public static String nameValidator(String name, String message) throws Exception {
        if (name != null && Pattern.matches("^[a-zA-Z0-9]{3,15}$", name)) {
            return name;
        }else{
            throw new Exception(message);
        }
    }

    public static String nationalIdValidator(String nationalID, String message) throws Exception {
        if(nationalID != null && Pattern.matches("(\\d{10}|\\d{3}-\\d{6}-\\d)", nationalID)){
            return nationalID;
        }else {
            throw new Exception(message);
        }
    }
    public static String emailValidator(String email, String message) throws Exception {
        if(email != null && Pattern.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", email)){
            return email;
        }else {
            throw new Exception(message);
        }
    }

    public static int ageValidator(int age, String message) throws Exception {
        if( age >= 6 && age <= 90){
            return age;
        }
        else{
            throw new Exception(message);
        }
    }
    public static String checkNullValidator(String value, String message) throws Exception {
        if(value != null){
            return value;
        }
        else{
            throw new Exception(message);
        }
    }
}

