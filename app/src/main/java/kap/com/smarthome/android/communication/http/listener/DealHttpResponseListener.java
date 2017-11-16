package kap.com.smarthome.android.communication.http.listener;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

public interface DealHttpResponseListener {
    /**
     * Listener HttpResponse get Success
     * @param responseHttp
     */
    public  void  onHttpSuccess(Object responseHttp);


    /**
     * Listener Http Response get Fail
     * @param exceptionObject
     */
    public  void  onHttpFail(Object exceptionObject);

}
