package kap.com.smarthome.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.Room;
import kap.com.smarthome.android.presenter.utils.ResourcesUtils;


/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class AddRemoteControlChooseRoomGvAdapter extends BaseAdapter{

    private Context mContext;

    private int[] mRoomTypeIcons = {R.drawable.room_living, R.drawable.room_bedroom, R.drawable.room_children,
                                   R.drawable.room_balcony,R.drawable.room_kitchen, R.drawable.room_booking,
                                   R.drawable.room_toilets};

    private String[] mRoomTypeNames = null;

    private int selectPosition = -1;


    private List<Room> mAllRooms;

    public AddRemoteControlChooseRoomGvAdapter(Context context , List<Room> rooms) {
        mContext = context;
        mAllRooms = rooms;
        mRoomTypeNames = ResourcesUtils.getStringArray(mContext, R.array.default_rooms);
    }

    @Override
    public int getCount() {
        return mAllRooms.size();
    }

    @Override
    public Object getItem(int position) {
        return mAllRooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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


        //房间是根据房间的添加的时候定义的房间类型选择对应的图标表示，因为房间的的类型是有限的，默认为7个类型，7种图标
        initRoomIcon(gvHoldview , mAllRooms.get(position).getTYPE());

        gvHoldview.mRoomNames.setText(mAllRooms.get(position).getNAME());

        if(selectPosition > -1 && selectPosition == position ){
            gvHoldview.mSelectView.setBackgroundResource(R.drawable.selected_icon);

        }else {
            gvHoldview.mSelectView.setBackgroundResource(R.drawable.unselected_icon);
        }

        return convertView;
    }


    /**
     *  房间类型有7种，分别从0-6表示
     */
    private void initRoomIcon(GvViewHold gvHoldview , int type) {
        switch (type){
            case 0 :
                gvHoldview.mIconVew.setBackgroundResource(mRoomTypeIcons[0]);
                break;
            case 1 :
                gvHoldview.mIconVew.setBackgroundResource(mRoomTypeIcons[1]);
                break;
            case 2 :
                gvHoldview.mIconVew.setBackgroundResource(mRoomTypeIcons[2]);
                break;
            case 3 :
                gvHoldview.mIconVew.setBackgroundResource(mRoomTypeIcons[3]);
                break;
            case 4 :
                gvHoldview.mIconVew.setBackgroundResource(mRoomTypeIcons[4]);
                break;
            case 5 :
                gvHoldview.mIconVew.setBackgroundResource(mRoomTypeIcons[5]);
                break;
            case 6 :
                gvHoldview.mIconVew.setBackgroundResource(mRoomTypeIcons[6]);
                break;
        }
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
