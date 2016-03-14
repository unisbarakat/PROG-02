package com.unisbarakat.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ExpandableListView;

import com.twitter.sdk.android.core.TwitterException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import twitter4j.Status;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


/**
 * Created by unisbarakat on 2/25/16.
 */
public class GeneralView extends Activity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    //    ArrayList<Official> _header;
    public static ArrayList<Official> listDataHeader;
    String state;
    String county;

    int pos;

    //GET COUNTY STUFF
    public class GetCountyStuff extends AsyncTask {
        Context context;


        public GetCountyStuff(Context context) {
            this.context = context;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            String json = null;
            if (params[0] != null) {
                try {

                    InputStream is = getAssets().open("election-county-2012.json");

                    int size = is.available();

                    byte[] buffer = new byte[size];

                    is.read(buffer);

                    is.close();

                    json = new String(buffer, "UTF-8");
                    return json;


                } catch (IOException ex) {
                    ex.printStackTrace();
                    return null;
                }
            }
            return json;
        }
    }

    //Getting Location Data from URL
    public class ReverseGecoding extends AsyncTask {
        Context context;
        private Address address;
        private String GEOCODINGKEY = "&key=AIzaSyClHawKVLt8hVydZNLmCrItaPmEWDD5iaw";
        private String REVERSE_GEOCODING_URL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";

        public ReverseGecoding(Context context) {
            this.context = context;
        }


        private StringBuilder readResponse(InputStream inputStream) throws IOException, NullPointerException {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder;
        }


        @Override
        protected Object doInBackground(Object[] params) {
            if (params[0] != null) {
                String result = "";
                try {
                    String mUrl = REVERSE_GEOCODING_URL + params[0] + ","
                            + params[1] + GEOCODINGKEY;

                    URL url = new URL(mUrl);
                    HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
                    httpsURLConnection.setReadTimeout(20000);
                    httpsURLConnection.setConnectTimeout(30000);
                    httpsURLConnection.setDoInput(true);
                    httpsURLConnection.setRequestMethod("GET");
                    httpsURLConnection.connect();
                    int mStatus = httpsURLConnection.getResponseCode();
                    if (mStatus == 200)
                        return readResponse(httpsURLConnection.getInputStream()).toString();

                    return result;

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
            return null;


        }

    }


    //GET TWITTER INFO
    public class getTweets extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("K6W0dtOGsNdCr68d9Scv4pIP3")
                    .setOAuthConsumerSecret(
                            "Q30tUZ5qzHi7TgjsWCNgHpFt99HgPdiFYXRuA9BGzaeNQDsjqH")
                    .setOAuthAccessToken(
                            "975512155-EuCvvucv04nyzytigpOHv18HGzxaM3fJpJcyBzz3")
                    .setOAuthAccessTokenSecret(
                            "YM4Hsu4uCBqNWLaJ0APGPnNvCxRSOUEIo46NuyWaBhIw0");
            TwitterFactory tf = new TwitterFactory(cb.build());
            twitter4j.Twitter twitter = tf.getInstance();
            twitter4j.Status status = null;

            try {
                List<twitter4j.Status> statuses;
                String user;
                user = (String) params[0];
                status = null;
                try {
                    statuses = twitter.getUserTimeline(user);

                    if (statuses.size() > 0) {
                        status = statuses.get(0);
//                        profileImage = statuses.get(0).getUser().getOriginalProfileImageURL();
                    }

                } catch (twitter4j.TwitterException e) {
                    e.printStackTrace();
                }

            } catch (TwitterException te) {
                te.printStackTrace();
            }

            return status;

        }

    }


    //GET Congress GPS Info
    public class getCongress extends AsyncTask {
        Context context;
        private String KEY = "&apikey=ed077de7534a4568bfab0e8bad0c58d5";
        private String URL = "https://congress.api.sunlightfoundation.com/legislators/locate?latitude=";

        public getCongress(Context context) {
            this.context = context;
        }


        private StringBuilder readResponse(InputStream inputStream) throws IOException, NullPointerException {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder;
        }


        @Override
        protected Object doInBackground(Object[] params) {
            if (params[0] != null) {
                String result = "";
                try {
                    String mUrl = URL + params[0] + "&longitude="
                            + params[1] + KEY;

                    java.net.URL url = new URL(mUrl);
                    HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
                    httpsURLConnection.setReadTimeout(20000);
                    httpsURLConnection.setConnectTimeout(30000);
                    httpsURLConnection.setDoInput(true);
                    httpsURLConnection.setRequestMethod("GET");
                    httpsURLConnection.connect();
                    int mStatus = httpsURLConnection.getResponseCode();
                    if (mStatus == 200)
                        return readResponse(httpsURLConnection.getInputStream()).toString();

                    return result;

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
            return null;


        }

    }

    //GET Congress ZIP Info
    public class getCongressZIP extends AsyncTask {
        Context context;
        private String KEY = "&apikey=ed077de7534a4568bfab0e8bad0c58d5";
        private String URL = "https://congress.api.sunlightfoundation.com/legislators/locate?zip=";

        public getCongressZIP(Context context) {
            this.context = context;
        }


        private StringBuilder readResponse(InputStream inputStream) throws IOException, NullPointerException {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder;
        }


        @Override
        protected Object doInBackground(Object[] params) {
            if (params[0] != null) {
                String result = "";
                try {
                    String mUrl = URL + params[0] + KEY;

                    URL url = new URL(mUrl);
                    HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
                    httpsURLConnection.setReadTimeout(20000);//changed from 10000
                    httpsURLConnection.setConnectTimeout(30000); //changed from 15000
                    httpsURLConnection.setDoInput(true);
                    httpsURLConnection.setRequestMethod("GET");
                    httpsURLConnection.connect();
                    int mStatus = httpsURLConnection.getResponseCode();
                    if (mStatus == 200)
                        return readResponse(httpsURLConnection.getInputStream()).toString();

                    return result;

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
            return null;


        }

    }


    //GET Bills
    public class getBills extends AsyncTask {
        Context context;
        private Address address;
        private String KEY = "&apikey=ed077de7534a4568bfab0e8bad0c58d5";
        private String URL = "https://congress.api.sunlightfoundation.com/bills?sponsor_id=";

        public getBills(Context context) {
            this.context = context;
        }


        private StringBuilder readResponse(InputStream inputStream) throws IOException, NullPointerException {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder;
        }


        @Override
        protected Object doInBackground(Object[] params) {
            if (params[0] != null) {
                String result = "";
                try {
                    String mUrl = URL + params[0] + KEY;

                    URL url = new URL(mUrl);
                    HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
                    httpsURLConnection.setReadTimeout(10000);
                    httpsURLConnection.setConnectTimeout(15000);
                    httpsURLConnection.setDoInput(true);
                    httpsURLConnection.setRequestMethod("GET");
                    httpsURLConnection.connect();
                    int mStatus = httpsURLConnection.getResponseCode();
                    if (mStatus == 200)
                        return readResponse(httpsURLConnection.getInputStream()).toString();

                    return result;

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
            return null;


        }


    }


    //GET Committies
    public class getCommitties extends AsyncTask {
        Context context;
        private Address address;
        private String KEY = "&apikey=ed077de7534a4568bfab0e8bad0c58d5";
        private String URL = "https://congress.api.sunlightfoundation.com/committees?member_ids=";

        public getCommitties(Context context) {
            this.context = context;
        }


        private StringBuilder readResponse(InputStream inputStream) throws IOException, NullPointerException {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder;
        }


        @Override
        protected Object doInBackground(Object[] params) {
            if (params[0] != null) {
                String result = "";
                try {
                    String mUrl = URL + params[0] + KEY;

                    URL url = new URL(mUrl);
                    HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
                    httpsURLConnection.setReadTimeout(10000);
                    httpsURLConnection.setConnectTimeout(15000);
                    httpsURLConnection.setDoInput(true);
                    httpsURLConnection.setRequestMethod("GET");
                    httpsURLConnection.connect();
                    int mStatus = httpsURLConnection.getResponseCode();
                    if (mStatus == 200)
                        return readResponse(httpsURLConnection.getInputStream()).toString();

                    return result;

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
            return null;


        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_general);


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        //Get Extras
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras.get("m") != null) {
            String m = (String) extras.get("m");

            System.out.println("THE MESSAGE FROM WATCH IS : " + m);
            System.out.println("THE SIZE OF LISTDATAHEADER : " + listDataHeader.size());

            for (int i = 0; i < listDataHeader.size(); i++) {
                assert m != null;
                System.out.println(listDataHeader.get(i).position + " " + listDataHeader.get(i).name);
                if (m.equals(listDataHeader.get(i).position + " " + listDataHeader.get(i).name)) {
                    this.pos = i;
                    System.out.println(this.pos);
                    listAdapter = new ExpandableListAdapter(this, listDataHeader, this.pos);
                    expListView.setAdapter(listAdapter);
                    expListView.setSelectedGroup(pos);
//                    listAdapter.notifyDataSetChanged();
                }
            }
        } else {

            if (extras.get("LNG") != null && extras.get("LAT") != null) {
                Double lat = (Double) extras.get("LAT");
                Double lng = (Double) extras.get("LNG");

                prepareGPSData(lat, lng);

//            //GO TO WATCH
//            Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
//            sendIntent.putExtra("header", listDataHeader);
//
//            startService(sendIntent);


            } else if (extras.get("ZIP") != null) {

                prepareZIPData((int) extras.get("ZIP"));

//
//            //GO TO WATCH
//            Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
//            sendIntent.putExtra("header", listDataHeader);
//            sendIntent.putExtra("State", this.state);
//            sendIntent.putExtra("County", this.county);
//
//            startService(sendIntent);


            }
        }

//            Intent myIntent = new Intent(this, GeneralView.class);
//            myIntent.putExtra("header", listDataHeader);
//            myIntent.putExtra("m", m);
//            startActivity(myIntent);

    }


//        if (extras.get("header") != null) {
//
//            ArrayList<Official> header = (ArrayList<Official>) extras.get("header");
//            this._header = header;
//                // setting list adapter
//            if ( extras.get("m") != null){
//                System.out.println(extras.get("m"));
//                System.out.println(_header.get(0).position + " " + _header.get(0).name);
//
//                if( extras.get("m").equals(_header.get(0).position + " " + _header.get(0).name)){
//                    this.pos = 0;
//                } else if ( extras.get("m").equals(_header.get(1).position + " " + _header.get(1).name)){
//                    this.pos = 1;
//                } else if ( extras.get("m").equals(_header.get(2).position + " " + _header.get(2).name)){
//                    this.pos = 2;
//                } else {
//                    this.pos = -1;
//                }
//
//                listAdapter = new ExpandableListAdapter(this, this._header, this.pos);
//                expListView.setAdapter(listAdapter);
//
//            }else{
//                listAdapter = new ExpandableListAdapter(this, this._header);
//                expListView.setAdapter(listAdapter);
//            }
//
//
//
//        }


//    @Override
//    public void onBackPressed(){
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//    }


    private void prepareGPSData(Double lat, Double lng) {

        listDataHeader = new ArrayList<Official>();

        //Get State and County
        ReverseGecoding rg = new ReverseGecoding(getBaseContext());
        String rgResult = "NONE";
        String State = "NONE";
        String County = "NONE";
        try {
            rgResult = (String) rg.execute(lat, lng).get();
            if (rgResult != null) {
                JSONObject jsonObject = new JSONObject(rgResult);
                if (jsonObject.getString("status").equals("OK")) {
                    jsonObject = jsonObject.getJSONArray("results")
                            .getJSONObject(0);

                    JSONArray address_components = jsonObject.getJSONArray("address_components");

                    for (int i = 0; i < address_components.length(); i++) {
                        JSONObject zero2 = address_components.getJSONObject(i);
                        String long_name = zero2.getString("long_name");
                        String short_name = zero2.getString("short_name");

                        JSONArray mtypes = zero2.getJSONArray("types");
                        String Type = mtypes.getString(0);

                        if (TextUtils.isEmpty(long_name) == false || !long_name.equals(null) || long_name.length() > 0 || long_name != "") {
                            if (Type.equalsIgnoreCase("administrative_area_level_2")) {
                                County = long_name;
                            } else if (Type.equalsIgnoreCase("administrative_area_level_1")) {
                                State = short_name;
                            }
                        }
                    }
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.state = State;
        this.county = County;


        //Get Congress Info
        getCongress gc = new getCongress(getBaseContext());
        String result = "NONE";

        try {
            result = (String) gc.execute(lat, lng).get();

            if (result != null) {

                JSONObject jsonObject = new JSONObject(result);
                int count = jsonObject.getInt("count");

                for (int i = 0; i < count; i++) {

                    Official of = new Official();

                    JSONObject temp = jsonObject.getJSONArray("results").getJSONObject(i);

                    of.setName(temp.getString("first_name") + " " + temp.getString("last_name"));
                    of.setNumber(i);

                    if (Objects.equals(temp.getString("party"), "D")) {
                        of.setParty("Democrat");
                    } else if (Objects.equals(temp.getString("party"), "R")) {
                        of.setParty("Republican");
                    } else {
                        of.setParty("Independent");
                    }
                    of.setEmail(temp.getString("oc_email"));
                    of.setPosition(temp.getString("title") + ".");
                    of.setTwitter("https://twitter.com/" + temp.getString("twitter_id"));
                    of.setWeb(temp.getString("website"));

                    of.setEND(temp.getString("term_end"));

                    //Get Tweet and Assign it
                    try {
                        Status status = (Status) new getTweets().execute(temp.getString("twitter_id")).get();
                        if (status != null) {
                            Long tweetID = status.getId();
                            of.setTweet(tweetID);
                            of.setImage(status.getUser().getOriginalProfileImageURL());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    //Set Committies

                    getCommitties getCommitties = new getCommitties(getBaseContext());
                    result = (String) getCommitties.execute(temp.getString("bioguide_id")).get();

                    if (result != null) {

                        JSONObject jsonCom = new JSONObject(result);
                        int comCount = jsonCom.getInt("count");

                        ArrayList<String> comList = new ArrayList<String>();

                        for (int n = 0; n < comCount; n++) {
                            JSONObject tempCom = jsonCom.getJSONArray("results").getJSONObject(n);
                            comList.add(tempCom.getString("name"));
                        }

                        of.setComList(comList);
                    }

                    //Set Bills
                    getBills getBills = new getBills(getBaseContext());
                    result = (String) getBills.execute(temp.getString("bioguide_id")).get();

                    if (result != null) {

                        JSONObject jsonBill = new JSONObject(result);
                        int bCount = jsonBill.getInt("count");

                        int getCountOf = 5;

                        if (bCount < 5) {
                            getCountOf = bCount;
                        }

                        ArrayList<String> bList = new ArrayList<String>();

                        for (int n = 0; n < getCountOf; n++) {
                            JSONObject tempBill = jsonBill.getJSONArray("results").getJSONObject(n);

                            if (tempBill.getString("short_title") != null) {
                                bList.add(tempBill.getString("short_title"));
                            } else {
                                bList.add(tempBill.getString("official_title"));
                            }
                        }

                        of.setbList(bList);
                    }

                    listDataHeader.add(i, of);
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        GetCountyStuff gs = new GetCountyStuff(getBaseContext());

        String Rcounty = "NONE";
        String Ocounty = "NONE";


        System.out.println("Ocounty before is: " + Ocounty);
        System.out.println(" Rcounty before is: " + Rcounty);


        String r = null;
        try {
            r = (String) gs.execute("hi").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (r != null) {

            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(r);
                for (int i = 0; i < jsonArray.length(); i++) {


                    JSONObject zero2 = jsonArray.getJSONObject(i);
                    String countyName = zero2.getString("county-name") + " County";
                    String stateName = zero2.getString("state-postal");

                    String O = zero2.getString("obama-percentage") + "%";
                    String R = zero2.getString("romney-percentage") + "%";

                    System.out.println("THEIR COUNTY: " + countyName + " MY COUNTY: " + this.county);


                    if (Objects.equals(countyName, this.county) && Objects.equals(stateName, this.state)) {
                        Rcounty = R;
                        Ocounty = O;

                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }




        System.out.println("Ocounty is: " + Ocounty);
        System.out.println(" Rcounty is: " + Rcounty);

        listAdapter = new ExpandableListAdapter(this, listDataHeader);
        expListView.setAdapter(listAdapter);


        System.out.println("STATE IS: " + state + " and county is: " + county);


        //delay in ms
        int DELAY = 5000;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //GO TO WATCH
                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("header", listDataHeader);
                sendIntent.putExtra("State", state);
                sendIntent.putExtra("County", county);
                startService(sendIntent);
            }
        }, DELAY);

    }


    private void prepareZIPData(int zip) {

        listDataHeader = new ArrayList<Official>();

        //Get Congress Info
        getCongressZIP gc = new getCongressZIP(getBaseContext());
        String result = "NONE";

        try {
            result = (String) gc.execute(zip).get();

            if (result != null) {

                JSONObject jsonObject = new JSONObject(result);
                int count = jsonObject.getInt("count");

                for (int i = 0; i < count; i++) {

                    Official of = new Official();

                    JSONObject temp = jsonObject.getJSONArray("results").getJSONObject(i);

                    of.setName(temp.getString("first_name") + " " + temp.getString("last_name"));
                    of.setNumber(i);

                    if (Objects.equals(temp.getString("party"), "D")) {
                        of.setParty("Democrat");
                    } else if (Objects.equals(temp.getString("party"), "R")) {
                        of.setParty("Republican");
                    } else {
                        of.setParty("Independent");
                    }
                    of.setEmail(temp.getString("oc_email"));
                    of.setPosition(temp.getString("title") + ".");
                    of.setTwitter("https://twitter.com/" + temp.getString("twitter_id"));
                    of.setWeb(temp.getString("website"));
                    of.setEND(temp.getString("term_end"));


                    //Get Tweet and Assign it
                    try {
                        Status status = (Status) new getTweets().execute(temp.getString("twitter_id")).get();
                        if (status != null) {
                            Long tweetID = status.getId();
                            of.setTweet(tweetID);
                            of.setImage(status.getUser().getOriginalProfileImageURL());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    //Set Committies

                    getCommitties getCommitties = new getCommitties(getBaseContext());
                    result = (String) getCommitties.execute(temp.getString("bioguide_id")).get();

                    if (result != null) {

                        JSONObject jsonCom = new JSONObject(result);
                        int comCount = jsonCom.getInt("count");

                        ArrayList<String> comList = new ArrayList<String>();

                        for (int n = 0; n < comCount; n++) {
                            JSONObject tempCom = jsonCom.getJSONArray("results").getJSONObject(n);
                            comList.add(tempCom.getString("name"));
                        }

                        of.setComList(comList);
                    }

                    //Set Bills
                    getBills getBills = new getBills(getBaseContext());
                    result = (String) getBills.execute(temp.getString("bioguide_id")).get();

                    if (result != null) {

                        JSONObject jsonBill = new JSONObject(result);
                        int bCount = jsonBill.getInt("count");

                        int getCountOf = 5;

                        if (bCount < 5) {
                            getCountOf = bCount;
                        }

                        ArrayList<String> bList = new ArrayList<String>();

                        for (int n = 0; n < getCountOf; n++) {
                            JSONObject tempBill = jsonBill.getJSONArray("results").getJSONObject(n);

                            if (tempBill.getString("short_title") != null) {
                                bList.add(tempBill.getString("short_title"));
                            } else {
                                bList.add(tempBill.getString("official_title"));
                            }
                        }

                        of.setbList(bList);
                    }

                    listDataHeader.add(i, of);
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }




//        GetCountyStuff gs = new GetCountyStuff(getBaseContext());
//
//        String Rcounty = "NONE";
//        String Ocounty = "NONE";
//
//
//        System.out.println("Ocounty before is: " + Ocounty);
//        System.out.println(" Rcounty before is: " + Rcounty);
//
//
//        String r = null;
//        try {
//            r = (String) gs.execute("hi").get();
//
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        if (r != null) {
//
//            JSONArray jsonArray = null;
//            try {
//                jsonArray = new JSONArray(r);
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//
//                    JSONObject zero2 = jsonArray.getJSONObject(i);
//                    String countyName = zero2.getString("county-name") + " County";
//                    String stateName = zero2.getString("state-postal");
//
//                    String O = zero2.getString("obama-percentage") + "%";
//                    String R = zero2.getString("romney-percentage") + "%";
//
//                    System.out.println("THEIR COUNTY: " + countyName + " MY COUNTY: " + this.county);
//
//
//                    if (Objects.equals(countyName, this.county) && Objects.equals(stateName, this.state)) {
//                        Rcounty = R;
//                        Ocounty = O;
//
//                    }
//                }
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//
//
//        System.out.println("Ocounty is: " + Ocounty);
//        System.out.println(" Rcounty is: " + Rcounty);

        listAdapter = new ExpandableListAdapter(this, listDataHeader);
        expListView.setAdapter(listAdapter);


        //delay in ms
        int DELAY = 5000;



        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //GO TO WATCH
                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("header", listDataHeader);
                sendIntent.putExtra("State", state);
                sendIntent.putExtra("County", county);
//                sendIntent.putExtra("Ocounty", finalOcounty);
//                sendIntent.putExtra("Rcounty", finalRcounty);
                startService(sendIntent);
            }
        }, DELAY);


    }


//        public String loadJSONFromAsset() {
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


