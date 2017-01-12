package tk.deepesh.loginclient.Credentials;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Set;

import tk.deepesh.loginclient.R;

/**
 * Created by deepesh on 26/12/16.
 */

public class CredentialDialog {
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
        View promptsView = li.inflate(R.layout.dialog_credentials, null);
        final EditText newssid = (EditText) promptsView
                .findViewById(R.id.add_cred_user_value);

        ad.setView(promptsView);
        ad.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Set<String> ssidlist;
                SharedPreferences sharedPref = context.getSharedPreferences("ssilist", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                if ((sharedPref.getStringSet("ssidlist", null) == null)) {
                    ssidlist = new HashSet<>();
                } else {
                    ssidlist = sharedPref.getStringSet("ssidlist", null);
                }
                ssidlist.add(newssid.getText().toString());
                editor.putStringSet("ssidlist", ssidlist);
                editor.commit();

                if (listener != null)
                    listener.onAdd();

            }
        });

        final android.app.AlertDialog alertDialog = ad.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(ad.getContext(), R.color.colorPrimary));
            }
        });
        alertDialog.show();
    }

    public interface ClickListener {
        void onAdd();
    }
}
