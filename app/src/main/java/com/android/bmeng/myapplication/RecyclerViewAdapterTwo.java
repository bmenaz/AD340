package com.android.bmeng.myapplication;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewAdapterTwo extends RecyclerView.Adapter<RecyclerViewAdapterTwo.ViewHolder> {


    String[] mDataset;
    private android.content.Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public android.widget.TextView mTextView;



        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.textView);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapterTwo(String[] myDataset, Context context){
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapterTwo.ViewHolder onCreateViewHolder(android.view.ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = android.view.LayoutInflater.from(context).inflate(R.layout.one_row_single, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(!mDataset[position].contains("http")){
            holder.mTextView.setText(mDataset[position]);
        }



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}