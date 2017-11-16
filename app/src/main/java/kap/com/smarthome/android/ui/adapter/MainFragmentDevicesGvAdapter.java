package kap.com.smarthome.android.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.ScenesDevice;
import kap.com.smarthome.android.presenter.control.DeviceHandleUtils;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class MainFragmentDevicesGvAdapter extends BaseAdapter{
    private Context mContext;

    /*private int[]  devicesDrawable ={R.drawable.home_devices_lamp_on , R.drawable.home_devices_air_condition,
            R.drawable.home_devices_curtain, R.drawable.home_devices_tv};*/

    private List<Devices> mDeviceList = null;

    public MainFragmentDevicesGvAdapter(Context context , List<Devices> devicesList){
        mDeviceList = devicesList;
        mContext = context;
    }


    @Override
    public int getCount() {
        return mDeviceList.size();
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
            convertView = layoutInflater.inflate(R.layout.commuse_devices_gv_item, null);

            viewHolder.largeImageIconRl = (RelativeLayout) convertView.findViewById(R.id.home_device_icon_bg);
            viewHolder.smallImageIconIv = (ImageView) convertView.findViewById(R.id.home_device_icon_iv);
            viewHolder.deviceName = (TextView) convertView.findViewById(R.id.home_device_name_tv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        setDeviceIcon(viewHolder, mDeviceList.get(position));

        return convertView;
    }

    class ViewHolder{
        RelativeLayout largeImageIconRl;
        ImageView  smallImageIconIv;
        TextView   deviceName;

    }


    public void  setDeviceIcon(ViewHolder viewHolder , Devices device) {
        switch (device.getTYPE()) {
            case DeviceHandleUtils.ADJUST_LAMP_SQL://可调灯


                viewHolder.largeImageIconRl.setBackgroundResource(R.drawable.home_adjust_lamp_bg);
                viewHolder.smallImageIconIv.setBackgroundResource(R.drawable.adjust_lamp_86);

                break;
            case DeviceHandleUtils.UNADJUST_LAMP_SQL: //不可调灯

               // resId =  R.drawable.new_device_unadjust_lamp;
                viewHolder.largeImageIconRl.setBackgroundResource(R.drawable.home_un_adjust_lamp_bg);
                viewHolder.smallImageIconIv.setBackgroundResource(R.drawable.un_adjust_lamp_86);

                break;

            case DeviceHandleUtils.WIRELESS_SOCKET_SQL: //无线插座
                viewHolder.largeImageIconRl.setBackgroundResource(R.drawable.home_wire_less_btn_bg);
                //viewHolder.smallImageIconIv.setBackgroundResource(R.drawable.);
                break;
            case DeviceHandleUtils.DEFENCE_SQL: //安防设备


                break;
            case DeviceHandleUtils.WIRELESS_CURTAIN_SQL: //无线窗帘

                //resId = R.drawable.new_device_curtain;

                viewHolder.largeImageIconRl.setBackgroundResource(R.drawable.home_curtain_bg);
                viewHolder.smallImageIconIv.setBackgroundResource(R.drawable.new_device_curtain);

                break;
            case DeviceHandleUtils.DEFENCE_PROBE_SQL: //探头性质

                break;

            case DeviceHandleUtils.DEFENCE_TYPE_SQL: //探头类型

                break;

            case DeviceHandleUtils.CAMERA_SQL: //摄像头

                break;

            case DeviceHandleUtils.SCENE_SWITCH_SQL: //灯光联动控制器

                viewHolder.largeImageIconRl.setBackgroundResource(R.drawable.home_scenes_swith_bg);
                viewHolder.smallImageIconIv.setBackgroundResource(R.drawable.scenes_switch_86);


                break;
            case DeviceHandleUtils.IR_TV_SQL: //电视

                viewHolder.largeImageIconRl.setBackgroundResource(R.drawable.home_tv_bg);
                viewHolder.smallImageIconIv.setBackgroundResource(R.drawable.new_device_tv);

                break;
            case DeviceHandleUtils.IR_DVD_SQL: //DVD/VCD



                break;
            case DeviceHandleUtils.IR_SOUND_SQL: //音响

                break;


            case DeviceHandleUtils.IR_AIR_CONDITION_SQL: //空调
                viewHolder.largeImageIconRl.setBackgroundResource(R.drawable.home_air_condition_bg);
                viewHolder.smallImageIconIv.setBackgroundResource(R.drawable.air_condition_86);

                break;
            case DeviceHandleUtils.BACKGROUND_MUSIC_SQL: //红外背景音乐

                break;
        }

        viewHolder.deviceName.setText(device.getNAME());
    }


}
