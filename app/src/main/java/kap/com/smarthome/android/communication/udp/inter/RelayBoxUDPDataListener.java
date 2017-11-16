package kap.com.smarthome.android.communication.udp.inter;


import kap.com.smarthome.android.communication.bean.base.DATABean.UDPDevicesData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;

/**
 * Created by Administrator on 2017/6/1 0001.
 * 具体的数据处理接口
 * BaseActivity 继承这个类，
 * 在每一个界面中根据自己的需求对方法进行回调调用
 * 在UI Threa调用
 */

public interface RelayBoxUDPDataListener {
    
    /**
     * 搜索中继盒子
     * @param udpReceiver
     */
    void udpSearchRelayBoxListen(UDPReceiverData  udpReceiver);


    /**
     * 添加中继盒子
     */
    void udpAddRelayBoxListen(UDPReceiverData udpReceiver);

    /**
     *
     * 对中继盒子进行注册 添加到APP
     */
    void udpRegisterRelayBoxListen(UDPReceiverData  udpReceiver);



    /**
     *
     * 1.5	通知中继盒子进入RF设备注册状态
     */
    void udpNoteRelayBoxStartRegisterRFDeviceListen(UDPReceiverData  udpReceiver);


    /**
     * 匹配中继盒子
     * @param udpReceiver
     */
    void udpStartMatchRelayBoxListen(UDPReceiverData udpReceiver);


    /**
     * 终止匹配中继盒子
     * @param udpReceiver
     */
    void udpStopMatchRelayBoxListen(UDPReceiverData udpReceiver);


    /**
     * 1.8	中继盒子上传 RF设备注册信息到终端   (后来加上的 2017-09-27)
     */

    void  udpNoteRelayBoxAddDeviceListen(UDPReceiverData  udpReceiver);

    /**
     * 1.9	通知中继盒子进入红外设备注册状态
     */
    void  udpNoteRelayBoxIRDeviceRegisterListen(UDPReceiverData  udpReceiver);


    /**
     * 1.10 通知中继盒子退出红外设备注册状态
     * @return
     */
    void  udpNoteRelayBoxExitIRDeviceRegisterListen(UDPReceiverData  udpReceiver);


    /**
     * 1.11	中继盒子上传红外设备学习数据到终端
     */
    void udpLearnIrDataListen(UDPReceiverData udpReceiver);


    /**
     * 1.17 终端发送删除指定设备消息到中继盒子
     */
    void  udpDeleteDevicesFromRelayBoxListen(UDPReceiverData  udpReceiver);


    /**
     * 1.11 收到安防码值
     * @return
     */
    void udpGetSecurityCodeListen(UDPReceiverData udpReceiver);


    /**
     * 1.12 收到安防设备的ID号
     */
    void udpReceiverSecurityDeviceIdListen(UDPReceiverData udpReceiver);


    /**
     * 添加设备到中继盒子的监听
     * @param udpReceiver
     */
    void udpReceiverAddDevicesListen(UDPReceiverData udpReceiver);



    /**
     * 1.13	设备控制
     */
    void udpControlDeviceByRelayBoxListen(UDPReceiverData  udpReceiver);


    /**
     * 收到一个设备的信息上报
     * @param udpDevicesData
     */
    void udpUpdateOneDevicesStateFromRelayBoxListen(UDPReceiverData udpDevicesData);


//----------------------------------------------------------------------------------------------
    /**
     * 1.12	 终端发送一组学习的有效红外数据给中继盒子（中继盒子上传到云端）
     */
    void udpSendIRStudyDataToRelayBoxListen(UDPReceiverData  udpReceiver);



    /**
     * 1.15	终端获取所有设备列表和设备状态
     */
    void udpUpdateAllDevicesStateFromRelayBoxListen(UDPReceiverData  udpReceiver);


    /**
     * 1.16	终端发送场景联动数据到中继盒子
     */
    void udpSendSceneDataToRelayBoxListen(UDPReceiverData  udpReceiver);

    /**
     * 1.6 通知中继盒子退出RF设备注册状态
     */
    void udpNoteRelayBoxExitRegisterRFDeviceListen(UDPReceiverData  udpReceiver);


    /**
     *
     * APP读取中继盒子的网络配置信息
     */
    void  udpReadRelayBoxNetConfigListen(UDPReceiverData  udpReceiver);

    /**
     *
     * 1.4	终端写入中继盒子的网络配置信息
     */
    void udpWriteRelayBoxNetConfigListen(UDPReceiverData  udpReceiver);


}
