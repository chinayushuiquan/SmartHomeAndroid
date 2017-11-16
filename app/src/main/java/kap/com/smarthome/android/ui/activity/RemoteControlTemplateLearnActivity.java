package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.DATABean.DeviceData;
import kap.com.smarthome.android.communication.bean.base.DATABean.IrkeysData;
import kap.com.smarthome.android.communication.bean.base.DATABean.UDPDevicesData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.IRKey;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.BeanDataConvertUtils;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.RelayBoxUDPHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.ui.fragment.RemoteControlFragment;
import kap.com.smarthome.android.ui.view.MyLoadingDialog;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by yushq on 2017/10/20 0020.
 */

public class RemoteControlTemplateLearnActivity extends  BaseActivity implements View.OnClickListener{

    private Fragment mTemplateFragment;

    private RelayBox channelRelayBox;

    private LinearLayout bottomLayout;

    private MyTopBarBuilder mTopBarBuilder;

    private TextView mStepNextTv;

    private TextView mLearnKeyNoteTv;

    private RelativeLayout mNextRl;
    private RelativeLayout mLastRl;

    //需要添加的设备实例
    public Devices newIrRcDevice;

    private OnIrKeyBtnLearnControlListen onIrKeyBtnLearnControlListen;

    //顶部右侧的导航按钮，初始化的状态下点击发送进入红外学习指令， 再次点击进行保存该红外设备
    private boolean isLearnIrDone = false;


