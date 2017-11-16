package kap.com.smarthome.android.communication.udp.control;



import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.communication.bean.base.DATABean.UDPBoxIdData;
import kap.com.smarthome.android.communication.bean.base.DATABean.UDPDevicesData;
import kap.com.smarthome.android.communication.bean.base.DATABean.UDPScenesDeviceData;
import kap.com.smarthome.android.communication.bean.base.DATABean.UDPScenesTriggerData;
import kap.com.smarthome.android.communication.bean.base.JsonHeadBase;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPRequestBodyBase;
import kap.com.smarthome.android.communication.bean.extend.UDP.UDPAddRelayBoxRequestBody;
import kap.com.smarthome.android.communication.bean.extend.UDP.UDPAddRfDevicesRequestBody;
import kap.com.smarthome.android.communication.bean.extend.UDP.UDPAddScenesRequestBody;
import kap.com.smarthome.android.communication.bean.extend.UDP.UDPControlDevicesRequestBody;
import kap.com.smarthome.android.communication.bean.extend.UDP.UDPDeleteOneRfDevicesRequestBody;
import kap.com.smarthome.android.communication.bean.extend.UDP.UDPGetRfDeviceIdRequestBody;
import kap.com.smarthome.android.communication.bean.extend.UDP.UDPRelayBoxEnterAddDevicesRequestBody;
import kap.com.smarthome.android.communication.udp.constants.MsgINSIP;
import kap.com.smarthome.android.communication.udp.constants.UDPContants;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.data.bean.Scenes;
import kap.com.smarthome.android.data.bean.ScenesDevice;
import kap.com.smarthome.android.data.bean.ScenesTrigger;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.BeanDataConvertUtils;
import kap.com.smarthome.android.presenter.utils.TimeUtils;

/**
 * Created by Administrator on 2017/6/9 0009.
 * 为了让代码用起来更清晰，便于后面修改，防止复杂化，
 * 就对每一个操作需要的body进行单独的构建，没有采用面向对象的复用
 */

public class CreateUDPJsonData {

    // --------------------------------- UDP消息头  广播

    /**
     * 发送UDP广播信息 的头部信息
     * 当DEVICEID = "0000000000000000" 时是广播信息，
     * 发送给所有在线的中继盒子
     * @return
     */
    public static JsonHeadBase buildBroadCastHead(){
        JsonHeadBase  headBean = new JsonHeadBase();
        headBean.setTIMESTAMP(TimeUtils.getSystemCurrentTime());
        headBean.setSERVICEID(UDPContants.UDP_BROADCAST_HEAD_SERVICE_ID);
        headBean.setVERSION(UDPContants.UDP_JSON_HEAD_VERSION);
        headBean.setDEVICEID(UDPContants.UDP_BROADCAST_HEAD_DEVICES_ID);
        headBean.setREPEATCOUNT(UDPContants.UDP_REPEAT_SEND_COUNT);
        //TODO: 2017/10/13 0013 暂时直接用数据库中的user_id
        //headBean.setUSERID(DataBaseHandle.getUser().getUSER_ID());
        headBean.setUSERID(AllVariable.CURRENT_USER_ID);
        headBean.updateSERIALNUM();

        return  headBean;
    }

    // --------------------------------- UDP消息头 点对点信息

    /**
     *  发送UDP点对点信息 的头部信息
     *  发送点对点信息是 中继盒子的 DEVICEID 为某一个特定的UUID
     *  传入需要进行通信的中继盒子的Id
     * @return
     */
    public static JsonHeadBase buildOneRelayBoxHead(String  boxId){
        JsonHeadBase  headBean = new JsonHeadBase();
        headBean.setTIMESTAMP(TimeUtils.getSystemCurrentTime());
        headBean.setSERVICEID(UDPContants.UDP_BROADCAST_HEAD_SERVICE_ID);
        headBean.setVERSION(UDPContants.UDP_JSON_HEAD_VERSION);
        headBean.setREPEATCOUNT(UDPContants.UDP_REPEAT_SEND_COUNT);
        //TODO: 2017/10/13 0013 暂时直接用数据库中的user_id
        //headBean.setUSERID(DataBaseHandle.getUser().getUSER_ID());
        headBean.setUSERID(AllVariable.CURRENT_USER_ID);
        headBean.setDEVICEID(boxId);
        headBean.updateSERIALNUM();
        return  headBean;
    }

   // --------------------------------- 消息体 --------------------------------------------------

