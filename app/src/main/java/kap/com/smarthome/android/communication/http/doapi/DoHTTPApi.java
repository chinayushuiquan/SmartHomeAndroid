package kap.com.smarthome.android.communication.http.doapi;

import android.util.Log;


import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPResponseMsgBase;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseAccreditUserBody;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseAccreditUserMsg;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseDataMsgClass;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseMsgLogin;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseQueryAllDataBody;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseQueryAllDataMsg;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseQueryDevicesBody;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseUpdateHeadMsg;
import kap.com.smarthome.android.communication.http.listener.DealHttpResponseDownloadListener;
import kap.com.smarthome.android.communication.http.listener.DealHttpResponseHandle;
import kap.com.smarthome.android.communication.http.listener.DealHttpResponseListener;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.communication.http.request.CommonRequest;
import kap.com.smarthome.android.presenter.constants.AllConstants;

/**
 * Created by Administrator on 2017/9/30 0030.
 */

public class DoHTTPApi {

    /**
     * @param requestJson
     * @return
     * 带上了class 实例的post请求
     * 返回的Json数据用 转化为HTTPResponseMsgBase实例
     * protected String INSTP ;
       protected String RESULT;
     */
    public  static void httpPostBaseClazz( String  url , final String requestJson , final UIHttpCallBack uiHttpCallBack){
        CommonOkHttpClient.post(CommonRequest.createJsonPostRequest(url, requestJson) , new DealHttpResponseHandle(
                new DealHttpResponseListener() {
                    @Override
                    public void onHttpSuccess(Object responseHttp) {
                        uiHttpCallBack.success(responseHttp);
                    }
                    @Override
                    public void onHttpFail(Object exceptionObject) {
                        uiHttpCallBack.failure(exceptionObject);
                    }
                },HTTPResponseMsgBase.class));
    }


    /**
     * 由于 用户登录返回的结果 需要用HTTPResponseMsgLogin 进行接收(后续可以用泛型或者多态进行处理，以便复用)
     * @param requestJson
     * @param uiHttpCallBack
     */
    public  static void httpPostLoginClazz( String  url ,final String requestJson , final UIHttpCallBack uiHttpCallBack){
        CommonOkHttpClient.post(CommonRequest.createJsonPostRequest(url , requestJson ) , new DealHttpResponseHandle(
                new DealHttpResponseListener() {
                    @Override
                    public void onHttpSuccess(Object responseHttp) {
                        uiHttpCallBack.success(responseHttp);
                    }
                    @Override
                    public void onHttpFail(Object exceptionObject) {
                        uiHttpCallBack.failure(exceptionObject);
                    }
                }, HTTPResponseMsgLogin.class));
    }


    /**
     * 由于 用户登录返回的结果 需要用HTTPResponseMsgLogin 进行接收(后续可以用泛型或者多态进行处理，以便复用)
     * @param requestJson
     * @param uiHttpCallBack
     *
     */
    public  static void httpUpdateHeadClazz(String  url , final String requestJson , final UIHttpCallBack uiHttpCallBack){
        CommonOkHttpClient.post(CommonRequest.createJsonPostRequest(url , requestJson ) , new DealHttpResponseHandle(
                new DealHttpResponseListener() {
                    @Override
                    public void onHttpSuccess(Object responseHttp) {
                        uiHttpCallBack.success(responseHttp);
                    }
                    @Override
                    public void onHttpFail(Object exceptionObject) {
                        uiHttpCallBack.failure(exceptionObject);
                    }
                }, HTTPResponseUpdateHeadMsg.class));
    }



    /**
     * 获取用户头像
     * @param url
     * @param uiHttpCallBack
     */
    public static void httpGetUserHead(final String url, final UIHttpCallBack uiHttpCallBack) {
        CommonOkHttpClient.downloadFile(CommonRequest.createUrlPostRequest(url), new DealHttpResponseHandle(new DealHttpResponseDownloadListener() {
            @Override
            public void onProgress(int progrss) {

            }

            @Override
            public void onHttpSuccess(Object responseHttp) {
                uiHttpCallBack.success(responseHttp);
            }

            @Override
            public void onHttpFail(Object exceptionObject) {

            }
        }, AllConstants.HEAD_PATH));
    }



