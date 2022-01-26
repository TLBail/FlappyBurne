package com.tlbail.FlappyBurne.Model;

import androidx.annotation.NonNull;

import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.concurrent.Executor;

public class ScoreDataBase {

    public static final String PATHSCORE = "scores";

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String pseudo;


    /**
     * Base de donné firebase qui gère l'ajout de score et la récupération du score
     * @param pseudo
     */
    public ScoreDataBase(String pseudo){
        this.pseudo = pseudo;
        connectToDataBase();
    }




    public void connectToDataBase(){
        database = FirebaseDatabase.getInstance("https://flappybird-839ad-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = database.getReference();

    }

    public SnapshotParser<Score> getSnapShotParser(){
        SnapshotParser<Score> parser = new SnapshotParser<Score>() {
            @NonNull
            @Override
            public Score parseSnapshot(@NonNull DataSnapshot snapshot) {

                Score score = snapshot.getValue(Score.class);
                if(score != null){
                    score.setId(snapshot.getKey());
                }
                return score;
            }
        };
        return parser;

    }

    public DatabaseReference getdatabaseReferenceOfScores(){
        return databaseReference.child(PATHSCORE);
    }



    public void AddScore(int score){
        databaseReference.child(PATHSCORE).push().setValue(new Score(pseudo, score));
    }

}
