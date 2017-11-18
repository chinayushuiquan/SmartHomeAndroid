package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import kap.com.smarthome.android.R;

import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPResponseBaseMsg;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.UserHead.HTTPResponseUpdateHeadMsg;
import kap.com.smarthome.android.communication.http.constants.HTTPMsgINSIP;
import kap.com.smarthome.android.communication.http.constants.HttpResponseCode;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.User;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.ui.utils.ImageUtils;
import kap.com.smarthome.android.ui.view.CircleImageView;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by yushq on 2017/8/21 0021.
 */

public class UserInfoActivity extends BaseActivity  implements View.OnClickListener{

    private final  static int  OPEN_GALLERY = 1;
    private static final int CROP_SMALL_PICTURE = 2;

    private CircleImageView mUserHeadImage;

    private RelativeLayout mNickNameRl;
    private RelativeLayout mUserIdRl;
    private RelativeLayout mPhoneNumRl;
    private RelativeLayout mQQNumberRl;
    private RelativeLayout mWeChatRl;
    private RelativeLayout mChangePswRl;


    private TextView mNickNameIv;
    private TextView mUserIdIv;
    private TextView mPhoneNumIv;
    private TextView mQQNumberIv;
    private TextView mWechatIv;
    private TextView mChangePswIv;


    private Button mExitBtn;

    //用户表中的用户对象
    private User user;


