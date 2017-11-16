package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.DATABean.AllBeanData;
import kap.com.smarthome.android.communication.bean.base.DATABean.DeviceData;
import kap.com.smarthome.android.communication.bean.base.DATABean.IrkeysData;
import kap.com.smarthome.android.communication.bean.base.DATABean.RelayBoxData;
import kap.com.smarthome.android.communication.bean.base.DATABean.RoomData;
import kap.com.smarthome.android.communication.bean.base.DATABean.ScenesData;
import kap.com.smarthome.android.communication.bean.base.DATABean.ScenesDevicesData;
import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPResponseMsgBase;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseAccreditUserBody;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseAccreditUserMsg;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseDataMsgClass;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseQueryAllDataBody;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseQueryAllDataMsg;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.IRKey;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.data.bean.Room;
import kap.com.smarthome.android.data.bean.Scenes;
import kap.com.smarthome.android.data.bean.ScenesDevice;
import kap.com.smarthome.android.data.bean.ScenesTrigger;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.control.BeanDataConvertUtils;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.RelayBoxUDPHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.presenter.utils.DataLegitimacyCheckUtils;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by Administrator on 2017/11/1 0001.
 */

public  class UserAccreditDoneActivity extends  BaseActivity{

    private Button mClick_btn;

    private Button getVerifyCode_btn;

    private TextInputEditText mVerifyCodeEdit;

    private String accredit_account = "";

    private String  accredit_userId = "";