    /**
     * 1.1 UDP广播体  搜索中继盒子
     * @return
     */
    public static UDPRequestBodyBase buildBodySearchRelayBox(){
        UDPRequestBodyBase comJsonUDPBody = new UDPRequestBodyBase();
        comJsonUDPBody.setINSTP(MsgINSIP.SEARCH_REPETERBOX);
        return  comJsonUDPBody;
    }


    /**
     * 1.2 添加中继盒子 点对点信息
     * @return
     */
    public static UDPRequestBodyBase buildBodyAddRelayBox(String userId , String boxId) {
        UDPAddRelayBoxRequestBody udpAddRelayBoxRequestBody = new UDPAddRelayBoxRequestBody();
        udpAddRelayBoxRequestBody.setINSTP(MsgINSIP.ADD_REPETERBOX);
        udpAddRelayBoxRequestBody.setUSERID(userId);
        udpAddRelayBoxRequestBody.setBOXID(boxId);
        return  udpAddRelayBoxRequestBody;
    }

    /**
     * 删除中继盒子 点对点信息
     * @param userId
     * @param boxId
     * @return
     */
    public static UDPRequestBodyBase buildBodyDeteleRelayBox(String userId, String boxId) {
        UDPAddRelayBoxRequestBody udpAddRelayBoxRequestBody = new UDPAddRelayBoxRequestBody();
        udpAddRelayBoxRequestBody.setINSTP(MsgINSIP.UNBIND_ING_REQ);
        udpAddRelayBoxRequestBody.setUSERID(userId);
        udpAddRelayBoxRequestBody.setBOXID(boxId);
        return udpAddRelayBoxRequestBody;
    }



    /**
     * 1.3 对中继盒子进行设置 准备接受指令 进入添加设备的状态
     */
    public static UDPRequestBodyBase buildBodyCeateConnectToRelayBox(){
        UDPRequestBodyBase comJsonUDPBody = new UDPRequestBodyBase();
        comJsonUDPBody.setINSTP(MsgINSIP.REGISTER_REPETERBOX);
        return  comJsonUDPBody;
    }



    /**
     * 1.5	发送指令，控制中继盒子准备RF 设备添加
     * @param relayBoxList
     */
    public static UDPRequestBodyBase  buildBodyNoteRelayBoxStartAddRFDevice(List<RelayBox> relayBoxList){

        List<UDPBoxIdData>  boxIdDataList = new ArrayList<>();

        for (int i = 0; i < relayBoxList.size() ; i++){
            UDPBoxIdData udpBoxIdData = new UDPBoxIdData(relayBoxList.get(i).getBOX_ID());
            boxIdDataList.add(udpBoxIdData);
        }

        UDPRelayBoxEnterAddDevicesRequestBody body = new UDPRelayBoxEnterAddDevicesRequestBody();
        body.setINSTP(MsgINSIP.REGISTER_DEVICE_TO_REPETERBOX);
        body.setBOXNUM(boxIdDataList.size());
        body.setBOXIDLIST(boxIdDataList);

        return  body;
    }


    /**
     * 1.6  发送指令，控制中继盒子退出RF 设备添加
     */

    public static UDPRequestBodyBase  buildBodyNoteRelayBoxExitAddRfDevice(){
        UDPRequestBodyBase comJsonUDPBody = new UDPRequestBodyBase();
        comJsonUDPBody.setINSTP(MsgINSIP.EXIT_REGISTER_DEVICE_TO_REPETERBOX);
        return  comJsonUDPBody;
    }


    /**
     * 构建需要添加的设备的body 到中继盒子
     * @param selectDevices
     * @return
     */
    public static UDPRequestBodyBase buildBodyAddRfDevices(List<UDPDevicesData> selectDevices) {
        UDPAddRfDevicesRequestBody body = new UDPAddRfDevicesRequestBody();
        body.setINSTP(MsgINSIP.ADD_TERMINAL_DEVICELIST_REQ);
        body.setDEVNUM(selectDevices.size());
        body.setDEVICELIST(selectDevices);
        return body;
    }



    /**
     * 通知对应的 中继盒子删除关联的一个设备
     * @param device
     * @return
     */
    public static UDPRequestBodyBase buildBodyDeleteOneDevice(Devices device) {
        UDPDeleteOneRfDevicesRequestBody  udpRequestBodyBase  = new UDPDeleteOneRfDevicesRequestBody();
        udpRequestBodyBase.setINSTP(MsgINSIP.DELETE_ONE_DEVICE_ON_RELAY_BOX);
        udpRequestBodyBase.setTERMINALDEVICEID(device.getDEVICE_ID());
        udpRequestBodyBase.setTERMINALDEVICEVALUE(device.getVALUE());
        return  udpRequestBodyBase;
    }


