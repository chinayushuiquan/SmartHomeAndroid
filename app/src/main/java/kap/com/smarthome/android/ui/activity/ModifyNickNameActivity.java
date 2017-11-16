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
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.ui.view.MyLoadingDialog;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public  class ModifyNickNameActivity  extends BaseActivity{
    private EditText mNickNameEdit;
    private String  mNickName;
    private MyLoadingDialog myLoadingDialog ;
    //用户表中的用户对象
    private String  userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_nickname);
        userID = getIntent().getStringExtra(AllConstants.USER_ID);

        mNickNameEdit = (EditText) findViewById(R.id.modify_nick_name_edit);

        getEditNickName();

        //user = DataBaseHandle.getUser();
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
                        //保存昵称
                        saveNickName();
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
     * 保存昵称
     */
    private void saveNickName() {
        getEditNickName();
        showLoadingDialog("保存昵称");
        httpModifyNickName();
    }



    /**
     * 获取输入的用户昵称
     */
    private void getEditNickName() {
        mNickName = mNickNameEdit.getText().toString().trim();
    }


    /**
     * 修改服务端的保存的昵称
     */
    private void httpModifyNickName() {
        ServerCommunicationHandle.modifyUserInfo(userID , "1",  mNickName, new UIHttpCallBack() {
            @Override
            public void success(Object object) {
                if(object != null){
                    final HTTPResponseMsgBase httpResponseMsgLogin = (HTTPResponseMsgBase) object;
                    if(httpResponseMsgLogin.getBODY().getINSTP().equals(HTTPMsgINSIP.EDIT_USERINFO_ACK)){
                        if(httpResponseMsgLogin.getBODY().getRESULT().equals(HttpResponseCode.SUCCESS)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ModifyNickNameActivity.this ,"昵称修改成功！", Toast.LENGTH_SHORT).show();
                                    if(DataBaseHandle.updateUserByUserId( userID , mNickName , "1") != -1){//用户数据更新成功
                                        dismissLoadingDialog();
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
