package com.tlbail.FlappyBurne.View;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tlbail.FlappyBurne.Model.Score;
import com.tlbail.FlappyBurne.R;

public class ScoreViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    /**
     * View holder des score
     * set le text en fonction du pseudo et du score du joueur
     * @param itemView
     */
    public ScoreViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.scoreTextViewScoreLayout);
    }


    public void setViewHolder(Score score){
        textView.setText(score.getPseudo() + "  " + score.getScore());
    }


}
