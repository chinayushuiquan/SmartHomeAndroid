package kap.com.smarthome.android.ui.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class ScreenUtils {

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    /**
     * 根据手机分辨率从dip的单位 装换成像素值（px）
     */
    public static int dipToPx(Context context, float dpValue){
        final  float  scale = context.getResources().getDisplayMetrics().density;
        return  (int)  (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机分辨率从px(像素)的单位
     */
    public static int pxTodip(Context context , float pxValue){
        final  float scale = context.getResources().getDisplayMetrics().density;
        return  (int) (pxValue/scale + 0.5f);
    }

}