    /**
     * 通知对应的 中继盒子删除关联的一个设备
     * @param devicesList
     * @return
     */
    public static UDPRequestBodyBase buildBodyDeleteDeviceList(List<Devices> devicesList) {
        List<UDPDevicesData> deviceDataList = BeanDataConvertUtils.convertToUDPDevicesData(devicesList);
        UDPAddRfDevicesRequestBody  udpRequestBodyBase  = new UDPAddRfDevicesRequestBody();
        udpRequestBodyBase.setINSTP(MsgINSIP.DELETE_DEVICE_LIST_ON_RELAY_BOX);
        udpRequestBodyBase.setDEVNUM(devicesList.size());
        udpRequestBodyBase.setDEVICELIST(deviceDataList);
        return  udpRequestBodyBase;
    }


    /**
     1.13	设备控制
     BODY =     {
     CONTROLTYPE = 0;
     DEVICEID = 01101E0A;
     INSTP = DEVICECONTROLREQ;
     VALUE = 00ff};
     */
    public static UDPControlDevicesRequestBody buildBodyControlDeviceByRelayBox(String deviceID, String value){
        UDPControlDevicesRequestBody  deviceControlUDPBody= new UDPControlDevicesRequestBody();
        deviceControlUDPBody.setINSTP(MsgINSIP.PHONE_CONTROL_DEVICES);
        deviceControlUDPBody.setDEVICEID(deviceID);
        deviceControlUDPBody.setCONTROLTYPE(0);
        deviceControlUDPBody.setVALUE(value);
        return  deviceControlUDPBody;
    }

    /**
     * 构建匹配中继盒子的UDP请求
     * @return
     */
    public static UDPRequestBodyBase buildBodyStartMatchMessage(String boxId) {
        UDPRequestBodyBase  udpRequestBodyBase  = new UDPRequestBodyBase();
        udpRequestBodyBase.setINSTP(MsgINSIP.SELECTED_REQ);
        udpRequestBodyBase.setBOXID(boxId);
        return  udpRequestBodyBase;
    }

    /**
     * 构建终止匹配中继盒子的UDP请求
     * @return
     */
    public static UDPRequestBodyBase buildBodyStopMatchMessage(String boxId) {
        UDPRequestBodyBase  udpRequestBodyBase  = new UDPRequestBodyBase();
        udpRequestBodyBase.setINSTP(MsgINSIP.UN_SELECTED_REQ);
        udpRequestBodyBase.setBOXID(boxId);
        return  udpRequestBodyBase;
    }


    /**
     * 开始进入红外学习
     * @return
     */
    public static UDPRequestBodyBase buildBodyStartIrLearn() {
        UDPRequestBodyBase  udpRequestBodyBase  = new UDPRequestBodyBase();
        udpRequestBodyBase.setINSTP(MsgINSIP.REGISTER_IR_STUDY_TO_REPETERBOX);
        return  udpRequestBodyBase;
    }

    /**
     * 中继盒子退出红外学习
     * @return
     */
    public static UDPRequestBodyBase buildBodyExitIrLearn() {
        UDPRequestBodyBase  udpRequestBodyBase  = new UDPRequestBodyBase();
        udpRequestBodyBase.setINSTP(MsgINSIP.EXIT_REGISTER_IR_STUDY_TO_RELAYRBOX);
        return  udpRequestBodyBase;
    }



    /**
     * 1.16	终端发送场景联动数据到中继盒子
     *
     “INSTP”:”SENDSCENEDATAREQ” ,
     “SCENEID”: “1234”, //场景ID号
     “SCENENAME”:”scene1”,
     “USERID”:”1234”,
     “DEVICENUMBER”: 2,
     “TRIGGERNUMBER”: 2,
     “TRIGGERSTATUS”: 0,
     “EXCUTEDEVICE”:[ {“DEVICEID”:”1234”, “DEVICEVALUE” :”1234”, “RELAYBOXID”:”1234”},
     {“DEVICEID”:”1234”, “DEVICEVALUE” :”1234”, “RELAYBOXID”:”1234”}],
     “TRIGGERDEVICE”:[ {“DEVICEID”:”1234”, “TRIGGERVALUE” :”1234”, “RELAYBOXID”:”1234”},
     {“DEVICEID”:”1234”, “TRIGGERVALUE” :”1234”, “RELAYBOXID”:”1234”}]
     *
     */
    public static  UDPRequestBodyBase  buildBodySendSceneDataToRelayBox(Scenes scenes, List<ScenesDevice> scenesDevices, List<ScenesTrigger> scenesTriggers ){
        //转换数据
        List<UDPScenesDeviceData> udpScenesDeviceDataList = BeanDataConvertUtils.convertToUDPScenesDeviceData(scenesDevices);
        List<UDPScenesTriggerData> udpScenesTriggerDataList = BeanDataConvertUtils.convertToUDPScenesTriggerData(scenesTriggers);

        UDPAddScenesRequestBody body = new UDPAddScenesRequestBody();
        body.setINSTP(MsgINSIP.UPDATE_SCENE_DATA_TO_RELAY_BOX);
        body.setTRIGGERSTATUS(scenes.getTRIGGER_STATUS());
        body.setSCENEID(scenes.getSCENE_ID());
        body.setDEVICENUMBER(scenesDevices.size());
        body.setTRIGGERNUMBER(scenesTriggers.size());
        body.setEXCUTEDEVICE(udpScenesDeviceDataList);
        body.setTRIGGERDEVICE(udpScenesTriggerDataList);
        return  body;
    }



