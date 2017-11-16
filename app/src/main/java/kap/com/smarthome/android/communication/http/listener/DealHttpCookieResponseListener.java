package kap.com.smarthome.android.communication.http.listener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/25 0025.
 * 需要处理cookie的时候调用这个监听
 */

public interface DealHttpCookieResponseListener extends DealHttpResponseListener {

    public void onCookie(ArrayList<String> cookieStrLists);


}
