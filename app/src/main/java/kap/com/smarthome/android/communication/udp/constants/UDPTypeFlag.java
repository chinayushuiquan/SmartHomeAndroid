package kap.com.smarthome.android.communication.udp.constants;

/**
 * Created by Administrator on 2017/6/13 0013.
 *
 */

public class UDPTypeFlag {

    //搜索中继盒子的 INSTP, 和收到的回应
    public static final int SEARCH_RELAYBOX = 0x0001;

    public static final int ACK_SEARCH_RELAYBOX = 0x0002;


    //终端向中继盒子注册 和收到的回应
    public static final int  REGISTER_RELAYBOX = 0x0003;

    public static final int  ACK_EGISTER_RELAYBOX = 0x0004;



    //终端读取中继盒子的网络配置信息 和收到的回应
    public static final int  GET_RELAYBOX_NET_CONFIG = 0x0005;

    public static final int  ACK_GET_RELAYBOX_NET_CONFIG = 0x0006;


    //终端写入网络配置信息到中继盒子 和收到的回应
    public static final int  WRITE_RELAYBOX_NET_CONFIG = 0x0007;

    public static final int ACK_WRITE_RELAYBOX_NET_CONFIG = 0x0008;

    //APP通知中继盒子进入RF设备注册状态 和收到的回应
    public static final int  REGISTER_DEVICE_TO_RELAYBOX =0x0009;

    public static final int  ACK_REGISTER_DEVICE_TO_RELAYBOX = 0x0010;

    //APP通知中继盒子退出RF设备注册状态
    public static final int  EXIT_REGISTER_DEVICE_TO_RELAYBOX = 0x0011;

    public static final int  ACK_EXIT_REGISTER_DEVICE_TO_RELAYBOX =0x0012;


    //中继盒子上传 RF设备注册信息到云端
    public static final int  RELAY_BOX_UPDATA_TO_WEB = 0x0013;

    public static final int  ACK_RELAY_BOX_UPDATA_TO_WEB =0x0014;

    //中继盒子上传 RF设备注册信息到APP终端
    public static final int  RELAY_BOX_UPDATA_TO_PHONE = 0x0015;

    public static final int  ACK_RELAY_BOX_UPDATA_TO_PHONE = 0x0016;


    //通知中继盒子进入红外设备注册状态
    public static final int  REGISTER_IR_STUDY_TO_RELAYBOX = 0x0017;

    public static final int  ACK_REGISTER_IR_STUDY_TO_RELAYBOX = 0x0018;

    //通知中继盒子退出红外设备注册状态
    public static final int  EXIT_REGISTER_IR_STUDY_TO_RELAYBOX = 0x0019;

    public static final int  ACK_EXIT_REGISTER_IR_STUDY_TO_RELAYBOX = 0x0020;

    //1.11	中继盒子上传红外设备学习数据到APP终端
    public static final int  RELAYBOX_UPDATA_IRSTUDYDATA_TO_PHONE = 0x0021;

    public static final int  ACE_RELAYBOX_UPDATA_IRSTUDYDATA_TO_PHONE = 0x0022;

    //APP终端发送一组学习的有效红外数据给中继盒子
    public static final int  PHONE_UPDATA_IRSTUDYDATA_TO_RELAYBOX = 0x0023;

    public static final int  ACK_PHONE_UPDATA_IRSTUDYDATA_TO_RELAYBOX = 0x0024;

    //4.18	设备控制
    public static final int  PHONE_CONTROL_DEVICES = 0x0025;

    public static final int  ACK_PHONE_CONTROL_DEVICES = 0x0026;


    //从中继盒子更新设备状态到APP终端
    public static final int  PHONE_UPDATA_DEVICES_STATE = 0x0027;

    public static final int  ACK_PHONE_UPDATA_DEVICES_STATE = 0x0028;

    //1.15	APP终端获取所有设备列表和设备状态
    public static final int  PHONE_UPDATA_ALL_DEVICES_STATE = 0x0029;

    public static final int  ACK_PHONE_UPDATA_ALL_DEVICES_STATE = 0x0030;


    //1.16	APP终端发送场景联动数据到中继盒子
    public static final int  PHONE_UPDATA_SCENEDATA_TO_RELAYBOX = 0x0031;

    public static final int  ACK_PHONE_UPDATA_SCENEDATA_TO_RELAYBOX = 0x0032;


    //终端发送删除指定设备消息到中继盒子
    public static final int  PHONE_DELECT_DEVICE_ON_RELAYBOX = 0x0033;

    public static final int  ACK_PHONE_DELECT_DEVICE_ON_RELAYBOX = 0x0034;



}
