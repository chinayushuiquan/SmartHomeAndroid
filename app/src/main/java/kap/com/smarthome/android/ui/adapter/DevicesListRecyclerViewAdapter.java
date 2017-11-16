package kap.com.smarthome.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.presenter.control.DeviceHandleUtils;

/**
 * Created by Administrator on 2017/8/25 0025.
 */
public class DevicesListRecyclerViewAdapter extends RecyclerView.Adapter<DevicesListRecyclerViewAdapter.MyViewHolder>{

    private Context context;
    private List<Devices> mDevicesList;
    private boolean isEditState = false;
    private OnRecyclerItemClickListener mOnItemClickListener;//单击事件


    public DevicesListRecyclerViewAdapter(Context context , List<Devices> list) {
        this.context = context;
        this.mDevicesList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //找到item的布局
        View view= LayoutInflater.from(context).inflate(R.layout.devices_and_room_recycler_item, parent,false);
        return new MyViewHolder(view);//将布局设置给holder
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if(isEditState){
            holder.mDeleteIv.setVisibility(View.VISIBLE);
        }else{
            holder.mDeleteIv.setVisibility(View.GONE);
        }

        //设置单击事件
        if(mOnItemClickListener != null){
            holder.mDeleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //回调传入的mOnItemClickListener 删除按钮的点击事件
                    mOnItemClickListener.onDeleteIconClick(v , holder.getLayoutPosition());
                }
            });

            holder.mDevicesIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //回调传入的mOnItemClickListener 回传设备的点击事件
                    if(isEditState){
                        mOnItemClickListener.onItemEditClick(v , holder.getLayoutPosition());
                    }else{
                        mOnItemClickListener.onItemControlClick(v , holder.getLayoutPosition());
                    }

                }
            });
        }


        //根据设备类型设置设备的图标和名称
        Devices device = mDevicesList.get(position);

        //设备图标的类型设备方法  此处传入0
        int resType = DeviceHandleUtils.getDeviceIconRes(device , DeviceHandleUtils.DEVICE_LOCAL_0);

        holder.mDevicesIv.setBackgroundResource(resType);

        holder.mDeviceName.setText(device.getNAME());
    }


    @Override
    public int getItemCount() {
        return mDevicesList.size();
    }


      class MyViewHolder extends RecyclerView.ViewHolder{
            ImageView mDevicesIv;
            ImageView mDeleteIv;
            TextView  mDeviceName;

        public MyViewHolder(View itemView) {
            super(itemView);
            mDevicesIv = (ImageView) itemView.findViewById(R.id.recycler_view_large_item_iv);
            mDeleteIv = (ImageView) itemView.findViewById(R.id.large_delete_iv);
            mDeviceName = (TextView) itemView.findViewById(R.id.recycler_view_large_item_tv);
        }
    }


    public boolean getEditState() {
        return isEditState;
    }


    public void setEditState(boolean editState) {
        isEditState = editState;
        notifyDataSetChanged();
    }

    /**
     * 处理item的点击事件,因为recycler没有提供单击事件,所以只能自己写了
     */
    public interface OnRecyclerItemClickListener {
        void onItemControlClick(View view, int position);
        void onItemEditClick(View view, int position);
        void onDeleteIconClick(View view , int position);
    }

    /**
     * 暴露给外面的设置单击事件
     */
    public void setOnItemClickListener(OnRecyclerItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }




}