    //验证码相关
    private boolean runningThree = false;
    private String second ;
    private static  final int  GET_CODE_TIME = 60*1000;
    private static  final int  CHANGE_TIME = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_accredit_done);




        accredit_account = getIntent().getStringExtra(AllConstants.USER_ACCREDIT_ACCOUNT);

        mClick_btn = (Button) findViewById(R.id.user_accredit_done);

        getVerifyCode_btn = (Button) findViewById(R.id.user_accredit_get_verify_code_btn);

        mVerifyCodeEdit = (TextInputEditText) findViewById(R.id.user_accredit_verify_code_et);

        second = getResources().getString(R.string.get_verify_code_second);

        /**
         * 获取验证码
         */
        getVerifyCode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVerification() ;
            }
        });


        mClick_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String verify_code = mVerifyCodeEdit.getText().toString().trim();

                if (verify_code.isEmpty()){
                    Toast.makeText(UserAccreditDoneActivity.this, "验证码不能为空！" , Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!DataLegitimacyCheckUtils.checkVerifyCode(verify_code)){
                    Toast.makeText(UserAccreditDoneActivity.this, "验证码格式错误！6位数字" , Toast.LENGTH_SHORT).show();
                    return;
                }

                showLoadingDialog("同步数据");

                /**
                 * 用户授权
                 */
                ServerCommunicationHandle.reqOtherUserAccredit(accredit_account, verify_code, 3, new UIHttpCallBack() {
                    @Override
                    public void success(Object object) {
                        if(object != null){
                            final HTTPResponseAccreditUserMsg httpResponseMsgBase = (HTTPResponseAccreditUserMsg) object;
                            HTTPResponseAccreditUserBody httpResponseAccreditUserBody = (HTTPResponseAccreditUserBody)httpResponseMsgBase.getBODY();
                            if(httpResponseAccreditUserBody.getRESULT().equals("0")){
                                accredit_userId = httpResponseAccreditUserBody.getOLDUSERID();
                                /**
                                 * 发送广播到中继盒子 进行授权
                                 */
                                RelayBoxUDPHandle.reqOtherUserAccredit(accredit_userId);

                                ServerCommunicationHandle.queryOtherUserAllData(new UIHttpCallBack() {
                                    @Override
                                    public void success(Object object){
                                        Log.e("HTTP", "success: ");
                                        final HTTPResponseQueryAllDataMsg response = (HTTPResponseQueryAllDataMsg) object;
                                        HTTPResponseQueryAllDataBody body = (HTTPResponseQueryAllDataBody)response.getBODY();

                                        if(body.getRESULT().equals("0")){
                                            AllBeanData allData = body.getDATA();
                                            /**
                                             * 房间
                                             */
                                            List<RoomData> roomDataList = allData.getROOMDATA();
                                            List<Room> roomList = BeanDataConvertUtils.convertToRoom(roomDataList);

                                            /**
                                             * 中继盒子
                                             */
                                            List<RelayBoxData> relayBoxDataList = allData.getBOXDATA();
                                            List<RelayBox> relayBoxList = BeanDataConvertUtils.convertToRelayBox(relayBoxDataList);

                                            /**
                                             * 设备
                                             */
                                            List<DeviceData> deviceDataList = allData.getDEVICEDATA();
                                            List<Devices>  devicesList = BeanDataConvertUtils.convertToDevices(deviceDataList);

                                            /**
                                             * 场景
                                             */
                                            List<ScenesData> scenesDataList = allData.getSCENEDATA();
                                            List<Scenes> scenesList = BeanDataConvertUtils.convertToScenes(scenesDataList);

                                            /**
                                             * 场景设备
                                             */
                                            List<ScenesDevice> scenesDevicesList = BeanDataConvertUtils.convertToScenesDevice(scenesDataList);
                                            /**
                                             * 场景触发条件
                                             */
                                            List<ScenesTrigger> scenesTriggerList = BeanDataConvertUtils.convertToScenesTrigger(scenesDataList);

                                            /**
                                             * 红外码库表
                                             */
                                            List<IrkeysData>  irkeysDataList = allData.getREDCODEDATA();
                                            List<IRKey>  irKeyList =  BeanDataConvertUtils.convertToIrKeys(irkeysDataList);

                                            if(DataBaseHandle.deleteALlData()){
                                                if(DataBaseHandle.insertAllData(roomList ,relayBoxList , devicesList , scenesList , scenesDevicesList , scenesTriggerList , irKeyList)){

                                                    Toast.makeText(UserAccreditDoneActivity.this , "数据同步成功" , Toast.LENGTH_LONG).show();
                                                    dismissLoadingDialog();
                                                }else {
                                                    Toast.makeText(UserAccreditDoneActivity.this , "数据保存失败" , Toast.LENGTH_LONG).show();
                                                    dismissLoadingDialog();
                                                }
                                            } else {
                                                Toast.makeText(UserAccreditDoneActivity.this , "数据删除失败" , Toast.LENGTH_LONG).show();
                                                dismissLoadingDialog();
                                            }
                                        }else {
                                            Toast.makeText(UserAccreditDoneActivity.this , "同步数据失败失败" , Toast.LENGTH_LONG).show();
                                            dismissLoadingDialog();
                                        }
                                    }
                                    @Override
                                    public void failure(Object object) {
                                        Toast.makeText(UserAccreditDoneActivity.this , "服务器错误" , Toast.LENGTH_LONG).show();
                                        dismissLoadingDialog();
                                    }
                                });
                            }else{
                                Toast.makeText(UserAccreditDoneActivity.this , "用户授权失败" , Toast.LENGTH_LONG).show();
                                dismissLoadingDialog();
                            }
                        }

                    }
                    @Override
                    public void failure(Object object) {
                        Toast.makeText(UserAccreditDoneActivity.this , "服务器错误" , Toast.LENGTH_LONG).show();
                        dismissLoadingDialog();
                    }
                });

            }
        });
    }

    /**
     * //请求验证码
     */
    private void getVerification() {
        if(runningThree){
            Toast.makeText(this, "正在获取，请稍后" , Toast.LENGTH_SHORT).show();
        }else{
            //http请求
            httpRequestGetVerification();
        }
    }


    /**
     * 新用户授权方式
     * type= 3
     */
    private void httpRequestGetVerification() {
        downTimer.start();
        ServerCommunicationHandle.getRegisterVerification(accredit_account, "zh", "3", new UIHttpCallBack() {
            @Override
            public void success(Object object) {
                if(object != null){
                    HTTPResponseMsgBase httpResponseBase = (HTTPResponseMsgBase) object;
                    if(httpResponseBase.getBODY().getRESULT().equals("0")){
                        //开启定时器，进行倒计时
                       // downTimer.start();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //返回成功，弹出提示
                                Toast.makeText(UserAccreditDoneActivity.this, "验证码已经发送对方账号！", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            }
            @Override
            public void failure(Object object) {
                Toast.makeText(UserAccreditDoneActivity.this, "获取验证码失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 实现验证码的一分钟倒计时功能
     */
    private CountDownTimer downTimer = new CountDownTimer(GET_CODE_TIME , CHANGE_TIME) {
        @Override
        public void onTick(long l) {
            runningThree = true;
            getVerifyCode_btn.setText((l / 1000) + second);
        }


        @Override
        public void onFinish() {
            runningThree = false;
            getVerifyCode_btn.setText(getString(R.string.get_verify_code_again));
        }
    };




    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
        myTopBarBuilder.setLeftImage(R.drawable.back_icon)
                .setTitleColor(R.color.white)
                .setTitleText(R.string.authorization_user)
                .setLeftImageOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }
}
