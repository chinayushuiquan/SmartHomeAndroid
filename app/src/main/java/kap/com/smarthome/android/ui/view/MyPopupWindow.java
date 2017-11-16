package kap.com.smarthome.android.ui.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import kap.com.smarthome.android.R;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class MyPopupWindow extends PopupWindow{

    private Context mContext;
    private Window mWindow;

    public  MyPopupWindow(Context context ,View view , int style) {
        super(view , ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mContext = context;
        init(style);
    }


    private void  init(int style){
        if(mContext != null) {
            ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.transparent));
            setBackgroundDrawable(dw);
            setAnimationStyle(style);

        }
        setOutsideTouchable(true);
        setFocusable(true);
    }

    /**
     * 如果想在弹出PopWindow时候让背景变暗，就调用该方法
     * @param window
     * @param alpha
     */
    public  void setWindowAlpha(Window window , float alpha){
        mWindow = window;
        WindowManager.LayoutParams wl = mWindow.getAttributes();
        wl.alpha = alpha;
        mWindow.setAttributes(wl);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if(mWindow != null) {
                    WindowManager.LayoutParams wl = mWindow.getAttributes();
                    wl.alpha = 1.0f;
                    mWindow.setAttributes(wl);
                }
            }
        });
    }

}
