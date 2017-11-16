package kap.com.smarthome.android.communication.udp.constants;

/**
 * Created by Administrator on 2017/6/1 0001.
 *
 * 该类中定义的字符串是 APP 与中继盒子通信的信息类型
 * 根据不同的INSTP 值进行不同的操作和响应
 * 该类中的操作类型对应 UDPDoFlag中的十六进制操作码
 */

public class MsgINSIP {

    //搜索中继盒子的 INSTP, 和收到的回应
    public static final String SEARCH_REPETERBOX = "DEVICEFINDREQ";

    public static final String ACK_SEARCH_REPETERBOX = "DEVICEFINDACK";


    //添加中继盒子的 INSTP, 和收到的回应
    public static final String ADD_REPETERBOX = "BINDINGREQ";

    public static final String ACK_ADD_REPETERBOX = "BINDINGACK";


    //删除中继盒子的 INSTP, 和收到的回应
    public static final String UNBIND_ING_REQ = "UNBINDINGREQ";

    public static final String ACK_UNBIND_ING = "UNBINDINGACK";



    //APP 添加设备的时候发出的第一条消息
    public static final String  REGISTER_REPETERBOX = "REGISTERMAGICTOUCHREQ";

    public static final String  ACK_EGISTER_REPETERBOX = "REGISTERMAGICTOUCHACK";


    //APP通知中继盒子进入RF设备注册状态 和收到的回应
    public static final String  REGISTER_DEVICE_TO_REPETERBOX = "REGISTERSTARTREQ";

    public static final String  ACK_REGISTER_DEVICE_TO_REPETERBOX = "REGISTERSTARTACK";

    //APP通知中继盒子退出RF设备注册状态
    public static final String  EXIT_REGISTER_DEVICE_TO_REPETERBOX = "REGISTERENDREQ";

    public static final String  ACK_EXIT_REGISTER_DEVICE_TO_REPETERBOX = "REGISTERENDACK";


    //终端发送删除指定单个设备消息到 中继盒子  单个设备
    public static final String  DELETE_ONE_DEVICE_ON_RELAY_BOX = "DELTERMINALDEVICEREQ";

    public static final String  ACK_DELETE_ONE_DEVICE_ON_RELAY_BOX = "DELTERMINALDEVICEACK";


    //终端发送删除指定设备列表消息到 中继盒子  删除多个设备 （设备列表）

    public static final String  DELETE_DEVICE_LIST_ON_RELAY_BOX = "DELTERMINALDEVICELISTREQ";

    public static final String  ACK_DELETE_DEVICE_LIST_ON_RELAY_BOX = "DELTERMINALDEVICELISTACK";




    //终端发送匹配中继盒子的信息
    public static final String  SELECTED_REQ = "SELECTEDREQ";

    public static final String  SELECTED_ACK = "SELECTEDACK";

    //终止匹配中继盒子的信息

    public static final String  UN_SELECTED_REQ = "UNSELECTEDREQ";

    public static final String  UN_SELECTED_ACK = "UNSELECTEDACK";

    //通知中继盒子进入红外设备注册状态
    public static final String  REGISTER_IR_STUDY_TO_REPETERBOX = "IRSTUDYSTARTREQ";

    public static final String  ACK_REGISTER_IR_STUDY_TO_REPETERBOX = "IRSTUDYSTARTACK";


    //通知中继盒子退出红外设备注册状态
    public static final String  EXIT_REGISTER_IR_STUDY_TO_RELAYRBOX = "IRSTUDYENDREQ";

    public static final String ACK_EXIT_REGISTER_IR_STUDY_TO_RELAYRBOX = "IRSTUDYENDACK";


    //1.11	中继盒子上传红外设备学习数据到APP终端
    public static final String  REQ_IRSTUDYDATA_TO_PHONE = "IRSTUDYDATAREQ";

    public static final String  ACE_IRSTUDYDATA_TO_PHONE = "IRSTUDYDATAACK";


    //1.16	APP终端发送场景联动数据到中继盒子  SENDSCENEDATAREQ
    public static final String  UPDATE_SCENE_DATA_TO_RELAY_BOX = "SENDSCENEDATAREQ";

    public static final String  ACK_UPDATE_SCENE_DATA_TO_RELAY_BOX = "SENDSCENEDATAACK";


    //1.19 执行场景 请求和回复
    public static final String  EXCUTE_SCENE_REQ = "EXCUTESCENEREQ";

    public static final String  EXCUTE_SCENE_ACK = "EXCUTESCENEACK";


    //1.19 删除场景 请求和回复
    public static final String  DEL_SCENE_DATA_REQ = "DELSCENEDATAREQ";

    public static final String  DEL_SCENE_DATA_ACK = "DELSCENEDATAACK";


    //1.19 场景触发条件开关 请求和回复
    public static final String  SET_SCENE_SWITCH_REQ = "SETSCENESWITCHREQ";

    public static final String  SET_SCENE_SWITCH_ACK = "SETSCENESWITCHACK";


