package kap.com.smarthome.android.presenter.constants;

/**
 * Created by yushq on 2017/10/13 0013.
 *
 * 这个类中保存的是全局的变量字段，用于一些全局的参数
 * 相当于map中的value 值 ， 能对其中的值作更改
 *
 * 相对应的 key 值可能保存在 AllConstants 类中
 */

public class AllVariable {


    /**
     * wifi is_able
     */
    public static boolean WIFI_CONNECT = false ;

    /**
     * MOBILE is_able
     */
    public static boolean MOBILE_CONNECT = false ;

    /**
     * no is_able
     */
    public static boolean NO_CONNECT = false ;

    /**
     * 连接上中继盒子
     */
    public static boolean CONNECT_RELAY = false;


    /**
     * 用户ID
     */
    //当前登录的用户的 user_id;

    public static String CURRENT_USER_ID = "";


    /**
     * 广播状态 相关
     */
    //是否成功添加了新的房间  默认为false
    public static boolean  IS_BROAD_CAST_ADD_ROOM = false;

    //是否成功添加了新的设备  默认为false
    public static boolean  IS_BROAD_CAST_ADD_NEW_DEVICE = false;

    /**
     * 和log有关的
     */
    //是否是debug模式，如果是该模式可以进行log打印输出
    public static final boolean ISDEBUG = true;

    //是否是debug模式，如果是该模式可以进行log打印输出
    public static final boolean ISDEBUG_KOTITAG = true;

    /**
     * device control json data
     */
    public static String CURRENT_DEVICE_CONTROL = "";

}
