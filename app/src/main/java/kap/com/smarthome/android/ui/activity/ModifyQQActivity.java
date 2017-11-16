package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPResponseMsgBase;
import kap.com.smarthome.android.communication.http.constants.HTTPMsgINSIP;
import kap.com.smarthome.android.communication.http.constants.HttpResponseCode;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.User;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.ui.view.MyLoadingDialog;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public  class ModifyQQActivity extends BaseActivity{

    private EditText mQQNumberEdit;
    private String  mQQNumberStr;
    private MyLoadingDialog myLoadingDialog ;

    //用户表中的用户对象
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_qq);
        mQQNumberEdit = (EditText) findViewById(R.id.modify_user_qq_edit);
        getEditNickName();
        user = DataBaseHandle.queryUser();
    }



    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
        myTopBarBuilder.setLeftImage(R.drawable.back_icon_black)
                .setTitleText(R.string.modify_qq_number)
                .setRightText(R.string.save)
                .setRightTextColor(R.color.orange)
                .setRightTextOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //保存昵称
                        saveNewQQNumber();
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
     * 保存QQ号
     */
    private void saveNewQQNumber() {
        getEditNickName();
        showLoadingDialog("");
        httpModifyNickName();
    }


    /**
     * 获取输入的QQ号
     */
    private void getEditNickName() {
        mQQNumberStr = mQQNumberEdit.getText().toString().trim();
    }


    /**
     * 修改服务端的保存的QQ号
     */
    private void httpModifyNickName() {
        ServerCommunicationHandle.modifyUserInfo(user.getUSER_ID(), "4", mQQNumberStr, new UIHttpCallBack() {
            @Override
            public void success(Object object) {
                if(object != null){
                    final HTTPResponseMsgBase httpResponseMsgLogin = (HTTPResponseMsgBase) object;
                    if(httpResponseMsgLogin.getBODY().getINSTP().equals(HTTPMsgINSIP.EDIT_USERINFO_ACK)){
                        if(httpResponseMsgLogin.getBODY().getRESULT().equals(HttpResponseCode.SUCCESS)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ModifyQQActivity.this ,"QQ号修改成功！", Toast.LENGTH_SHORT).show();
                                    if(DataBaseHandle.updateUserByUserId(user.getUSER_ID() , mQQNumberStr, "4") != -1){//用户数据更新成功
                                        dismissLoadingDialog();
                                        startActivity(new Intent(ModifyQQActivity.this, UserInfoActivity.class));
                                        finish();
                                    }
                                }
                            });
                        }
                    }

                }
            }
            @Override
            public void failure(Object object) {

            }
        });
    }

}
