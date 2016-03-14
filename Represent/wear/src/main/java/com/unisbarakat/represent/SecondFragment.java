package com.unisbarakat.represent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SecondFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_second, container, false);

        TextView state = (TextView) v.findViewById(R.id.State);
        state.setText(getArguments().getString("State"));

        TextView county = (TextView) v.findViewById(R.id.County);
        county.setText(getArguments().getString("County"));

        TextView Ostate = (TextView) v.findViewById(R.id.Ostate);
        Ostate.setText(getArguments().getString("Ostate"));

        TextView Ocounty = (TextView) v.findViewById(R.id.Ocounty);
        Ocounty.setText(getArguments().getString("Ocounty"));

        TextView Rstate = (TextView) v.findViewById(R.id.Rstate);
        Rstate.setText(getArguments().getString("Rstate"));

        TextView Rcounty = (TextView) v.findViewById(R.id.Rcounty);
        Rcounty.setText(getArguments().getString("Rcounty"));

        return v;
    }

    public static SecondFragment newInstance(String State, String County, String OState, String Ocounty, String Rstate, String Rcounty) {

        SecondFragment f = new SecondFragment();
        Bundle b = new Bundle();

        System.out.println("STATE: " + State + " County: " + County + " Ocounty: " + Ocounty + " Rcounty: " + Rcounty );
        b.putString("State", State);
        b.putString("County", County);
        b.putString("Ostate", OState);
        b.putString("Ocounty", Ocounty);
        b.putString("Rstate", Rstate);
        b.putString("Rcounty", Rcounty);

        f.setArguments(b);

        return f;
    }
}
