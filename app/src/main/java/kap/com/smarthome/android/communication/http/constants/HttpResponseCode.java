package kap.com.smarthome.android.communication.http.constants;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public class HttpResponseCode {

    /**
     * http远程请求的返回码
     *
     * 0	成功
     1001	开户的帐户已存在
     1002	指定帐户不存在
     1007	信息出现重复值登录名
     1008	信息出现重复值手机号
     1009	信息出现重复值微信
     1010	信息出现重复值QQ号
     8001	短信发送失败
     8002	验证码错误
     9000	数据非法
     9999	其它错误（未知异常）
     *
     */
    public static  final  String  SUCCESS = "0";

    public static  final  String  ACCOUNT_IS_EXIST = "1001";

    public static  final  String  ACCOUNT_IS_NOT_EXIST = "1002";

    public static  final  String  LOGINNAME_IS_NOT_EXIST = "1007";

    public static  final  String  PHONE_NUMBER_IS_NOT_EXIST = "1008";

    public static  final  String  WECHAT_IS_NOT_EXIST = "1009";

    public static  final  String  QQ_IS_NOT_EXIST = "1010";

    public static  final  String  SMS_SEND_FAIL = "8001";

    public static  final  String  VERIFI_CODE_ERROE = "8002";

    public static  final  String  DATA_ERROR = "9000";

    public static  final  String  OTHER_ERROR = "9999";
}
