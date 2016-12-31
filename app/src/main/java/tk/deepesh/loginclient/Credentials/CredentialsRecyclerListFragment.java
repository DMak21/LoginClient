package tk.deepesh.loginclient.Credentials;

/**
 * Created by deepesh on 20/12/16.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tk.deepesh.helper.SimpleItemTouchHelperCallback;
import tk.deepesh.loginclient.R;

/**
 * @author Paul Burke (ipaulpro)
 */
public class CredentialsRecyclerListFragment extends Fragment implements CredentialsRecyclerListAdapter.OnStartDragListener {

    private ItemTouchHelper mItemTouchHelper;

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_credentials, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs1 = getContext().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = prefs1.edit();

        List<List<String>> params = new ArrayList<>();

        for (int i = 0; i < prefs1.getAll().size(); i++) {
            Set<String> restoredText = prefs1.getStringSet(Integer.toString(i), null);
            params.add(i, new ArrayList<>(restoredText));
        }

        CredentialsRecyclerListAdapter adapter = new CredentialsRecyclerListAdapter(this, params, getContext());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDividerItemDecoration);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter, recyclerView);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}

