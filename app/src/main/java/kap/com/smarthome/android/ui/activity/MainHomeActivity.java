package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;
import kap.com.smarthome.android.data.bean.User;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.ui.fragment.DevicesFragment;
import kap.com.smarthome.android.ui.fragment.MainFragment;
import kap.com.smarthome.android.ui.fragment.RoomFragment;
import kap.com.smarthome.android.ui.fragment.ScenesFragment;
import kap.com.smarthome.android.presenter.broadcast.NetConnectBroadCastReceiver;
import kap.com.smarthome.android.ui.utils.ImageUtils;
import kap.com.smarthome.android.ui.utils.PermissionUtils;
import kap.com.smarthome.android.ui.view.CircleImageView;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;


public class MainHomeActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "MainHomeActivity";
    //侧滑栏
    private DrawerLayout drawerLayout;
    //底部的Icon
    private RadioGroup  mRadioGroup;
    private RadioButton mRbMain , mRbDevices , mRbScenes ,mRbRoom;
    //Fragment
    private Fragment[] mFragments = new Fragment[4];
    private Fragment   mCurrentFragment;

    //侧滑栏的相关控件
    private CircleImageView mUserHeadIv;
    private TextView  mUserNameTv;
    private Button    mUserInfoBtn;
    private Button    mSystemSetBtn;
    private Button    mVersionCheckBtn;
    private Button    mAuthorizationUser;

    private User user;

    private NetConnectBroadCastReceiver mNetConnectBroadCastReceiver = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        initView();
        initFirstFragment();
        initListener();
        writeExternalStorage();

        mNetConnectBroadCastReceiver = new NetConnectBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(mNetConnectBroadCastReceiver,filter);
    }


    private void initView() {
        //侧滑栏
        drawerLayout = (DrawerLayout) findViewById(R.id.simple_navigation_drawer);

        //底部的Icon
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mRbMain = (RadioButton) findViewById(R.id.rb1);
        mRbDevices = (RadioButton) findViewById(R.id.rb2);
        mRbScenes = (RadioButton) findViewById(R.id.rb3);
        mRbRoom = (RadioButton) findViewById(R.id.rb4);

        //初始化Fragment
        mFragments[0] = MainFragment.newInstance("main");
        mFragments[1] = DevicesFragment.newInstance("devices");
        mFragments[2] = ScenesFragment.newInstance("scenes");
        mFragments[3] = RoomFragment.newInstance("room");

        //侧滑栏的相关控件
        mUserHeadIv = (CircleImageView) findViewById(R.id.sliding_user_head_iv);
        mUserNameTv = (TextView) findViewById(R.id.sliding_user_name_tv);
        mUserInfoBtn = (Button) findViewById(R.id.sliding_user_info_btn);
        mSystemSetBtn = (Button) findViewById(R.id.sliding_system_set_btn);
        mVersionCheckBtn = (Button) findViewById(R.id.sliding_about_version_btn);
        mAuthorizationUser = (Button) findViewById(R.id.sliding_authorization_user_btn);


        mUserInfoBtn.setOnClickListener(this);
        mSystemSetBtn.setOnClickListener(this);
        mVersionCheckBtn.setOnClickListener(this);
        mAuthorizationUser.setOnClickListener(this);

        user = DataBaseHandle.queryUser();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(user != null) {
            //设置头像
            mUserHeadIv.setImageBitmap(ImageUtils.getDiskHeadBitmap(AllConstants.HEAD_PATH));
            mUserNameTv.setText(user.getNICK_NAME());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mNetConnectBroadCastReceiver != null)
        unregisterReceiver(mNetConnectBroadCastReceiver);
    }

    private void initFirstFragment(){
        if(!mFragments[0].isAdded()){
            getSupportFragmentManager().beginTransaction().
                    add(R.id.view_frameLayout, mFragments[0]).commit();
            mCurrentFragment = mFragments[0];
        }
        mRbMain.setChecked(true);
    }



    private void initListener(){
        //设置mRadioGroup底部导航栏的切换功能。
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int position) {
                switch (position){
                    case R.id.rb1:
                        setSelect(0);
                        break;
                    case R.id.rb2:
                        setSelect(1);
                        break;
                    case R.id.rb3:
                        setSelect(2);
                        break;
                    case R.id.rb4:
                        setSelect(3);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * //自定义一个方法进行Fragment的切换
     * @param i
     */
    private void setSelect( int  i){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = mFragments[i];
        if(mCurrentFragment  == fragment)
            return;

        if(!fragment.isAdded()){
            transaction.hide(mCurrentFragment).add(R.id.view_frameLayout , fragment).commit();
        }else{
            transaction.hide(mCurrentFragment).show(fragment).commit();
        }
        mCurrentFragment = fragment;
    }

    public void showSlidingDrawerLayout(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    /**
     * 动态申请读写权限  2017-0823
     */
    public void writeExternalStorage() {
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sliding_user_info_btn:
                startActivity(new Intent(MainHomeActivity.this, UserInfoActivity.class));
                break;
            case R.id.sliding_system_set_btn:
                startActivity(new Intent(MainHomeActivity.this, SystemSetActivity.class));
                break;
            case R.id.sliding_about_version_btn:
                startActivity(new Intent(MainHomeActivity.this, VersionCheckActivity.class));
                break;
            case  R.id.sliding_authorization_user_btn:
                // TODO: 2017/10/9 0009 授权
                startActivity(new Intent(MainHomeActivity.this, UserAccreditActivity.class));
                break;
        }

    }

    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {}


    @Override
    public void uiRegisterRelayBoxCallback(UDPReceiverData item) {
        super.uiRegisterRelayBoxCallback(item);
        AllVariable.CONNECT_RELAY = true;
        Log.e("UDP", "主页面接收 中继盒子连接" + AllVariable.CONNECT_RELAY);
    }
}
