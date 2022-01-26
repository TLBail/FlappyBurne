package com.tlbail.FlappyBurne.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.tlbail.FlappyBurne.Model.Score;
import com.tlbail.FlappyBurne.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ScoreAdaptater extends FirebaseRecyclerAdapter<Score, ScoreViewHolder> {

    /**
     * Adaptater de la liste des score
     * prend une liste de Score en paramètre et l'affiche dans une recyclerView
     * @param options
     */
    public ScoreAdaptater(@NonNull FirebaseRecyclerOptions<Score> options) {
        super(options);
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.scorelayout, parent, false);
        return new ScoreViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ScoreViewHolder scoreViewHolder, int i, @NonNull Score score) {
        scoreViewHolder.setViewHolder(score);
    }

}