    private Bitmap newHead = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        initData();
    }


    private void initView() {
        mUserHeadImage = (CircleImageView) findViewById(R.id.userinfo_user_head_iv);
        mUserHeadImage.setOnClickListener(this);

        mNickNameRl = (RelativeLayout) findViewById(R.id.nick_name_rl);
        mUserIdRl = (RelativeLayout) findViewById(R.id.user_id_rl);
        mPhoneNumRl = (RelativeLayout) findViewById(R.id.phone_number_rl);
        mQQNumberRl = (RelativeLayout) findViewById(R.id.qq_number_rl);
        mWeChatRl = (RelativeLayout) findViewById(R.id.wechat_name_rl);
        mChangePswRl = (RelativeLayout) findViewById(R.id.change_password_rl);

        mNickNameIv = (TextView) findViewById(R.id.user_info_user_nick_name);
        mUserIdIv = (TextView) findViewById(R.id.user_info_user_id);
        mPhoneNumIv = (TextView) findViewById(R.id.user_info_user_phone_number);
        mQQNumberIv = (TextView) findViewById(R.id.user_info_user_qq_number);
        mWechatIv = (TextView) findViewById(R.id.user_info_user_wechat_name);
        mChangePswIv = (TextView) findViewById(R.id.user_info_user_change_password);

        mExitBtn = (Button) findViewById(R.id.exit_login);

        mExitBtn.setOnClickListener(this);

        mNickNameRl.setOnClickListener(this);
        mUserIdRl.setOnClickListener(this);
        mPhoneNumRl.setOnClickListener(this);
        mQQNumberRl.setOnClickListener(this);
        mWeChatRl.setOnClickListener(this);
        mChangePswRl.setOnClickListener(this);
    }

    /**
     * 初始化用户的个人信息，头像，昵称 ，微信，qq...
     */
    private void initData() {
        user = DataBaseHandle.queryUser();
        if(user != null) {
            initHead();
            intOtherInfo();
            Log.e("HTTP", "initData: user = "+ user.toString());
        }
    }

    /**
     * 初始化其他信息
     */
    private void intOtherInfo() {

        if(null != user.getLOGIN_NAME() && !user.getLOGIN_NAME().isEmpty()){
            mUserIdIv.setText(user.getLOGIN_NAME());
        }

        if(null != user.getPHONE() && !user.getPHONE().isEmpty()){
            mPhoneNumIv.setText(user.getPHONE());
        }

        if(null != user.getQQ() && !user.getQQ().isEmpty()){
            mQQNumberIv.setText(user.getQQ());
        }

        if(null != user.getWECHAT() && !user.getWECHAT().isEmpty()){
            mWechatIv.setText(user.getWECHAT());
        }

        if(null != user.getNICK_NAME() && !user.getNICK_NAME().isEmpty()){
            mNickNameIv.setText(user.getNICK_NAME());
        }
    }



    /**
     * 初始化头像
     */
    private void initHead() {
        Bitmap bitmap = ImageUtils.getDiskHeadBitmap(AllConstants.HEAD_PATH);
        if(bitmap == null && user.getUSER_HEAD() != null){
            Log.e("HTTP", "initData: 本地头像不存在 ，下载头像 "); //从网上下载头像
            showLoadingDialog("");
            ServerCommunicationHandle.getUserHead(user.getUSER_HEAD(), new UIHttpCallBack() {
                @Override
                public void success(Object object) {
                    boolean isSuccess = (boolean) object;
                    if(isSuccess) {
                        Bitmap bitmap = ImageUtils.getDiskHeadBitmap(AllConstants.HEAD_PATH);
                        mUserHeadImage.setImageBitmap(bitmap);
                        Log.e("HTTP", "initData: 从网络下载头像成功 "); //从网上下载头像
                        dismissLoadingDialog();
                    }else{
                        dismissLoadingDialog();
                    }
                }
                @Override
                public void failure(Object object) {
                      dismissLoadingDialog();
                }
            });
        }else{
            mUserHeadImage.setImageBitmap(bitmap);
        }
    }


    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
        myTopBarBuilder.setLeftImage(R.drawable.back_icon_black)
                .setTitleText(R.string.user_info)
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
            case R.id.userinfo_user_head_iv:
                pickImageFromAlbum();
                break;
            case R.id.nick_name_rl:
                Intent intent = new Intent(UserInfoActivity.this, ModifyNickNameActivity.class);
                intent.putExtra(AllConstants.USER_ID , user.getUSER_ID());
                startActivity(intent);
                break;
            case R.id.user_id_rl:
                //startActivity(new Intent(UserInfoActivity.this, ModifyNickNameActivity.class));
                break;
            case R.id.phone_number_rl:
                Intent intent1 = new Intent(UserInfoActivity.this, ModifyPhoneNumberActivity.class);
                intent1.putExtra(AllConstants.USER_ID , user.getUSER_ID());
                startActivity(intent1);
                break;
            case R.id.qq_number_rl:
                Intent intent2 = new Intent(UserInfoActivity.this, ModifyQQActivity.class);
                intent2.putExtra(AllConstants.USER_ID , user.getUSER_ID());
                startActivity(intent2);
                break;
            case R.id.wechat_name_rl:
                Intent intent3 = new Intent(UserInfoActivity.this, ModifyWeChatActivity.class);
                intent3.putExtra(AllConstants.USER_ID , user.getUSER_ID());
                startActivity(intent3);
                break;
            case R.id.change_password_rl:
                Intent intent4 = new Intent(UserInfoActivity.this, ModifyPassWordActivity.class);
                intent4.putExtra(AllConstants.USER_ID , user.getUSER_ID());
                startActivity(intent4);
                break;
            case R.id.exit_login://退出登录
                exitLogin();
                break;
        }
    }


    /**
     * 退出登录
     */
    private void exitLogin() {
        showLoadingDialog("");
        String session_id = null;
        int  status = 0;
        if(user != null){
            status = user.getStatus();
            session_id = user.getSESSION_ID();
        }
        if(status == 1){// status == 1 表示用户在登录状态
            ServerCommunicationHandle.exitServerLogin(session_id, new UIHttpCallBack(){
                @Override
                public void success(Object object) {
                    if(object != null) {
                        final HTTPResponseBaseMsg httpResponseBase = (HTTPResponseBaseMsg) object;
                        if (httpResponseBase.getBODY().getINSTP().equals(HTTPMsgINSIP.USER_LOGOUT_ACK)) {
                            if (httpResponseBase.getBODY().getRESULT().equals(HttpResponseCode.SUCCESS)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dismissLoadingDialog();
                                    }
                                });
                            }
                        }
                    }
                }
                @Override
                public void failure(Object object) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissLoadingDialog();
                        }
                    });
                }
            });
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case OPEN_GALLERY:
                    showLoadingDialog("");
                    saveNewHeadImage(data);
                    updateUserHead();
                    break;
                case CROP_SMALL_PICTURE:
                    break;
            }
        }
    }


    /**
     * 打开系统相册获取图片
     */
    public void pickImageFromAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, OPEN_GALLERY);
    }



    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("DATA", "The uri is not exist.");
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);

        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }


    /**
     * 保存裁剪之后的图片数据
     * @param
     * @param  new_head
     */
    protected void setImageToView(Bitmap new_head) {
        //保存图片
        ImageUtils.saveFile(new_head);
        mUserHeadImage.setImageBitmap(new_head);
    }

    /**
     * 临时先保存新的头像图片
     */
    protected void saveNewHeadImage(Intent data) {
        newHead = getBitmap(data);
        ImageUtils.saveNewHeadImage(newHead);
    }


    private Bitmap getBitmap(Intent data) {
        Uri imageUri = data.getData();
        String selectedImagePath2 = ImageUtils.getPath(UserInfoActivity.this , imageUri);
        return ImageUtils.lessenUriImage(selectedImagePath2);
    }

    /**
     * 上传头像
     */
    private void updateUserHead(){
        String base64_head = ImageUtils.imageToBase64(AllConstants.NEW_HEAD_PATH);
        ServerCommunicationHandle.updateUserHead(AllVariable.CURRENT_USER_ID, base64_head, new UIHttpCallBack() {
            @Override
            public void success(Object object) {
                if(object != null) {
                    final HTTPResponseUpdateHeadMsg httpResponseUpdateHeadMsg = (HTTPResponseUpdateHeadMsg) object;
                    if (httpResponseUpdateHeadMsg.getBODY().getINSTP().equals(HTTPMsgINSIP.EDIT_USER_HEADLOGO_ACK)) {
                        if (httpResponseUpdateHeadMsg.getBODY().getRESULT().equals(HttpResponseCode.SUCCESS)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String head_url = httpResponseUpdateHeadMsg.getBODY().getHEADLOGOURL();
                                    if(DataBaseHandle.updateUserByUserId(user.getUSER_ID() , head_url , "6") != -1){
                                        setImageToView(newHead);
                                        dismissLoadingDialog();

                                    }
                                }
                            });
                        }else {
                            dismissLoadingDialog();
                            Toast.makeText(UserInfoActivity.this, "修改头像失败！" , Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
            @Override
            public void failure(Object object) {
              dismissLoadingDialog();
              Toast.makeText(UserInfoActivity.this, "修改头像失败！" , Toast.LENGTH_LONG).show();
            }
        });
    }

}



