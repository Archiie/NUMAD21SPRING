package edu.neu.madcourse.numad21s_archita_sundaray;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class NetworkInfoActivity extends AppCompatActivity  {

    // The BroadcastReceiver that tracks network connectivity changes.
    private NetworkReceiver receiver = new NetworkReceiver();

    // User pre-defined network preference
    private String userNetworkPref = "";

    TextView wifiTV;
    TextView cellTV;
    TextView pref_tv;

    @Override
    protected void onStart() {
        super.onStart();
        initUserNetworkPreference();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_info);

        wifiTV = (TextView)findViewById(R.id.wifi_connected_textview);
        cellTV = (TextView)findViewById(R.id.cell_connected_textview);
        pref_tv = (TextView)findViewById(R.id.user_pref_textview);

        registerReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregisters BroadcastReceiver when app is destroyed.
        if (receiver != null) {
            this.unregisterReceiver(receiver);
        }
    }

    // Button
    public void checkConnection(View view){

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // How to do this changed in Lollipop. Including both versions here.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Even if build version makes this unnecessary, this is left in to show the old way (see the else below)
            // Scan each status of the network
            Network[] networks = connMgr.getAllNetworks();

            // In this project only focus cellular and wifi two status
            boolean[] flags = new boolean[2];

            for (Network mNetwork : networks) {
                NetworkInfo networkInfo = connMgr.getNetworkInfo(mNetwork);

                String info = getNetworkDetail(networkInfo);
                int currentStatus = networkInfo.getType();

                if(currentStatus == ConnectivityManager.TYPE_WIFI){
                    wifiTV.setText(info);
                    flags[0] = true;
                }
                if(currentStatus == ConnectivityManager.TYPE_MOBILE){
                    cellTV.setText(info);
                    flags[1] = true;
                }
            }

            // WIFI is closed
            if(flags[0] == false){
                wifiTV.setText("");
            }
            // Cellular data is closed
            if(flags[1] == false){
                cellTV.setText("");
            }

        }
        else{
            // To account for Android API < 21.
            NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            wifiTV.setText(getNetworkDetail(networkInfo));

            networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            cellTV.setText(getNetworkDetail(networkInfo));
        }

    }

    // Set the user's network preference settings into default SharedPreferences in Android
    // Not recommend in the later version
    private void initUserNetworkPreference(){
        // Initial SharedPreferences
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // You can name any status as you prefer,
        // Not the current network status
        String networkPref = sharedPrefs.getString("UserPref", "Wi-Fi");

        userNetworkPref = networkPref;
        receiver.setUserPreference(userNetworkPref);

        pref_tv.setText(networkPref);
    }

    // Registers BroadcastReceiver to track network connection changes.
    private void registerReceiver(){
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(receiver, filter);
    }

    // Network status: CONNECTING, CONNECTED, SUSPENDED, DISCONNECTING, DISCONNECTED, UNKNOWN
    // Please dive deep into NetworkInfo class, it contains all network information you need
    // https://developer.android.com/reference/android/net/NetworkInfo
    private static String getNetworkDetail(NetworkInfo networkInfo) {

        // Connection Status
        String info = "Status: " + (networkInfo.getState() == NetworkInfo.State.CONNECTED?"connected":"not connected") + "\n";

        // Network type
        info += "Network: "+networkInfo.getTypeName()+"\n";

        if(networkInfo.getTypeName().equals("WIFI")){
            return info;
        }

        // Cellular type(LTE/3G/....)
        info += "Cellular: "+networkInfo.getSubtypeName();
        return info;
    }

}