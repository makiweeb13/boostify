package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GameViewHolder> {
    private List<Game> gamesList;

    public GamesAdapter(List<Game> gamesList) {
        this.gamesList = gamesList;
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        public TextView gameNameTextView;
        public GameViewHolder(CardView itemView) {
            super(itemView);
            gameNameTextView = itemView.findViewById(R.id.gameNameTextView);
        }
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView itemView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_item, parent, false);
        return new GameViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game currentGame = gamesList.get(position);
        holder.gameNameTextView.setText(currentGame.name);
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }
}
