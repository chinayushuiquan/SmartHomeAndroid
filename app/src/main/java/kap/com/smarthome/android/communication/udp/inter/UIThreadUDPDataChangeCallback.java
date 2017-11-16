package kap.com.smarthome.android.communication.udp.inter;


import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;

/**
 * Created by yushq on 2017/6/9 0009.
 * 通过UDP或者Http方式从中继盒子或者服务器的接收数据进行
 * 进行不同的类型判断，根据收到不同的数据指令进行不同的调用
 * 在UDPDataManage和
 */

public interface UIThreadUDPDataChangeCallback {

    /**
     * 中继盒子搜索成功操作
     */
    void uiSearchRelayBoxSuccessCallback(UDPReceiverData udpReceiver);


    /**
     * 添加中继盒子成功
     * @param udpReceiver
     */
    void uiAddRelayBoxSuccessCallback(UDPReceiverData udpReceiver);

    /**
     * 删除中继盒子的返回
     * @param udpReceiver
     */
    void uiDeleteRelayBoxSuccessCallback(UDPReceiverData udpReceiver);


    /**
     *
     * 对中继盒子进行注册 准备开始添加设备
     */
    void uiRegisterRelayBoxCallback(UDPReceiverData udpReceiver);


    /**
     *
     * 1.5	通知中继盒子进入RF设备注册状态
     */
    void uiNoteRelayBoxStartRegisterRFDeviceCallback(UDPReceiverData udpReceiver);

    /**
     * 1.6 通知中继盒子退出RF设备注册状态
     */
    void uiNoteRelayBoxExitRegisterRFDeviceCallback(UDPReceiverData udpReceiver);



    /**
     * 1.17 终端发送删除指定设备消息到中继盒子
     */
    void uiDeleteDevicesFromRelayBoxCallback(UDPReceiverData udpReceiver);


    /**
     * 1.17 匹配中继盒子返回信息
     */
    void uiStartMatchRelayBoxCallback(UDPReceiverData udpReceiver);


    /**
     * 1.17 终止匹配中继盒子返回信息
     */
    void uiStopMatchRelayBoxCallback(UDPReceiverData udpReceiver);


    /**
     * 中继转发红外学习udpReceiver到app终端
     * @param udpReceiver
     */
    void uiLearnIrDataSaveListen(UDPReceiverData udpReceiver);


    /**
     * 1.9	通知中继盒子进入IR红外设备注册状态
     */
    void uiNoteRelayBoxIRDeviceRegisterCallback(UDPReceiverData udpReceiver);


    /**
     * 1.10 通知中继盒子退出IR红外设备注册状态
     * @return
     */
    void uiNoteRelayBoxExitIRDeviceRegisterCallback(UDPReceiverData udpReceiver);


    /**
     * 1.8	中继盒子上传 RF设备注册信息到终端  (后来加上的 2017-09-27)
     */
    void uiNoteRelayBoxAddDeviceCallback(UDPReceiverData udpReceiver);


    /**
     * 1.9 中继盒子发送一个安防设备的安防码值到app
     */
    void uiGetSecurityCodeCallback(UDPReceiverData udpReceiver);


    /**
     *  收到安防设备的ID号
     */
    void uiReceiverSecurityDeviceIdCallback(UDPReceiverData udpReceiver);


    /**
     * 添加设备到中继盒子
     * @param udpReceiver
     */
     void uiReceiverAddDevicesCallback(UDPReceiverData udpReceiver);


    /**
     * 1.13	设备控制  状态信息回复
     */
    void uiControlDeviceByRelayBoxCallback(UDPReceiverData udpReceiver);


    /**
     * 设备状态信息上报
     * @param udpReceiver
     */
    void uiUpdateOneDevicesStateFromRelayBoxCallback(UDPReceiverData udpReceiver);


    //-------------------------------------------------------------------------------------

    /**
     *
     * APP读取中继盒子的网络配置信息
     */
    void uiReadRelayBoxNetConfigCallback(UDPReceiverData udpReceiver);

    /**
     *
     * 1.4	终端写入中继盒子的网络配置信息
     */
    void uiWriteRelayBoxNetConfigCallback(UDPReceiverData udpReceiver);



    /**
     * 1.7	中继盒子上传 RF设备注册信息到云端
     */
    /*public static ComJsonUDPBody  UDPnoteRelayBoxExitRegisterRFDevice(){
        ComJsonUDPBody comJsonUDPBody = new ComJsonUDPBody();
        comJsonUDPBody.setINSTP(MsgINSIP.EXIT_REGISTER_DEVICE_TO_REPETERBOX);
        return  comJsonUDPBody;
    }*/





    /**
     * 1.11	中继盒子上传红外设备学习数据到终端
     */



    /**
     * 1.12终端发送一组学习的有效红外数据给中继盒子（中继盒子上传到云端）
     */
    void uiSendIRStudyDataToRelayBoxCallback(UDPReceiverData udpReceiver);


    /**
     * 1.15	终端获取所有设备列表和设备状态
     */
    void uiUpdateAllDevicesStateFromRelayBoxCallback(UDPReceiverData udpReceiver);

    /**
     * 1.16	终端发送场景联动数据到中继盒子
     */
    void uiSendSceneDataToRelayBoxCallback(UDPReceiverData udpReceiver);



}
