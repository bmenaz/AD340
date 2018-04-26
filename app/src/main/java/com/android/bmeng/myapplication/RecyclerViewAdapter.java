package com.android.bmeng.myapplication;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private String[][] mDataset;
    private Context context;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public android.widget.TextView mTextView1;
        public android.widget.TextView mTextView2;
        Context context;
        int position;
        String[][] myDataset;

        public ViewHolder(View v, Context context, String[][] myDataset) {
            super(v);
            mTextView1 = v.findViewById(R.id.textView1);
            mTextView2 = v.findViewById(R.id.textView2);
            itemView.setOnClickListener(this);
            position = 0;
            this.context = context;
            this.myDataset = myDataset;

        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, DetailScreen.class);
            String[] info = myDataset[position];
            intent.putExtra("info", info);
            context.startActivity(intent);
        }
        public void setPosition(int position){
            this.position = position;
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(String[][] myDataset, Context context){
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(android.view.ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = android.view.LayoutInflater.from(context).inflate(R.layout.one_row, parent, false);
        ViewHolder vh = new ViewHolder(v, context, mDataset);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.setPosition(position);
        holder.mTextView1.setText(mDataset[position][0]);
        holder.mTextView2.setText(mDataset[position][1]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}