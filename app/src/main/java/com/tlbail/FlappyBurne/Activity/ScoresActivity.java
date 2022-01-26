package com.tlbail.FlappyBurne.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.Query;
import com.tlbail.FlappyBurne.Model.Score;
import com.tlbail.FlappyBurne.Model.ScoreDataBase;
import com.tlbail.FlappyBurne.R;
import com.tlbail.FlappyBurne.View.ScoreAdaptater;
import com.tlbail.FlappyBurne.View.ScoreViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ScoresActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ScoreDataBase scoreDataBase;
    public FirebaseRecyclerAdapter<Score, ScoreViewHolder> firebaseRecyclerAdapter;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private String pseudo;


    //affiche tout les score via firebass
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        setupfireBaseAuth();
        scoreDataBase = new ScoreDataBase(pseudo);
        bindUI();
        setUpRecyclerView();

    }

    private void setupfireBaseAuth(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser == null){
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish();
            return;
        }else{
            pseudo = firebaseUser.getDisplayName();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseRecyclerAdapter.startListening();

    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseRecyclerAdapter.stopListening();
    }

    private void setUpRecyclerView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        Query query = scoreDataBase.getdatabaseReferenceOfScores().orderByChild("score");
        FirebaseRecyclerOptions<Score> options =
                new FirebaseRecyclerOptions.Builder<Score>().setQuery(query, scoreDataBase.getSnapShotParser()).build();
        firebaseRecyclerAdapter = new ScoreAdaptater(options);
        recyclerView.setAdapter( firebaseRecyclerAdapter);


    }

    private void bindUI() {
        recyclerView = findViewById(R.id.scoresrecyclerView);
    }
}