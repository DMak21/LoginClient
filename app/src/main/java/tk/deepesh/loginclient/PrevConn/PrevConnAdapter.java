package tk.deepesh.loginclient.PrevConn;

/**
 * Created by deepesh on 30/12/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tk.deepesh.loginclient.R;

public class PrevConnAdapter extends RecyclerView.Adapter<PrevConnAdapter.ViewHolder> {
    private ArrayList<String> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public PrevConnAdapter(ArrayList<String> myDataset) {
        mDataset = myDataset;
    }

    public void add(int position, String item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PrevConnAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prev_conn, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = mDataset.get(position);
        holder.text_prev_conn_wifi_name.setText(mDataset.get(position));
        holder.text_prev_conn_time.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(name);
            }
        });

        holder.text_prev_conn_time.setText("22 hrs");

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView text_prev_conn_wifi_name;
        public TextView text_prev_conn_time;

        public ViewHolder(View v) {
            super(v);
            text_prev_conn_wifi_name = (TextView) v.findViewById(R.id.text_prev_conn_wifi_name);
            text_prev_conn_time = (TextView) v.findViewById(R.id.text_prev_conn_time);
        }
    }

}