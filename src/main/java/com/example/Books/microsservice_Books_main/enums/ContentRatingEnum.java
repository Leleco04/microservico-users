package com.example.Books.microsservice_Books_main.enums;

public enum ContentRatingEnum {
    LIVRE("Livre para todos os públicos"),
    DEZ("Não recomendado para menores de 10 anos"),
    DOZE("Não recomendado para menores de 12 anos"),
    QUATORZE("Não recomendado para menores de 14 anos"),
    DEZESSEIS("Não recomendado para menores de 16 anos"),
    DEZOITO("Conteúdo adulto, não recomendado para menores de 18 anos");

    private final String description;

    ContentRatingEnum(String description) {this.description = description;}

    public String getDescription() {return description;}

    public static ContentRatingEnum fromString(String text){
        for(ContentRatingEnum b : ContentRatingEnum.values()){
            if(b.name().equalsIgnoreCase(text)){
                return b;
            }
        }throw new IllegalArgumentException("Nenhuma classificação encontrada para a string fornecida: " + text);
    }

}