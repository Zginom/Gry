package com.example.daniel.gry;


public class Rekord {

    Long id;
    String description;
    int number;

    public Rekord(Long id, String description, int number) {
        this.id = id;
        this.description = description;
        this.number = number;
    }

    public Long getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public int getNumber() {
        return number;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setNumber(int number) {
        this.number = number;
    }
}
