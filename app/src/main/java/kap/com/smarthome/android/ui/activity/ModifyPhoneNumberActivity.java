package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPResponseBaseMsg;
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

public  class ModifyPhoneNumberActivity extends BaseActivity implements View.OnClickListener{

    private EditText mPhoneNumEdit;
    private String  mPhoneNumStr;

    private TextView mChoseLocalPhonePrefixTv;

    private String mPhoneNum = "86-18123610958";
    private String mLocalFlag = "zh";
    private static final int CHOSE_COUNTRY_NUM_REQ= 100;
    private String countryNum = "86";
    private String countryName = "中国";


    private MyLoadingDialog myLoadingDialog ;

    //用户表中的用户对象
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone_number);
        mPhoneNumEdit = (EditText) findViewById(R.id.modify_phone_number_edit);
        mChoseLocalPhonePrefixTv = (TextView) findViewById(R.id.chose_local_phone_number_tv);
        mChoseLocalPhonePrefixTv.setOnClickListener(this);


        user = DataBaseHandle.queryUser();
        Log.e("DATABASE", "ModifyPhoneNumberActivityr = "+ user.toString());

    }

    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
        myTopBarBuilder.setLeftImage(R.drawable.back_icon_black)
                .setTitleText(R.string.modify_phone_number)
                .setRightText(R.string.save)
                .setRightTextColor(R.color.orange)
                .setRightTextOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //保存昵称
                        saveUserPhoneNumber();
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
    private void saveUserPhoneNumber() {
        getUserNewPhoneNumber();
        showLoadingDialog("");
        httpModifyNickName();
    }



    /**
     * 获取输入的用户昵称
     */
    private void getUserNewPhoneNumber() {
        mPhoneNumStr = mPhoneNumEdit.getText().toString().trim();
    }


    /**
     * 修改服务端的保存的昵称
     */
    private void httpModifyNickName() {
        ServerCommunicationHandle.modifyUserInfo(user.getUSER_ID(), "2", mPhoneNumStr, new UIHttpCallBack() {
            @Override
            public void success(Object object) {
                if(object != null){
                    final HTTPResponseBaseMsg httpResponseMsgLogin = (HTTPResponseBaseMsg) object;
                    if(httpResponseMsgLogin.getBODY().getINSTP().equals(HTTPMsgINSIP.EDIT_USERINFO_ACK)){
                        if(httpResponseMsgLogin.getBODY().getRESULT().equals(HttpResponseCode.SUCCESS)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ModifyPhoneNumberActivity.this ,"电话号码修改成功！", Toast.LENGTH_SHORT).show();
                                    if(DataBaseHandle.updateUserByUserId(user.getUSER_ID() , mPhoneNumStr , "2") != -1){//用户数据更新成功
                                        dismissLoadingDialog();
                                        startActivity(new Intent(ModifyPhoneNumberActivity.this, UserInfoActivity.class));
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chose_local_phone_number_tv:
                chosePhoneNumLocal();
                break;
        }
    }

    /**
     *  选择电话号码的地区代号
     */
    private void chosePhoneNumLocal() {
        Intent intent = new Intent(this, ChoseCountryNumActivity.class);
        startActivityForResult(intent, CHOSE_COUNTRY_NUM_REQ);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 100){
            Bundle bundle = data.getExtras();
            if(bundle != null){
                countryNum = bundle.getString("result_country_number", "86");
                countryName = bundle.getString("result_country_name" , "中国");
                mChoseLocalPhonePrefixTv.setText(countryName + " +"+countryNum);
            }
        }
    }
}
