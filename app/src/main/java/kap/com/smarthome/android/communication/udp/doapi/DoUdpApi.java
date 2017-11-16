package kap.com.smarthome.android.communication.udp.doapi;

import java.util.List;

import kap.com.smarthome.android.communication.bean.base.DATABean.UDPDevicesData;
import kap.com.smarthome.android.communication.bean.base.DATABean.UDPScenesDeviceData;
import kap.com.smarthome.android.communication.bean.base.DATABean.UDPScenesTriggerData;
import kap.com.smarthome.android.communication.udp.control.BuildUDPJsonData;
import kap.com.smarthome.android.communication.udp.control.CreateUDPJsonData;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.data.bean.Scenes;
import kap.com.smarthome.android.data.bean.ScenesDevice;
import kap.com.smarthome.android.data.bean.ScenesTrigger;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class DoUdpApi {

    //保证全局只有一个UDP操作类
    private DoUdpApi(){}

    private  static DoUdpApi doUdpApi = null;

    public   static  synchronized  DoUdpApi  getInstance(){
        if (doUdpApi == null){
            doUdpApi  = new DoUdpApi();
        }
        return  doUdpApi;
    }


    /**
     * registerRelayBox
     * 和中继盒子建立连接
     */
    public void  createConnectToRelayBox(){
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildBroadCastHead(),
                CreateUDPJsonData.buildBodyCeateConnectToRelayBox());
        BuildUDPJsonData.sendUDPDataToAllRelay(jsonData);
    }

    /**
     * searchRelayBox  广播信息
     * 查找中继盒子
     */
    public void searchRelayBox(){
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildBroadCastHead(),
                CreateUDPJsonData.buildBodySearchRelayBox());
        BuildUDPJsonData.sendUDPDataToAllRelay(jsonData);
    }


    /**
     * 添加中继盒子  点对点信息  （由于中继盒子需要 ，虽然是点对点信息，协议头还是广播形式的头信息）
     */
    public void addRelayBox(String userId , String boxId , String boxIP) {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildBroadCastHead(),
                CreateUDPJsonData.buildBodyAddRelayBox(userId,boxId));
        BuildUDPJsonData.sendUDPDataToOneRelayBox(jsonData , boxIP);
    }


    /**
     * 删除中继盒子 点对点信息 （由于中继盒子需要 ，虽然是点对点信息，协议头还是广播形式的头信息）
     */

    public void deleteRelayBox(String userId , String boxId , String boxIP) {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildBroadCastHead(),
                CreateUDPJsonData.buildBodyDeteleRelayBox(userId,boxId));
        BuildUDPJsonData.sendUDPDataToOneRelayBox(jsonData , boxIP );
    }


    /**
     * 让中继盒子开始接收设备注册信息  广播信息
     */
    public void setRelayBoxRegisterAddDevicesState(List<RelayBox> relayBoxList) {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildBroadCastHead(),
                CreateUDPJsonData.buildBodyNoteRelayBoxStartAddRFDevice(relayBoxList));
        BuildUDPJsonData.sendUDPDataToAllRelay(jsonData);
    }


    /**
     * 让中继盒子退出设备注册注册状态  广播信息
     */
    public void setRelayBoxExitAddDeviceState() {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildBroadCastHead(),
                CreateUDPJsonData.buildBodyNoteRelayBoxExitAddRfDevice());
        BuildUDPJsonData.sendUDPDataToAllRelay(jsonData);
    }



    /**
     * 发送控制命令到中继盒子 广播信息
     */
    public  void  controlDevice(Devices device , String value , String boxIP) {

        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildOneRelayBoxHead(device.getRELAY_ID()),

                CreateUDPJsonData.buildBodyControlDeviceByRelayBox(device.getDEVICE_ID(), value));

        BuildUDPJsonData.sendUDPDataToOneRelayBox(jsonData , boxIP);
    }


    /**
     * 构建控制设备的指令
     */

    public  String  controlDevice(Devices device , String value) {

        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildOneRelayBoxHead(device.getRELAY_ID()),

                CreateUDPJsonData.buildBodyControlDeviceByRelayBox(device.getDEVICE_ID(), value));

        return jsonData ;
    }

    /**
     * 添加设备 广播
     * @param selectDevices
     */
    public void addRfDevice(List<UDPDevicesData> selectDevices) {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildBroadCastHead(),
                CreateUDPJsonData.buildBodyAddRfDevices(selectDevices));
        BuildUDPJsonData.sendUDPDataToAllRelay(jsonData);
    }


    /**
     * 删除设备  点对点信息
     * @param device
     */
    public void deleteOneDevices(Devices device, String boxIP) {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildOneRelayBoxHead(device.getRELAY_ID()),
                CreateUDPJsonData.buildBodyDeleteOneDevice(device));
        BuildUDPJsonData.sendUDPDataToOneRelayBox(jsonData, boxIP);
    }


    /**
     * 删除中继盒子设备 列表中设备，广播方式
     * @param devicesList
     */
    public void deleteDevicesList(List<Devices> devicesList) {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildBroadCastHead(),
                CreateUDPJsonData.buildBodyDeleteDeviceList(devicesList));
        BuildUDPJsonData.sendUDPDataToAllRelay(jsonData);
    }


    /**
     * 匹配中继盒子信息  点对点信息
     * @param boxIP boxId
     */
    public void sendStartMatchMessage(String boxID, String boxIP) {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildOneRelayBoxHead(boxID),
                CreateUDPJsonData.buildBodyStartMatchMessage(boxID));
        BuildUDPJsonData.sendUDPDataToOneRelayBox(jsonData, boxIP);
    }


    /**
     * 解除匹配中继盒子  点对点信息
     * @param boxIP boxId
     */
    public void sendStopMatchMessage(String boxID, String boxIP) {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildOneRelayBoxHead(boxID),
                CreateUDPJsonData.buildBodyStopMatchMessage(boxID));
        BuildUDPJsonData.sendUDPDataToOneRelayBox(jsonData, boxIP);
    }


    /**
     * 开始红外学习  点对点
     * @param boxID
     * @param boxIP
     * <BODY>
     * <INSTP>IRSTUDYSTARTREQ</INSTP>
     * <RFID>1001116016400000</RFID>
     * </BODY>
     */
    public void startIrLearn(String boxID, String boxIP) {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildOneRelayBoxHead(boxID),
                CreateUDPJsonData.buildBodyStartIrLearn());
        BuildUDPJsonData.sendUDPDataToOneRelayBox(jsonData, boxIP);
    }


    /**
     * 退出红外学习  点对点
     */
    public void exitIrLearn(String boxID, String boxIP) {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildOneRelayBoxHead(boxID),
                CreateUDPJsonData.buildBodyExitIrLearn());
        BuildUDPJsonData.sendUDPDataToOneRelayBox(jsonData, boxIP);
    }


    /**
     * 添加场景
     * @param scenes  场景   点对点
     * @param scenesDevices 场景的控制设备集合
     * @param scenesTriggers 场景的触发条件集合
     */
    public void addScenes(Scenes scenes, List<ScenesDevice> scenesDevices, List<ScenesTrigger> scenesTriggers , String  boxID , String boxIP) {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildOneRelayBoxHead(boxID),
                CreateUDPJsonData.buildBodySendSceneDataToRelayBox(scenes , scenesDevices , scenesTriggers));
        BuildUDPJsonData.sendUDPDataToOneRelayBox(jsonData, boxIP);
    }


    /**
     * 删除一个场景 点对点
     * @param scene_id
     */
    public void deleteOneScenes(String scene_id ,  String  boxID , String boxIP) {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildOneRelayBoxHead(boxID),
                CreateUDPJsonData.buildBodyDeleteOneScenes(scene_id));
        BuildUDPJsonData.sendUDPDataToOneRelayBox(jsonData, boxIP);
    }


    /**
     * 执行场景 点对点
     */
    public void executeOneScenes(String scene_id ,  String  boxID , String boxIP) {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildOneRelayBoxHead(boxID),
                CreateUDPJsonData.buildBodyExecuteOneScenes(scene_id));
        BuildUDPJsonData.sendUDPDataToOneRelayBox(jsonData, boxIP);
    }

    /**
     * 执行场景 点对点
     */
    public String executeOneScenes(String scene_id ,  String  boxID) {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildOneRelayBoxHead(boxID),
                CreateUDPJsonData.buildBodyExecuteOneScenes(scene_id));
        return  jsonData;
    }

    /**
     * 设置场景开关 点对点
     */
    public void setScenesTriggerState(String scene_id , int trigger_state , String  boxID , String boxIP) {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildOneRelayBoxHead(boxID),
                CreateUDPJsonData.buildBodyTriggerStateOneScenes(scene_id , trigger_state));
        BuildUDPJsonData.sendUDPDataToOneRelayBox(jsonData, boxIP);
    }


    /**
     * 获取RF类安防设备的ID号， 把码值和安防设备的类型发给中继   点对点
     * @param securitySubtype
     * @param securityCode
     * @param boxID
     * @param boxIP
     */
    public void getSecurityDeviceIdReq(String securitySubtype, String securityCode, String boxID, String boxIP) {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildOneRelayBoxHead(boxID),
                CreateUDPJsonData.buildBodyGetSecurityDeviceId(securitySubtype, securityCode));
        BuildUDPJsonData.sendUDPDataToOneRelayBox(jsonData, boxIP);
    }


    /**
     * 用户授权  广播
     * @param accredit_userId
     */
    public void reqOtherUserAccredit(String accredit_userId) {
        String jsonData = BuildUDPJsonData.buildData(CreateUDPJsonData.buildBroadCastHead(),
                CreateUDPJsonData.buildBodyReqUserAccredit(accredit_userId));
        BuildUDPJsonData.sendUDPDataToAllRelay(jsonData);
    }


}
