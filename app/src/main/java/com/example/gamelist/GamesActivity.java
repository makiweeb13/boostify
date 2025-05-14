package com.example.gamelist;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dashboard.R;
import com.example.dashboard.databinding.ActivityGameListBinding;
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

        // Initialize the game list
        gameList = new ArrayList<>();

        // Add TEMPORARY hardcoded Game objects to the ArrayList
        gameList.add(new Game("1", "The Witcher 3", "CD Projekt Red", "RPG", "PC, PlayStation, Xbox, Switch"));
        gameList.add(new Game("2", "Cyberpunk 2077", "CD Projekt Red", "RPG", "PC, PlayStation, Xbox"));
        gameList.add(new Game("3", "Grand Theft Auto V", "Rockstar Games", "Action-Adventure", "PC, PlayStation, Xbox"));
        gameList.add(new Game("4", "Red Dead Redemption 2", "Rockstar Games", "Action-Adventure", "PC, PlayStation, Xbox"));
        gameList.add(new Game("5", "Minecraft", "Mojang Studios", "Sandbox", "Multiple Platforms"));
        gameList.add(new Game("6", "Valiant Hearts: The Great War", "Ubisoft Montpellier", "Adventure Puzzle", "PC, PlayStation, Xbox, Switch, Mobile"));
        gameList.add(new Game("7", "Stardew Valley", "ConcernedApe", "Simulation RPG", "PC, PlayStation, Xbox, Switch, Mobile"));
        gameList.add(new Game("8", "Hades", "Supergiant Games", "Roguelike Action RPG", "PC, PlayStation, Xbox, Switch"));
        gameList.add(new Game("9", "Among Us", "Innersloth", "Social Deduction", "PC, Mobile, Switch, Xbox, PlayStation"));
        gameList.add(new Game("10", "Apex Legends", "Respawn Entertainment", "Battle Royale", "PC, PlayStation, Xbox, Switch, Mobile"));
        gameList.add(new Game("11", "Genshin Impact", "miHoYo", "Action RPG", "PC, Mobile, PlayStation"));
        gameList.add(new Game("12", "Fortnite", "Epic Games", "Battle Royale", "Multiple Platforms"));
        gameList.add(new Game("13", "Call of Duty: Warzone", "Infinity Ward, Raven Software", "Battle Royale", "PC, PlayStation, Xbox"));
        gameList.add(new Game("14", "Valorant", "Riot Games", "Tactical Shooter", "PC"));
        gameList.add(new Game("15", "League of Legends", "Riot Games", "MOBA", "PC"));
        gameList.add(new Game("16", "Dota 2", "Valve", "MOBA", "PC"));
        gameList.add(new Game("17", "Counter-Strike: Global Offensive", "Valve, Hidden Path Entertainment", "First-person Shooter", "PC"));
        gameList.add(new Game("18", "Overwatch 2", "Blizzard Entertainment", "Hero Shooter", "PC, PlayStation, Xbox, Switch"));
        gameList.add(new Game("19", "World of Warcraft", "Blizzard Entertainment", "MMORPG", "PC"));
        gameList.add(new Game("20", "Final Fantasy XIV", "Square Enix", "MMORPG", "PC, PlayStation"));
        gameList.add(new Game("21", "Elden Ring", "FromSoftware", "Action RPG", "PC, PlayStation, Xbox"));
        gameList.add(new Game("22", "Sekiro: Shadows Die Twice", "FromSoftware", "Action-Adventure", "PC, PlayStation, Xbox"));
        gameList.add(new Game("23", "Bloodborne", "FromSoftware", "Action RPG", "PlayStation"));
        gameList.add(new Game("24", "Dark Souls III", "FromSoftware", "Action RPG", "PC, PlayStation, Xbox"));
        gameList.add(new Game("25", "DOOM Eternal", "id Software", "First-person Shooter", "PC, PlayStation, Xbox, Switch"));
        gameList.add(new Game("26", "Animal Crossing: New Horizons", "Nintendo", "Social Simulation", "Switch"));

//        // Get a reference to the "games" node in the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        gamesRef = database.getReference("Game");

        // Set up RecyclerView
        gamesAdapter = new GamesAdapter(gameList, this);
        binding.gamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.gamesRecyclerView.setAdapter(gamesAdapter);

//        // Fetch games from Firebase
//        fetchGamesFromFirebase();

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
        for (Game game : gameList) {
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
