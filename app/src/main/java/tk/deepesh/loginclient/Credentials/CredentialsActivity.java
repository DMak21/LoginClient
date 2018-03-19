package tk.deepesh.loginclient.Credentials;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import tk.deepesh.loginclient.R;

public class CredentialsActivity extends AppCompatActivity {
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(CredentialsActivity.this);

        if (sharedPref.getBoolean("light_theme_switch", false)) {
            setTheme(R.style.AppThemeLight);
        }

        setContentView(R.layout.activity_credentials);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CredentialDialog alert = new CredentialDialog();
                alert.showDialog(CredentialsActivity.this, "ADD");
                alert.setClickListener(new CredentialDialog.ClickListener() {
                    @Override
                    public void onAdd() {
                        recreate();
                    }
                });
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
