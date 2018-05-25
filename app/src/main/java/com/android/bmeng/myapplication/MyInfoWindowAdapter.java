package com.android.bmeng.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    ArrayList<Camera> cameras;
    Context context;
    View markerInfoWindow;
    public MyInfoWindowAdapter(Context context, ArrayList cameras){
        this.cameras = cameras;
        this.context = context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        markerInfoWindow = layoutInflater.inflate(R.layout.info_window_layout, null);

    }
    @Override
    public View getInfoWindow(Marker marker) {
        loadView(marker, markerInfoWindow);
        return markerInfoWindow;
    }

    private void loadView(final Marker marker, View markerInfoWindow) {
        ImageView imageView = markerInfoWindow.findViewById(R.id.imageView);
        TextView textView = markerInfoWindow.findViewById(R.id.textView);
        int zIndex = (int)marker.getZIndex();
        textView.setText(cameras.get(zIndex).description);
        Camera camera = cameras.get(zIndex);

        String imageUrl;

        if(camera.type.equals("sdot")){
            imageUrl = ("http://www.seattle.gov/trafficcams/images/" + camera.url);
        }
        else{
            imageUrl = ("http://images.wsdot.wa.gov/nw/" + camera.url);
        }

        Picasso.get()
                .load(imageUrl)
                .into(imageView, new MarkerCallback(marker));



    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    public class MarkerCallback implements Callback {
        Marker marker;

        MarkerCallback(Marker marker) {
            this.marker=marker;
        }

        @Override
        public void onError(Exception e) {
            Log.d(getClass().getSimpleName(), "Error loading thumbnail!");
        }

        @Override
        public void onSuccess() {
            if (marker != null && marker.isInfoWindowShown()) {
                marker.hideInfoWindow();
                marker.showInfoWindow();
            }
        }
    }
}
