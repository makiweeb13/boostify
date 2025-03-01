package com.example.myapplication;

public class Game {
    public String developer;
    public String genre;
    public String name;
    public String platform;

    public Game() {
        // Default constructor required for calls to DataSnapshot.getValue(Game.class)
    }

    public Game(String developer, String genre, String name, String platform) {
        this.developer = developer;
        this.genre = genre;
        this.name = name;
        this.platform = platform;
    }
}