    /**
     * 删除场景
     * @param scene_id
     * @return
     */
    public static UDPRequestBodyBase buildBodyDeleteOneScenes(String scene_id) {
        UDPAddScenesRequestBody body = new UDPAddScenesRequestBody();
        body.setINSTP(MsgINSIP.DEL_SCENE_DATA_REQ);
        body.setSCENEID(scene_id);
        return  body;
    }

    /**
     * 执行场景
     * @param scene_id
     * @return
     */
    public static UDPRequestBodyBase buildBodyExecuteOneScenes(String scene_id) {
        UDPAddScenesRequestBody body = new UDPAddScenesRequestBody();
        body.setINSTP(MsgINSIP.EXCUTE_SCENE_REQ);
        body.setSCENEID(scene_id);
        return  body;
    }


    /**
     * 设置场景条件开关
     * @param scene_id
     * @param trigger_state
     * @return
     */
    public static UDPRequestBodyBase buildBodyTriggerStateOneScenes(String scene_id, int trigger_state) {
        UDPAddScenesRequestBody body = new UDPAddScenesRequestBody();
        body.setINSTP(MsgINSIP.SET_SCENE_SWITCH_REQ);
        body.setTRIGGERSTATUS(trigger_state);
        body.setSCENEID(scene_id);
        return  body;
    }



    /**
     * 获取安防或者第三方设备的ID号
     * @param securitySubtype
     * @param securityCode
     * @return
     */
    public static UDPRequestBodyBase buildBodyGetSecurityDeviceId(String securitySubtype, String securityCode) {
        UDPGetRfDeviceIdRequestBody body = new UDPGetRfDeviceIdRequestBody();
        body.setINSTP(MsgINSIP.SECURITY_DEVICEID_REQ);
        body.setSECURITYSUBTYPE(securitySubtype);
        body.setSECURITYCODE(securityCode);
        return  body;

    }




    /**
     * 用户授权
     * @param accredit_userId
     * @return
     */
    public static UDPRequestBodyBase buildBodyReqUserAccredit(String accredit_userId) {
        UDPAddRelayBoxRequestBody udpAddRelayBoxRequestBody = new UDPAddRelayBoxRequestBody();
        udpAddRelayBoxRequestBody.setINSTP(MsgINSIP.ADD_REPETERBOX);
        udpAddRelayBoxRequestBody.setUSERID(accredit_userId);
        udpAddRelayBoxRequestBody.setBOXID("");
        return  udpAddRelayBoxRequestBody;
    }






//-----------------------------------------------------------------------------------

    /**
     *
     * APP读取中继盒子的网络配置信息
     */
    public static UDPRequestBodyBase  buildBodyReadRelayBoxNetConfig(){
        UDPRequestBodyBase comJsonUDPBody = new UDPRequestBodyBase();
        comJsonUDPBody.setINSTP(MsgINSIP.GET_REPETERBOX_NET_CONFIG);
        return  comJsonUDPBody;
    }

    /**
     *
     * 1.4	终端写入中继盒子的网络配置信息
     */

