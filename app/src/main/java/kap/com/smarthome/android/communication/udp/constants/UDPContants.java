package kap.com.smarthome.android.communication.udp.constants;


/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class UDPContants {
    /**
     * UDP通信 配置常量
     */
    //UDP 接收数据缓冲区大小
    public static final int UDP_RECEIVER_BUF_SIZE = 4096;


    //UDP 到中继盒子数据广播
    public static final String UDP_BROADCAST_RELAY_BXO_IP = "255.255.255.255";


    //UDP APP和中继盒子的通信端口
    public static final int UDP_TO_RELAY_BOX_PORT = 6902;


    //UDP 数据发送的次数设定 (防止丢包的重发次数)
    public static final int UDP_REPEAT_SEND_COUNT = 1;


    //UDP 数据重发的时间间隔设置 (防止丢包的重发次数)
    public static final int UDP_REPEAT_SEND_TIME= 500;


    //UDP 与中继盒子通信的协议版本号
    public static final String UDP_JSON_HEAD_VERSION = "V0.02";


    //UDP 与中继盒子广播通信的协议头中的DevicesId, 广播为全零
    public static final String UDP_BROADCAST_HEAD_DEVICES_ID = "0000000000000000";


    // UDP 与中继盒子广播通信的协议头中的SERVICE_ID 默认为12345678
    public static final String UDP_BROADCAST_HEAD_SERVICE_ID = "12345678";




    /**
     * UDP返回 结果成功
     * UDP_RESPONSE_SUCCESS
     */
    public static final int UDP_RESPONSE_SUCCESS = 0 ;

    /**
     * UDP返回 结果失败
     * UDP_RESPONSE_FAIL
     */
    public static final int UDP_RESPONSE_FAIL = 1 ;






}
