package kap.com.smarthome.android.presenter.control;


import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.Devices;

/**
 * Created by yushq on 2017/9/27 0027.
 * 因为搜到的设备是一个八位的ID号，通过ID号中的设备类型标志位解析出不同的设备类型，
 * 根据定义的设备表 存入对应的数据值类型到数据库
 */

public class DeviceHandleUtils {
    /**
     * RF设备
     可调灯
     不可调灯
     无线插座
     无线窗帘
     场景开关
     */
    //可调灯   数据库type = 1
    public static final String  ADJUST_LAMP = "01";
    public static final int  ADJUST_LAMP_SQL = 1;

    //不可调灯  数据库type = 2
    public static final String  UNADJUST_LAMP = "02";
    public static final int  UNADJUST_LAMP_SQL = 2;

    //无线插座 数据库type = 3
    public static final String  WIRELESS_SOCKET = "03";
    public static final int  WIRELESS_SOCKET_SQL = 3;

    //安防设备  数据库type = 10
    public static final String  DEFENCE =  "10";
    public static final int  DEFENCE_SQL = 16;

    //无线窗帘  数据库type = 12
    public static final String  WIRELESS_CURTAIN =  "0B";
    public static final int  WIRELESS_CURTAIN_SQL = 12;

    //探头性质  数据库type = 208
    public static final String  DEFENCE_PROBE =  "D0";
    public static final int  DEFENCE_PROBE_SQL = 208;

    //探头类型  数据库type = 209
    public static final String  DEFENCE_TYPE =  "D1";
    public static final int  DEFENCE_TYPE_SQL = 209;

    //摄像头  数据库type = 240
    public static final String  CAMERA =  "F0";
    public static final int  CAMERA_SQL = 240;

    //灯光联动控制器  数据库type = 82
    public static final String  LAMP_CONTROL =  "82";
    public static final int  LAMP_CONTROL_SQL = 130;

    //场景开关  数据库type = 73
    public static final String  SCENE_SWITCH =  "73";
    public static final int  SCENE_SWITCH_SQL = 115;

    /**
     * 红外传感器
     *
     *
     电视       0X04
     DVD/VCD	 0X05
     音响		 0X07
     空调		 0X08
     投影仪     0X87
     其他遥控器  0X80
     *
     * 空调 = 8 投影 = 135 功放 = 07 电视 = 4  播放器 = 5 其他 = 128
     *
     */
    //电视  数据库type = 04
    public static final String  IR_TV =  "04";
    public static final int  IR_TV_SQL= 4;

    //DVD/VCD  数据库type = 05
    public static final String  IR_DVD =  "05";
    public static final int  IR_DVD_SQL = 5;

    //音响 数据库type = 07
    public static final String  IR_SOUND =  "07";
    public static final int  IR_SOUND_SQL = 7;

    //空调 数据库type = 08
    public static final String  IR_AIR_CONDITION =  "08";
    public static final int  IR_AIR_CONDITION_SQL = 8;


    //投影仪
    public static final String  IR_PROJECTOR =  "87";
    public static final int  IR_PROJECTOR_SQL = 135;


    //其他遥控器
    public static final String  IR_OTHER =  "80";
    public static final int  IR_OTHER_SQL = 128;

    //红外背景音乐  数据库type = 225
    public static final String  BACKGROUND_MUSIC =  "E1";
    public static final int  BACKGROUND_MUSIC_SQL = 225;


    /**
     *
     * 安防设备
     *
     光电烟雾探测器             011
     漏水探测器                012
     帘幕探测器                013
     燃气探测器                014
     燃气泄漏报警器             015
     烟雾探测器                016
     紧急按钮                 017
     */

    //光电烟雾探测器  数据库type = 17 Photoelectric smoke detector
    public static final String  SECURITY_PHOTOELECTRIC_SMOKE_DETECTOR =  "011";
    public static final int  SECURITY_PHOTOELECTRIC_SMOKE_DETECTOR_SQL= 17;

    //漏水探测器  数据库type = 12 Leak detector
    public static final String  SECURITY_LEAK_WATER_DETECTOR =  "012";
    public static final int  SECURITY_LEAK_WATER_DETECTOR_SQL = 18;

    //帘幕探测器 数据库type = 19 Curtain detector
    public static final String  SECURITY_CURTAIN_DETECTOR =  "014";
    public static final int  SECURITY_CURTAIN_DETECTOR_SQL = 20;

    //燃气探测器 数据库type = 20 Gas detector
    public static final String  SECURITY_GAS_DETECTOR =  "015";
    public static final int  SECURITY_GAS_DETECTOR_SQL = 21;

