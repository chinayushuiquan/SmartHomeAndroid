package kap.com.smarthome.android.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPResponseMsgBase;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseMsgLogin;
import kap.com.smarthome.android.communication.http.constants.HTTPMsgINSIP;
import kap.com.smarthome.android.communication.http.constants.HttpResponseCode;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.presenter.control.SharedPreferencesHandle;
import kap.com.smarthome.android.ui.activity.ChoseCountryNumActivity;
import kap.com.smarthome.android.ui.activity.MainHomeActivity;
import kap.com.smarthome.android.ui.view.MyLoadingDialog;


public class LoginSmsFragment extends Fragment implements View.OnClickListener{

    private static  final  String  LOGIN_TYPE = "2";

    private static  final int  GET_CODE_TIME = 60*1000;
    private static  final int  CHANGE_TIME = 1000;

    private boolean runningThree = false;
    private String second;


    private String mPhoneNum = "86-18123610958";
    private String mLocalFlag = "zh";
    private static final int CHOSE_COUNTRY_NUM_REQ= 100;
    private String countryNum = "86";

    private TextInputEditText mPhoneNumEdit;
    private TextInputEditText mVerifiCodeEdit;

    private String phone_num ;
    private String verification_code ;

    private Button mLoginBtn;
    private Button mGetVerifiCodeBtn;
    private Button mChosePhoneNumLocalBtn;

    private MyLoadingDialog myLoadingDialog ;

    private Activity  mActivity;

