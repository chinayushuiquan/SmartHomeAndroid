package kap.com.smarthome.android.presenter.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by yushq on 2017/11/2 0002.
 *
 * 网络连接的服务类
 *
 */

public class NetConnectService {

    /**
     * 监听广播的服务
     */
    class  NetworkChangeReceiver  extends BroadcastReceiver{

       private NetworkInfo.State wifiState;
       private NetworkInfo.State mobileState;

       private  boolean  WIFI_IS_AVAILABLE = false;

        private int netState; // 0 断开 1 连接

      public NetworkChangeReceiver() {


      }

      @Override
      public void onReceive(Context context, Intent intent) {

          ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

          NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
          NetworkInfo mobileInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

          wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
          mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();


          if (wifiState != null && NetworkInfo.State.CONNECTED == wifiState) {
              WIFI_IS_AVAILABLE = true;
              // TODO: 2017/11/2 0002 WIFI打开 本地连接通讯可用 先使用本地控制




          } else if (mobileState != null && NetworkInfo.State.CONNECTED == mobileState) {
              // TODO: 2017/11/2 0002  WIFI未连接  手机流量打开 使用远程控制
              WIFI_IS_AVAILABLE = false;

          }






      }
  }


}
