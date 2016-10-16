package com.gkarkow.quizzle;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gkarkow.session.Session;

public class MasterFragment extends Fragment {

    public MasterFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master, container, false);

        TextView tvMaster = (TextView) rootView.findViewById(R.id.tvMaster);
        tvMaster.setText(getResources().getString(R.string.master) + " " + Session.field.getName());

        return rootView;
    }
}