    //燃气泄漏报警器 数据库type = 21 Gas leakage alarm
    public static final String  SECURITY_GAS_LEAK_ALARM =  "016";
    public static final int  SECURITY_GAS_LEAK_ALARM_SQL = 22;


    //烟雾探测器  Smoke detector
    public static final String  SECURITY_SMOKE_DETECTOR =  "017";
    public static final int  SECURITY_SMOKE_DETECTOR_SQL = 23;

    //紧急按钮 Emergency button
    public static final String  SECURITY_EMERGENCY_BUTTON =  "018";
    public static final int  SECURITY_EMERGENCY_BUTTON_SQL = 24;


    /**
     * 其他设备
     */

    //闹钟 scenes timer
    public static final String  SCENES_TIMER =  "04";
    public static final int  SCENES_TIMER_SQL = 100;



    /**
     * 设备的Vaule 表示设备现在的状态同时也是发送的设备值
     * @param device
     * @return
     */
    public static final String  DEVICE_VALUE_OPEN = "00ff";
    public static final String  DEVICE_VALUE_CLOSE = "0000";
    public static final String  DEVICE_VALUE_STOP = "0080";


    /**
     *
     * 设备在不同的界面以不同的图标形式呈现， 为了统一入口，定义showLocaL的值，更据该值进行判断设备呈现方式
     *
     * @param device   根据设备判断类型
     * @param showLocal
     *
     * showLocal = 0  是设备在DevicesListRecyclerViewAdapter 界面中的呈现方式 ，是设备依据房间进行设备呈现 ，需要判断设备的开、关、停止等状态， 呈现不同的图标
     *
     * showLocal = 1  是在添加场景的界面侧滑栏 中，设备的图标为纯白色 ，背景为纯黑 ，只有一种图标类型
     *
     * if(showLocal == DEVICE_LOCAL_0) {

      }else if (showLocal == DEVICE_LOCAL_1){

      }
     *
     *
     * @return
     */

    public static final int   DEVICE_LOCAL_0 = 0;

    public static final int   DEVICE_LOCAL_1 = 1;



