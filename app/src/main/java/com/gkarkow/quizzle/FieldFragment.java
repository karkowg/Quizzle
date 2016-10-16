package com.gkarkow.quizzle;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gkarkow.session.Session;

public class FieldFragment extends Fragment {

    public FieldFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_field, container, false);

        TextView tvLevelName = (TextView) rootView.findViewById(R.id.tvLevelName);
        tvLevelName.setText(Session.level.getName());

        TextView tvRound = (TextView) rootView.findViewById(R.id.tvRound);
        String round = getResources().getString(R.string.round) + " " + Session.round.getNumber();
        tvRound.setText(round);

        return rootView;
    }
}
