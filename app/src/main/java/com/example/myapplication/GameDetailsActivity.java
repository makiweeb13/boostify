package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityGameDetailsBinding;

public class GameDetailsActivity extends AppCompatActivity {
    private ActivityGameDetailsBinding binding;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Enable the back button in the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Retrieve the game object from the intent
        game = getIntent().getParcelableExtra("game");

        // Set the title in the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(game.name);
        }

        // Display the game details
        TextView nameTextView = binding.gameNameTextView;
        TextView developerTextView = binding.gameDeveloperTextView;
        TextView genreTextView = binding.gameGenreTextView;
        TextView platformTextView = binding.gamePlatformTextView;

        nameTextView.setText("Name: " + game.name);
        developerTextView.setText("Developer: " + game.developer);
        genreTextView.setText("Genre: " + game.genre);
        platformTextView.setText("Platform: " + game.platform);

        Button homeButton = binding.homeButton;
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to DashboardActivity
                Intent intent = new Intent(GameDetailsActivity.this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        Button boostButton = binding.boostButton;
        boostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to BoostRequestActivity
                Intent intent = new Intent(GameDetailsActivity.this, BoostRequestActivity.class);
                startActivity(intent);
            }
        });

        Button searchButton = binding.searchButton;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to GamesActivity
                Intent intent = new Intent(GameDetailsActivity.this, GamesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle the back button press
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
