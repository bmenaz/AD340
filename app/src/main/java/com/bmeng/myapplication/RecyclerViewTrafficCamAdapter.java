package com.bmeng.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class RecyclerViewTrafficCamAdapter extends RecyclerView.Adapter<RecyclerViewTrafficCamAdapter.ViewHolder> {


    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    private Context context;
    private ArrayList<Camera> cameraList;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public NetworkImageView image;
        public android.widget.TextView mTextView;



        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.textView);
            image = v.findViewById(R.id.image);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewTrafficCamAdapter(ArrayList<Camera> cameraList, Context context){
        this.cameraList = cameraList;
        this.context = context;

        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewTrafficCamAdapter.ViewHolder onCreateViewHolder(android.view.ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View v = android.view.LayoutInflater.from(context).inflate(R.layout.traffic_cam_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerViewTrafficCamAdapter.ViewHolder holder, int position) {
        Camera camera = cameraList.get(position);
        holder.mTextView.setText(camera.description);

        if(camera.type.equals("sdot")){
            holder.image.setImageUrl("http://www.seattle.gov/trafficcams/images/" + camera.url, mImageLoader);;
        }
        else{
            holder.image.setImageUrl("http://images.wsdot.wa.gov/nw/" + camera.url, mImageLoader);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cameraList.size();
    }
}