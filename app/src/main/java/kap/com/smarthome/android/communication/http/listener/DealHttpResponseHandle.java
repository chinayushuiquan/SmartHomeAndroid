package kap.com.smarthome.android.communication.http.listener;

/**
 * Created by Administrator on 2017/5/25 0025.
 *  deal http response
 *  potting Listener and data
 */

public class DealHttpResponseHandle{

     public DealHttpResponseListener dealHttpResponseListener;

     public Class<?>  mClass;

     public String  mSource;


    public DealHttpResponseHandle(DealHttpResponseListener listener){
        this.dealHttpResponseListener = listener;
    }

    public DealHttpResponseHandle(DealHttpResponseListener listener, Class<?>  clazz){
        this.dealHttpResponseListener = listener ;
        this.mClass = clazz;
    }


    public DealHttpResponseHandle(DealHttpResponseListener listener, String source) {
        this.dealHttpResponseListener = listener;
        this.mSource = source;
    }




}
