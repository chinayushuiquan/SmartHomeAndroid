package kap.com.smarthome.android.presenter.utils;

import android.util.Log;

/**
 * Created by Administrator on 2017/10/31 0031.
 */

public class DeviceIdInitUtils {

    public static  String  createDeviceID(String id_type){
        String id = UUIDUtils.getUUID().substring(0,14);
        StringBuilder sb = new StringBuilder(id);//构造一个StringBuilder对象
        sb.insert(8, id_type);//在指定的位置1，插入指定的字符串
        id = sb.toString();
        Log.e("UDP", "createDeviceID: 自定义设备ID号 =  " +  id);
        return  id;
    }

}
