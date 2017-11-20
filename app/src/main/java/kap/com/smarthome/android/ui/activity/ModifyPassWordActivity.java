package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPResponseBaseMsg;
import kap.com.smarthome.android.communication.http.constants.HTTPMsgINSIP;
import kap.com.smarthome.android.communication.http.constants.HttpResponseCode;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public  class ModifyPassWordActivity extends BaseActivity{

    private EditText mOldPassWordEdit;
    private EditText mNewPassWordEdit;
    private EditText mConfirmPassWordEdit;

    private String mOldPasswordStr;
    private String mNewPasswordStr;
    private String mConfirmPasswordStr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

        mOldPassWordEdit = (EditText) findViewById(R.id.modify_old_password_edit);
        mNewPassWordEdit = (EditText) findViewById(R.id.modify_new_password_edit);
        mConfirmPassWordEdit = (EditText) findViewById(R.id.modify_confirm_new_password_edit);
        getPassWordStr();

    }



    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
        myTopBarBuilder.setLeftImage(R.drawable.back_icon_black)
                .setTitleText(R.string.modify_nick_name)
                .setRightText(R.string.save)
                .setRightTextColor(R.color.orange)
                .setRightTextOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //保存密码
                        changePassWord();
                    }
                })
                .setLeftImageOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }




    /**
     * xiugaimima
     */
    private void changePassWord() {
        getPassWordStr();
        showLoadingDialog("");
        httpModifyPassWord();
    }



    /**
     * 获取输入的用户昵称
     */
    private void getPassWordStr() {
        mOldPasswordStr = mOldPassWordEdit.getText().toString().trim();
        mNewPasswordStr = mNewPassWordEdit.getText().toString().trim();
        mConfirmPasswordStr = mConfirmPassWordEdit.getText().toString().trim();
    }


    /**
     * 修改服务端的保存的昵称
     */
    private void httpModifyPassWord() {
        ServerCommunicationHandle.modifyPassWord(AllVariable.CURRENT_USER_ID, mNewPasswordStr, mOldPasswordStr, new UIHttpCallBack() {
            @Override
            public void success(Object object) {
                if(object != null){
                    final HTTPResponseBaseMsg httpResponseMsgLogin = (HTTPResponseBaseMsg) object;
                    if(httpResponseMsgLogin.getBODY().getINSTP().equals(HTTPMsgINSIP.CHANGE_PWD_ACK)){
                        if(httpResponseMsgLogin.getBODY().getRESULT().equals(HttpResponseCode.SUCCESS)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissLoadingDialog();
                                    Toast.makeText(ModifyPassWordActivity.this ,"修改密码成功！", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ModifyPassWordActivity.this, UserInfoActivity.class));
                                    finish();
                                }
                            });
                        }else {
                            dismissLoadingDialog();
                            Toast.makeText(ModifyPassWordActivity.this ,"密码修改失败！", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
            @Override
            public void failure(Object object) {
                dismissLoadingDialog();
                Toast.makeText(ModifyPassWordActivity.this ,"密码修改失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
