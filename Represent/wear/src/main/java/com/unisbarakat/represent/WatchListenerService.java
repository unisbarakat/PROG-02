package com.unisbarakat.represent;

import android.content.Intent;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by unisbarakat on 2/26/16.
 */
public class WatchListenerService extends WearableListenerService {
    // In PhoneToWatchService, we passed in a path, either "/FRED" or "/LEXY"
    // These paths serve to differentiate different phone-to-watch messages
//    private static final String FRED_FEED = "/Fred";
//    private static final String LEXY_FEED = "/Lexy";

    private static final String size = "/size";

    public int count;

    public ArrayList<Person> people;

    public  String state = " ";
    public String county = " ";
    public String Ocounty = " ";
    public String Rcounty = " ";




    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        people = new ArrayList<Person>();



        if (messageEvent.getPath().equalsIgnoreCase(size)) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);

            this.count = Integer.parseInt(value);

        }

        if (messageEvent.getPath().equalsIgnoreCase("/data")) {
            String data = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            String[] parts = data.split(",");

            for (int i = 0; i < count; i++) {
                String[] temp = parts[i].split("-");
                String name = temp[0];
                String party = temp[1];

                Person p = new Person(name, party);
                people.add(i, p);



            }



        }

        if (messageEvent.getPath().equalsIgnoreCase("/County")) {
            this.county = new String(messageEvent.getData(), StandardCharsets.UTF_8);

        }

        if (messageEvent.getPath().equalsIgnoreCase("/State")) {
            this.state = new String(messageEvent.getData(), StandardCharsets.UTF_8);

        }

        if (messageEvent.getPath().equalsIgnoreCase("/Ocounty")) {
            this.Ocounty = new String(messageEvent.getData(), StandardCharsets.UTF_8);

        }

        if (messageEvent.getPath().equalsIgnoreCase("/Rcounty")) {
            this.Rcounty = new String(messageEvent.getData(), StandardCharsets.UTF_8);

        }


//        String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra("people", people);
        intent.putExtra("county", county);
        intent.putExtra("state", state);
        intent.putExtra("Ocounty", Ocounty);
        intent.putExtra("Rcounty", Rcounty);


        startActivity(intent);
//    } else {
//        super.onMessageReceived( messageEvent );
//    }









//
//        if(messageEvent.getPath().equalsIgnoreCase("/0")){
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //you need to add this flag since you're starting a new activity from a service
//
//
//            String string = value;
//            String[] parts = string.split(",");
//            String part1 = parts[0];
//            String part2 = parts[1];
//            String part3 = parts[2];
//
//
//            intent.putExtra("0", part1);
//            intent.putExtra("1", part2);
//            intent.putExtra("2", part3);
//            startActivity(intent);
//        }
// else if(messageEvent.getPath().equalsIgnoreCase("/1")){
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //you need to add this flag since you're starting a new activity from a service
//            intent.putExtra("1", value);
//            startActivity(intent);
//
//        }else if(messageEvent.getPath().equalsIgnoreCase("/2")){
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //you need to add this flag since you're starting a new activity from a service
//            intent.putExtra("2", value);
//            startActivity(intent);
//
//        }
//
//        if( messageEvent.getPath().equalsIgnoreCase( head ) ) {
//
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //you need to add this flag since you're starting a new activity from a service
//            intent.putExtra("temp", "IT FUCKING WORKED");
//            Log.d("T", "about to start watch MainActivity with temp: IT FUCKING WORKED");
//            startActivity(intent);
//        } else if (messageEvent.getPath().equalsIgnoreCase( LEXY_FEED )) {
////            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
////            Intent intent = new Intent(this, MainActivity.class );
////            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
////            //you need to add this flag since you're starting a new activity from a service
////            intent.putExtra("CAT_NAME", "Lexy");
////            Log.d("T", "about to start watch MainActivity with CAT_NAME: Lexy");
////            startActivity(intent);

//

    }






}

