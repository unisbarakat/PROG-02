package com.unisbarakat.represent;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class MainActivity extends Activity implements LocationListener {

    private ImageButton locateMe;
    LocationManager locationManager;
//    public static ArrayList<Official> listDataHeader;

    Double lat;
    Double lng;
    String provider;
//    String profileImage;
//    ProgressDialog progress;

//    private Location mLastLocation;
//    private GoogleApiClient mGoogleApiClient;
//    private LocationRequest mLocationRequest;
//    private static int REQUEST_CODE_RECOVER_PLAY_SERVICES = 200;


//    //Getting Location Data from URL
//    public class ReverseGecoding extends AsyncTask {
//        Context context;
//        private Address address;
//        private String GEOCODINGKEY = "&key=AIzaSyClHawKVLt8hVydZNLmCrItaPmEWDD5iaw";
//        private String REVERSE_GEOCODING_URL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
//
//        public ReverseGecoding(Context context) {
//            this.context = context;
//        }
//
//
//        private StringBuilder readResponse(InputStream inputStream) throws IOException, NullPointerException {
//            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
//            StringBuilder stringBuilder = new StringBuilder();
//            String line;
//            while ((line = r.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//            return stringBuilder;
//        }
//
//
//        @Override
//        protected Object doInBackground(Object[] params) {
//            if (params[0] != null) {
//                String result = "";
//                try {
//                    String mUrl = REVERSE_GEOCODING_URL + params[0] + ","
//                            + params[1] + GEOCODINGKEY;
//
//                    URL url = new URL(mUrl);
//                    HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
//                    httpsURLConnection.setReadTimeout(20000);
//                    httpsURLConnection.setConnectTimeout(30000);
//                    httpsURLConnection.setDoInput(true);
//                    httpsURLConnection.setRequestMethod("GET");
//                    httpsURLConnection.connect();
//                    int mStatus = httpsURLConnection.getResponseCode();
//                    if (mStatus == 200)
//                        return readResponse(httpsURLConnection.getInputStream()).toString();
//
//                    return result;
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
//            }
//            return null;
//
//
//        }
//
//    }

//
//    //GET TWITTER INFO
//    public class getTweets extends AsyncTask {
//
//        @Override
//        protected Object doInBackground(Object[] params) {
//            ConfigurationBuilder cb = new ConfigurationBuilder();
//            cb.setDebugEnabled(true)
//                    .setOAuthConsumerKey("K6W0dtOGsNdCr68d9Scv4pIP3")
//                    .setOAuthConsumerSecret(
//                            "Q30tUZ5qzHi7TgjsWCNgHpFt99HgPdiFYXRuA9BGzaeNQDsjqH")
//                    .setOAuthAccessToken(
//                            "975512155-EuCvvucv04nyzytigpOHv18HGzxaM3fJpJcyBzz3")
//                    .setOAuthAccessTokenSecret(
//                            "YM4Hsu4uCBqNWLaJ0APGPnNvCxRSOUEIo46NuyWaBhIw0");
//            TwitterFactory tf = new TwitterFactory(cb.build());
//            twitter4j.Twitter twitter = tf.getInstance();
//            Long id = null;
//
//            try {
//                List<twitter4j.Status> statuses;
//                String user;
//                user = (String) params[0];
//                id = null;
//                try {
//                    statuses = twitter.getUserTimeline(user);
//
//                    if (statuses.size() > 0) {
//                        id = statuses.get(0).getId();
//                        profileImage = statuses.get(0).getUser().getOriginalProfileImageURL();
//                    }
//
//                } catch (twitter4j.TwitterException e) {
//                    e.printStackTrace();
//                }
//
//            } catch (TwitterException te) {
//                te.printStackTrace();
//            }
//
//            return id;
//
//        }
//
//    }
//
//
//    //GET Congress GPS Info
//    public class getCongress extends AsyncTask {
//        Context context;
//        private String KEY = "&apikey=ed077de7534a4568bfab0e8bad0c58d5";
//        private String URL = "https://congress.api.sunlightfoundation.com/legislators/locate?latitude=";
//
//        public getCongress(Context context) {
//            this.context = context;
//        }
//
//
//        private StringBuilder readResponse(InputStream inputStream) throws IOException, NullPointerException {
//            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
//            StringBuilder stringBuilder = new StringBuilder();
//            String line;
//            while ((line = r.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//            return stringBuilder;
//        }
//
//
//        @Override
//        protected Object doInBackground(Object[] params) {
//            if (params[0] != null) {
//                String result = "";
//                try {
//                    String mUrl = URL + params[0] + "&longitude="
//                            + params[1] + KEY;
//
//                    URL url = new URL(mUrl);
//                    HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
//                    httpsURLConnection.setReadTimeout(20000);
//                    httpsURLConnection.setConnectTimeout(30000);
//                    httpsURLConnection.setDoInput(true);
//                    httpsURLConnection.setRequestMethod("GET");
//                    httpsURLConnection.connect();
//                    int mStatus = httpsURLConnection.getResponseCode();
//                    if (mStatus == 200)
//                        return readResponse(httpsURLConnection.getInputStream()).toString();
//
//                    return result;
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
//            }
//            return null;
//
//
//        }
//
//    }
//
//    //GET Congress ZIP Info
//    public class getCongressZIP extends AsyncTask {
//        Context context;
//        private String KEY = "&apikey=ed077de7534a4568bfab0e8bad0c58d5";
//        private String URL = "https://congress.api.sunlightfoundation.com/legislators/locate?zip=";
//
//        public getCongressZIP(Context context) {
//            this.context = context;
//        }
//
//
//        private StringBuilder readResponse(InputStream inputStream) throws IOException, NullPointerException {
//            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
//            StringBuilder stringBuilder = new StringBuilder();
//            String line;
//            while ((line = r.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//            return stringBuilder;
//        }
//
//
//        @Override
//        protected Object doInBackground(Object[] params) {
//            if (params[0] != null) {
//                String result = "";
//                try {
//                    String mUrl = URL + params[0] + KEY;
//
//                    URL url = new URL(mUrl);
//                    HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
//                    httpsURLConnection.setReadTimeout(20000);//changed from 10000
//                    httpsURLConnection.setConnectTimeout(30000); //changed from 15000
//                    httpsURLConnection.setDoInput(true);
//                    httpsURLConnection.setRequestMethod("GET");
//                    httpsURLConnection.connect();
//                    int mStatus = httpsURLConnection.getResponseCode();
//                    if (mStatus == 200)
//                        return readResponse(httpsURLConnection.getInputStream()).toString();
//
//                    return result;
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
//            }
//            return null;
//
//
//        }
//
//    }
//
//
//    //GET Bills
//    public class getBills extends AsyncTask {
//        Context context;
//        private Address address;
//        private String KEY = "&apikey=ed077de7534a4568bfab0e8bad0c58d5";
//        private String URL = "https://congress.api.sunlightfoundation.com/bills?sponsor_id=";
//
//        public getBills(Context context) {
//            this.context = context;
//        }
//
//
//        private StringBuilder readResponse(InputStream inputStream) throws IOException, NullPointerException {
//            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
//            StringBuilder stringBuilder = new StringBuilder();
//            String line;
//            while ((line = r.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//            return stringBuilder;
//        }
//
//
//        @Override
//        protected Object doInBackground(Object[] params) {
//            if (params[0] != null) {
//                String result = "";
//                try {
//                    String mUrl = URL + params[0] + KEY;
//
//                    URL url = new URL(mUrl);
//                    HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
//                    httpsURLConnection.setReadTimeout(10000);
//                    httpsURLConnection.setConnectTimeout(15000);
//                    httpsURLConnection.setDoInput(true);
//                    httpsURLConnection.setRequestMethod("GET");
//                    httpsURLConnection.connect();
//                    int mStatus = httpsURLConnection.getResponseCode();
//                    if (mStatus == 200)
//                        return readResponse(httpsURLConnection.getInputStream()).toString();
//
//                    return result;
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
//            }
//            return null;
//
//
//        }
//
//
//    }
//
//
//    //GET Committies
//    public class getCommitties extends AsyncTask {
//        Context context;
//        private Address address;
//        private String KEY = "&apikey=ed077de7534a4568bfab0e8bad0c58d5";
//        private String URL = "https://congress.api.sunlightfoundation.com/committees?member_ids=";
//
//        public getCommitties(Context context) {
//            this.context = context;
//        }
//
//
//        private StringBuilder readResponse(InputStream inputStream) throws IOException, NullPointerException {
//            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
//            StringBuilder stringBuilder = new StringBuilder();
//            String line;
//            while ((line = r.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//            return stringBuilder;
//        }
//
//
//        @Override
//        protected Object doInBackground(Object[] params) {
//            if (params[0] != null) {
//                String result = "";
//                try {
//                    String mUrl = URL + params[0] + KEY;
//
//                    URL url = new URL(mUrl);
//                    HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
//                    httpsURLConnection.setReadTimeout(10000);
//                    httpsURLConnection.setConnectTimeout(15000);
//                    httpsURLConnection.setDoInput(true);
//                    httpsURLConnection.setRequestMethod("GET");
//                    httpsURLConnection.connect();
//                    int mStatus = httpsURLConnection.getResponseCode();
//                    if (mStatus == 200)
//                        return readResponse(httpsURLConnection.getInputStream()).toString();
//
//                    return result;
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
//            }
//            return null;
//
//
//        }
//
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        //TAKE ME TO GENERAL VIEW

        if (extras != null) {
            String m = (String) extras.get("m");

            Intent myIntent = new Intent(this, GeneralView.class);
            myIntent.putExtra("m", m);
            startActivity(myIntent);

        }



        System.out.println("I'm in main Activity");

        locateMe = (ImageButton) findViewById(R.id.locateMe);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
//        Location location = locationManager.getLastKnownLocation(provider);


        locateMe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                getLocation(v);
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET

                }, 10);
                return;

            }
        }


        final EditText zc = (EditText) findViewById(R.id.zipCode);
        zc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (zc.getText().length() != 5) {
//                        InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                        v.clearFocus();

                        Toast.makeText(getApplicationContext(), "Zip Code is 5 digits, you have only entered " + zc.getText().length(), Toast.LENGTH_LONG).show();

                    } else {

                        System.out.println("ZIP CODE IS : " + Integer.parseInt(zc.getText().toString()));

//
//                        progress = ProgressDialog.show(MainActivity.this, "dialog title",
//                                "dialog message", true);

//                        prepareZIPData(Integer.parseInt(zc.getText().toString()));

//                        progress.dismiss();


                        //Go to next Mobile Screen
                        Intent myIntent = new Intent(getBaseContext(), GeneralView.class);
                        myIntent.putExtra("ZIP", Integer.parseInt(zc.getText().toString()));
                        startActivity(myIntent);


                        //TAKE ME TO GENERAL VIEW
//                        //GO TO WATCH
//                        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
//                        sendIntent.putExtra("header", listDataHeader);
//                        startService(sendIntent);

                    }


                }
                return handled;
            }
        });




    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locateMe.performClick();
                }
                return;
        }
    }


    //LOCATION STUFF


    @Override
    protected void onResume() {
        super.onResume();

        locationManager.requestLocationUpdates(provider, 400, 1, this);

    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);

    }


    @Override
    public void onLocationChanged(Location location) {

        if (location != null) {
            Double lat = location.getLatitude();
            Double lng = location.getLongitude();

            Log.i("Location info: Lat", lat.toString());
            Log.i("Location info: Lng", lng.toString());
        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

//        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//        startActivity(intent);
//
//        return;

    }


    public void getLocation(View view) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET

                }, 10);


                return;
            }
        }


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        Location location = locationManager.getLastKnownLocation(provider);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // Build the alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location Services and GPS");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        } else {

            onLocationChanged(location);

            Double lat = location.getLatitude();
            Double lng = location.getLongitude();

            this.lat = lat;
            this.lng = lng;


