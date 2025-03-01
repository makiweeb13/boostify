package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.databinding.ActivityGameListBinding;

import java.util.ArrayList;
import java.util.List;

public class GamesActivity extends AppCompatActivity {
    private ActivityGameListBinding binding;
    private GamesAdapter gamesAdapter;
    private List<String> gameList;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        binding = ActivityGameListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the title in the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Games");
        }

        // Initialize the game list
        gameList = new ArrayList<>();
        gameList.add("Elden Ring");
        gameList.add("Valorant");
        gameList.add("Genshin Impact");
        gameList.add("Overwatch2");
        gameList.add("League of Legends");
        gameList.add("Fortnite");

        // Set up RecyclerView
        gamesAdapter = new GamesAdapter(gameList);
        binding.gamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.gamesRecyclerView.setAdapter(gamesAdapter);
    }
}
