package com.unisbarakat.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {



    ArrayList<Person> p;
    String County = "Alameda County";
    String State = "CA";
    int count = 0;

    String Ostate = "60.24%";
    String Ocounty = "78.5%";


    String Rstate = "37.12%";
    String Rcounty = "18.7%";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        TextView textView = (TextView) findViewById(R.id.initialTV);


        if (extras != null) {
            textView.setVisibility(View.GONE);


            this.p = (ArrayList<Person>) extras.get("people");
            this.count = p.size();


            if(extras.getString("county") != null) {
                this.County = extras.getString("county");
            }
            if(extras.getString("state") != null) {
                this.State = extras.getString("state");
            }
            if(extras.getString("Ocounty") != null) {
                this.Ocounty = extras.getString("Ocounty");
            }

            if(extras.getString("Rcounty") != null) {
                this.Rcounty = extras.getString("Rcounty");
            }


            if (this.count != 0 ) {
                System.out.println("STATE IS :  " + this.State);
                System.out.println("COUNTY IS : " + this.County);
                    ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
                    pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), this.p, this.County, this.State, this.count, this.Ostate, this.Ocounty, this.Rstate, this.Rcounty));
                }
            }

         else {

            textView.setText("Start the Phone App!");


        }


    }


    private class MyPagerAdapter extends FragmentPagerAdapter {


        int count;

        String state = "CA";
        String county = "Alameda County";

        String Ostate = "60.24%";
        String Ocounty = "78.5%";


        String Rstate = "37.12%";
        String Rcounty = "18.7%";



        ArrayList<Person> p;

        public MyPagerAdapter(FragmentManager fm, ArrayList<Person> p, String county, String state, int count, String Ostate, String Ocounty, String Rstate, String Rcounty) {
            super(fm);

            this.count = count;

            this.county = county;
            this.state = state;
            this.Ostate = Ostate;
//            this.Ocounty = Ocounty;
            this.Rstate = Rstate;
//            this.Rcounty = Rcounty;

            this.p = p;


            ///IMPLEMENT API CALL TO UPDATE THE COUNTY AND STATE VOTES

        }

        @Override
        public Fragment getItem(int pos) {

            System.out.println("THE POSITION IS: " + pos + "and the count is : " + count);



               if (pos == count & count != 0 ) {
                   if (this.state != "NONE" && this.county != "NONE") {
                       return SecondFragment.newInstance(this.state, this.county, this.Ostate, this.Ocounty, this.Rstate, this.Rcounty);
                   }else{
                       return SecondFragment.newInstance("NONE", "NONE", "0%", "0%", "0%", "0%");

                   }

               } else {
                   return FirstFragment.newInstance(this.p.get(pos).name, this.p.get(pos).party);
               }



        }

        @Override
        public int getCount() {

            return this.count + 1;
        }
    }


//    public String loadJSONFromAsset() {
//        String json = null;
//        try {
//
//            InputStream is = getAssets().open("election-county-2012.json");
//
//            int size = is.available();
//
//            byte[] buffer = new byte[size];
//
//            is.read(buffer);
//
//            is.close();
//
//            json = new String(buffer, "UTF-8");
//
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//
//    }
}

