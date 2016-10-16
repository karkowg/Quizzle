package com.gkarkow.quizzle;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gkarkow.session.Session;
import com.gkarkow.util.Util;

public class FinishedRoundActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_round);

        initGUI();
    }

    private void initGUI() {
        String round = getResources().getString(R.string.round) + " " + Session.round.getNumber();

        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setTitle(round);

        TextView tvScore = (TextView) findViewById(R.id.tvRoundScore);
        tvScore.setText(getResources().getString(R.string.partial_score) + ": " + String.format("%.2f", Session.level.getAverage()));
    }

    public void playNext(View view) {
        Session.currentQuestion = 1;
        Session.level.setCurrentRound(Session.level.getCurrentRound()+1);
        Session.level.save();

        Session.round = Session.level.rounds().get(Session.level.getCurrentRound()-1);

        Session.difficulties = Util.quotaDifficulties(Session.level, Session.round);

        Intent intent = new Intent(this, QuestionActivity.class);
        startActivity(intent);
        finish();
    }

    public void pauseGame(View view) {
        Session.currentQuestion = 1;
        Session.level.setCurrentRound(Session.level.getCurrentRound()+1);
        Session.level.save();

        Session.round = Session.level.rounds().get(Session.level.getCurrentRound()-1);
        finish();
    }
}
