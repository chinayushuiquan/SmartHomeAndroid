package kap.com.smarthome.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.presenter.control.DeviceHandleUtils;

/**
 * Created by Administrator on 2017/9/4 0004.
 */

public class ScenesChoseDevicesAndConditionAdapter extends BaseAdapter{

    private Context mContext;
    private List<Devices>  mDevicesDataList;

    //选中要添加的设备列表
    private List<Devices>  selectDevicesList = null;

    public ScenesChoseDevicesAndConditionAdapter(Context context , List<Devices> dataList){
          mContext = context;
          mDevicesDataList = dataList;
          selectDevicesList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDevicesDataList.size();

    }

    @Override
    public Object getItem(int position) {

        return mDevicesDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder ;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.scenes_sliding_add_list_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mAddDevicesIv = (ImageView) convertView.findViewById(R.id.scenes_sliding_add_item_iv);
            mViewHolder.mSelectCheckIv = (ImageView) convertView.findViewById(R.id.scenes_sliding_add_item_selected_iv);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.mAddDevicesIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewHolder.mSelectCheckIv.getVisibility() == View.GONE){
                    mViewHolder.mSelectCheckIv.setVisibility(View.VISIBLE);
                    selectDevicesList.add(mDevicesDataList.get(position));
                }else {
                    mViewHolder.mSelectCheckIv.setVisibility(View.GONE);
                    selectDevicesList.remove(mDevicesDataList.get(position));
                }
            }
        });


        Devices device = mDevicesDataList.get(position);

        //设备图标的类型设备方法  此处传入1
        int deviceIconType = DeviceHandleUtils.getDeviceIconRes(device , DeviceHandleUtils.DEVICE_LOCAL_1);

        mViewHolder.mAddDevicesIv.setBackgroundResource(deviceIconType);

        return convertView;
    }


    static class ViewHolder {
        ImageView mSelectCheckIv;
        ImageView mAddDevicesIv;
    }


    /**
     * 获取需要添加到该场景的控制设备列表
     * @return
     */
    public List<Devices> getSelectDevicesList() {
        return selectDevicesList;
    }

    public void setSelectDevicesList(List<Devices> selectDevicesList) {
        this.selectDevicesList = selectDevicesList;
    }
}
