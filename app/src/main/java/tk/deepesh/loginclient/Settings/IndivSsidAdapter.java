package tk.deepesh.loginclient.Settings;

/**
 * Created by deepesh on 30/12/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tk.deepesh.loginclient.R;

public class IndivSsidAdapter extends RecyclerView.Adapter<IndivSsidAdapter.ViewHolder> {
    private ArrayList<String> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public IndivSsidAdapter(ArrayList<String> myDataset) {
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
    public IndivSsidAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_indiv_ssid, parent, false);
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
        holder.text_indiv_ssid_url_value.setText(mDataset.get(position));
        holder.text_indiv_ssid_param1.setText(mDataset.get(position));
        holder.text_indiv_ssid_param2.setText(mDataset.get(position));
        holder.text_indiv_ssid_param3.setText(mDataset.get(position));
        holder.text_indiv_ssid_param4.setText(mDataset.get(position));
        holder.text_indiv_ssid_param5.setText(mDataset.get(position) + " : " + mDataset.get(position));
//        holder.text_indiv_ssid_param.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                remove(name);
//            }
//        });

//        holder.text_indiv_ssid_param.setText("Footer: " + mDataset.get(position));

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
        public TextView text_indiv_ssid_url_value, text_indiv_ssid_param1, text_indiv_ssid_param2, text_indiv_ssid_param3, text_indiv_ssid_param4, text_indiv_ssid_param5;

        public ViewHolder(View v) {
            super(v);
            text_indiv_ssid_url_value = (TextView) v.findViewById(R.id.text_indiv_ssid_url_value);
            text_indiv_ssid_param1 = (TextView) v.findViewById(R.id.text_indiv_ssid_param1);
            text_indiv_ssid_param2 = (TextView) v.findViewById(R.id.text_indiv_ssid_param2);
            text_indiv_ssid_param3 = (TextView) v.findViewById(R.id.text_indiv_ssid_param3);
            text_indiv_ssid_param4 = (TextView) v.findViewById(R.id.text_indiv_ssid_param4);
            text_indiv_ssid_param5 = (TextView) v.findViewById(R.id.text_indiv_ssid_param5);
        }
    }

}