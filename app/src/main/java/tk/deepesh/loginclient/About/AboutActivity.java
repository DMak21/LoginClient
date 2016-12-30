package tk.deepesh.loginclient.About;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import tk.deepesh.loginclient.R;

public class AboutActivity extends AppCompatActivity {

    private TextView text_about_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }


        text_about_3 = (TextView) findViewById(R.id.text_about_3);
        text_about_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://github.com/deepeshmakhijani/LoginClient";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(Intent.createChooser(i, "Choose Browser"));
            }
        });
    }

}
