package kap.com.smarthome.android.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.ScenesDevice;
import kap.com.smarthome.android.presenter.control.DeviceHandleUtils;


/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class ScenesControlDevicesGvAdapter extends BaseAdapter{


    private Context mContext;

    private List<ScenesDevice> scenesDeviceList;


    public ScenesControlDevicesGvAdapter(Context context , List<ScenesDevice> scenesDevices){
        mContext = context;
        scenesDeviceList = scenesDevices;
    }


    @Override
    public int getCount() {
        return scenesDeviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.senes_control_device_gv, null);
            viewHolder.devicesView = (ImageView) convertView.findViewById(R.id.scenes_control_device_item_iv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据设备类型设置设备的图标和名称
        ScenesDevice device = scenesDeviceList.get(position);

        //设备图标的类型设备方法  此处传入0
        int resType = getDeviceIconRes(device);
        viewHolder.devicesView.setImageResource(resType);


        return convertView;
    }

    class ViewHolder{
        ImageView  devicesView;
    }

    public static int getDeviceIconRes(ScenesDevice device ) {

        int resId = 0;

        switch (device.getTYPE()) {
            case DeviceHandleUtils.ADJUST_LAMP_SQL://可调灯

                resId =  R.drawable.new_device_adjust_lamp;
                Log.e("DATA", "getDeviceIconRes: 场景显示可调灯 ");

                break;
            case DeviceHandleUtils.UNADJUST_LAMP_SQL: //不可调灯

                resId =  R.drawable.new_device_unadjust_lamp;

                break;

            case DeviceHandleUtils.WIRELESS_SOCKET_SQL: //无线插座

                break;

            case DeviceHandleUtils.DEFENCE_SQL: //安防设备

                break;
            case DeviceHandleUtils.WIRELESS_CURTAIN_SQL: //无线窗帘

                resId = R.drawable.new_device_curtain;

                break;
            case DeviceHandleUtils.DEFENCE_PROBE_SQL: //探头性质

                break;

            case DeviceHandleUtils.DEFENCE_TYPE_SQL: //探头类型

                break;

            case DeviceHandleUtils.CAMERA_SQL: //摄像头

                break;

            case DeviceHandleUtils.SCENE_SWITCH_SQL: //灯光联动控制器

                break;

            case DeviceHandleUtils.IR_TV_SQL: //电视

                resId =  R.drawable.new_device_tv;

                break;
            case DeviceHandleUtils.IR_DVD_SQL: //DVD/VCD



                break;
            case DeviceHandleUtils.IR_SOUND_SQL: //音响

                break;


            case DeviceHandleUtils.IR_AIR_CONDITION_SQL: //空调

                break;


            case DeviceHandleUtils.BACKGROUND_MUSIC_SQL: //红外背景音乐

                break;

            case  DeviceHandleUtils.SECURITY_PHOTOELECTRIC_SMOKE_DETECTOR_SQL: //光电烟雾探测器

                resId =  R.drawable.device_photoelectric_smoke_detector;

                break;

            case DeviceHandleUtils.SECURITY_LEAK_WATER_DETECTOR_SQL: //漏水探测器

                resId =  R.drawable.device_leak_detector;

                break;

            case DeviceHandleUtils.SECURITY_CURTAIN_DETECTOR_SQL: //帘幕探测器

                resId =  R.drawable.device_curtain_detector;

                break;


            case DeviceHandleUtils.SECURITY_GAS_DETECTOR_SQL: //燃气探测器


               resId = R.drawable.device_gas_detector;

                break;

            case DeviceHandleUtils.SECURITY_SMOKE_DETECTOR_SQL: //烟雾探测器

                resId =  R.drawable.device_smoke_detector;

                break;

            case DeviceHandleUtils.SECURITY_GAS_LEAK_ALARM_SQL: //燃气泄漏报警器

                resId =  R.drawable.device_gas_leakage_alarm;

                break;

            case DeviceHandleUtils.SECURITY_EMERGENCY_BUTTON_SQL: //紧急按钮

                resId =  R.drawable.device_emergency_button;

                break;

        }
        return resId;
    }


}