    public static LoginSmsFragment newInstance(String param1) {
        LoginSmsFragment fragment = new LoginSmsFragment();
        Bundle args = new Bundle();
        args.putString("name", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public LoginSmsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sms_login, container, false);

        mPhoneNumEdit = (TextInputEditText) view.findViewById(R.id.login_sms_phone_number_et);
        mVerifiCodeEdit = (TextInputEditText) view.findViewById(R.id.login_sms_verifi_code_psw_et);

        mLoginBtn = (Button) view.findViewById(R.id.login_sms_btn);
        mGetVerifiCodeBtn = (Button) view.findViewById(R.id.login_sms_get_verifi_code_btn);
        mChosePhoneNumLocalBtn = (Button) view.findViewById(R.id.login_sms_local_number_btn);

        mLoginBtn.setOnClickListener(this);
        mGetVerifiCodeBtn.setOnClickListener(this);
        mChosePhoneNumLocalBtn.setOnClickListener(this);

        second = getResources().getString(R.string.get_verify_code_second);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.login_sms_btn:
                remoteSmsLogin();
                break;
            case  R.id.login_sms_get_verifi_code_btn:
                httpRequestGetVerification();
                break;
            case R.id.login_sms_local_number_btn:
                chosePhoneNumLocal();
                break;
        }
    }


    /**
     *  选择电话号码的地区代号
     */
    private void chosePhoneNumLocal() {
        Intent intent = new Intent(mActivity, ChoseCountryNumActivity.class);
        startActivityForResult(intent, CHOSE_COUNTRY_NUM_REQ);
    }


    /**
     * 请求验证码
     */
    private void httpRequestGetVerification() {
        downTimer.start();
        getEditText();
        ServerCommunicationHandle.getRegisterVerification(phone_num , mLocalFlag, "4", new UIHttpCallBack() {
            @Override
            public void success(Object object) {
                if(object != null){
                    final HTTPResponseMsgBase httpJsonMsgBase = (HTTPResponseMsgBase) object;
                    if(httpJsonMsgBase.getBODY().getRESULT().equals(HttpResponseCode.SUCCESS)){ //返回成功 .开启定时器，进行倒计时
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mActivity, "请查看短信验证码", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }
            }
            @Override
            public void failure(Object object) {

            }
        });
    }


    /**
     * 登录 点击登录按钮，执行手机和验证码登录方法
     */
    private void remoteSmsLogin() {
        showLoadingDialog();
        getEditText();
        checkInputIsCorrect();
    }


    /**
     * 获取输入的内容
     */
    private void getEditText() {
        phone_num = countryNum+"-" + mPhoneNumEdit.getText().toString().trim();
        verification_code = mVerifiCodeEdit.getText().toString().trim();
    }

    /**
     * 检查输入是否合法
     */
    private void checkInputIsCorrect() {
        if (TextUtils.isEmpty(phone_num)){
            Toast.makeText(mActivity, "手机号不能为空！" , Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return;
        }

        if (TextUtils.isEmpty(verification_code)){
            Toast.makeText(mActivity, "验证码不能为空！" , Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return;
        }

        //在数据校验正确之后进行登录操作
        httpLoginRemote();
    }


    /**
     * 显示注册的loadingDialog
     */
    private void showLoadingDialog() {
        if(myLoadingDialog == null) {
            myLoadingDialog = new MyLoadingDialog(mActivity).setMessage("正在登录...");
        }
        myLoadingDialog.show();
    }


    /**
     * 关闭注册的loadingDialog
     */
    private void dismissLoadingDialog() {
        if(myLoadingDialog != null && myLoadingDialog.isShowing()) {
            myLoadingDialog.dismiss();
        }
        myLoadingDialog = null;
    }


    /**
     * http 远程登录请求
     */
    private void httpLoginRemote() {
        ServerCommunicationHandle.loginRemoteServer("", phone_num,  verification_code , "", LOGIN_TYPE, new UIHttpCallBack() {
            @Override
            public void success(Object object) {
                if(object != null){
                    final HTTPResponseMsgLogin httpResponseMsgLogin = (HTTPResponseMsgLogin) object;
                    if(httpResponseMsgLogin.getBODY().getINSTP().equals(HTTPMsgINSIP.USER_LOGIN_ACK)){
                        if(httpResponseMsgLogin.getBODY().getRESULT().equals(HttpResponseCode.SUCCESS)){
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String  userId =  httpResponseMsgLogin.getBODY().getUSERID();
                                    String  sessionId = httpResponseMsgLogin.getBODY().getSESSIONID();

                                    //使用SharePreferences保存当前登录用户的UserID
                                    SharedPreferencesHandle.initSharedPreferencesHandle(mActivity.getApplicationContext()).saveCurrentLoginUserId(userId);
                                    AllVariable.CURRENT_USER_ID = userId ;


                                    if(DataBaseHandle.updateUserByPhone(phone_num , userId , sessionId, 1) != -1){//用户数据更新成功
                                        Toast.makeText(mActivity,"登录成功!", Toast.LENGTH_SHORT).show();
                                        dismissLoadingDialog();
                                        startActivity( new Intent(mActivity, MainHomeActivity.class));
                                    }
                                }
                            });
                        }else if(httpResponseMsgLogin.getBODY().getRESULT().equals(HttpResponseCode.VERIFI_CODE_ERROE)){
                            Toast.makeText(mActivity,"验证码错误!", Toast.LENGTH_SHORT).show();
                            dismissLoadingDialog();
                        }else if(httpResponseMsgLogin.getBODY().getRESULT().equals(HttpResponseCode.DATA_ERROR)){
                            Toast.makeText(mActivity,"数据非法!", Toast.LENGTH_SHORT).show();
                            dismissLoadingDialog();
                        }
                    }
                }
            }
            @Override
            public void failure(Object object) {//登录失败

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
            mGetVerifiCodeBtn.setText((l / 1000) + second);
        }

        @Override
        public void onFinish() {
            runningThree = false;
            mGetVerifiCodeBtn.setText(getString(R.string.get_verify_code_again));
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 100){
            Bundle bundle = data.getExtras();
            if(bundle != null){
                countryNum = bundle.getString("result_country_number", "86");
                mChosePhoneNumLocalBtn.setText("+"+countryNum+">");
            }
        }
    }
}
