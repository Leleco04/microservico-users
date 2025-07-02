package com.example.projeto_spring_boot_user.util;

public class ConversorNumerico {

    public static String formatarId(String id) {
        try {
            Long idFormatado = Long.parseLong(id);
            return String.valueOf(idFormatado);
        } catch(NumberFormatException e) {
            return e.getMessage();
        }
    }
}