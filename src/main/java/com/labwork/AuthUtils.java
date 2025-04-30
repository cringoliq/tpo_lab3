package com.labwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AuthUtils {
    public static String getPasswordFromFile(String filePath) {
        String password = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            password = reader.readLine(); // Чтение первой строки
        } catch (IOException e) {
            e.printStackTrace();
        }
        return password;
    }
}
