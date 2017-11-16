package kap.com.smarthome.android.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPResponseMsgBase;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.IRKey;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.utils.JsonUtils;
import kap.com.smarthome.android.presenter.utils.ResourcesUtils;
import kap.com.smarthome.android.presenter.utils.UUIDUtils;
import kap.com.smarthome.android.ui.activity.RemoteControlTemplateLearnActivity;
import kap.com.smarthome.android.ui.view.MyPopupWindow;

/**
 * Created by Administrator on 2017/10/20 0020.
 */

public class RemoteControlFragment extends BaseFragment {

    private RemoteControlTemplateLearnActivity mActivity;
    private int model_id;
    private View mFragmentView = null;
    private View mNumberView  = null;

    //所有的按钮
    private Button btn0 , btn1 , btn2 , btn3 , btn4, btn5 ,
            btn6 , btn7 , btn8 , btn9 , btn10, btn11,
            btn12 , btn13 , btn14 , btn15 , btn16, btn17;

    //按钮的集合
    private List<Button> mButtonList;

    //数字按钮
    private Button numberBtn;

    //当前选中进行学习的按钮 , 选中的按钮选中呈现为红色
    private Button mCurrentChoseBtn;

    //上一次选中的按钮
    private Button mLastChoseBtn;

    //当前学习的按键的索引
    private int mCurrentLearnIndex = -1 ;

    // 数字键盘View mNumberView 的按键学习角标，当mCurrentLearnIndex为10的时候进行number的学习
    private int mNumberBtnIndex = -1;

    //学习按钮的提示 提示现在学习的是那个按键 角标上限值由需要学习的按键的总个数确定 mTv1KeysName.length
    private int mLearnNoteTvIndex = -1;

    //控件的名字的数组
    private String[] mTv1KeysName = null;

    //该红外学习 遥控器设备的 实例
    private Devices mNewIrRcDevice;

    //红外学习的一个按键的实例
    private IRKey mIrKey = null;

    //提示的信息
    private String mKeyInfo;

    //第一次学习的code
    private String firstCode = "";

    //第二次学习的code
    private String secondCode = "";

    private RemoteControlTemplateLearnActivity.OnIrKeyBtnLearnControlListen onIrKeyBtnLearnControlListen;

    //实现弹出PopupWindow的时候设备背景界面变暗
    private Window mWindow;
    private float windowAlpha = 0.8f;

