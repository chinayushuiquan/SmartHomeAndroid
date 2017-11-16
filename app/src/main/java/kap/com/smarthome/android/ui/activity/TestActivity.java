package kap.com.smarthome.android.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.greenrobot.greendao.query.QueryBuilder;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;
import kap.com.smarthome.android.communication.http.doapi.CommonOkHttpClient;
import kap.com.smarthome.android.communication.http.listener.DealHttpResponseHandle;
import kap.com.smarthome.android.communication.http.listener.DealHttpResponseListener;
import kap.com.smarthome.android.communication.udp.doapi.DoUdpApi;
import kap.com.smarthome.android.data.control.DoDBApi;
import kap.com.smarthome.android.communication.http.request.CommonRequest;
import kap.com.smarthome.android.presenter.utils.MyLogUtils;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;


/**
 * Created by Administrator on 2017/9/14 0014.
 */

public class TestActivity extends BaseActivity {

    private DoDBApi doDBApi;

    private TextView tv;


    public  static String  test_data2 = "{\"HEAD\":{\n" +
            "      \"TIMESTAMP\":1496294692,\n" +
            "\t  \"VERSION\":\"V0.01\",\n" +
            "\t  \"SERVICEID\":\"12345678\",\n" +
            "\t  \"SERIALNUM\":0,\n" +
            "\t  \"DEVICEID\":\"0000000000000000\"\n" +
            "\t  },\n" +
            "\t  \"BODY\":{\n" +
            "\t  \"INSTP\":\"DEVICEFINDREQ\"\n" +
            "\t  }\n" +
            "}";

    public  static String  test_data3 = "{\"HEAD\":{\"DEVICEID\":\"\",\"REPEATCOUNT\":1,\"SERIALNUM\":1,\"TIMESTAMP\":2343434,\"USERID\":\"\",\"VERSION\":\"2.0\"},\"BODY\":{\"INSTP\":\"GRANTNEWREQ\",\"USERID\":\"4028b8815e2b93f3015e2b946b7b0000\",\"LOGINNAME\":\"woxow\",\"VALIDCODE\":\"123456\",\"LOGINTYPE\":\"2\", \"APPKEY\":\"a14508c3574149209785aaa002bbb6b8\",\"CHECKSUM\":\"foejwoww2293l\"}}";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        tv = (TextView) findViewById(R.id.textView2);


        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {

    }

    /**
     * 数据库插入测试
     * @param view
     */
    public  void  inset(View view){
        /*DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        MyTestBeanDao myTestBeanDao = daoSession.getMyTestBeanDao();

        DoDBApi doDBApi = new DoDBApi();
        doDBApi.insertUser(myTestBeanDao);*/

        /*DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        MyTestBeanDao myTestBeanDao = daoSession.getMyTestBeanDao();

        DoDBApi doDBApi = new DoDBApi();
        doDBApi.getAll(myTestBeanDao);

        doDBApi.queryEq(myTestBeanDao);
        doDBApi.queryThread(myTestBeanDao);*/
    }


    /**
     * 数据库查询测试
     * @param view
     */
    public  void  query(View view){

    }


    /**
     * http通信测试
     * @param view
     */
    public  void  http(View view){
 /* //get
        OkHttpClient  okHttpClient = new OkHttpClient();
        Request  request = new Request.Builder().url("").build();

        try {
            Response  response  = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


        //post 1
        //創建一個表單數據
        FormBody.Builder  formBodyBuild = new FormBody.Builder();
        formBodyBuild.add("mobile" , "12345645");
        formBodyBuild.add("pwd" , "999");
        //創建一個請求
        Request request1 = new Request.Builder().post(formBodyBuild.build()).url("").build();

        okHttpClient.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

        //post2
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "a");

        //创建一个请求对象
        Request request2 = new Request.Builder()
                .url("")
                .post(requestBody)
                .build();

        //发送请求获取响应

         okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
     */


        /**
         * 通过Gson装换成实际的数据
         */
     /*   CommonOkHttpClient.get(CommonRequest.createJsonPostRequest("http://10.20.110.35:8080/shs/alarm/asynctask" , test_data2) ,
                new DealHttpResponsehandle(
                        new DealHttpReponseListener() {

                            @Override
                            public void onHttpSuccess(Object responseHttp) {



                            }

                            @Override
                            public void onHttpFail(Object exceptionObject) {

                            }
                        }, Room.class));*/

     //服务器接口调试
     CommonOkHttpClient.post(CommonRequest.createJsonPostRequest("http://10.20.110.33:8080/ams/api/v4" , test_data3) , new DealHttpResponseHandle(
             new DealHttpResponseListener() {

                 @Override
                 public void onHttpSuccess(Object responseHttp) {

                     Log.i("CHRIS", "onHttpSuccess: " + responseHttp.toString());


                 }

                 @Override
                 public void onHttpFail(Object exceptionObject) {
                     Log.e("CHRIS", "onHttpFail: " + exceptionObject.toString());
                 }
             }));
    }


    /**
     * UDP通信测试
     * @param view
     */
    public  void  udp(View view){

        //DoUdpApi.getInstance().searchRelayBox();
        /*JsonHeadBean comJsonUDPHead = new JsonHeadBean();
        comJsonUDPHead.setTIMESTAMP(JsonUtils.getSystemCurrentTime());
        comJsonUDPHead.setSERVICEID(UDPContants.UDP_BROADCAST_HEAD_SERVICE_ID);
        comJsonUDPHead.setVERSION(UDPContants.UDP_JSON_HEAD_VERSION);
        comJsonUDPHead.setDEVICEID(UDPContants.UDP_BROADCAST_HEAD_DEVICES_ID);
        comJsonUDPHead.setSERIALNUM(0);

        Gson gson  = new Gson();
        String s = gson.toJson(comJsonUDPHead, JsonHeadBean.class);
        //Object o  = JSON.toJSON(comJsonUDPHead);
        //JSON.toJSON(comJsonUDPHead);

        Log.e("CHRIS", "udp: JSON = " + s);*/
    }


    @Override
    public void uiSearchRelayBoxSuccessCallback(final UDPReceiverData item) {
        super.uiSearchRelayBoxSuccessCallback(item);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(item.data);
                MyLogUtils.LogUtils(-1, "---UI呈现----"+item.data);
            }
        });
    }



}
