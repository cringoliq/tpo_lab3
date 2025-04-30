package com.labwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AuthUtils {
    public static String[] getCredentialsFromFile(String filePath) {
        String[] creds = new String[2]; // Массив для хранения логина и пароля
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // Чтение первой строки
            if (line != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    creds[0] = parts[0]; // Логин
                    creds[1] = parts[1]; // Пароль
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return creds;
    }
}
