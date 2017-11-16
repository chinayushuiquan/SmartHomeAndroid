package kap.com.smarthome.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.Room;


/**
 * Created by Administrator on 2017/10/18 0018.
 */

public class RoomChooseListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Room> mRooms;


    public RoomChooseListAdapter(Context context , List<Room>  rooms ) {
        mContext = context;
        mRooms = rooms;
    }

    @Override
    public int getCount() {
        return mRooms.size();
    }

    @Override
    public Object getItem(int position) {
        return mRooms.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.room_list_item, null);
        }

        TextView textview = (TextView) convertView.findViewById(R.id.room_list_item_tv);

        textview.setText(mRooms.get(position).getNAME());

        return  convertView;

    }
}
