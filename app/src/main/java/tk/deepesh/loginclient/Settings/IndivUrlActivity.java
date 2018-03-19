package tk.deepesh.loginclient.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

import tk.deepesh.loginclient.R;

public class IndivUrlActivity extends AppCompatActivity {
    EditText param3_key, param3_value, param4_key, param4_value, param5_key, param5_value, next_request_regex, next_request_value, result_regex, url;
    Button save_btn;
    com.bigmercu.cBox.CheckBox checkbox_param3, checkbox_param4, checkbox_param5, checkbox_username, checkbox_password;
    CheckBox checkbox_next_request, checkbox_result, insecure_connection;
    Req req = new Req();
    String[] a;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(IndivUrlActivity.this);

        if (sharedPref.getBoolean("light_theme_switch", false)) {
            setTheme(R.style.AppThemeLight);
        }

        setContentView(R.layout.activity_indiv_url);

        req.insecure = false;


        SharedPreferences prefs1 = getSharedPreferences("credentials", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = prefs1.edit();

        SharedPreferences prefs2 = getSharedPreferences("ind_url", MODE_PRIVATE);
        final SharedPreferences.Editor editor2 = prefs2.edit();

        save_btn = (Button) findViewById(R.id.save_btn);

        url = (EditText) findViewById(R.id.url_value);

        param3_key = (EditText) findViewById(R.id.param3_key);
        param3_value = (EditText) findViewById(R.id.param3_value);
        param4_key = (EditText) findViewById(R.id.param4_key);
        param4_value = (EditText) findViewById(R.id.param4_value);
        param5_key = (EditText) findViewById(R.id.param5_key);
        param5_value = (EditText) findViewById(R.id.param5_value);

        checkbox_username = (com.bigmercu.cBox.CheckBox) findViewById(R.id.checkbox_username);
        checkbox_password = (com.bigmercu.cBox.CheckBox) findViewById(R.id.checkbox_password);
        checkbox_param3 = (com.bigmercu.cBox.CheckBox) findViewById(R.id.checkbox_param3);
        checkbox_param4 = (com.bigmercu.cBox.CheckBox) findViewById(R.id.checkbox_param4);
        checkbox_param5 = (com.bigmercu.cBox.CheckBox) findViewById(R.id.checkbox_param5);

        insecure_connection = (CheckBox) findViewById(R.id.insecure_connection);

        checkbox_next_request = (CheckBox) findViewById(R.id.checkbox_next_request);

        next_request_regex = (EditText) findViewById(R.id.next_request_regex);
        next_request_value = (EditText) findViewById(R.id.next_request_value);

        result_regex = (EditText) findViewById(R.id.result_regex);

        checkbox_result = (CheckBox) findViewById(R.id.checkbox_result);


        Set setA = new HashSet();

        setA.add("password");
        setA.add("f2015764");

        Set setB = new HashSet();

        setB.add("pas");
        setB.add("f20");

        Set setC = new HashSet();

        setC.add("pa");
        setC.add("f2");

        Set setD = new HashSet();

        setD.add("passwor");
        setD.add("f201576");

        editor1.putStringSet("0", setA);
        editor1.putStringSet("1", setB);
        editor1.putStringSet("2", setC);
        editor1.putStringSet("3", setD);
        editor1.commit();

        final Set<String> restoredText = prefs1.getStringSet("0", null);

        if (restoredText != null) {
            a = restoredText.toArray(new String[restoredText.size()]);
        }

        Bundle extras = getIntent().getExtras();
        final int position = extras.getInt("position");

        Gson gson = new Gson();
        String json = prefs2.getString(String.valueOf(position), null);
        if (json != null) {
            Req obj = gson.fromJson(json, Req.class);

            url.setText(obj.url);

            if (obj.numOfParams >= 1) {
                checkbox_param3.setChecked(true);
                param3_key.setText(obj.params[0][0]);
                param3_value.setText(obj.params[0][1]);
            }

            if (obj.numOfParams >= 2) {
                checkbox_param4.setChecked(true);
                param4_key.setText(obj.params[1][0]);
                param4_value.setText(obj.params[1][1]);
            }

            if (obj.numOfParams >= 3) {
                checkbox_param5.setChecked(true);
                param5_key.setText(obj.params[2][0]);
                param5_value.setText(obj.params[2][1]);
            }

            if (obj.username_fromMain) {
                checkbox_username.setChecked(true);
            }

            if (obj.password_fromMain) {
                checkbox_password.setChecked(true);
            }

            if (obj.next_request_regex != null) {
                next_request_regex.setText(obj.next_request_regex);
                next_request_value.setText(obj.next_request_value);

                checkbox_next_request.setChecked(true);
            }

            if (obj.insecure) {
                insecure_connection.setChecked(true);
            }

            if (obj.result_regex != null) {
                checkbox_result.setChecked(true);
                result_regex.setText(obj.result_regex);
            }
        }

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                req.url = url.getText().toString();

                req.numOfParams = 0;

                String[][] params = new String[3][2];

                if (checkbox_param3.isChecked()) {
                    params[req.numOfParams][0] = param3_key.getText().toString();
                    params[req.numOfParams][1] = param3_value.getText().toString();
                    req.numOfParams += 1;
                }

                if (checkbox_param4.isChecked()) {
                    params[req.numOfParams][0] = param4_key.getText().toString();
                    params[req.numOfParams][1] = param4_value.getText().toString();
                    req.numOfParams += 1;
                }

                if (checkbox_param5.isChecked()) {
                    params[req.numOfParams][0] = param5_key.getText().toString();
                    params[req.numOfParams][1] = param5_value.getText().toString();
                    req.numOfParams += 1;
                }

                req.params = params;

                req.username_fromMain = checkbox_username.isChecked();
                req.password_fromMain = checkbox_password.isChecked();

                if (checkbox_next_request.isChecked()) {
                    req.next_request_regex = next_request_regex.getText().toString();
                    req.next_request_value = next_request_value.getText().toString();
                }

                if (checkbox_result.isChecked()) {
                    req.result_regex = result_regex.getText().toString();
                }

                req.insecure = insecure_connection.isChecked();

                if (restoredText != null) {
                    req.username = a[0];
                    req.password = a[1];
                }

                Gson gson = new Gson();
                String json = gson.toJson(req); // myObject - instance of MyObject
                editor2.putString(String.valueOf(position), json);
                editor2.commit();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    class Req {
        String url;
        String username;
        String password;
        Integer numOfParams;
        String[][] params;
        Boolean insecure;
        String next_request_regex;
        String next_request_value;
        String result_regex;
        Boolean username_fromMain;
        Boolean password_fromMain;
    }

}
