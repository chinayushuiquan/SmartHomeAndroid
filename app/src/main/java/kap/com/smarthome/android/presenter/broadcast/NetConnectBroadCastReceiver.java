package kap.com.smarthome.android.presenter.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.RelayBoxUDPHandle;

/**
 * Created by yushq on 2017/11/7 0007.
 */

public class NetConnectBroadCastReceiver extends BroadcastReceiver {

    private NetworkInfo.State wifiState;
    private NetworkInfo.State mobileState;

    private  boolean  WIFI_IS_AVAILABLE = false;

    private int netState; // 0 断开 1 连接

    public NetConnectBroadCastReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        AllVariable.CONNECT_RELAY = false;

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            Log.i("CHRIS", "CONNECTIVITY_ACTION");
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.isConnected()) {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        // connected to wifi
                        AllVariable.MOBILE_CONNECT = false;
                        AllVariable.WIFI_CONNECT = true;
                        AllVariable.NO_CONNECT = false;
                        Log.e("CHRIS", "当前WiFi连接可用 ");
                        RelayBoxUDPHandle.createConnectToRelayBox();
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        // connected to the mobile provider's data plan
                        AllVariable.MOBILE_CONNECT = true;
                        AllVariable.WIFI_CONNECT = false;
                        AllVariable.NO_CONNECT = false;
                        Log.e("CHRIS", "当前移动网络连接可用 ");
                        AllVariable.CONNECT_RELAY = false;
                    }
                } else {
                    Log.e("CHRIS", "当前没有网络连接，请确保你已经打开网络 ");
                    AllVariable.MOBILE_CONNECT = false;
                    AllVariable.WIFI_CONNECT = false;
                    AllVariable.NO_CONNECT = true;
                    AllVariable.CONNECT_RELAY = false;
                }
            } else {   // not connected to the internet
                Log.e("CHRIS", "当前没有网络连接，请确保你已经打开网络 ");
                AllVariable.MOBILE_CONNECT = false;
                AllVariable.WIFI_CONNECT = false;
                AllVariable.NO_CONNECT = true;
                AllVariable.CONNECT_RELAY = false;
            }
        }
    }
}