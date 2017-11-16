package kap.com.smarthome.android.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

public class SyncDataActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_data);
    }

    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {

    }
}
