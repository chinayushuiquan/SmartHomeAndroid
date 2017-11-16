package kap.com.smarthome.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.DATABean.DeviceData;
import kap.com.smarthome.android.presenter.utils.ResourcesUtils;


/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class AddRemoteControlChoossIconGvAdapter extends BaseAdapter{

    private Context mContext;

    private int[] mRcTypeIcons = {R.drawable.rc_air_condition, R.drawable.rc_bioscope, R.drawable.rc_power_amplifier,
                                   R.drawable.rc_tv,R.drawable.rc_video_play, R.drawable.rc_other};

    private String[] mRcTypeNames = null;

    private int selectPosition = -1;

    public AddRemoteControlChoossIconGvAdapter(Context context) {
        mContext = context;
        mRcTypeNames = ResourcesUtils.getStringArray(mContext, R.array.default_rc_name);
    }

    @Override
    public int getCount() {
        return mRcTypeIcons.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GvViewHold  gvHoldview = null;
        if (convertView == null){
            gvHoldview = new GvViewHold();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.add_room_chose_icon_item,null);
            gvHoldview.mIconVew = (ImageView) convertView.findViewById(R.id.room_add_icon_item_iv);
            gvHoldview.mSelectView = (ImageView) convertView.findViewById(R.id.room_add_select_item_iv);
            gvHoldview.mRoomNames = (TextView) convertView.findViewById(R.id.room_add_icon_item_tv);
            convertView.setTag(gvHoldview);
        }else{
            gvHoldview = (GvViewHold) convertView.getTag();
        }

        gvHoldview.mIconVew.setBackgroundResource(mRcTypeIcons[position]);

        if(selectPosition > -1 && selectPosition == position ){
            gvHoldview.mSelectView.setBackgroundResource(R.drawable.selected_icon);
        }else {
            gvHoldview.mSelectView.setBackgroundResource(R.drawable.unselected_icon);
        }

        gvHoldview.mRoomNames.setText(mRcTypeNames[position]);

        return convertView;
    }

    static  class  GvViewHold {
        ImageView mIconVew;
        ImageView mSelectView;
        TextView  mRoomNames;
    }


    /**
     * 获取选中的遥控器类型
     * @return
     */
    public int getSelectPosition() {
        return selectPosition;
    }

    /**
     * 设置哪个房间的type被选中了
     * @param selectPosition
     */
    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }


    /**
     * 更据选择的遥控器位置， 得到遥控器的类型并存入数据库中  类型的定义在DeviceControl类中
     *
     * 默认的有空调、投影、功放 、电视、播放器、其他六个类型 对应以上的六个图标
     *
     * 电视       0X04
       DVD/VCD	 0X05
       音响		 0X07
       空调		 0X08
       投影仪     0X87
       其他遥控器  0X80
     *
     * 空调 = 8 投影 = 135 功放 = 7 电视 = 4  (DVD/VCD)播放器 = 5 其他 = 128
     * @return
     */
    public  int getIrRcDeviceType(){
       switch (selectPosition){
           case 0:
                 return 8;
           case 1:
               return 135;
           case 2:
               return 7;
           case 3:
               return 4;
           case 4:
               return 5;
           case 5:
               return 128;
       }
       return 128;
    }


    /**
     * 更据选择的遥控器位置， 得到遥控器的默认名称, 名字的字符串定义在字符串数组 default_rc_name中
     *
     *<string-array name="default_rc_name">
     <item>空调</item>
     <item>投影仪</item>
     <item>功放</item>
     <item>电视</item>
     <item>播放器</item>
     <item>其他</item>
     </string-array>
     * @return
     */
    public  String getIrRcDeviceName(){
        switch (selectPosition){
            case 0:
                return mRcTypeNames[0];
            case 1:
                return mRcTypeNames[1];

            case 2:
                return mRcTypeNames[2];

            case 3:
                return mRcTypeNames[3];

            case 4:
                return mRcTypeNames[4];

            case 5:
                return mRcTypeNames[5];
        }
        return mRcTypeNames[5];
    }


}
