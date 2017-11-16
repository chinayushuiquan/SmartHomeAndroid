package kap.com.smarthome.android.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kap.com.smarthome.android.R;

/**
 * Created by Administrator on 2017/10/9 0009.
 */

public class MyLoadingDialog extends Dialog {

    private TextView tv_text;

    public MyLoadingDialog(@NonNull Context context) {
        super(context);
        /**设置对话框背景透明*/
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.loading_dialog);

        tv_text = (TextView) findViewById(R.id.loading_dialog_tv);

        //加载的动画
        ImageView mImageViewFilling = (ImageView) findViewById(R.id.loading_dialog_iv);
        ((AnimationDrawable) mImageViewFilling.getBackground()).start();

        setCanceledOnTouchOutside(false);
    }

    public MyLoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public MyLoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }




    /**
     * 为加载进度个对话框设置不同的提示消息
     * @param message 给用户展示的提示信息
     * @return build模式设计，可以链式调用
     */
    public MyLoadingDialog setMessage(String message) {
        if(tv_text.getVisibility() == View.GONE){
            tv_text.setVisibility(View.VISIBLE);
        }
        tv_text.setText(message);
        return this;
    }

}
