package com.Afrochow.food_app.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class ReUsableFunctions {

    public String encryptPassword(String userPassword) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(userPassword.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();

            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUpdatedValue(String newValue, String currentValue) {
        return (newValue != null && !newValue.trim().isEmpty()) ? newValue.trim() : currentValue;
    }


    public String randomAlphanumeric(int length){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public String randomDigit(int length){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomNumber = random.nextInt(10);
            sb.append(randomNumber);
        }
        return sb.toString();
    }

    public String generateId (String name) {
             return name.toUpperCase().substring(0,3) + this.randomDigit(5);
      }

    // Return new boolean value if not null, otherwise current value
    public boolean getUpdatedValueBoolean(Boolean newValue, boolean currentValue) {
        return newValue != null ? newValue : currentValue;
    }
}