    //1.17  中继盒子发送安防对码值到app, app接收到安防码值弹出相应的列表让用户选择
    public static final String  SECURITY_CODE_REQ = "SECURITYCODEREQ";

    public static final String  SECURITY_CODE_ACK = "SECURITYCODEACK";


    //1.18 发送获取安防设备的ID号的指令和回复
    public static final String  SECURITY_DEVICEID_REQ = "SECURITYDEVICEIDREQ";

    public static final String  SECURITY_DEVICEID_ACK = "SECURITYDEVICEIDACK";



    //1.19 添加设备 请求和回复
    public static final String  ADD_TERMINAL_DEVICELIST_REQ = "ADDTERMINALDEVICELISTREQ";

    public static final String  ADD_TERMINAL_DEVICELIST_ACK = "ADDTERMINALDEVICELISTACK";


    //4.18	设备控制
    public static final String  PHONE_CONTROL_DEVICES = "DEVICECONTROLREQ";

    public static final String  ACK_PHONE_CONTROL_DEVICES = "DEVICECONTROLACK";


    //从中继盒子更新设备状态到APP终端
    public static final String  PHONE_UPDATE_DEVICES_STATE = "DEVICESTATEUPDATEREQ";

    public static final String  ACK_PHONE_UPDATA_DEVICES_STATE = "DEVICESTATEUPDATEACK";



    //-----------------------------------------------------------------------------------------------

/*
    1.1	选中中继盒子，采用点对点方式，只是局域网内实现
    方向：移动终端- >中继盒子,
    请求：
            “HEAD”:{
“TIMESTAMP”:时间戳, （系统当前时间）
“SERVICEID”:业务代码,
“VERSION”:” V4.0”
“DEVICEID”:中继盒子ID,
“USERID”:用户ID,
“SERIALNUM”:流水号,
“REPEATCOUNT”:该消息重复次数,
    },
            “BODY”:{
“INSTP”:”SELECTEDREQ” ,
“BOXID” :” 0000000002000001”
    }
    回应：
            “BODY”:{
“INSTP”:” SELECTEDACK”,
“RESULT”:0
    }

1.2	取消选中中继盒子，采用点对点方式，只是局域网内实现
    方向：移动终端- >中继盒子,
    请求：
            “HEAD”:{
“TIMESTAMP”:时间戳, （系统当前时间）
“SERVICEID”:业务代码,
“VERSION”:” V4.0”
“DEVICEID”:中继盒子ID,
“USERID”:用户ID,
“SERIALNUM”:流水号,
“REPEATCOUNT”:该消息重复次数,
    },
            “BODY”:{
“INSTP”:”UNSELECTEDREQ” ,
“BOXID” :” 0000000002000001”
    }
    回应：
            “BODY”:{
“INSTP”:” UNSELECTEDACK”,
“RESULT”:0
    }*/


    //终端读取中继盒子的网络配置信息 和收到的回应
    public static final String  GET_REPETERBOX_NET_CONFIG = "GATEWAYCFGGETREQ";

    public static final String  ACK_GET_REPETERBOX_NET_CONFIG = "GATEWAYCFGGETACK";

    //终端写入网络配置信息到中继盒子 和收到的回应
    public static final String  WRITE_REPETERBOX_NET_CONFIG = "GATEWAYCFGSETREQ";

    public static final String ACK_WRITE_REPETERBOX_NET_CONFIG = "GATEWAYCFGSETACK";



    //中继盒子上传 RF设备注册信息到云端
    public static final String  REPETER_BOX_UPDATA_TO_WEB = "REGISTERDATACLOUDREQ";

    public static final String  ACK_REPETER_BOX_UPDATA_TO_WEB = "REGISTERDATAACK";


    //中继盒子上传 RF设备注册信息到APP终端
    public static final String  REPETER_BOX_UPDATA_TO_PHONE = "REGISTERDATAREQ";

    public static final String  ACK_REPETER_BOX_UPDATA_TO_PHONE = "REGISTERDATAACK";


    //1.11	中继盒子上传红外设备学习数据到APP终端
    public static final String  REPETERBOX_UPDATA_IRSTUDYDATA_TO_PHONE = "IRSTUDYDATAREQ";

    public static final String  ACE_REPETERBOX_UPDATA_IRSTUDYDATA_TO_PHONE = "IRSTUDYDATAACK";


    //APP终端发送一组学习的有效红外数据给中继盒子
    public static final String  PHONE_UPDATA_IRSTUDYDATA_TO_REPETERBOX = "IRSTUDYDATAREQ";

    public static final String  ACK_PHONE_UPDATA_IRSTUDYDATA_TO_REPETERBOX = "IRSTUDYDATASENDACK";


    //1.15	APP终端获取所有设备列表和设备状态
    public static final String  PHONE_UPDATA_ALL_DEVICES_STATE = "DEVICESTATEUPDATEREQ";

    public static final String  ACK_PHONE_UPDATA_ALL_DEVICES_STATE = "DEVICESTATEUPDATEACK";





}
