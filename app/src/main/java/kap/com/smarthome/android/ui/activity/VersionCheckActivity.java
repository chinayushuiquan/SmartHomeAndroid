package kap.com.smarthome.android.ui.activity;

import android.os.Bundle;
import android.view.View;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;


public class VersionCheckActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_check);

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

}
