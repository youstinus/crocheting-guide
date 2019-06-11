package com.youstinus.funnychrocket.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youstinus.funnychrocket.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SchemesListAdapter extends RecyclerView.Adapter<SchemesListAdapter.SchemesListViewHolder> {

    //private ArrayList<String> mDataset;
    private ArrayList<String> mDataset = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class SchemesListViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView image;
        TextView textView;
        RelativeLayout parentLayout;

        public SchemesListViewHolder(View itemView) {
            super(itemView);
            image =  itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SchemesListAdapter(ArrayList<String> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SchemesListAdapter.SchemesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        SchemesListViewHolder holder = new SchemesListViewHolder(v);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SchemesListViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset.get(position));
        Log.d(TAG, "binded");

        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mDataset.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
