package com.gkarkow.quizzle;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gkarkow.session.Session;

public class StatisticsFragment extends Fragment {

    public StatisticsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);

        TextView tvStatsLevel = (TextView) rootView.findViewById(R.id.tvStatsLevel);
        tvStatsLevel.setText(Session.game.lastFinishedLevelName());

        Float cumulative = Session.game.getCumulative() == null? 0 : Session.game.getCumulative();
        Float percentHitEasy = Session.game.getPercentHitEasy() == null? 0 : Session.game.getPercentHitEasy()*100;
        Float percentHitMedium = Session.game.getPercentHitMedium() == null? 0 : Session.game.getPercentHitMedium()*100;
        Float percentHitHard = Session.game.getPercentHitHard() == null? 0 : Session.game.getPercentHitHard()*100;

        TextView tvCumulative = (TextView) rootView.findViewById(R.id.tvCumulative);
        tvCumulative.setText(String.format("%.2f", cumulative));
        TextView tvEasy = (TextView) rootView.findViewById(R.id.tvPercentEasy);
        tvEasy.setText(String.format("%.0f%%", percentHitEasy));
        tvEasy.setTextColor(Color.GREEN);
        TextView tvMedium = (TextView) rootView.findViewById(R.id.tvPercentMedium);
        tvMedium.setText(String.format("%.0f%%", percentHitMedium));
        tvMedium.setTextColor(Color.YELLOW);
        TextView tvHard = (TextView) rootView.findViewById(R.id.tvPercentHard);
        tvHard.setText(String.format("%.0f%%", percentHitHard));
        tvHard.setTextColor(Color.RED);

        return rootView;
    }
}
