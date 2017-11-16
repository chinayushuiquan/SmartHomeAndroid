package kap.com.smarthome.android.communication.http.response;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;


import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import kap.com.smarthome.android.communication.http.listener.DealHttpResponseHandle;
import kap.com.smarthome.android.communication.http.listener.DealHttpResponseListener;
import kap.com.smarthome.android.communication.http.exception.MyOkHttpException;
import kap.com.smarthome.android.communication.http.listener.DealHttpCookieResponseListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by yushq on 2017/5/25 0025.
 */

public class CommomJsonClassCallback implements Callback{

    protected final String RESULT_CODE = "eCode"; // 有返回则对于http请求来说是成功的，但还有可能是业务逻辑上的错误
    protected final int    RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "eMsg";
    protected final String EMPTY_MSG = "";
    protected final String COOKIE_STORE = "Set-Cookie"; // decide the server it
    // can has the value of
    // set-cookie2

    /**
     * 错误码
     */
    protected final int NETWORK_ERROR = -1;
    protected final int JSON_ERROR = -2;
    protected final int OTHER_ERROR = -3;

    /**
     * 将其它线程的数据转发到UI线程
     */
    private Handler mDeliveryHandler;
    private DealHttpResponseListener mListener;
    private Class<?> mClass;

    public CommomJsonClassCallback(DealHttpResponseHandle handle) {
        this.mListener = handle.dealHttpResponseListener;
        this.mClass = handle.mClass;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(final Call call, final IOException ioexception) {
        /**
         * 此时还在非UI线程，因此要转发
         */
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onHttpFail(new MyOkHttpException(NETWORK_ERROR,ioexception));
                Log.e("HTTP", " 服务器连接错误 = " + ioexception );
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        final String result = response.body().string();
        final ArrayList<String> cookieLists = handleCookie(response.headers());
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
                /**
                 * handle the cookie
                 */
                if (mListener instanceof DealHttpCookieResponseListener) {
                    ((DealHttpCookieResponseListener) mListener).onCookie(cookieLists);
                }
            }
        });
    }


    private ArrayList<String> handleCookie(Headers headers) {
        ArrayList<String> tempList = new ArrayList<String>();
        for (int i = 0; i < headers.size(); i++) {
            if (headers.name(i).equalsIgnoreCase(COOKIE_STORE)) {
                tempList.add(headers.value(i));
            }
        }
        return tempList;
    }



    private void handleResponse(Object responseObj) {
        Log.e("HTTP", "服务器返回数据  == " + responseObj.toString());
        if (responseObj == null) {
            mListener.onHttpFail(new MyOkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }
        try {
            JSONObject result = new JSONObject(responseObj.toString());
            if(mClass != null){
                Object obj = new Gson().fromJson(responseObj.toString(), mClass);
                if(obj != null) {
                    Log.e("HTTP", "mClass  == " + obj.toString());
                    mListener.onHttpSuccess(obj);
                }else{
                    mListener.onHttpFail(new MyOkHttpException(JSON_ERROR , result.optString(ERROR_MSG)));
                }
            } else{
                //直接回调成功，返回JsonObject,不转换成 class 实例;
                mListener.onHttpSuccess(result);
            }
        } catch (Exception e) {
            mListener.onHttpFail(new MyOkHttpException(OTHER_ERROR, e.getMessage()));
            e.printStackTrace();
        }
    }

}
