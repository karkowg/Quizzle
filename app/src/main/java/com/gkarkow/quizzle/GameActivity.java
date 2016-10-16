package com.gkarkow.quizzle;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.Locale;

import com.gkarkow.model.Game;
import com.gkarkow.model.Level;
import com.gkarkow.model.Round;
import com.gkarkow.session.Session;
import com.gkarkow.util.Util;

public class GameActivity extends Activity implements ActionBar.TabListener {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initGUI();

        final ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent refresh = new Intent(this, GameActivity.class);
        startActivity(refresh);
        finish();
    }

    private void initGUI() {
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setTitle(Session.field.getName());

        initSession();
    }

    private void initSession() {
        Session.level = Session.game.levels().get(Session.game.getCurrentLevel()-1);
        Session.round = Session.level.rounds().get(Session.level.getCurrentRound()-1);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    protected class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return Session.game.isFinished() ? new MasterFragment() : new FieldFragment();
                case 1:
                    return new StatisticsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_activity_game).toUpperCase(l);
                case 1:
                    return getString(R.string.statistics).toUpperCase(l);
            }
            return null;
        }
    }

    public void play(View view) {
        Session.difficulties = Util.quotaDifficulties(Session.level, Session.round);
        Intent intent = new Intent(this, QuestionActivity.class);
        startActivity(intent);
    }

    public void reset(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(getResources().getString(R.string.msg_reset));
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                resetGame(Session.game);
                onRestart();
            }
        });
        alert.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alert.show();
    }

    private void resetGame(Game game) {
        game.setCurrentLevel(1);
        game.setFinished(false);
        game.save();
        for (Level l : Session.game.levels()) {
            l.setCurrentRound(1);
            l.setFinished(false);
            l.save();
            for (Round r : l.rounds()) {
                r.setHitsEasy(0);
                r.setHitsMedium(0);
                r.setHitsHard(0);
                r.save();
            }
        }
    }
}
