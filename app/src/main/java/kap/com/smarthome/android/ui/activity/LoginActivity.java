package kap.com.smarthome.android.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.ui.fragment.LoginPswFragment;
import kap.com.smarthome.android.ui.fragment.LoginSmsFragment;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;
import kap.com.smarthome.android.ui.view.SwitchButton;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "LoginActivity";

    //4028b8815f0ac71b015f0adcff810000 userid

    //顶部的icon  侧滑、登录、标题
    private ImageView mIvback;
    private TextView  mTvTopTitle;

    /*private TextView  mPdLoginText;
    private TextView  mSmsLoginText;*/

    private ViewPager  mMethodViewPager;
    private List<Fragment>  fragments = new ArrayList<>();

    private SwitchButton  mSwitchButton;
    private SwitchButton.ChangeSelectListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //登录方法选择部分
       /*mPdLoginText = (TextView) findViewById(R.id.login_iv_pw);
        mSmsLoginText = (TextView) findViewById(R.id.login_iv_sms);*/

        /*mPdLoginText.setBackgroundResource(R.drawable.login_selected_text_bg);
        mPdLoginText.setOnClickListener(this);
        mSmsLoginText.setOnClickListener(this);*/

        mSwitchButton = (SwitchButton) findViewById(R.id.switchButton);

        //实例化viewPager 和 切换不同登录方式的Fragment
        mMethodViewPager = (ViewPager) findViewById(R.id.login_view_pager);
        fragments.add(LoginPswFragment.newInstance("password"));
        fragments.add(LoginSmsFragment.newInstance("sms"));


        //设置ViewPager的适配器， 设置展示的fragment
        mMethodViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });


        //给ViewPager添加切换的监听器
        mMethodViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG, "onPageScrolled: positionOffset " + positionOffset);
                mSwitchButton.setSlidingMarginLeft(positionOffset);
            }
            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    //selectPswLoginMethod
                    mSwitchButton.setSelectLeft(true);
                }else{
                   // selectSmsLoginMethod();
                    mSwitchButton.setSelectLeft(false);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });


        mListener = new SwitchButton.ChangeSelectListener() {
            @Override
            public void changeSelect(boolean isSelectLeft) {
                if(isSelectLeft){
                    mMethodViewPager.setCurrentItem(0);
                }else{
                    mMethodViewPager.setCurrentItem(1);
                }
            }
        };

        mSwitchButton.setChangeSelectListener(mListener);
    }


    @Override
    public void onClick(View v) {
        /*switch (v.getId()){
            case R.id.login_iv_pw:
                selectPswLoginMethod();
                mMethodViewPager.setCurrentItem(0);
                break;
            case R.id.login_iv_sms:
                selectSmsLoginMethod();
                mMethodViewPager.setCurrentItem(1);
                break;
        }*/
    }

   /* //选择密码登录
    private void selectSmsLoginMethod() {
        mPdLoginText.setBackgroundResource(R.color.transparent);
        mSmsLoginText.setBackgroundResource(R.drawable.login_selected_text_bg);
    }


    //选择短信登录
    private void selectPswLoginMethod() {
        mPdLoginText.setBackgroundResource(R.drawable.login_selected_text_bg);
        mSmsLoginText.setBackgroundResource(R.color.transparent);
    }*/




    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
        myTopBarBuilder.setLeftImage(R.drawable.back_icon)
                       .setTitleColor(R.color.white)
                       .setTitleText(R.string.login_activity_title)
                       .setLeftImageOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               finish();
                           }
                       });

    }
}
