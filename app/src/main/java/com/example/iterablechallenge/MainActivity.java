package com.example.iterablechallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.iterable.iterableapi.IterableApi;
import com.iterable.iterableapi.IterableConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Iterable SDK
        IterableConfig config = new IterableConfig.Builder().build();
        IterableApi.initialize(this, getString(R.string.api_key), config);
        IterableApi.getInstance().setEmail(getString(R.string.my_email));
        //IterableApi.getInstance().registerForPush();

        Button updateProfileButton = findViewById(R.id.button_update_profile);
        Button sendEventButton = findViewById(R.id.button_send_event);

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });

        sendEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEvent();
            }
        });
    }

    //Update user profile
    public void updateProfile() {
        JSONObject datafields = new JSONObject();

        try {
            datafields.put("firstName", getString(R.string.my_name));
            datafields.put("isRegisteredUser", true);
            datafields.put("SA_User_Test_Key", "completed");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IterableApi.getInstance().updateUser(datafields);
    }

    //Send a custom event to Iterable
    public void sendEvent() {
        JSONObject datafields = new JSONObject();

        try {
            datafields.put("platform", getString(R.string.platform));
            datafields.put("isTestEvent", true);
            datafields.put("url", getString(R.string.platform) + getString(R.string.my_name).toLowerCase());
            datafields.put("secret_code_key", getString(R.string.secret_code));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IterableApi.getInstance().track(getString(R.string.event_name), datafields);
    }
}