package kap.com.smarthome.android.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;


/**
 * Created by Administrator on 2017/8/18 0018.
 */

public class ForgetPswThreeActivity extends  BaseActivity{

    private Button mClick_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psw_three);
        mClick_btn = (Button) findViewById(R.id.find_psw_done_btn);
        mClick_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
