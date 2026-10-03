package org.example.week5_webservice.dto;

public record BookResponse() {
    Long id,
    String title,
    String author,
    int price;
}
