package com.example.firebase_push_notification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private RequestQueue mRequestQue;

    //in firebase go to ur project and search "Firebase Cloud Messaging HTTP protocol"
    private String URL = "https://fcm.googleapis.com/fcm/send";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        button = findViewById(R.id.button);
        mRequestQue = Volley.newRequestQueue(this);



        //device subscribe to this topic will be notified
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
    }

    private void sendNotification() {

        //creating new main json object
        JSONObject mainOBJ = new JSONObject();
        try {

            //add main/parent/root name
            mainOBJ.put("to","/topics/"+"news");



            //below content is sent to other device
            JSONObject notificationOBJ = new JSONObject();
            notificationOBJ.put("title","any title");
            notificationOBJ.put("body","any .. body");

            mainOBJ.put("notification",notificationOBJ);


            //json request
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    mainOBJ,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("test","yes");
                            // success run here

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("test","nope");

                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAA_OAtefo:APA91bGCe37WmoO3T25FzOYU-rsycuztyyZ9diSW4lKQh7nmcLCG84_Y0nrbqNReiAdiV6zavx-UwE8MEuknQ8SjcNIQdoXvvQIrcW2djZZzyahdTuBdKeVqpXypMa8Wrs8JKFurc8BO");
                    return header;
                }
            };

            mRequestQue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
