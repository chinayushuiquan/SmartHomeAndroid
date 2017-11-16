package kap.com.smarthome.android.ui.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.presenter.utils.ResourcesUtils;


/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class RoomIconChoseGvAdapter extends BaseAdapter{

    private Context mContext;

    private int[] mRoomTypeIcons = {R.drawable.room_living, R.drawable.room_bedroom, R.drawable.room_children,
                                   R.drawable.room_balcony,R.drawable.room_kitchen, R.drawable.room_booking,
                                   R.drawable.room_toilets};

    private String[] mRoomTypeNames = null;

    private int selectPosition = -1;

    public RoomIconChoseGvAdapter(Context context) {
        mContext = context;
        mRoomTypeNames = ResourcesUtils.getStringArray(mContext, R.array.default_rooms);
    }

    @Override
    public int getCount() {
        return mRoomTypeNames.length;
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
        gvHoldview.mIconVew.setBackgroundResource(mRoomTypeIcons[position]);
        if(selectPosition > -1 && selectPosition == position ){
            gvHoldview.mSelectView.setBackgroundResource(R.drawable.selected_icon);
        }else {
            gvHoldview.mSelectView.setBackgroundResource(R.drawable.unselected_icon);
        }

        gvHoldview.mRoomNames.setText(mRoomTypeNames[position]);
        return convertView;
    }

    static  class  GvViewHold {
        ImageView mIconVew;
        ImageView mSelectView;
        TextView  mRoomNames;
    }


    /**
     * 由AddRoomActivity调用，得到选择的房间类型的编号
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
}
