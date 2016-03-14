package com.unisbarakat.represent;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;

/**
 * Created by unisbarakat on 2/26/16.
 */
public class PhoneToWatchService extends Service {

    private GoogleApiClient mApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize the googleAPIClient for message passing
        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                    }
                })
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mApiClient.disconnect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Which cat do we want to feed? Grab this info from INTENT
        // which was passed over when we called startService


        if (intent != null) {
            Bundle extras = intent.getExtras();

            final ArrayList<Official> header = (ArrayList<Official>) extras.get("header");


            final String state = extras.getString("State");
            final String county = extras.getString("County");

            final String Ocounty = extras.getString("Ocounty");
            final String Rcounty = extras.getString("Rcounty");



            // Send the message with the cat name
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //first, connect to the apiclient
                    mApiClient.connect();


                    if (header != null) {
                        sendMessage("/size", Integer.toString(header.size()));


//                    for (int i = 0; i < header.size(); i++){
//                        String temp1 = "/" + i + "name";
//                        sendMessage(temp1, header.get(i).position + ". " + header.get(i).name );
//                        String temp2 = "/" + i + "party";
//                        sendMessage(temp2, header.get(i).party );
//                    }


                        String data = "";

                        for (int i = 0; i < header.size(); i++) {

                            if (i == header.size() - 1) {
                                data += header.get(i).position + " " + header.get(i).name + "-" + header.get(i).party;
                            } else {

                                data += header.get(i).position + " " + header.get(i).name + "-" + header.get(i).party + ",";
                            }

                        }


                        sendMessage("/data", data);

                        if (state != null) {
                            sendMessage("/State", state);
                        }

                        if (county != null) {
                            sendMessage("/county", county);
                        }

                        if (Ocounty != null) {
                            sendMessage("/Ocounty", Ocounty);
                        }

                        if (Rcounty != null) {
                            sendMessage("/Rcounty", Rcounty);
                        }



                    }






                }
            }).start();
        }
        return START_STICKY;
    }

    @Override //remember, all services need to implement an IBiner
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendMessage( final String path, final String name ) {
        //one way to send message: start a new thread and call .await()
        //see watchtophoneservice for another way to send a message
        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mApiClient ).await();
                for(Node node : nodes.getNodes()) {
                    //we find 'nodes', which are nearby bluetooth devices (aka emulators)
                    //send a message for each of these nodes (just one, for an emulator)



                      MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(mApiClient,
                              node.getId(), path, name.getBytes()).await();

//                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
//                            mApiClient, node.getId(), path, head ).await();
                    //4 arguments: api client, the node ID, the path (for the listener to parse),
                    //and the message itself (you need to convert it to bytes.)
                }
            }
        }).start();
    }

}