    private List<IRKey> irKeyList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.template_learn_activity);

        channelRelayBox = (RelayBox) getIntent().getSerializableExtra("match_relay_box");

        newIrRcDevice = (Devices) getIntent().getSerializableExtra("add_ir_device");

        mTemplateFragment = RemoteControlFragment.newInstance("tv1");

        bottomLayout = (LinearLayout) findViewById(R.id.ll_bottom);

        mStepNextTv = (TextView) findViewById(R.id.rc_learn_again_btn);

        mLearnKeyNoteTv = (TextView) findViewById(R.id.ir_rc_learn_note_tv);

        mNextRl = (RelativeLayout) findViewById(R.id.rc_learn_next_btn);

        mLastRl = (RelativeLayout) findViewById(R.id.rc_learn_last_btn);

        mStepNextTv.setOnClickListener(this);

        mNextRl.setOnClickListener(this);

        mLastRl.setOnClickListener(this);

        initFirstFragment();

        /**
         * 先发一个退出红外学习的指令
         */
        RelayBoxUDPHandle.exitIrLearn(channelRelayBox.getBOX_ID(),channelRelayBox.getIP());

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //界面退出的时候退出红外学习
        RelayBoxUDPHandle.exitIrLearn(channelRelayBox.getBOX_ID(),channelRelayBox.getIP());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rc_learn_again_btn:
                //重置
                onIrKeyBtnLearnControlListen.reSet();

                break;
            case R.id.rc_learn_next_btn:
                //跳过到下一个
                onIrKeyBtnLearnControlListen.next();

                break;
            case R.id.rc_learn_last_btn:
                 //返回上一个
                onIrKeyBtnLearnControlListen.last();

                break;

        }
    }

    private void initFirstFragment(){
            getSupportFragmentManager().beginTransaction().
                    add(R.id.remote_control_fragment, mTemplateFragment).commit();
    }


    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder){
        mTopBarBuilder =  myTopBarBuilder.setLeftImage(R.drawable.back_icon_black)
                .setTitleBgRes(R.color.white)
                .setTitleText(R.string.choose_rc_template)
                .setRightText(R.string.template_learn)
                .setRightTextColor(R.color.orange)
                .setLeftImageOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        finish();
                    }
                }).setRightTextOnClickListener(new View.OnClickListener() { //进行红外学习
                    @Override
                    public void onClick(View v) {

                        if (isLearnIrDone) { //完成红外学习 ， 保存红外设备

                            List<Devices> newIrDevices = new ArrayList<>();
                            newIrDevices.add(newIrRcDevice);

                             //UDP 中继盒子添加设备
                            List<UDPDevicesData> udpDevicesDataList = BeanDataConvertUtils.convertToUDPDevicesData(newIrDevices);
                            RelayBoxUDPHandle.addRfDevice(udpDevicesDataList);

                            showLoadingDialog("");


                        } else { //开始让选中中继盒子进入红外学习状态
                            bottomLayout.setVisibility(View.VISIBLE);
                            mTopBarBuilder.setRightText(getString(R.string.done));
                            RelayBoxUDPHandle.startIrLearn(channelRelayBox.getBOX_ID(), channelRelayBox.getIP());
                            isLearnIrDone = true;
                        }
                    }
                });
              }




    public void setmLearnKeyNoteTv(String keyInfo){
        mLearnKeyNoteTv.setText(keyInfo);
    }


    /**
     * 监听Back键按下事件,方法1:
     * 注意:
     * super.onBackPressed()会自动调用finish()方法,关闭当前Activity.
     * 若要屏蔽Back键盘,注释该行代码即可
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("按下了back键   onBackPressed()");
    }


    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            System.out.println("按下了back键   onKeyDown()");
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }


    /**
     *  底部按钮的回调接口 ， 在选择的模板的Fragment进行实例化
     */
   public  interface  OnIrKeyBtnLearnControlListen{
       //下一个
       void last();

       //上一个
       void next();

        // 重置
        void reSet();
    }


    public void setOnIrKeyBtnLearnControlListen(OnIrKeyBtnLearnControlListen onIrKeyBtnLearnControlListen) {
        this.onIrKeyBtnLearnControlListen = onIrKeyBtnLearnControlListen;
    }


    /**
     * 中继盒子退出红外学习的 回调接口
     * @param item
     */
    @Override
    public void uiNoteRelayBoxExitIRDeviceRegisterCallback(UDPReceiverData item) {
        super.uiNoteRelayBoxExitIRDeviceRegisterCallback(item);
        Log.e("UDP", "uiNoteRelayBoxExitIRDeviceRegisterCallback: + 退出红外学习");
    }


    @Override
    public void uiReceiverAddDevicesCallback(UDPReceiverData udpReceiver) {
        super.uiReceiverAddDevicesCallback(udpReceiver);
        //向设备表中插入设备
        if (DataBaseHandle.insertOneDevice(newIrRcDevice)) {

            List<Devices> newIrDevices = new ArrayList<>();
            newIrDevices.add(newIrRcDevice);
            List<DeviceData>  deviceDataList = BeanDataConvertUtils.convertToDevicesData(newIrDevices);

            //先上传添加一个红外设备 如电视、空调、等遥控器
            ServerCommunicationHandle.addDevices(deviceDataList, new UIHttpCallBack() {
                @Override
                public void success(Object object) {

                    irKeyList = DataBaseHandle.queryOneIrDeviceAllKeys(newIrRcDevice.getDEVICE_ID());

                    //添加一个红外码值库
                    ServerCommunicationHandle.addIrLearnKeysData(irKeyList, new UIHttpCallBack() {
                        @Override
                        public void success(Object object) {
                            dismissLoadingDialog();

                            AllVariable.IS_BROAD_CAST_ADD_NEW_DEVICE = true;
                            startActivity(new Intent(RemoteControlTemplateLearnActivity.this, MainHomeActivity.class));
                        }

                        @Override
                        public void failure(Object object) {
                            dismissLoadingDialog();
                            Toast.makeText(RemoteControlTemplateLearnActivity.this , "红外码库添加失败！" , Toast.LENGTH_LONG).show();
                         }
                    }) ;
                }

                @Override
                public void failure(Object object) {
                    dismissLoadingDialog();
                    Toast.makeText(RemoteControlTemplateLearnActivity.this , "遥控器添加失败！" , Toast.LENGTH_LONG).show();
                }
            });
        }else{


        }
    }





}
