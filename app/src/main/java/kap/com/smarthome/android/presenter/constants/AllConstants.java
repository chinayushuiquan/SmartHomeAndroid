package kap.com.smarthome.android.presenter.constants;

import android.os.Environment;

/**
 * Created by yushq on 2017/5/31 0031.
 *
 * 这个类中保存的是全局的常量字段，用于一些标志字段
 * 相当于map中的键值 ， 不能对其中的值作更改
 *
 * 相对应的 values 值可能保存在 AllVariable 类中
 *
 */

public class AllConstants {

    /**
     * SharedPerferencesHandle类的常量
     */
    //本地键值数据对SharedPerferences的文件名
    public  static  final String  SP_NAME = "smart_home_sp";

    //是否是第一次打开app ,默认为true 是第一次打开
    public  static   final  String   IS_OPEN_FIRST  = "is_open_first";


    /**
     * UI部分
     */
    //房间个数的extra
    public  static  final String  ROOM_NUM_EXTRA = "room_num_extra";

    //DevicesFragment跳转到AddDeviceActivity 带的guid参数
    public  static  final  String CURRENT_ROOM_GUID = "current_room_guid";





    /**
     * 广播常量
     */
    //添加新房间的广播通知
    public static  final  String   BROAD_CAST_ADD_ROOM = "broad_cast_add_room";

    //添加新房间的广播通知
    public static  final  String   BROAD_CAST_ADD_SCENES = "broad_cast_add_scenes";

    //添加了新的中继盒子
    public static  final  String  BROAD_CAST_ADD_RElAY_BOX = "broad_cast_add_relay_box";


    //房间的UUID key
    public static final String ROOM_UUID = "room_uuid";

    /**
     * 本地头像地址
     */
    public static final String HEAD_PATH = Environment.getExternalStorageDirectory().toString()+"/kotihome/user_head.jpg";

    /**
     * 新更换头像保存在本地的地址
     */
    public static final String NEW_HEAD_PATH = Environment.getExternalStorageDirectory().toString()+"/kotihome/new_user_head.jpg";

    /**
     * 用户 id 的key
     */
    public static final String USER_ID = "user_id";

    //默认的USER_ID
    public  static final String DEFAULT_USER_ID = "4028b8815f145833015f1466607b0000" ;

    //用户授权
    public static final String USER_ACCREDIT_ACCOUNT = "user_accredit_account";

    /**
     * UDP 通信 超时时间
     */
    public static  final long  UDP_REQ_OVER_TIME = 5000;


    /**
     * 场景开关
     */
    public static final int SCENES_TRIGGER_STATUS_OPEN =  1;

    public static final int  SCENES_TRIGGER_STATUS_CLOSE = 0;


    /**
     * HTTP 连接 错误码归类
     */


    /**
     *  注册操作结果类型
     0 - 成功；
     9000 - 数据非法； Data is illegal
     1001 - 开户的帐户已存在；
     9999 - 其它错误（未知异常）
     */
    //成功 注册
    public static final String SIGN_SUCCESS = "0";

    //账号重复 用户存在
    public static final String SIGN_ACCOUNT_EXIST = "1001";

    public static final String SIGN_DATA_ILLEGAL = "9000";

    public static final String SIGN_OTHER_ERROR = "9999";











    /**
     * 登录 类型
     *
     1-	远程登录账号/密码 （校验 “远程登录账号”、“用户密码”、“用户的电话号码”是否为空，“手机验证码”必须正确）；
     2-	电话号（校验“用户的电话号码”是否为空，“手机验证码”必须正确）；
     3-	微信
     4-	QQ
     5-	E-MAIL（暂未提供）;
     6-	随机码（暂未提供）
     *
     */
    // 账号和密码
    public static final int LOGIN_TYPE_1 = 1;




}
