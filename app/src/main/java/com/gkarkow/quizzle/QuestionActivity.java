package com.gkarkow.quizzle;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import com.gkarkow.model.Alternative;
import com.gkarkow.model.Difficulty;
import com.gkarkow.model.Question;
import com.gkarkow.repository.DifficultyDAO;
import com.gkarkow.repository.QuestionDAO;
import com.gkarkow.session.Session;

public class QuestionActivity extends Activity {

    private TextView tvTimer;
    private Countdown counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        initGUI();
    }

    @Override
    public void onBackPressed() {
        counter.cancel();
        Session.question = null;
        Session.currentQuestion = 1;
    }

    private void initGUI() {
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.hide();

        Session.question = QuestionDAO.getRandom(Session.field,
                DifficultyDAO.getDifficultyByName(Session.difficulties.remove(0)));

        TextView tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvQuestion.setText(Session.question.getDescription());
        Difficulty difficulty = Session.question.getDifficulty();
        switch (difficulty.getNumber()) {
            case 1: tvQuestion.setTextColor(Color.GREEN);
                break;
            case 2: tvQuestion.setTextColor(Color.YELLOW);
                break;
            case 3: tvQuestion.setTextColor(Color.RED);
                break;
        }

        ArrayList<String> alternatives = randomizeAlternatives(Session.question);

        Button altA = (Button) findViewById(R.id.btAltA);
        altA.setText(alternatives.get(0));
        Button altB = (Button) findViewById(R.id.btAltB);
        altB.setText(alternatives.get(1));
        Button altC = (Button) findViewById(R.id.btAltC);
        altC.setText(alternatives.get(2));
        Button altD = (Button) findViewById(R.id.btAltD);
        altD.setText(alternatives.get(3));

        TextView tvQuestionLevel = (TextView) findViewById(R.id.tvQuestionLevel);
        tvQuestionLevel.setText(Session.level.getName());
        TextView tvCurrentQuestion = (TextView) findViewById(R.id.tvCurrentQuestion);
        tvCurrentQuestion.setText(Session.currentQuestion.toString());

        tvTimer = (TextView) findViewById(R.id.tvTimer);

        counter = new Countdown(difficulty.getTimeout()*1000, 1000);
        counter.start();
    }

    public void onClick(View view) {
        counter.cancel();
        Session.currentQuestion++;
        chosenAlternative(view.getId());
    }

    private void chosenAlternative(Integer id) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        if (id != null) {
            Button pressed = (Button) findViewById(id);
            String alternative = pressed.getText().toString();

            Alternative chosen = findAlternativeByDescription(alternative);
            incrementHits(chosen);

            alert.setMessage(chosen.isCorrect() ? getResources().getString(R.string.hit) : getResources().getString(R.string.miss));
        } else {
            alert.setMessage(getResources().getString(R.string.timeout));
        }

        alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                nextQuestion();
            }
        });
        alert.show();
    }

    private void incrementHits(Alternative chosen) {
        if (chosen.isCorrect()) {
            switch (Session.question.getDifficulty().getNumber()) {
                case 1: Session.round.setHitsEasy(Session.round.getHitsEasy()+1);
                    break;
                case 2: Session.round.setHitsMedium(Session.round.getHitsMedium()+1);
                    break;
                case 3: Session.round.setHitsHard(Session.round.getHitsHard()+1);
                    break;
            }
            Session.round.save();
        }
    }

    private void nextQuestion() {
        /**
         * Round/level finished
         */
        if (noMoreQuestions()) {
            if (isLastRound()) {
                Intent i = new Intent(getApplicationContext(), FinishedLevelActivity.class);
                startActivity(i);
            } else {
                Intent i = new Intent(getApplicationContext(), FinishedRoundActivity.class);
                startActivity(i);
            }
        }
        /**
         * Next question
         */
        else {
            Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
            startActivity(intent);
        }

        finish();
    }

    private boolean isLastRound() {
        return Session.level.rounds().size() == Session.level.getCurrentRound();
    }

    private boolean noMoreQuestions() {
        return Session.currentQuestion > Session.round.getNumQuestions();
    }

    private Alternative findAlternativeByDescription(String description) {
        if (Session.question.getA().getDescription().equals(description)) return Session.question.getA();
        if (Session.question.getB().getDescription().equals(description)) return Session.question.getB();
        if (Session.question.getC().getDescription().equals(description)) return Session.question.getC();
        if (Session.question.getD().getDescription().equals(description)) return Session.question.getD();
        return null;
    }

    private ArrayList<String> randomizeAlternatives(Question question) {
        ArrayList<String> alternatives = new ArrayList<String>();
        alternatives.add(question.getA().getDescription());
        alternatives.add(question.getB().getDescription());
        alternatives.add(question.getC().getDescription());
        alternatives.add(question.getD().getDescription());
        Collections.shuffle(alternatives);
        return alternatives;
    }

    /**
     * Countdown Timer
     */
    private class Countdown extends CountDownTimer {
        public Countdown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            tvTimer.setText("00:" + String.format("%02d", l/1000));
        }

        @Override
        public void onFinish() {
            tvTimer.setText("00:00");
            Session.currentQuestion++;
            chosenAlternative(null);
        }
    }
}
