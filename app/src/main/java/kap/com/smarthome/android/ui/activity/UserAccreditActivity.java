package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.utils.DataLegitimacyCheckUtils;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by Administrator on 2017/11/1 0001.
 */

public  class UserAccreditActivity extends  BaseActivity {


    private Button mClick_btn;

    private TextInputEditText mUserEdit;

    private String other_account = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_accredit);

        mUserEdit = (TextInputEditText) findViewById(R.id.user_accredit_account_et);

        mClick_btn = (Button) findViewById(R.id.user_accredit_next_btn);

        mClick_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                other_account = mUserEdit.getText().toString().trim();

                if(other_account.isEmpty()){

                    Toast.makeText(UserAccreditActivity.this , "对方账号为空！" ,  Toast.LENGTH_LONG).show();

                    return ;
                }


                if(!DataLegitimacyCheckUtils.checkUserAccount(other_account)){
                    Toast.makeText(UserAccreditActivity.this, "账号格式错误！1～20位的中文或者英文" , Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent  intent = new Intent(UserAccreditActivity.this, UserAccreditDoneActivity.class);
                intent.putExtra(AllConstants.USER_ACCREDIT_ACCOUNT , other_account);
                startActivity(intent);


            }
        });
    }




    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
        myTopBarBuilder.setLeftImage(R.drawable.back_icon)
                .setTitleColor(R.color.white)
                .setTitleText(R.string.authorization_user)
                .setLeftImageOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

}
