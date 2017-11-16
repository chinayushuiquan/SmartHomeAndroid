package kap.com.smarthome.android.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseMsgLogin;
import kap.com.smarthome.android.communication.http.constants.HTTPMsgINSIP;
import kap.com.smarthome.android.communication.http.constants.HttpResponseCode;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.presenter.control.SharedPreferencesHandle;
import kap.com.smarthome.android.ui.activity.ForgetPswOneActivity;
import kap.com.smarthome.android.ui.activity.MainHomeActivity;
import kap.com.smarthome.android.ui.activity.RegisterActivity;
import kap.com.smarthome.android.ui.view.MyLoadingDialog;


public class LoginPswFragment extends Fragment implements View.OnClickListener{

    private static  final  String  LOGIN_TYPE = "1";

    private Activity  mActivity;

    private Button mRegister_btn;
    private TextView mForget_Psw_Tv;

    private Button mLoginBtn;
    private TextInputEditText mLoginAccountEdit;
    private TextInputEditText mLoginPassWordEdit;

    private MyLoadingDialog myLoadingDialog ;


    private String account ;
    private String password ;


    public static LoginPswFragment newInstance(String param1) {
        LoginPswFragment fragment = new LoginPswFragment();
        Bundle args = new Bundle();
        args.putString("name", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public LoginPswFragment() {}

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
        View view = inflater.inflate(R.layout.fragment_psw_login, container, false);

        mRegister_btn = (Button) view.findViewById(R.id.login_register_btn);
        mRegister_btn.setOnClickListener(this);

        mForget_Psw_Tv = (TextView) view.findViewById(R.id.login_forget_psw_tv);
        mForget_Psw_Tv.setOnClickListener(this);

        mLoginBtn = (Button) view.findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(this);


        mLoginAccountEdit = (TextInputEditText) view.findViewById(R.id.login_account_et);
        mLoginPassWordEdit = (TextInputEditText) view.findViewById(R.id.login_account_psw_et);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_register_btn:
                startActivity(new Intent(mActivity, RegisterActivity.class));
                break;
            case  R.id.login_forget_psw_tv:
                startActivity(new Intent(mActivity , ForgetPswOneActivity.class));
                break;
            case  R.id.login_btn://登录
                remoteLogin();
                break;
        }
    }

    /**
     * 登录 点击登录按钮，执行该登录方法
     */
    private void remoteLogin() {
        showLoadingDialog();
        getEditText();
        checkInputIsCorrect();
    }



    /**
     * 获取输入的内容
     */
    private void getEditText() {
        account =  mLoginAccountEdit.getText().toString().trim();
        password = mLoginPassWordEdit.getText().toString().trim();

    }

    /**
     * 检查输入是否合法
     */
    private void checkInputIsCorrect() {
        if (TextUtils.isEmpty(account)){
            Toast.makeText(mActivity, "账号不能为空！" , Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return ;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(mActivity, "请输入密码！" , Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return;
        }

        if(account.length() < 3 ) {
            Toast.makeText(mActivity, "账号不能少于3个字符" , Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return ;
        }

        if(account.length() > 16) {
            Toast.makeText(mActivity, "账号不能超过16个字符" , Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return ;
        }

        //在数据校验正确之后进行远程的登录
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
        ServerCommunicationHandle.loginRemoteServer(account, "", "", password, LOGIN_TYPE, new UIHttpCallBack() {
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
                                    AllVariable.CURRENT_USER_ID = userId;

                                    //1表示现在的状态是登录成功
                                    if(DataBaseHandle.updateUserByAccount(account , userId , sessionId, 1) != -1){//用户数据更新成功
                                        Toast.makeText(mActivity,"登录成功!", Toast.LENGTH_SHORT).show();
                                        dismissLoadingDialog();
                                        startActivity( new Intent(mActivity, MainHomeActivity.class));
                                    }
                                }
                            });
                        }else if(httpResponseMsgLogin.getBODY().getRESULT().equals(HttpResponseCode.ACCOUNT_IS_NOT_EXIST)){
                            dismissLoadingDialog();
                            Toast.makeText(mActivity,"指定账户不存在!", Toast.LENGTH_SHORT).show();
                        }else if(httpResponseMsgLogin.getBODY().getRESULT().equals(HttpResponseCode.OTHER_ERROR)){
                            dismissLoadingDialog();
                            Toast.makeText(mActivity,"登录失败!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            @Override
            public void failure(Object object) {
                //登录失败

            }
        });

    }



}