    public static int getDeviceIconRes(Devices device , int showLocal) {

        int resId = 0;
        int openResId = 0, stopResId = 0, closeResId = 0;

        switch (device.getTYPE()) {
            case ADJUST_LAMP_SQL: //可调灯
                if(showLocal == DEVICE_LOCAL_0) {

                    closeResId = stopResId = R.drawable.device_adjust_lamp;
                    openResId = R.drawable.device_adjust_lamp_red;

                }else if(showLocal == DEVICE_LOCAL_1){

                    resId = R.drawable.scenes_silding_add_adjust_lamp;

                }
                break;
            case UNADJUST_LAMP_SQL: //不可调灯
                if(showLocal == DEVICE_LOCAL_0) {

                    closeResId = stopResId = R.drawable.device_un_adjust_lamp;
                    openResId = R.drawable.device_un_adjust_lamp;

                }else if(showLocal == DEVICE_LOCAL_1){

                    resId = R.drawable.scenes_sliding_add_unadjust_lamp;

                }
                break;
            case WIRELESS_SOCKET_SQL: //无线插座

                if(showLocal == DEVICE_LOCAL_0) {

                    closeResId = stopResId = R.drawable.devices_socket_default;
                    openResId = R.drawable.devices_socket_selected;

                }else if(showLocal == DEVICE_LOCAL_1){
                    resId = R.drawable.scenes_silding_add_socket;
                }
                break;
            case DEFENCE_SQL: //安防设备

                break;
            case WIRELESS_CURTAIN_SQL: //无线窗帘
                if(showLocal == DEVICE_LOCAL_0) {
                    openResId = R.drawable.device_curtain_open;
                    closeResId = R.drawable.device_curtain_close;
                    stopResId = R.drawable.device_curtain_stop;

                }else if (showLocal == DEVICE_LOCAL_1){

                    resId = R.drawable.scenes_silding_add_curtain;

                }
                break;
            case DEFENCE_PROBE_SQL: //探头性质

                break;

            case DEFENCE_TYPE_SQL: //探头类型

                break;

            case CAMERA_SQL: //摄像头

                break;

            case SCENE_SWITCH_SQL: //灯光联动控制器
                if(showLocal == DEVICE_LOCAL_0) {

                    openResId = R.drawable.device_scenes_swith;
                    closeResId = R.drawable.device_scenes_swith;
                    stopResId = R.drawable.device_scenes_swith;

                }else if (showLocal == DEVICE_LOCAL_1) {

                    resId = R.drawable.scenes_silding_add_scenes_switch;

                }

                break;

            case IR_TV_SQL: //电视

                if(showLocal == DEVICE_LOCAL_0) {

                    openResId = R.drawable.device_tv;
                    closeResId = R.drawable.device_tv;
                    stopResId = R.drawable.device_tv;

                }else if (showLocal == DEVICE_LOCAL_1) {

                   resId = R.drawable.scenes_silding_add_tv;

                }

                break;
            case IR_DVD_SQL: //DVD/VCD

                break;


            case IR_SOUND_SQL: //音响

                break;


            case IR_AIR_CONDITION_SQL: //空调

                if(showLocal == DEVICE_LOCAL_0) {

                    openResId = R.drawable.device_air_condition;
                    closeResId = R.drawable.device_air_condition;
                    stopResId = R.drawable.device_air_condition;

                }else if (showLocal == DEVICE_LOCAL_1) {

                    resId = R.drawable.scenes_silding_add_air_condition;

                }

                break;
            case BACKGROUND_MUSIC_SQL: //红外背景音乐


                break;
            case  SECURITY_PHOTOELECTRIC_SMOKE_DETECTOR_SQL: //光电烟雾探测器

                if(showLocal == DEVICE_LOCAL_0) {

                    openResId = closeResId = stopResId = R.drawable.device_photoelectric_smoke_detector;


                }else if (showLocal == DEVICE_LOCAL_1) {

                    resId = R.drawable.scenes_silding_photoelectric_smoke_detector;

                }

                break;

            case SECURITY_LEAK_WATER_DETECTOR_SQL: //漏水探测器

                if(showLocal == DEVICE_LOCAL_0) {

                    openResId = closeResId = stopResId = R.drawable.device_leak_detector;


                }else if (showLocal == DEVICE_LOCAL_1) {

                    resId = R.drawable.scenes_silding_leak_detector;

                }

                break;

            case SECURITY_CURTAIN_DETECTOR_SQL: //帘幕探测器

                if(showLocal == DEVICE_LOCAL_0) {

                    openResId = closeResId = stopResId = R.drawable.device_curtain_detector;


                }else if (showLocal == DEVICE_LOCAL_1) {

                    resId = R.drawable.scenes_silding_curtain_detector;

                }

                break;


            case DeviceHandleUtils.SECURITY_GAS_DETECTOR_SQL: //燃气探测器

                if(showLocal == DEVICE_LOCAL_0) {

                    openResId = closeResId = stopResId = R.drawable.device_gas_detector;


                }else if (showLocal == DEVICE_LOCAL_1) {

                    resId = R.drawable.scenes_silding_gas_detector;

                }


                break;

            case SECURITY_SMOKE_DETECTOR_SQL: //烟雾探测器

                if(showLocal == DEVICE_LOCAL_0) {

                    openResId = closeResId = stopResId = R.drawable.device_smoke_detector;


                }else if (showLocal == DEVICE_LOCAL_1) {

                    resId = R.drawable.scenes_silding_smoke_detector;

                }

                break;

            case SECURITY_GAS_LEAK_ALARM_SQL: //燃气泄漏报警器

                if(showLocal == DEVICE_LOCAL_0) {

                    openResId = closeResId = stopResId = R.drawable.device_gas_leakage_alarm;


                }else if (showLocal == DEVICE_LOCAL_1) {

                    resId = R.drawable.scenes_silding_gas_leakage_alarm;

                }

                break;

            case SECURITY_EMERGENCY_BUTTON_SQL: //紧急按钮

                if(showLocal == DEVICE_LOCAL_0) {

                    openResId = closeResId = stopResId = R.drawable.device_emergency_button;

                }else if (showLocal == DEVICE_LOCAL_1) {

                    resId = R.drawable.scenes_silding_emergency_button;

                }

                break;

            case SCENES_TIMER_SQL:

                if(showLocal == DEVICE_LOCAL_1){
                    resId = R.drawable.scenes_silding_add_timer;
                }

                break;

        }

        if(showLocal == DEVICE_LOCAL_0) {
            String state = device.getVALUE();
            if (state.equals(DEVICE_VALUE_CLOSE)) {//关闭
                resId = closeResId;
            } else if (state.equals(DEVICE_VALUE_OPEN)) {//打开
                resId = openResId;
            } else  {//其余状态值
                resId = stopResId;
            }
        }

        return resId;
    }

}
