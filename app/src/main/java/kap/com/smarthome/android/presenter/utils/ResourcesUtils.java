package kap.com.smarthome.android.presenter.utils;

import android.content.Context;
import android.content.res.Resources;


/**
 * Created by yushq on 2017/9/21 0021.
 * 处理资源相关的静态工具方法
 */

public class ResourcesUtils {

    /**
     * 定义在资源文件arrays中的字符串数组
     * 获取字符串数组资源文件的工具
     * @param context
     * @param arrayId
     * @return
     */
    public static final  String[] getStringArray(Context context, int arrayId){
        Resources resources = context.getResources();
        return  resources.getStringArray(arrayId);
    }




}
