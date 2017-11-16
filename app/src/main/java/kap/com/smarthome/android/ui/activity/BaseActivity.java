package kap.com.smarthome.android.ui.activity;


import android.os.Bundle;

import kap.com.smarthome.android.ui.view.MyLoadingDialog;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;


public abstract class BaseActivity extends InterfaceActivity {


    private MyTopBarBuilder myTopBarBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onStart() {
        super.onStart();
        myTopBarBuilder = MyTopBarBuilder.builder(this);
        initTopBar(myTopBarBuilder);
    }


    /**
     * 设置顶部标题栏
     * 因为每个Activity的标题栏不相同，所以分别实现
     * 在onStart方法中进行加载
     */
    public abstract void initTopBar(MyTopBarBuilder myTopBarBuilder);


    private MyLoadingDialog myLoadingDialog ;

    /**
     * 显示注册的loadingDialog
     */
    public void showLoadingDialog(String info) {
        if(myLoadingDialog == null) {
            myLoadingDialog = new MyLoadingDialog(this).setMessage(info);
        }
        myLoadingDialog.show();
    }


    /**
     * 关闭注册的loadingDialog
     */
    public void dismissLoadingDialog() {
        if(myLoadingDialog != null && myLoadingDialog.isShowing()) {
            myLoadingDialog.dismiss();
        }
        myLoadingDialog = null;
    }


}
