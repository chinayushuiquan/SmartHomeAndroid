package kap.com.smarthome.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.Room;
import kap.com.smarthome.android.presenter.control.DeviceHandleUtils;

/**
 * Created by yushq on 2017/8/31 0031.
 *
 */

public class ScanDevicesListRecyclerAdapter extends RecyclerView.Adapter<ScanDevicesListRecyclerAdapter.MyViewHold> {

    private Context mContext;

    private OnAddDevicesRecyclerItemClickListener  mOnItemClickListener;

    //标志扫描到的该设备是否被中中，选中为true
    private List<Boolean> mIsSelects;

    //扫描到的所有的新设备
    private List<Devices> mNewDevicesList;

    //选中要添加的所有的新设备
    private List<Devices> mNewSelectDevicesList;

    //房间集合
    private List<Room> mChoseRooms;

    public ScanDevicesListRecyclerAdapter(Context context , List<Devices> newDevices ,List<Boolean> isSelects , List<Room> rooms) {
        mContext = context;
        mNewDevicesList = newDevices;
        mIsSelects = isSelects;
        mNewSelectDevicesList = new ArrayList<>();
        mChoseRooms = rooms;
    }

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =   LayoutInflater.from(mContext).inflate(R.layout.devices_search_recycle_item, parent,false);
        return new MyViewHold(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHold holder, final int position) {
            // 添加按钮是否选中
            holder.mAddIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mIsSelects.get(position)){
                        mIsSelects.set(position,false);
                        holder.mAddIcon.setBackgroundResource(R.drawable.add);
                        mNewSelectDevicesList.remove(mNewDevicesList.get(position));
                    }else {
                        mIsSelects.set(position, true);
                        holder.mAddIcon.setBackgroundResource(R.drawable.devices_add_success_icon);
                        mNewSelectDevicesList.add(mNewDevicesList.get(position));
                    }
                }
            });


        if(mOnItemClickListener != null){
            holder.mNoteSelectRoomTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onNoteSelectRoomTvClick(v, holder.getLayoutPosition());
                }
            });
        }

        /**
         * 设置选择的name
         */
        String roomGuid = mNewDevicesList.get(position).getROOM_GUID();
        if(roomGuid != null && !roomGuid.equals("")){
            for (int i = 0; i< mChoseRooms.size() ; i++){
                if(roomGuid.equals(mChoseRooms.get(i).getGUID())) {
                    holder.mNoteSelectRoomTv.setText(mChoseRooms.get(i).getNAME());
                }
            }
        }

        /**
         * 判断设备的类型
         * 选中相应的设备图标 , 类型type设备的名称
         */
        initDeviceType(holder, position);
    }


    /**
     * 适配设备的初始化方法
     * @param holder
     * @param position
     */
    private void initDeviceType(MyViewHold holder, int position) {
        String  type = "";
        String device_Id = mNewDevicesList.get(position).getDEVICE_ID();
        if(device_Id.length() == 8){
             type = mNewDevicesList.get(position).getDEVICE_ID().substring(0,2);
        }else if(device_Id.length() == 16){
             type = mNewDevicesList.get(position).getDEVICE_ID().substring(4,7);
        }

        switch (type){
            case DeviceHandleUtils.WIRELESS_CURTAIN: //窗帘
                holder.mDevicesIconIv.setBackgroundResource(R.drawable.new_device_curtain);
                holder.mDevicesNameTv.setText(R.string.curtain);
                mNewDevicesList.get(position).setTYPE(DeviceHandleUtils.WIRELESS_CURTAIN_SQL);
                break;
            case DeviceHandleUtils.UNADJUST_LAMP://不可调灯
                holder.mDevicesIconIv.setBackgroundResource(R.drawable.new_device_unadjust_lamp);
                holder.mDevicesNameTv.setText(R.string.un_adjust_lamp);
                mNewDevicesList.get(position).setTYPE(DeviceHandleUtils.UNADJUST_LAMP_SQL);
                break;
            case DeviceHandleUtils.ADJUST_LAMP: //可调灯
                holder.mDevicesIconIv.setBackgroundResource(R.drawable.new_device_adjust_lamp);
                holder.mDevicesNameTv.setText(R.string.adjust_lamp);
                mNewDevicesList.get(position).setTYPE(DeviceHandleUtils.ADJUST_LAMP_SQL);
                break;
            case  DeviceHandleUtils.WIRELESS_SOCKET: //无线插座
                holder.mDevicesIconIv.setBackgroundResource(R.drawable.wireless_socket_86);
                holder.mDevicesNameTv.setText(R.string.wireless_socket);
                mNewDevicesList.get(position).setTYPE(DeviceHandleUtils.WIRELESS_SOCKET_SQL);
                break;
            case  DeviceHandleUtils.SCENE_SWITCH: //场景开关
                holder.mDevicesIconIv.setBackgroundResource(R.drawable.scenes_switch_86);
                holder.mDevicesNameTv.setText(R.string.scenes_switch);
                mNewDevicesList.get(position).setTYPE(DeviceHandleUtils.SCENE_SWITCH_SQL);
                break;
            case DeviceHandleUtils.SECURITY_PHOTOELECTRIC_SMOKE_DETECTOR: //光电烟雾探测器
                holder.mDevicesIconIv.setBackgroundResource(R.drawable.scenes_silding_photoelectric_smoke_detector_1);
                holder.mDevicesNameTv.setText(R.string.photoelectric_smoke_detector);
                mNewDevicesList.get(position).setTYPE(DeviceHandleUtils.SECURITY_PHOTOELECTRIC_SMOKE_DETECTOR_SQL);
                break;

            case DeviceHandleUtils.SECURITY_LEAK_WATER_DETECTOR: //漏水探测器
                holder.mDevicesIconIv.setBackgroundResource(R.drawable.scenes_silding_leak_detector_1);
                holder.mDevicesNameTv.setText(R.string.leak_detector);
                mNewDevicesList.get(position).setTYPE(DeviceHandleUtils.SECURITY_LEAK_WATER_DETECTOR_SQL);
                break;

            case DeviceHandleUtils.SECURITY_CURTAIN_DETECTOR: //帘幕探测器
                holder.mDevicesIconIv.setBackgroundResource(R.drawable.scenes_silding_curtain_detector_1);
                holder.mDevicesNameTv.setText(R.string.curtain_detector);
                mNewDevicesList.get(position).setTYPE(DeviceHandleUtils.SECURITY_CURTAIN_DETECTOR_SQL);
                break;


            case DeviceHandleUtils.SECURITY_GAS_DETECTOR: //燃气探测器
                holder.mDevicesIconIv.setBackgroundResource(R.drawable.scenes_silding_gas_detector_1);
                holder.mDevicesNameTv.setText(R.string.gas_detector);
                mNewDevicesList.get(position).setTYPE(DeviceHandleUtils.SECURITY_GAS_DETECTOR_SQL);
                break;

            case DeviceHandleUtils.SECURITY_SMOKE_DETECTOR: //烟雾探测器
                holder.mDevicesIconIv.setBackgroundResource(R.drawable.scenes_silding_smoke_detector_1);
                holder.mDevicesNameTv.setText(R.string.smoke_detector);
                mNewDevicesList.get(position).setTYPE(DeviceHandleUtils.SECURITY_SMOKE_DETECTOR_SQL);
                break;

            case DeviceHandleUtils.SECURITY_GAS_LEAK_ALARM: //燃气泄漏报警器
                holder.mDevicesIconIv.setBackgroundResource(R.drawable.scenes_silding_gas_leakage_alarm_1);
                holder.mDevicesNameTv.setText(R.string.gas_leakage_alarm);
                mNewDevicesList.get(position).setTYPE(DeviceHandleUtils.SECURITY_GAS_LEAK_ALARM_SQL);
                break;

            case DeviceHandleUtils.SECURITY_EMERGENCY_BUTTON: //紧急按钮
                holder.mDevicesIconIv.setBackgroundResource(R.drawable.scenes_silding_emergency_button_1);
                holder.mDevicesNameTv.setText(R.string.emergency_button);
                mNewDevicesList.get(position).setTYPE(DeviceHandleUtils.SECURITY_EMERGENCY_BUTTON_SQL);
                break;

        }
        //添加设备的名称
        mNewDevicesList.get(position).setNAME(holder.mDevicesNameTv.getText().toString().trim());
        //添加设备的默认状态
        mNewDevicesList.get(position).setVALUE(DeviceHandleUtils.DEVICE_VALUE_CLOSE);

    }


    @Override
    public int getItemCount() {
        return mNewDevicesList.size() ;
    }


    class  MyViewHold extends RecyclerView.ViewHolder{
        private ImageView mDevicesIconIv;
        private TextView  mDevicesNameTv;
        private TextView  mNoteSelectRoomTv;
        private ImageView mAddIcon;

        public MyViewHold(View itemView) {
            super(itemView);
            mDevicesIconIv = (ImageView) itemView.findViewById(R.id.devices_search_icon_iv);
            mDevicesNameTv = (TextView) itemView.findViewById(R.id.devices_search_name_tv);
            mNoteSelectRoomTv = (TextView) itemView.findViewById(R.id.devices_search_chose_room_tv);
            mAddIcon = (ImageView) itemView.findViewById(R.id.devices_item_add_icon_iv);
        }

    }


    /**
     * 处理item的点击事件,因为recycler没有提供单击事件,所以只能自己写了
     */
    public interface OnAddDevicesRecyclerItemClickListener {
        void onNoteSelectRoomTvClick(View view, int position);
    }


    /**
     * 暴露给外面的设置单击事件
     */
    public void setOnItemClickListener(OnAddDevicesRecyclerItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;

    }


    /**
     * AddDevicesActivity调用该方法获得选中的设备
     * @return
     */
    public List<Devices> getmNewSelectDevicesList() {
        return mNewSelectDevicesList;
    }




}
