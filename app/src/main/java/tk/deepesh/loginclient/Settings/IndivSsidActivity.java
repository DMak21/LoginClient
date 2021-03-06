package tk.deepesh.loginclient.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import tk.deepesh.loginclient.R;
import tk.deepesh.loginclient.RecyclerItemClickListener;

public class IndivSsidActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> urlList;
    private String[][] paramList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(IndivSsidActivity.this);

        if (sharedPref.getBoolean("light_theme_switch", false)) {
            setTheme(R.style.AppThemeLight);
        }

        setContentView(R.layout.activity_indiv_ssid);

        final int position_adv_set = getIntent().getIntExtra("position_adv_set", 0);

        urlList = new ArrayList<>();
        urlList.add("http://www.google.com");
        urlList.add("http://www.yahoo.com");

        paramList = new String[5][2];
        paramList[0][0] = "Makkamd";
        paramList[0][1] = "eddkjnsd";

        mRecyclerView = (RecyclerView) findViewById(R.id.recyler_view_indiv_ssid);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new IndivSsidAdapter(urlList, paramList);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(mDividerItemDecoration);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndivSsidActivity.this, IndivUrlActivity.class);
                intent.putExtra("position", "0");
                startActivity(intent);
            }
        });

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(IndivSsidActivity.this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(IndivSsidActivity.this, IndivUrlActivity.class);
                        intent.putExtra("position_indiv_ssid", position);
                        intent.putExtra("position_adv_set", position_adv_set);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

}
