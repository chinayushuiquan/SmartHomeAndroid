package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;


/**
 * Created by Administrator on 2017/8/18 0018.
 */

public class ForgetPswOneActivity extends  BaseActivity{

    private Button mClick_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psw_one);
        mClick_btn = (Button) findViewById(R.id.find_psw_next_btn);
        mClick_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPswOneActivity.this, ForgetPswTwoActivity.class));
            }
        });
    }

    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
        myTopBarBuilder.setLeftImage(R.drawable.back_icon)
                .setTitleColor(R.color.white)
                .setTitleText(R.string.find_back_psw)
                .setLeftImageOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }
}