    /**
     * 用户授权
     * @param userUrl
     * @param requestJson
     * @param uiHttpCallBack
     * "BODY":{"INSTP":"GRANTNEWACK","OLDUSERID":"4028b8815e2b93f3015e2b946b7b0000","RESULT":"0"}
     */
    public static void httpReqOtherUserAccredit(String userUrl, String requestJson, final UIHttpCallBack uiHttpCallBack) {
        CommonOkHttpClient.post(CommonRequest.createJsonPostRequest(userUrl , requestJson ) , new DealHttpResponseHandle(
                new DealHttpResponseListener() {
                    @Override
                    public void onHttpSuccess(Object responseHttp) {
                        uiHttpCallBack.success(responseHttp);
                    }
                    @Override
                    public void onHttpFail(Object exceptionObject) {
                        uiHttpCallBack.failure(exceptionObject);
                    }
                }, HTTPResponseAccreditUserMsg.class));
    }


    /**
     *
     * 由于查询中继盒子 返回的信息
     * @param requestJson
     * @param uiHttpCallBack
     *
     * 返回参数
     *
     * {"HEAD":{"DEVICEID":"20170904","REPEATCOUNT":1,"SERIALNUM":1,"TIMESTAMP":2343434,"USERID":"114","VERSION":"2.0"},
     * "BODY":{"INSTP":"FINDRELAYBOXANDUSERRSP","RESULT":"0", “DATA”:[“ID”:”1111”,其他参数省略] }
     */
    public static void httpQueryRelayBoxs(String url , String requestJson, final UIHttpCallBack uiHttpCallBack) {
        CommonOkHttpClient.post(CommonRequest.createJsonPostRequest(url , requestJson ) , new DealHttpResponseHandle(
                new DealHttpResponseListener() {
                    @Override
                    public void onHttpSuccess(Object responseHttp) {
                        uiHttpCallBack.success(responseHttp);
                    }
                    @Override
                    public void onHttpFail(Object exceptionObject) {
                        uiHttpCallBack.failure(exceptionObject);
                    }
                }, new HTTPResponseDataMsgClass<HTTPResponseQueryDevicesBody>().getClass()));

    }


    /**
     *
     * 由于查询设备 返回的信息
     * @param requestJson
     * @param uiHttpCallBack
     *
     * 返回参数
     *
     * {"HEAD":{"DEVICEID":"20170904","REPEATCOUNT":1,"SERIALNUM":1,"TIMESTAMP":2343434,"USERID":"114","VERSION":"2.0"},"
     * BODY":{"INSTP":"FINDDEVICERSP","RESULT":"0", “DATA”:[“ID”:”1111”,其他参数省略] }}
     */
    public static void httpQueryDevices(String url , String requestJson, final UIHttpCallBack uiHttpCallBack) {
        CommonOkHttpClient.post(CommonRequest.createJsonPostRequest(url , requestJson ) , new DealHttpResponseHandle(
                new DealHttpResponseListener() {
                    @Override
                    public void onHttpSuccess(Object responseHttp) {
                        uiHttpCallBack.success(responseHttp);
                    }
                    @Override
                    public void onHttpFail(Object exceptionObject) {
                        uiHttpCallBack.failure(exceptionObject);
                    }
                }, new HTTPResponseDataMsgClass<HTTPResponseQueryDevicesBody>().getClass()));
        }


    /**
     * 远程控制设备
     * @param requestJson
     * @param uiHttpCallBack
     * URL 和其他的不一样 ：control_url  服务端做透传
     */
    public static void httpControlDevices(String url , String requestJson, final UIHttpCallBack uiHttpCallBack) {
        CommonOkHttpClient.post(CommonRequest.createJsonPostRequest(url , requestJson ) , new DealHttpResponseHandle(
                new DealHttpResponseListener() {
                    @Override
                    public void onHttpSuccess(Object responseHttp) {
                        uiHttpCallBack.success(responseHttp);
                    }
                    @Override
                    public void onHttpFail(Object exceptionObject) {
                        uiHttpCallBack.failure(exceptionObject);
                    }
                }, new HTTPResponseDataMsgClass<HTTPResponseQueryDevicesBody>().getClass()));
    }


    /**
     * 用户授权之后获取其他用户的所有数据
     * @param requestJson
     * @param uiHttpCallBack
     */
    public static void httpQueryOtherUserData(String url , String requestJson, final  UIHttpCallBack uiHttpCallBack) {
        CommonOkHttpClient.post(CommonRequest.createJsonPostRequest(url , requestJson) , new DealHttpResponseHandle(
                new DealHttpResponseListener() {
            @Override
            public void onHttpSuccess(Object responseHttp) {
                uiHttpCallBack.success(responseHttp);
            }

            @Override
            public void onHttpFail(Object exceptionObject) {
                uiHttpCallBack.failure(exceptionObject);
            }
        } , HTTPResponseQueryAllDataMsg.class));
    }
}
