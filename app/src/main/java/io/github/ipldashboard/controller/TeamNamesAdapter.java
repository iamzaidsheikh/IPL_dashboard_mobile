package io.github.ipldashboard.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.ipldashboard.MainActivity;
import io.github.ipldashboard.R;
import io.github.ipldashboard.TeamActivity;
import io.github.ipldashboard.TeamColors;
import io.github.ipldashboard.model.Team;

import static androidx.core.content.ContextCompat.startActivity;

public class TeamNamesAdapter extends RecyclerView.Adapter<TeamNamesAdapter.ViewHolder> {

    private ArrayList<Team> teams;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView teamNameTextView;
        private final ImageView teamColorImageView;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(),getTextView().getText(),Toast.LENGTH_SHORT).show();
                    Context context = v.getContext();
                    Intent intent = new Intent(context, TeamActivity.class);
                    intent.putExtra("teamName", getTextView().getText().toString());
                    context.startActivity(intent);
                }
            });
            teamNameTextView =  view.findViewById(R.id.teamNameTextView);
            teamColorImageView = view.findViewById(R.id.teamColorImageView);
        }

        public TextView getTextView() {
            return teamNameTextView;
        }

        public ImageView getTeamColorImageView(){
            return teamColorImageView;
        }

    }

    public TeamNamesAdapter(ArrayList<Team> dataSet) {
        teams = dataSet;
    }

    @NonNull
    @Override
    public TeamNamesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.team_names_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamNamesAdapter.ViewHolder viewHolder, int position) {
        viewHolder.getTextView().setText(teams.get(position).getTeamName());
        viewHolder.getTeamColorImageView().setImageResource(TeamColors.getTeamColors().getTeamColorsMap().get(teams.get(position).getTeamName()));
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }


}