    /*public static UDPNetConfigBodyBean buildBodyWriteRelayBoxNetConfig(){
        UDPNetConfigBodyBean netConfigUDPBody = new UDPNetConfigBodyBean();
        netConfigUDPBody.setINSTP(MsgINSIP.WRITE_REPETERBOX_NET_CONFIG);
        netConfigUDPBody.setDHCP(0);
        netConfigUDPBody.setIP("192.168.1.128");
        netConfigUDPBody.setPORT(6902);
        netConfigUDPBody.setGWIP("192.168.1.1");
        netConfigUDPBody.setMASK("255.255.255.0");
        netConfigUDPBody.setSEVIP("192.168.1.245");
        netConfigUDPBody.setSEVPORT(6901);
        netConfigUDPBody.setDNS(0);
        netConfigUDPBody.setDNSSEV("202.96.128.86");
        netConfigUDPBody.setPLATF("www.koti.cn");
        netConfigUDPBody.setPLATFPORT(6911);
        netConfigUDPBody.setMYPORT(6912);
        return  netConfigUDPBody;
    }
*/




    /**
     * 1.7	中继盒子上传 RF设备注册信息到云端
     */
    public static UDPRequestBodyBase  buildBodyNoteRelayBoxExitRegisterRFDevice(){
        UDPRequestBodyBase comJsonUDPBody = new UDPRequestBodyBase();
        comJsonUDPBody.setINSTP(MsgINSIP.EXIT_REGISTER_DEVICE_TO_REPETERBOX);
        return  comJsonUDPBody;
    }

    /**
     * 1.8	中继盒子上传 RF设备注册信息到终端
     */



    /**
     * 1.9	通知中继盒子进入红外设备注册状态
     */
    public static UDPRequestBodyBase  buildBodyNoteRelayBoxIRDeviceRegister(){
        UDPRequestBodyBase comJsonUDPBody = new UDPRequestBodyBase();
        comJsonUDPBody.setINSTP(MsgINSIP.REGISTER_IR_STUDY_TO_REPETERBOX);
        return  comJsonUDPBody;
    }


    /**
     * 1.10 通知中继盒子退出红外设备注册状态
     * @return
     */
    public static UDPRequestBodyBase  buildBodyNoteRelayBoxExitIRDeviceRegister(){
        UDPRequestBodyBase comJsonUDPBody = new UDPRequestBodyBase();
        comJsonUDPBody.setINSTP(MsgINSIP.EXIT_REGISTER_IR_STUDY_TO_RELAYRBOX);
        return  comJsonUDPBody;
    }








    /**
     * 1.11	中继盒子上传红外设备学习数据到终端
     */



   /**
     * 1.12	 终端发送一组学习的有效红外数据给中继盒子（中继盒子上传到云端）
     */
    /*public static UDPIRCodeBodyBean buildBodySendIRStudyDataToRelayBox(){
        UDPIRCodeBodyBean irCodeUDPBody = new UDPIRCodeBodyBean();
        irCodeUDPBody.setINSTP(MsgINSIP.PHONE_UPDATA_IRSTUDYDATA_TO_REPETERBOX);
        return  irCodeUDPBody;
    }*/


    /**
     * 1.13	设备控制
     */


    /*public static UDPDeviceControlBodyBean buildBodyControlDeviceByRelayBox(int controltype, String deviceID, String value){
        UDPDeviceControlBodyBean  deviceControlUDPBody= new UDPDeviceControlBodyBean();
        deviceControlUDPBody.setINSTP(MsgINSIP.PHONE_CONTROL_DEVICES);
        deviceControlUDPBody.setDEVICEID(deviceID);
        deviceControlUDPBody.setCONTROLTYPE(controltype);
        deviceControlUDPBody.setVALUE(value);
        return  deviceControlUDPBody;
    }*/

    /**
     * 1.15	终端获取所有设备列表和设备状态
     */
    public static  UDPRequestBodyBase  buildBodyUpdataAllDevicesStateFromRelayBox(){
        UDPRequestBodyBase comJsonUDPBody = new UDPRequestBodyBase();
        comJsonUDPBody.setINSTP(MsgINSIP.PHONE_UPDATA_ALL_DEVICES_STATE);
       // comJsonUDPBody.setTIMESTAMP(JsonUtils.getSystemCurrentTime());
        return  comJsonUDPBody;
    }















    /*public static UDPRequestBodyBase buildBodyAddScenes() {


    }*/


    /**
     * 1.17 终端发送删除指定设备消息到中继盒子
     */

    /*public static  UDPDeviceControlBodyBean  buildBodyDeleteDevicesFromRelayBox(String  deviceId){
        UDPDeviceControlBodyBean deviceControlUDPBody = new UDPDeviceControlBodyBean();
        deviceControlUDPBody.setINSTP(MsgINSIP.PHONE_UPDATA_SCENEDATA_TO_REPETERBOX);
        deviceControlUDPBody.setDEVICEID(deviceId);
        return  deviceControlUDPBody;
    }*/



}