    /**
     * 处理红外学习
     */
    private static boolean IS_HANDLE_IR = false;
    private static final int HANDLE_IR_CODE = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLE_IR_CODE:
                    if (IS_HANDLE_IR)
                        return;
                    IS_HANDLE_IR = true;
                    String irCode = (String) msg.obj;
                    handleIrCode(irCode);
                    IS_HANDLE_IR = false;
                    break;
                default:
                    break;
            }

        }
    };



    public static RemoteControlFragment newInstance(String rcType) {
        RemoteControlFragment fragment = new RemoteControlFragment();
        Bundle bundle = new Bundle();
        bundle.putString("rc_model_type", rcType);
        fragment.setArguments(bundle);
        return fragment;
    }

    public RemoteControlFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String model = bundle.getString("rc_model_type");

        if(model.equals("tv1")){
            mFragmentView = inflater.inflate(R.layout.rc_tv_temp_1, container, false);
            mButtonList = new ArrayList<>();
            mTv1KeysName = ResourcesUtils.getStringArray(mActivity, R.array.tv1_key_names);

            numberBtn = (Button) mFragmentView.findViewById(R.id.tv1_btn_number);

            mNumberView  = inflater.inflate(R.layout.tv1_number_dialog , null);

            numberBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final MyPopupWindow popWindow = new MyPopupWindow(getContext(), mNumberView, R.style.right_popupWindow_anim);
                    //弹出PopupWindow设置窗口背景变暗
                    mWindow = mActivity.getWindow();
                    popWindow.setWindowAlpha(mWindow, windowAlpha);
                    popWindow.showAtLocation(mNumberView , Gravity.CENTER_VERTICAL , 0 , 0);
                }
            });


            btn0 = (Button) mFragmentView.findViewById(R.id.tv1_btn_enter);
            btn1 = (Button) mFragmentView.findViewById(R.id.tv1_btn_power);
            btn2 = (Button) mFragmentView.findViewById(R.id.tv1_btn_vol_mute);
            btn3 = (Button) mFragmentView.findViewById(R.id.tv1_btn_vol_add);
            btn4 = (Button) mFragmentView.findViewById(R.id.tv1_btn_vol_sub);
            btn5 = (Button) mFragmentView.findViewById(R.id.tv1_btn_left);
            btn6 = (Button) mFragmentView.findViewById(R.id.tv1_btn_top);
            btn7 = (Button) mFragmentView.findViewById(R.id.tv1_btn_right);
            btn8 = (Button) mFragmentView.findViewById(R.id.tv1_btn_bottom);
            btn9 = (Button) mFragmentView.findViewById(R.id.tv1_btn_ok);
            btn10 = (Button) mFragmentView.findViewById(R.id.tv1_btn_number);
            btn11 = (Button) mFragmentView.findViewById(R.id.tv1_btn_back);
            btn12 = (Button) mFragmentView.findViewById(R.id.tv1_btn_menu);


            mButtonList.add(btn0);
            mButtonList.add(btn1);
            mButtonList.add(btn2);
            mButtonList.add(btn3);
            mButtonList.add(btn4);
            mButtonList.add(btn5);
            mButtonList.add(btn6);
            mButtonList.add(btn7);
            mButtonList.add(btn8);
            mButtonList.add(btn9);
            mButtonList.add(btn10);
            mButtonList.add(btn11);
            mButtonList.add(btn12);

            //初始化第一个按键 为红色
            mButtonList.get(0).setPressed(true);
        }


        return mFragmentView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void onResume() {
        super.onResume();



    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mActivity = (RemoteControlTemplateLearnActivity)activity;

        mNewIrRcDevice = mActivity.newIrRcDevice;
        Log.e("UDP", " 红外设备的ID号 : "+ mNewIrRcDevice.getDEVICE_ID());

        onIrKeyBtnLearnControlListen = new RemoteControlTemplateLearnActivity.OnIrKeyBtnLearnControlListen() {
            @Override
            public void last() { //回到上一个
                if(mCurrentLearnIndex == 0){
                    mCurrentLearnIndex = 0;
                }else {
                    mCurrentLearnIndex --;
                }

                if(mCurrentChoseBtn != null) {
                    mLastChoseBtn = mCurrentChoseBtn;
                }

                mCurrentChoseBtn = mButtonList.get(mCurrentLearnIndex);
                mCurrentChoseBtn.setPressed(true);
                mLastChoseBtn.setPressed(false);
                firstCode = "";
                secondCode = "";
            }

            @Override
            public void next() {//回到下一个
                if(mCurrentLearnIndex == mButtonList.size() - 1){
                    mCurrentLearnIndex = mButtonList.size() -1 ;
                }else {
                    mCurrentLearnIndex ++;
                }

                if(mCurrentChoseBtn != null) {
                    mLastChoseBtn = mCurrentChoseBtn;
                }

                mCurrentChoseBtn = mButtonList.get(mCurrentLearnIndex);
                mCurrentChoseBtn.setPressed(true);
                mLastChoseBtn.setPressed(false);
                firstCode = "";
                secondCode = "";
            }

            @Override
            public void reSet() { //重置

            }
        };

        mActivity.setOnIrKeyBtnLearnControlListen(onIrKeyBtnLearnControlListen);

    }

    /**
     * 收到中继发送过来的红外学习Data
     * @param item
     */
    @Override
    public void uiLearnIrDataSaveListen(UDPReceiverData item) {
        String data = item.data;
        UDPResponseMsgBase irCodeMsg = JsonUtils.stringToObject(data , UDPResponseMsgBase.class);
        Message msg = new Message();
        msg.what = HANDLE_IR_CODE;
        msg.obj = irCodeMsg.getBODY().getIRCODE();
        handler.sendMessage(msg);
    }



    private void handleIrCode(String irCode) {
        if(mLearnNoteTvIndex < mTv1KeysName.length -1 ) {//mTv1KeysName.length 是所有需要学习的按键总数 ，当前学习的按键数小于该值的时候进行红外学习

            Log.w("UDP", "handleIrCode: mLearnNoteTvIndex = " +  mLearnNoteTvIndex + "mTv1KeysName.length = " + mTv1KeysName.length);

            if (firstCode.equals("")) {
                mIrKey = new IRKey();
                firstCode = irCode;
                mIrKey.setKEY1(firstCode);

                //从-1开始自增 第一次学习的时候为0
                mLearnNoteTvIndex++;

                if (mCurrentLearnIndex == 10) {//学习数字按钮的时候 mCurrentLearnIndex 始终为10, 直达数字键盘学习结束
                    mNumberBtnIndex++;
                    if (mNumberBtnIndex == 10) {//数字键盘有十一个按键 ，从1-9-“--/--”-0
                        mCurrentLearnIndex++;
                        if (mCurrentChoseBtn != null) {
                            mLastChoseBtn = mCurrentChoseBtn;
                        }
                        mCurrentChoseBtn = null;
                        mCurrentChoseBtn = mButtonList.get(mCurrentLearnIndex);
                    }
                } else {
                    mCurrentLearnIndex++;
                    if (mCurrentChoseBtn != null) {
                        mLastChoseBtn = mCurrentChoseBtn;
                    }
                    mCurrentChoseBtn = null;
                    mCurrentChoseBtn = mButtonList.get(mCurrentLearnIndex);
                }

                mKeyInfo = "'" + mTv1KeysName[mLearnNoteTvIndex] + "'"
                        + getString(R.string.it_learn_oneLearn);

                Log.e("UDP", "第一次学习" + firstCode);


            } else if (!firstCode.equals("") && secondCode.equals("")) {
                secondCode = irCode;
                mIrKey.setKEY2(secondCode);
                mIrKey.setBUTTON_NAME(mTv1KeysName[mLearnNoteTvIndex]);
                mIrKey.setDEVICE_ID(mNewIrRcDevice.getDEVICE_ID());
                mIrKey.setINDEX(mLearnNoteTvIndex);
                mIrKey.setUSER_ID(AllVariable.CURRENT_USER_ID);
                mIrKey.setGUID(UUIDUtils.getUUID());

                Log.e("UDP", "空外码值 =  " + mIrKey);

                mKeyInfo = "'" + mTv1KeysName[mLearnNoteTvIndex] + "'"
                        + getString(R.string.it_learn_secondLearn);

                if (DataBaseHandle.insertIrKey(mIrKey)) {//按键写入到数据库成功
                    firstCode = "";
                    secondCode = "";
                    mIrKey = null;
                    Log.e("UDP", "保存成功 =  " + mIrKey);

                } else {//按键写入到数据库失败
                    firstCode = "";
                    secondCode = "";
                    mIrKey = null;
                    Log.e("UDP", "保存失败 =  " + mIrKey);
                }
            }

        }else{
            mKeyInfo = "红外学习结束，点击完成保存";
            Log.i("UDP", "handleIrCode: mLearnNoteTvIndex = " +  mLearnNoteTvIndex + "mTv1KeysName.length = " + mTv1KeysName.length);
        }


        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(mLastChoseBtn != null)
                    mLastChoseBtn.setPressed(false);

                if(mCurrentChoseBtn != null)
                mCurrentChoseBtn.setPressed(true);

                mActivity.setmLearnKeyNoteTv(mKeyInfo);
            }
        });
    }





}
