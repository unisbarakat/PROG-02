package com.unisbarakat.represent;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first, container, false);

        TextView tv = (TextView) v.findViewById(R.id.name);
        tv.setText(getArguments().getString("name"));


        RelativeLayout layout = (RelativeLayout) v.findViewById((R.id.layout));

        if(getArguments().getString("party").equals("Democrat")){
            layout.setBackgroundColor(Color.parseColor("#FF094990"));
        } else if (getArguments().getString("party").equals("Republican")){
            layout.setBackgroundColor(Color.parseColor("#c93129"));
        } else {
            layout.setBackgroundColor(Color.parseColor("#000000"));
        }


        ImageButton button = (ImageButton) v.findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("BUTTON CLICKED ON WATCH");


                Intent sendIntent = new Intent(getActivity().getBaseContext() , WatchToPhoneService.class);
                sendIntent.putExtra("m", getArguments().getString("name"));

                System.out.println("THE MESSAGE I'M PASSING TO PHONE IS: " + getArguments().getString("name"));
                getActivity().startService(sendIntent);

            }
        });

        return v;
    }





    public static FirstFragment newInstance(String name, String party) {

        FirstFragment f = new FirstFragment();
        Bundle b = new Bundle();
        b.putString("name", name);
        b.putString("party", party);
        f.setArguments(b);
        return f;
    }
}
