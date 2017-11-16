package kap.com.smarthome.android.presenter.utils;

import java.util.UUID;

/**
 * Created by Administrator on 2017/9/20 0020.
 */

public class UUIDUtils {
    /**
     * 生成唯一键值
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }

}
