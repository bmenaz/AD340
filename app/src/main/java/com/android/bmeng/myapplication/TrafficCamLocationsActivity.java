package com.android.bmeng.myapplication;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrafficCamLocationsActivity extends AppCompatActivity {

    JSONObject jsonData;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewTrafficCamAdapter adapter;
    ArrayList<Camera> cameraList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_cam_locations);

        recyclerView = findViewById(R.id.my_recycler_view);
        layoutManager = new android.support.v7.widget.LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewTrafficCamAdapter(cameraList, this);
        recyclerView.setAdapter(adapter);

        //fetch the data and pass it in adapter
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        String url = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        jsonData = response;
                        //Log.d("JSON Value", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("JSON Error", error.getMessage());
                    }
                });

        mRequestQueue.add(jsonObjectRequest);

        if(jsonData == null){
            Log.d("JSON", "JSON is empty");
        }

        Toast.makeText(getApplicationContext(), "Connection status" + getActiveNetworkInfo().getState().toString(),
                Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        /*JSONArray features;
        try {
            features = jsonData.getJSONArray("Features");
            for(int i = 0; i < features.length(); i ++){
                JSONObject feature = features.getJSONObject(i);
                JSONArray cameras = feature.getJSONArray("Cameras");
                for(int j = 0; j < cameras.length(); j ++){
                    JSONObject camera = cameras.getJSONObject(j);
                    cameraList.add(new Camera(camera.getString("Description"), camera.getString("ImageUrl"),
                            camera.getString("Type")));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();*/
    }

    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

}
