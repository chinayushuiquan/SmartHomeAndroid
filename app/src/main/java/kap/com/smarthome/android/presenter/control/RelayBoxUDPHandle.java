package kap.com.smarthome.android.presenter.control;

import java.util.List;

import kap.com.smarthome.android.communication.bean.base.DATABean.UDPDevicesData;
import kap.com.smarthome.android.communication.bean.base.DATABean.UDPScenesDeviceData;
import kap.com.smarthome.android.communication.bean.base.DATABean.UDPScenesTriggerData;
import kap.com.smarthome.android.communication.udp.doapi.DoUdpApi;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.data.bean.Scenes;
import kap.com.smarthome.android.data.bean.ScenesDevice;
import kap.com.smarthome.android.data.bean.ScenesTrigger;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class RelayBoxUDPHandle{
    /**
     * 和中继盒子建立连接
     */
    public static void createConnectToRelayBox() {
        DoUdpApi.getInstance().createConnectToRelayBox();
    }

    /**
     * 搜索中继盒子
     */
    public static void searchRelayBox(){
        DoUdpApi.getInstance().searchRelayBox();
    }


    /**
     * 添加中继盒子信息
     * @param box_id
     */
    public static void addRelayBox(String user_id, String box_id, String ip) {
        DoUdpApi.getInstance().addRelayBox(user_id,box_id,ip);
    }

    /**
     * 删除中继盒子
     * @param user_id
     * @param box_id
     * @param ip
     */
    public static void deleteRelayBox(String user_id, String box_id, String ip) {
        DoUdpApi.getInstance().deleteRelayBox(user_id,box_id,ip);
    }


    /**
     * 中继盒子进行设备添加
     *
     * 在上一个信息接收到回复之后紧接着发送这个信息
     */
    public static void setRelayBoxRegisterAddDevicesState(List<RelayBox> relayBoxList) {
        DoUdpApi.getInstance().setRelayBoxRegisterAddDevicesState(relayBoxList);
    }


    /**
     * 中继盒子退出设备注册状态
     */
    public static void setRelayBoxExitAddDeviceState() {
        DoUdpApi.getInstance().setRelayBoxExitAddDeviceState();
    }


    /**
     * 删除单个设备
     * @param device
     */
    public static void deleteOneDevices(Devices device  , String  boxIP) {
        DoUdpApi.getInstance().deleteOneDevices(device , boxIP);
    }


    /**
     * 删除多个设备
     * @param devicesList
     */
    public static void deleteDevicesList(List<Devices> devicesList) {
        DoUdpApi.getInstance().deleteDevicesList(devicesList);
    }


    /**
     * 控制设备
     */
    public static void controlDevices(Devices devices , String value , String boxIP) {
        DoUdpApi.getInstance().controlDevice(devices , value ,  boxIP);
    }




    /**
     *
     * 匹配中继盒子的信息
     *
     * 红外学习需要找到对应的中继盒子，通过发送匹配信息 查看中继盒子的灯的闪烁状态
     */
    public static void sendStartMatchMessage(String boxID, String boxIP) {
        DoUdpApi.getInstance().sendStartMatchMessage(boxID,boxIP);
    }


    /**
     *
     * 终止某个 中继盒子的信息
     *
     * 退出匹配
     */
    public static void sendStopMatchMessage(String boxID, String boxIP) {
        DoUdpApi.getInstance().sendStopMatchMessage(boxID,boxIP);
    }


    /**
     *  开始红外学习
     */
    public static void startIrLearn(String boxID, String boxIP) {
        DoUdpApi.getInstance().startIrLearn(boxID,boxIP);
    }

    /**
     *  退出红外学习
     */
    public static void exitIrLearn(String boxID, String boxIP) {
        DoUdpApi.getInstance().exitIrLearn(boxID,boxIP);
    }


    /**
     * 添加场景到中继盒子
     * @param scenes
     * @param scenesDevices
     * @param scenesTriggers
     * @param boxID
     * @param boxIP
     */
    public static void addScenes(Scenes scenes , List<ScenesDevice> scenesDevices  , List<ScenesTrigger> scenesTriggers , String  boxID , String boxIP) {
        DoUdpApi.getInstance().addScenes(scenes,scenesDevices,scenesTriggers,boxID , boxIP);
    }


    /**
     * 删除场景
     * @param scenes_id
     * @param box_id
     * @param box_ip
     */
    public static void deleteOneScenes(String scenes_id, String box_id, String box_ip) {
        DoUdpApi.getInstance().deleteOneScenes(scenes_id, box_id , box_ip);
    }

    /**
     * 执行场景
     * @param scenes_id
     * @param box_id
     * @param box_ip
     */
    public static void executeOneScenes(String scenes_id, String box_id, String box_ip) {
        DoUdpApi.getInstance().executeOneScenes(scenes_id, box_id , box_ip);
    }

    /**
     * 触发条件开关
     * @param scenes_id
     * @param trigger_state
     * @param box_id
     * @param box_ip
     */
    public static void setScenesTriggerState(String scenes_id, int trigger_state , String box_id, String box_ip) {
        DoUdpApi.getInstance().setScenesTriggerState(scenes_id, trigger_state , box_id , box_ip);
    }



    /**
     * 获取RF类安防设备的ID号， 把码值和安防设备的类型发给中继
     * @param securitySubtype
     * @param securityCode
     */
    public static void getSecurityDeviceIdReq(String securitySubtype, String securityCode , String  boxID , String boxIP) {
        DoUdpApi.getInstance().getSecurityDeviceIdReq(securitySubtype, securityCode , boxID , boxIP);
    }


    /**
     * 添加设备
     * @param addDevices
     */
    public static void addRfDevice(List<UDPDevicesData> addDevices) {
        DoUdpApi.getInstance().addRfDevice(addDevices);
    }



    /**
     * 用户授权
     * @param accredit_userId
     */
    public static void reqOtherUserAccredit(String accredit_userId) {
        DoUdpApi.getInstance().reqOtherUserAccredit(accredit_userId);
    }


    /**
     * 构建远程控制场景数据  发送到云端进行远程控制
     * @return
     */
    public static String  executeOneScenesHttpData(String scene_id , String   boxID) {
           return  DoUdpApi.getInstance().executeOneScenes(scene_id , boxID);
    }


    /**
     * 只是构建控制设备的命令 发送到云端进行远程控制
     */
    public static String controlDevicesHttpData(Devices devices , String value ) {
        return DoUdpApi.getInstance().controlDevice(devices , value);
    }
}
