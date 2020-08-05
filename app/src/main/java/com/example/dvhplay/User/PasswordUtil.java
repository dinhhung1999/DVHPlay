package com.example.dvhplay.User;

import java.util.regex.Pattern;

public class PasswordUtil {
    public boolean hasSpace(String password){
        if(password.length()==password.trim().length())
            return true;
        else
            return false;
    }
    public boolean hasLength(String password){
        if(password.length()>=6)
            return true;
        else
            return false;
    }
    public boolean hasSymbol(String password){
        return !password.matches("[A-Za-z0-9]*");
    }
    public boolean hasUpperCase(String password){
        return !password.equals(password.toLowerCase());
    }
    public boolean hasLowerCase(String password){
        return !password.equals(password.toUpperCase());
    }
    public boolean hasNumber(String password){
        String regex = "(.)*(\\d)(.)*";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(password).matches();
    }
}