//            prepareGPSData(lat, lng);


            //Go to next Mobile Screen
            Intent myIntent = new Intent(getBaseContext(), GeneralView.class);
            myIntent.putExtra("LAT", this.lat);
            myIntent.putExtra("LNG", this.lng);

            startActivity(myIntent);


            //TAKE ME AWAY TO GENERALVIEW
//            //GO TO WATCH
//            Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
//            sendIntent.putExtra("header", listDataHeader);
//            startService(sendIntent);
        }

    }

//    private void prepareGPSData(Double lat, Double lng) {
//
//        listDataHeader = new ArrayList<Official>();
//
//        //Get State and County
//        ReverseGecoding rg = new ReverseGecoding(getBaseContext());
//        String rgResult = "NONE";
//        String State = "NONE";
//        String County = "NONE";
//        try {
//            rgResult = (String) rg.execute(lat, lng).get();
//
//            if (rgResult != null) {
//                JSONObject jsonObject = new JSONObject(rgResult);
//                if (jsonObject.getString("status").equals("OK")) {
//                    jsonObject = jsonObject.getJSONArray("results")
//                            .getJSONObject(0);
//
//                    JSONArray address_components = jsonObject.getJSONArray("address_components");
//
//                    for (int i = 0; i < address_components.length(); i++) {
//                        JSONObject zero2 = address_components.getJSONObject(i);
//                        String long_name = zero2.getString("long_name");
//                        JSONArray mtypes = zero2.getJSONArray("types");
//                        String Type = mtypes.getString(0);
//
//                        if (TextUtils.isEmpty(long_name) == false || !long_name.equals(null) || long_name.length() > 0 || long_name != "") {
//                            if (Type.equalsIgnoreCase("administrative_area_level_2")) {
//                                County = long_name;
//                            } else if (Type.equalsIgnoreCase("administrative_area_level_1")) {
//                                State = long_name;
//                            }
//                        }
//                    }
//                }
//            }
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        //Get Congress Info
//        getCongress gc = new getCongress(getBaseContext());
//        String result = "NONE";
//
//        try {
//            result = (String) gc.execute(lat, lng).get();
//
//            if (result != null) {
//
//                JSONObject jsonObject = new JSONObject(result);
//                int count = jsonObject.getInt("count");
//
//                for (int i = 0; i < count; i++) {
//
//                    Official of = new Official();
//
//                    JSONObject temp = jsonObject.getJSONArray("results").getJSONObject(i);
//
//                    of.setName(temp.getString("first_name") + " " + temp.getString("last_name"));
//                    of.setNumber(i);
//
//                    if (Objects.equals(temp.getString("party"), "D")) {
//                        of.setParty("Democrat");
//                    } else if (Objects.equals(temp.getString("party"), "R")) {
//                        of.setParty("Republican");
//                    } else {
//                        of.setParty("Independent");
//                    }
//                    of.setEmail(temp.getString("oc_email"));
//                    of.setPosition(temp.getString("title") + ".");
//                    of.setTwitter("https://twitter.com/" + temp.getString("twitter_id"));
//                    of.setWeb(temp.getString("website"));
//
//                    //Get Tweet and Assign it
//                    try {
//                        Long id = (Long) new getTweets().execute(temp.getString("twitter_id")).get();
//                        if (id != null) {
//                            Long tweetID = id;
//                            of.setTweet(tweetID);
//                            of.setImage(this.profileImage);
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    }
//
//                    //Set Committies
//
//                    getCommitties getCommitties = new getCommitties(getBaseContext());
//                    result = (String) getCommitties.execute(temp.getString("bioguide_id")).get();
//
//                    if (result != null) {
//
//                        JSONObject jsonCom = new JSONObject(result);
//                        int comCount = jsonCom.getInt("count");
//
//                        ArrayList<String> comList = new ArrayList<String>();
//
//                        for (int n = 0; n < comCount; n++) {
//                            JSONObject tempCom = jsonCom.getJSONArray("results").getJSONObject(n);
//                            comList.add(tempCom.getString("name"));
//                        }
//
//                        of.setComList(comList);
//                    }
//
//                    //Set Bills
//                    getBills getBills = new getBills(getBaseContext());
//                    result = (String) getBills.execute(temp.getString("bioguide_id")).get();
//
//                    if (result != null) {
//
//                        JSONObject jsonBill = new JSONObject(result);
//                        int bCount = jsonBill.getInt("count");
//
//                        int getCountOf = 5;
//
//                        if (bCount < 5) {
//                            getCountOf = bCount;
//                        }
//
//                        ArrayList<String> bList = new ArrayList<String>();
//
//                        for (int n = 0; n < getCountOf; n++) {
//                            JSONObject tempBill = jsonBill.getJSONArray("results").getJSONObject(n);
//
//                            if (tempBill.getString("short_title") != null) {
//                                bList.add(tempBill.getString("short_title"));
//                            } else {
//                                bList.add(tempBill.getString("official_title"));
//                            }
//                        }
//
//                        of.setbList(bList);
//                    }
//
//                    listDataHeader.add(i, of);
//                }
//
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//
//    private void prepareZIPData(int zip) {
//
//        listDataHeader = new ArrayList<Official>();
//
//        //Get Congress Info
//        getCongressZIP gc = new getCongressZIP(getBaseContext());
//        String result = "NONE";
//
//        try {
//            result = (String) gc.execute(zip).get();
//
//            if (result != null) {
//
//                JSONObject jsonObject = new JSONObject(result);
//                int count = jsonObject.getInt("count");
//
//                for (int i = 0; i < count; i++) {
//
//                    Official of = new Official();
//
//                    JSONObject temp = jsonObject.getJSONArray("results").getJSONObject(i);
//
//                    of.setName(temp.getString("first_name") + " " + temp.getString("last_name"));
//                    of.setNumber(i);
//
//                    if (Objects.equals(temp.getString("party"), "D")) {
//                        of.setParty("Democrat");
//                    } else if (Objects.equals(temp.getString("party"), "R")) {
//                        of.setParty("Republican");
//                    } else {
//                        of.setParty("Independent");
//                    }
//                    of.setEmail(temp.getString("oc_email"));
//                    of.setPosition(temp.getString("title") + ".");
//                    of.setTwitter("https://twitter.com/" + temp.getString("twitter_id"));
//                    of.setWeb(temp.getString("website"));
//
//                    //Get Tweet and Assign it
//                    try {
//                        Long id = (Long) new getTweets().execute(temp.getString("twitter_id")).get();
//                        if (id != null) {
//                            Long tweetID = id;
//                            of.setTweet(tweetID);
//                            of.setImage(this.profileImage);
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    }
//
//                    //Set Committies
//
//                    getCommitties getCommitties = new getCommitties(getBaseContext());
//                    result = (String) getCommitties.execute(temp.getString("bioguide_id")).get();
//
//                    if (result != null) {
//
//                        JSONObject jsonCom = new JSONObject(result);
//                        int comCount = jsonCom.getInt("count");
//
//                        ArrayList<String> comList = new ArrayList<String>();
//
//                        for (int n = 0; n < comCount; n++) {
//                            JSONObject tempCom = jsonCom.getJSONArray("results").getJSONObject(n);
//                            comList.add(tempCom.getString("name"));
//                        }
//
//                        of.setComList(comList);
//                    }
//
//                    //Set Bills
//                    getBills getBills = new getBills(getBaseContext());
//                    result = (String) getBills.execute(temp.getString("bioguide_id")).get();
//
//                    if (result != null) {
//
//                        JSONObject jsonBill = new JSONObject(result);
//                        int bCount = jsonBill.getInt("count");
//
//                        int getCountOf = 5;
//
//                        if (bCount < 5) {
//                            getCountOf = bCount;
//                        }
//
//                        ArrayList<String> bList = new ArrayList<String>();
//
//                        for (int n = 0; n < getCountOf; n++) {
//                            JSONObject tempBill = jsonBill.getJSONArray("results").getJSONObject(n);
//
//                            if (tempBill.getString("short_title") != null) {
//                                bList.add(tempBill.getString("short_title"));
//                            } else {
//                                bList.add(tempBill.getString("official_title"));
//                            }
//                        }
//
//                        of.setbList(bList);
//                    }
//
//                    listDataHeader.add(i, of);
//                }
//
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }


}