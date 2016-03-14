package com.unisbarakat.represent;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by unisbarakat on 2/26/16.
 */
public class PhoneListenerService extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private static final String message = "/m";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {


        System.out.println("I'm in phone Listener");

        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if( messageEvent.getPath().equalsIgnoreCase(message) ) {


            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service


            System.out.println("I'm DEEEEEEP in phone Listener");

            intent.putExtra("m", value);
            startActivity(intent);



        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}
