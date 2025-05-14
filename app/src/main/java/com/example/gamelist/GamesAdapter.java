package com.example.gamelist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashboard.R;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GameViewHolder> {
    private List<Game> gamesList;
    private Context context;
    public GamesAdapter(List<Game> gamesList, Context context) {
        this.gamesList = gamesList;
        this.context = context;
    }

    public void setFilteredList(List<Game> filteredList) {
        this.gamesList = filteredList;
        notifyDataSetChanged();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        public TextView gameNameTextView;
        public CardView cardView;
        public GameViewHolder(CardView itemView) {
            super(itemView);
            gameNameTextView = itemView.findViewById(R.id.gameNameTextView);
            cardView = itemView;
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
        holder.gameNameTextView.setText(currentGame.developer);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to GameDetailsActivity
                Intent intent = new Intent(context, GameDetailsActivity.class);
                intent.putExtra("game", currentGame);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }
}