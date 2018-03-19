package tk.deepesh.loginclient.Settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Set;

import tk.deepesh.loginclient.R;

/**
 * Created by deepesh on 21/12/16.
 */

public class AdvSetDialog {
    Context context;
    private ClickListener listener;

    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    public void showDialog(
            final Context context,
            String buttonText) {
        this.context = context;

        final android.app.AlertDialog.Builder ad = new android.app.AlertDialog.Builder(context);

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.dialog_adv_set, null);
        final EditText newssid = (EditText) promptsView
                .findViewById(R.id.newssid);

        ad.setView(promptsView);
        ad.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Set<String> ssidlist;
                SharedPreferences sharedPref = context.getSharedPreferences("ssidlist", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                Set<String> s;

                if ((sharedPref.getStringSet("ssidlist", null) == null)) {
                    s = new HashSet<>();
                } else {
                    ssidlist = sharedPref.getStringSet("ssidlist", null);
                    s = new HashSet<>(ssidlist);
                }

                s.add(newssid.getText().toString());

                editor.putStringSet("ssidlist", s);
                editor.commit();

                if (listener != null)
                    listener.onPosButtonClick();
            }
        });

        final android.app.AlertDialog alertDialog = ad.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                Button a = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                a.setTextColor(ContextCompat.getColor(ad.getContext(), R.color.colorText));
            }
        });
        alertDialog.show();
    }

    public interface ClickListener {
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        void onPosButtonClick();
    }

}