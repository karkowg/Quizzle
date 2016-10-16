package com.gkarkow.quizzle;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gkarkow.model.Level;
import com.gkarkow.model.Round;
import com.gkarkow.session.Session;
import com.gkarkow.util.Util;

public class FinishedLevelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_level);

        initGUI();
    }

    private void initGUI() {
        String level = Session.level.getName();

        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setTitle(level);

        TextView tvFinishedLevel = (TextView) findViewById(R.id.tvFinishedLevel);
        TextView tvScore = (TextView) findViewById(R.id.tvLevelScore);
        Button btNextLevel = (Button) findViewById(R.id.btNextLevel);
        Button btPauseGame = (Button) findViewById(R.id.btPauseGame);

        tvFinishedLevel.setText(getResources().getString(R.string.finished_level));
        tvScore.setText(getResources().getString(R.string.final_score) + ": " + String.format("%.2f", Session.level.getAverage()));
        btNextLevel.setEnabled(true);
        btNextLevel.setVisibility(View.VISIBLE);
        btNextLevel.setText(getResources().getString(R.string.next_level));
        btPauseGame.setText(getResources().getString(R.string.pause));

        if (Session.level.getAverage() < 7) {
            tvFinishedLevel.setText(getResources().getString(R.string.game_over));
            tvScore.setTextColor(Color.RED);
            btNextLevel.setText(getResources().getString(R.string.try_again));
        } else {
            tvScore.setTextColor(Color.GREEN);
            if (isLastLevel()) {
                btNextLevel.setEnabled(false);
                btNextLevel.setVisibility(View.INVISIBLE);
                btPauseGame.setText(getResources().getString(R.string.finish));
            }
        }
    }

    public void playNext(View view) {
        Session.currentQuestion = 1;

        if (Session.level.getAverage() < 7) {
            resetLevel(Session.level);
        } else {
            Session.level.setFinished(true);
            Session.level.save();
            Session.game.setCurrentLevel(Session.game.getCurrentLevel()+1);
            Session.game.save();

            Session.level = Session.game.levels().get(Session.game.getCurrentLevel()-1);
        }

        Session.round = Session.level.rounds().get(Session.level.getCurrentRound()-1);
        Session.difficulties = Util.quotaDifficulties(Session.level, Session.round);

        Intent intent = new Intent(this, QuestionActivity.class);
        startActivity(intent);
        finish();
    }

    public void pauseGame(View view) {
        Session.currentQuestion = 1;

        if (Session.level.getAverage() < 7) {
            resetLevel(Session.level);
            Session.round = Session.level.rounds().get(Session.level.getCurrentRound()-1);
        } else {
            Session.level.setFinished(true);
            Session.level.save();
            if (isLastLevel()) {
                Session.game.setFinished(true);
                Session.game.save();
            } else {
                Session.game.setCurrentLevel(Session.game.getCurrentLevel()+1);
                Session.game.save();

                Session.level = Session.game.levels().get(Session.game.getCurrentLevel()-1);
                Session.round = Session.level.rounds().get(Session.level.getCurrentRound()-1);
            }
        }

        finish();
    }

    private void resetLevel(Level level) {
        level.setCurrentRound(1);
        level.save();
        for (Round r : level.rounds()) {
            r.setHitsEasy(0);
            r.setHitsMedium(0);
            r.setHitsHard(0);
            r.save();
        }
    }

    private boolean isLastLevel() {
        return Session.game.levels().size() == Session.game.getCurrentLevel();
    }
}
