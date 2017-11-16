package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by yushq on 2017/10/9 0009.
 * 这个界面和SystemSetActivity 界面的功能类似，是在注册成功之后自动跳转到该界面，
 * 在该界面中进行中继盒子的添加，以及数据的同步，也可以跳过该操作，直接下一步操作
 */

public class SystemSetRegisterActivity  extends BaseActivity implements View.OnClickListener{

    private RelativeLayout mRelayBoxRl;
    private RelativeLayout mSyncDataRl;

    private TextView mStepNextIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_set_copy);
        mRelayBoxRl = (RelativeLayout) findViewById(R.id.system_relay_box_rl);
        mSyncDataRl = (RelativeLayout) findViewById(R.id.system_sync_data_rl);
        mStepNextIv = (TextView) findViewById(R.id.system_set_step_btn);

        mRelayBoxRl.setOnClickListener(this);
        mSyncDataRl.setOnClickListener(this);
        mStepNextIv.setOnClickListener(this);


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
            case R.id.system_relay_box_rl:
                startActivity(new Intent(this, RelayBoxActivity.class));
                break;
            case R.id.system_sync_data_rl:
                //startActivity(new Intent(this, SyncDataActivity.class));
                // TODO: 2017/10/9 0009 实现数据同步，执行数据下载
                break;
            case R.id.system_set_step_btn:
                Intent intent = new Intent(this, MainHomeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
