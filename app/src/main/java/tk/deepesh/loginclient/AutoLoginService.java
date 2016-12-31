package tk.deepesh.loginclient;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by deepesh on 20/11/16.
 */

public class AutoLoginService extends Service {

    public String ssid;
    //    Broadcast Receiver
    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        //        Get shared preferences
//        private SharedPreferences sharedPreferences, shared;

        //        On broadcast receive
        @Override
        public void onReceive(Context context, Intent intent) {


            final Context c = context;

            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

//        Detect changes in wifi
            if (info != null && info.isConnected()) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

//            If wifi is not previously connected
                if (!mWifi.isConnected()) {


//                Get default username, password
//                    sharedPreferences = context.getApplicationContext().getSharedPreferences("MyPref", 0);
//                    shared = context.getApplicationContext().getSharedPreferences("MyPref1", 0);
//                    final String user1 = shared.getString("Default", null);
//                    final String pass1 = sharedPreferences.getString(user1, null);

//                Get wifi ssid
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    ssid = wifiInfo.getSSID();

                    Toast.makeText(c, ssid, Toast.LENGTH_SHORT).show();

//                    Execute Login2 after 1 sec delay
//                    if ((user1 != null) && (pass1 != null)) {
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                new Login2(c).execute(user1, pass1);
//                            }
//                        }, 1000);
//                    }
                }
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //    This is called when the service gets started initially
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
        return START_STICKY;

    }

    //    This is the main function which is executed for the service
    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.wifi.STATE_CHANGE");

        registerReceiver(receiver, filter);

    }

    //    On Destroy - When the sevice is stopped from MainActivity
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        Toast.makeText(this, "Stopped", Toast.LENGTH_SHORT).show();
    }
}

//    Same Login2 function as used in MainActivity except the toast

//    class Login2 extends AsyncTask<String, Void, String> {
//        private OkHttpClient client = getOkHttpClient.getOkHttpClient();
//        private String result, message;
//
//        private Context c;
//
//        Login2(Context context) {
//            c = context;
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                RequestBody body = new FormBody.Builder()
//                        .add("username", params[0])
//                        .add("password", params[1])
//                        .add("mode", "191")
//                        .build();
//
//                Request request = new Request.Builder()
//                        .url("")
//                        .post(body)
//                        .build();
//
//                Response response = client.newCall(request).execute();
//                result = response.body().string();
//                return result;
//            } catch (Exception e) {
//                result = "ERR";
//                return null;
//            }
//        }
//
//        protected void onPostExecute(String result) {
////            Notification Builder
//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c);
//
//            if (ssid.isEmpty()) {
//                mBuilder.setContentTitle("BPGC LOGIN");
//            }
//            mBuilder.setContentTitle("Connecting to " + ssid);
//
//            if (result != null) {
//                String pattern = Pattern.quote("<message><![CDATA[") + "(.*?)" + Pattern.quote("]]></message>");
//                Pattern r = Pattern.compile(pattern);
//                Matcher m = r.matcher(result);
//                if (m.find()) {
////                        Get Message
//                    message = m.group(1);
//
//                    if (message.equals("You have successfully logged in")){
////                        mBuilder.setSmallIcon(R.drawable.wifi);
//                    }
//                    else {
////                        mBuilder.setSmallIcon(R.drawable.wifi_off);
//                    }
//
////                        Build notification
//                    mBuilder.setContentText(message);
//                    NotificationManager mNotificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
//
//                    int notificationID = 0;
////                        Send Notification
//                    mNotificationManager.notify(notificationID, mBuilder.build());
//                }
//            }
//        }
//    }
//}
