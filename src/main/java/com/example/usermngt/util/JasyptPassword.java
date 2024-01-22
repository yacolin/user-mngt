package com.example.usermngt.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptPassword {
    private static final String SECRET_KEY = "colin";
    private static StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

    static {
        encryptor.setPassword(SECRET_KEY);
    }

    public static String encryptPassword(String password) {
        return encryptor.encrypt(password);
    }


    public static boolean validatePassword(String encryptedPassword, String password) {
        String decryptedPassword = encryptor.decrypt(encryptedPassword);
        return decryptedPassword.equals(password);
    }
}
