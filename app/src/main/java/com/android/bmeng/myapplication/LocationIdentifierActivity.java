package com.android.bmeng.myapplication;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LocationIdentifierActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private Location myLocation;
    private GoogleApiClient mGoogleApiClient;
    private ArrayList<Camera> cameraList;
    private TrafficCamLocationsActivity.VolleyCallback volleyCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_identifier);
        cameraList = new ArrayList<>();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        //fetch the data and pass it in adapter
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        String url = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        volleyCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("JSON Error", error.getMessage());
                    }
                });

        mRequestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        volleyCallback = new TrafficCamLocationsActivity.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                JSONArray features;
                try {
                    features = result.getJSONArray("Features");
                    for (int i = 0; i < features.length(); i++) {
                        JSONObject feature = features.getJSONObject(i);
                        JSONArray cameraPostions = feature.getJSONArray("PointCoordinate");
                        LatLng latLng =  new LatLng(cameraPostions.getDouble(0), cameraPostions.getDouble(1));
                        JSONArray cameras = feature.getJSONArray("Cameras");
                        for (int j = 0; j < cameras.length(); j++) {
                            JSONObject camera = cameras.getJSONObject(j);
                            cameraList.add(new Camera(camera.getString("Description"), camera.getString("ImageUrl"),
                                    camera.getString("Type"), latLng));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mGoogleApiClient.connect();
            }
        };
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        myLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        //only when api is connected generate the map
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getApplicationContext(),"Connection to map suspended", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "Connection is failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng myLocationLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocationLatLng, 14));
        googleMap.addMarker(new MarkerOptions().position(myLocationLatLng).title("Current Location"));

        for(int i = 0; i < cameraList.size(); i++){
            googleMap.addMarker(new MarkerOptions().position(cameraList.get(i).latLng).title(cameraList.get(i).description)).setZIndex(i);
        }

        final MyInfoWindowAdapter myInfoWindowAdapter = new MyInfoWindowAdapter(this, cameraList);
        googleMap.setInfoWindowAdapter(myInfoWindowAdapter);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });
    }

}
