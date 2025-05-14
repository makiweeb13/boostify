package com.example.gamelist;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {
    public String id;
    public String developer;
    public String genre;
    public String name;
    public String platform;

    public Game() {
        // Default constructor required for calls to DataSnapshot.getValue(Game.class)
    }

    public Game(String id, String developer, String genre, String name, String platform) {
        this.id = id;
        this.developer = developer;
        this.genre = genre;
        this.name = name;
        this.platform = platform;
    }

    protected Game(Parcel in) {
        id = in.readString();
        developer = in.readString();
        genre = in.readString();
        name = in.readString();
        platform = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(developer);
        dest.writeString(genre);
        dest.writeString(name);
        dest.writeString(platform);
    }
}