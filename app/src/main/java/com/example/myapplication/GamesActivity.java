package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

    private DatabaseReference gamesRef;
    private SearchView gamesSearchView; // Declare the SearchView variable

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        binding = ActivityGameListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gamesSearchView = findViewById(R.id.gamesSearchView); // Initialize the SearchView
        gamesSearchView.clearFocus(); // Clear focus from the SearchView(It will show line of code in the search box if this line is not included)


        // Set the title in the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Search Games");
        }

        // Initialize the game list
        gameList = new ArrayList<>();

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
            public boolean onQueryTextChange(String newText) { //Reacts on inputted text in the search box
                filterGames(newText);
                return true;
            }
        });
    }

    private void fetchGamesFromFirebase() {
        gamesRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gameList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String gameId = dataSnapshot.getKey();
                    Game game = dataSnapshot.getValue(Game.class);
                    if (game != null) {
                        game.id = gameId;
                    }
                    gameList.add(game);
                }
                // Update the gameList and notify the adapter
                gamesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
                Log.e("GameActivity", "Error fetching games", error.toException());
            }
        });
    }

    //Displays the search results in the recycler view based on the query, non-case sensitive
    private void filterGames(String query) {
        List<Game> filteredList = new ArrayList<>();
        for (Game game : originalGameList) {
            if (game.name.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(game);
            }
            if (filteredList.isEmpty()){
                Toast.makeText(this, "No Games Found", Toast.LENGTH_SHORT).show();
            }
            else{
                gamesAdapter.setFilteredList(filteredList);
            }
        }
        gameList.clear();
        gameList.addAll(filteredList);
        gamesAdapter.notifyDataSetChanged();
    }
}
