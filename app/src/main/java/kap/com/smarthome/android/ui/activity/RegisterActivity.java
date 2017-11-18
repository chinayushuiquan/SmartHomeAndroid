package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Toast;

import kap.com.smarthome.android.R;

import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPResponseBaseMsg;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.Login.HTTPResponseLoginMsg;
import kap.com.smarthome.android.communication.http.constants.HTTPMsgINSIP;
import kap.com.smarthome.android.communication.http.constants.HttpResponseCode;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.presenter.control.SharedPreferencesHandle;
import kap.com.smarthome.android.presenter.utils.DataLegitimacyCheckUtils;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;


/**
 * Created by Administrator on 2017/8/17 0017.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    /**
     * 操作结果类型
     0 - 成功；
     9000 - 数据非法；
     1001 - 开户的帐户已存在；
     9999 - 其它错误（未知异常）
     */
    private static final String SUCCESS = "0";

    /**
     * 注册类型
     1-	远程登录账号/密码 （校验 “远程登录账号”、“用户密码”、“用户的电话号码”是否为空，“远程登录账号”及“用户的电话号码”不可以全局重复，“手机验证码”必须正确）；
     2-	电话号（校验“用户的电话号码”是否为空，“用户的电话号码”不可以全局重复，“手机验证码”必须正确）；
     3-	微信
     4-	QQ
     5-	E-MAIL（暂未提供）;
     6-	随机码（暂未提供）
     */
    private static final String REGISTER_TYPE = "1";

    /**
     * 登录类型
     1-	远程登录账号/密码 （校验 “远程登录账号”、“用户密码”、“用户的电话号码”是否为空，“手机验证码”必须正确）；
     2-	电话号（校验“用户的电话号码”是否为空，“手机验证码”必须正确）；
     3-	微信
     4-	QQ
     5-	E-MAIL（暂未提供）;
     6-	随机码（暂未提供）
     */
    private static final String LOGIN_TYPE = "1";


    private  TextInputEditText mUserAccountEdit;
    private  TextInputEditText mPhoneNumberEdit;
    private  TextInputEditText mVerifyCodeEdit;
    private  TextInputEditText mPassWordEdit;
    private  TextInputEditText mVerifyCodePassWordEdit;

    private Button mGetVerifyCodeBtn;
    private Button mRegisterBtn;
    private Button mChosePhoneNumLocalBtn;


    private String mPhoneNum = "86-18123610958";
    private String mLocalFlag = "zh";
    private static final int CHOSE_COUNTRY_NUM_REQ= 100;
    private String countryNum = "86";


    //验证码相关
    private boolean runningThree = false;
    private String second ;
    private static  final int  GET_CODE_TIME = 60*1000;
    private static  final int  CHANGE_TIME = 1000;



    private String account ;
    private String phone_num = "";
    private String verification_code ;
    private String password ;
    private String verification_password ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUserAccountEdit = (TextInputEditText) findViewById(R.id.register_account_et);
        mPhoneNumberEdit = (TextInputEditText) findViewById(R.id.register_phone_number_et);
        mVerifyCodeEdit = (TextInputEditText) findViewById(R.id.register_verifi_code_psw_et);
        mPassWordEdit = (TextInputEditText) findViewById(R.id.register_psd_psw_et);
        mVerifyCodePassWordEdit = (TextInputEditText) findViewById(R.id.register_confirm_psd_et);

        mUserAccountEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        mPhoneNumberEdit.setImeOptions(EditorInfo.IME_ACTION_DONE);

        second = getResources().getString(R.string.get_verify_code_second);

        mGetVerifyCodeBtn = (Button) findViewById(R.id.register_get_verification_code_btn);
        mChosePhoneNumLocalBtn = (Button) findViewById(R.id.register_chose_phone_number_local);
        mRegisterBtn = (Button) findViewById(R.id.register_btn);

        mGetVerifyCodeBtn.setOnClickListener(this);
        mChosePhoneNumLocalBtn.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);

    }


    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
        myTopBarBuilder.setLeftImage(R.drawable.back_icon)
                       .setTitleText(R.string.register_account)
                       .setTitleColor(R.color.white)
                       .setLeftImageOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               finish();
                           }
                       });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_get_verification_code_btn:
                //请求获取验证码
                getVerification();
                break;

            case R.id.register_chose_phone_number_local:
                //选择电话区号
                chosePhoneNumLocal();
                break;

            case R.id.register_btn:
                //注册
                signUPAccount();
                break;

        }
    }

    /**
     * 注册账号
     */
    private void signUPAccount() {
         showLoadingDialog("");
         getEditText();
         checkInputIsCorrect();
    }


    /**
     * 获取输入的内容
     */
    private void getEditText() {
        account = mUserAccountEdit.getText().toString().trim();
        phone_num = mPhoneNumberEdit.getText().toString().trim();
        verification_code = mVerifyCodeEdit.getText().toString().trim();
        password = mPassWordEdit.getText().toString().trim();
        verification_password = mVerifyCodePassWordEdit.getText().toString().trim();
    }

    /**
     * 检查输入是否合法
     */
    private void checkInputIsCorrect() {
        if (TextUtils.isEmpty(account)){
            Toast.makeText(this, "账号不能为空！" , Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return;
        }

        if (TextUtils.isEmpty(phone_num)){
            Toast.makeText(this, "手机号不能为空！" , Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return;
        }

        if (TextUtils.isEmpty(verification_code)){
            Toast.makeText(this, "验证码不能为空！" , Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "请输入密码！" , Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return;
        }

        if (TextUtils.isEmpty(verification_password)){
            Toast.makeText(this, "请输入确认密码！" , Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return;
        }

        if(!password.equals(verification_password)){
            Toast.makeText(this, "两次输入的密码不一样，请重新输入！" , Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return ;
        }

        if(account.length() < 3 ) {
            Toast.makeText(this, "账号不能少于3个字符" , Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return ;
        }

        if(account.length() > 16) {
            Toast.makeText(this, "账号不能超过16个字符" , Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return ;
        }

        if(!DataLegitimacyCheckUtils.checkUserAccount(account)){
            Toast.makeText(this, "账号格式错误！1～20位的中文或者英文" , Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return;
        }

        if(!DataLegitimacyCheckUtils.checkUserPsw(password)){
            Toast.makeText(this, "密码格式错误！6-18位数字密码组合" , Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return;
        }


        if(!DataLegitimacyCheckUtils.checkVerifyCode(verification_code)){
            Toast.makeText(this, "验证码格式错误！6位数字" , Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return;
        }


        //如果输入的数据校验正确 进行登录
        httpSignUserAccount();
    }



    /**
     * 请求服务器进行注册
     */
    private void httpSignUserAccount() {

        //手机号码需要加上一个国际标志数字，例如中国“86”

        final String phoneNum = countryNum+"-"+phone_num;

        ServerCommunicationHandle.registerUserAccount(account,
                phoneNum, verification_code, password, REGISTER_TYPE , new UIHttpCallBack(){
                    @Override
                    public void success(Object object) {
                        if(object != null){
                            final HTTPResponseBaseMsg httpResponseBase = (HTTPResponseBaseMsg) object;
                            if(httpResponseBase.getBODY().getINSTP().equals(HTTPMsgINSIP.NEW_ACCOUNT_ACK)){
                                if(httpResponseBase.getBODY().getRESULT().equals(SUCCESS)){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showLoadingDialog("正在登录...");
                                            /**
                                             * 新增用户数据
                                             */
                                            if(DataBaseHandle.insertUser(account, phone_num)){

                                                ServerCommunicationHandle.loginRemoteServer(account, phoneNum, verification_code, password, LOGIN_TYPE, new UIHttpCallBack() {
                                                    @Override
                                                    public void success(Object object) {
                                                        if(object != null){
                                                            final HTTPResponseLoginMsg httpResponseLoginMsg = (HTTPResponseLoginMsg) object;
                                                            if(httpResponseLoginMsg.getBODY().getINSTP().equals(HTTPMsgINSIP.USER_LOGIN_ACK)){
                                                                if(httpResponseLoginMsg.getBODY().getRESULT().equals(HttpResponseCode.SUCCESS)){
                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            Toast.makeText(RegisterActivity.this,"登录成功!", Toast.LENGTH_SHORT).show();

                                                                            String  userId =  httpResponseLoginMsg.getBODY().getUSERID();
                                                                            String  sessionId = httpResponseLoginMsg.getBODY().getSESSIONID();

                                                                            //使用SharePreferences保存当前登录用户的UserID
                                                                            SharedPreferencesHandle.initSharedPreferencesHandle(getApplicationContext()).saveCurrentLoginUserId(userId);
                                                                            AllVariable.CURRENT_USER_ID = userId;

                                                                            if(DataBaseHandle.updateUserByAccount(account , userId , sessionId, 1) != -1){//用户数据更新成功
                                                                                dismissLoadingDialog();
                                                                                startActivity(new Intent(RegisterActivity.this, SystemSetRegisterActivity.class));
                                                                                finish();
                                                                            }
                                                                        }
                                                                    });
                                                               }else{
                                                                    dismissLoadingDialog();
                                                                    Toast.makeText(RegisterActivity.this,"获取用户ID失败,请再次登录!", Toast.LENGTH_SHORT).show();
                                                                    startActivity(new Intent(RegisterActivity.this, MainHomeActivity.class));
                                                                    finish();
                                                                }
                                                            }

                                                        }
                                                    }
                                                    @Override
                                                    public void failure(Object object) {//登录失败
                                                        dismissLoadingDialog();
                                                        Toast.makeText(RegisterActivity.this,"登录失败!", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            }

                                        }
                                    });
                                }else if(httpResponseBase.getBODY().getRESULT().equals(AllConstants.SIGN_ACCOUNT_EXIST)){
                                    Toast.makeText(RegisterActivity.this, "账号已经存在！", Toast.LENGTH_SHORT).show();
                                    dismissLoadingDialog();
                                }else if(httpResponseBase.getBODY().getRESULT().equals(AllConstants.SIGN_DATA_ILLEGAL)){
                                    Toast.makeText(RegisterActivity.this, "数据非法，请检查！", Toast.LENGTH_SHORT).show();
                                    dismissLoadingDialog();
                                }else if(httpResponseBase.getBODY().getRESULT().equals(AllConstants.SIGN_OTHER_ERROR)){
                                    Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
                                    dismissLoadingDialog();
                                }
                            }

                        }
                    }
                    @Override
                    public void failure(Object object) {
                        Toast.makeText(RegisterActivity.this, "服务器错误！", Toast.LENGTH_SHORT).show();
                        dismissLoadingDialog();
                    }
                });
    }


    /**
     *  选择电话号码的地区代号
     */
    private void chosePhoneNumLocal() {
        Intent intent = new Intent(this, ChoseCountryNumActivity.class);
        startActivityForResult(intent, CHOSE_COUNTRY_NUM_REQ);
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


    private void httpRequestGetVerification() {

        String phoneNum = countryNum+"-"+mPhoneNumberEdit.getText().toString().trim();

        ServerCommunicationHandle.getRegisterVerification(phoneNum, mLocalFlag, "1", new UIHttpCallBack() {
            @Override
            public void success(Object object) {
                if(object != null){
                    HTTPResponseBaseMsg httpResponseBase = (HTTPResponseBaseMsg) object;
                    if(httpResponseBase.getBODY().getRESULT().equals(SUCCESS)){
                        //开启定时器，进行倒计时
                        downTimer.start();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //返回成功，弹出提示
                                Toast.makeText(RegisterActivity.this, "请查看短信验证码", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
            @Override
            public void failure(Object object) {
                Toast.makeText(RegisterActivity.this, "获取验证码错误！", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 100){
            Bundle bundle = data.getExtras();
            if(bundle != null){
                countryNum = bundle.getString("result_country_number", "86");
                mChosePhoneNumLocalBtn.setText("+"+countryNum+">");
            }
        }
    }

    /**
     * 实现验证码的一分钟倒计时功能
     */
    private CountDownTimer downTimer = new CountDownTimer(GET_CODE_TIME , CHANGE_TIME) {
        @Override
        public void onTick(long l) {
            runningThree = true;
            mGetVerifyCodeBtn.setText((l / 1000) + second);
        }

        @Override
        public void onFinish() {
            runningThree = false;
            mGetVerifyCodeBtn.setText(getString(R.string.get_verify_code_again));
        }
    };



}
