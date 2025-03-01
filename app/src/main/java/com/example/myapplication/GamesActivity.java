package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.databinding.ActivityGameListBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GamesActivity extends AppCompatActivity {
    private ActivityGameListBinding binding;
    private GamesAdapter gamesAdapter;
    private List<Game> gameList;
    private List<Game> originalGameList;
    private DatabaseReference gamesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        binding = ActivityGameListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the title in the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Search Games");
        }

        // Initialize the game list
        gameList = new ArrayList<>();
        originalGameList = new ArrayList<>();

        // Get a reference to the "games" node in the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        gamesRef = database.getReference("Game");

        // Set up RecyclerView
        gamesAdapter = new GamesAdapter(gameList, this);
        binding.gamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.gamesRecyclerView.setAdapter(gamesAdapter);

        // Fetch games from Firebase
        fetchGamesFromFirebase();

        // Set up SearchView
        binding.gamesSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void fetchGamesFromFirebase() {
        gamesRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                originalGameList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Game game = dataSnapshot.getValue(Game.class);
                    originalGameList.add(game);
                }
                // Update the gameList and notify the adapter
                gameList.clear();
                gameList.addAll(originalGameList);
                gamesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
                Log.e("GameActivity", "Error fetching games", error.toException());
            }
        });
    }
}
