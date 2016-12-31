package tk.deepesh.loginclient;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by deepesh on 27/12/16.
 */

public class LoginLogout extends AsyncTask<LoginLogout.Req, Void, LoginLogout.Raq> {
    Context context;
    //    Get okhttp client
    OkHttpClient client;
    private String message;

    public LoginLogout(Context context, Boolean insecure) {
        this.context = context;
    }

    @Override
    protected Raq doInBackground(Req... r) {
        try {
//                Generate request body
            if (r[0].insecure) {
                client = getOkHttpClient.getOkHttpClient();
            } else {
                client = (new OkHttpClient());
            }

            FormBody.Builder formBuilder = new FormBody.Builder().add("username", r[0].username).add("password", r[0].password);
            for (int i = 0; i < r[0].numOfParams; i++) {
                formBuilder.add(r[0].params[i][0], r[0].params[i][1]);
            }

            RequestBody body = formBuilder.build();
            Request request = new Request.Builder()
                    .url(r[0].url)
                    .post(body)
                    .build();
//                Get response

            Response response = client.newCall(request).execute();
            Raq re = new Raq();
            re.next_request_regex = r[0].next_request_regex;
            re.next_request_value = r[0].next_request_value;
            re.result_regex = r[0].result_regex;
            re.result_complete = response.body().string();
            return re;
        } catch (Exception e) {
            return null;
        }
    }

    protected void onPostExecute(Raq re) {
        if (re.result_complete != null) {
//                Get message using regex

            Pattern r = Pattern.compile(re.result_regex);
            Matcher m = r.matcher(re.result_complete);
            if (m.find()) {
                message = m.group(1);
            } else {
                message = "Wait for the issue to be resolved";
            }
        } else {
            message = "Reconnect the WiFi!";
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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

    class Raq {
        String result_complete;
        String next_request_regex;
        String next_request_value;
        String result_regex;
    }
}
