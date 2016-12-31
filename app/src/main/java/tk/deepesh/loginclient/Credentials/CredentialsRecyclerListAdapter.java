package tk.deepesh.loginclient.Credentials;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tk.deepesh.helper.ItemTouchHelperAdapter;
import tk.deepesh.helper.ItemTouchHelperViewHolder;
import tk.deepesh.loginclient.R;

/**
 * Simple RecyclerView.Adapter that implements {@link ItemTouchHelperAdapter} to respond to move and
 * dismiss events from a {@link android.support.v7.widget.helper.ItemTouchHelper}.
 *
 * @author Paul Burke (ipaulpro)
 */
public class CredentialsRecyclerListAdapter extends RecyclerView.Adapter<CredentialsRecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {


    private final List<List<String>> params;
    private final OnStartDragListener mDragStartListener;
    List<List<String>> params2 = new ArrayList<>();
    SharedPreferences prefs1;
    SharedPreferences.Editor editor1;
    private Context context;

    public CredentialsRecyclerListAdapter(OnStartDragListener dragStartListener, List<List<String>> params, Context context) {
        mDragStartListener = dragStartListener;
        this.context = context;
        this.params = new ArrayList<>(params);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credentials, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.text_credentials_username.setText(params.get(position).get(0));
        holder.text_credentials_password.setText(params.get(position).get(1));
        // Start a drag whenever the handle view it touched
        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });

        prefs1 = context.getSharedPreferences("credentials", Context.MODE_PRIVATE);
        editor1 = prefs1.edit();
    }

    @Override
    public void onItemDismiss(final int position, RecyclerView rv) {

        final List<String> removedItem = new ArrayList<>(Arrays.asList(params.get(position).get(0), params.get(position).get(1)));

        params.remove(position);
        notifyItemRemoved(position);

        Snackbar snackbar = Snackbar
                .make(rv, "Removed Credential", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        params.add(position, removedItem);
                        notifyDataSetChanged();

//                        for (int i = prefs1.getAll().size() - 1; i <= position; i--) {
//                            Set<String> restoredText = prefs1.getStringSet(Integer.toString(i), null);
//                            editor1.putStringSet(Integer.toString(i+1), restoredText);
//                        }
//
//                        editor1.putStringSet(Integer.toString(position), new HashSet<>(removedItem));
                    }
                });
        snackbar.show();

        editor1.remove(Integer.toString(position));

        for (int i = position; i < prefs1.getAll().size() - 1; i++) {
            Set<String> restoredText = prefs1.getStringSet(Integer.toString(i + 1), null);
            editor1.putStringSet(Integer.toString(i), restoredText);
        }
        editor1.remove(Integer.toString(prefs1.getAll().size() - 1));

        editor1.apply();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(params, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);

        editor1.putStringSet(Integer.toString(fromPosition), new HashSet<>(params.get(fromPosition)));
        editor1.apply();
        editor1.putStringSet(Integer.toString(toPosition), new HashSet<>(params.get(toPosition)));
        editor1.apply();
    }

    @Override
    public int getItemCount() {
        return params.size();
    }

    /**
     * Listener for manual initiation of a drag.
     */
    public interface OnStartDragListener {

        /**
         * Called when a view is requesting a start of a drag.
         *
         * @param viewHolder The holder of the view to drag.
         */
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    /**
     * Simple example of a view holder that implements {@link ItemTouchHelperViewHolder} and has a
     * "handle" view that initiates a drag event when touched.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        public final TextView text_credentials_username, text_credentials_password;
        public final ImageView handleView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            text_credentials_username = (TextView) itemView.findViewById(R.id.text_credentials_username);
            text_credentials_password = (TextView) itemView.findViewById(R.id.text_credentials_password);
            handleView = (ImageView) itemView.findViewById(R.id.handle);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(0xFF222326);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}