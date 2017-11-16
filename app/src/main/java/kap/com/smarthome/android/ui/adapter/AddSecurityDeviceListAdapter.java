package kap.com.smarthome.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import kap.com.smarthome.android.R;


/**
 * Created by yushq on 2017/10/18 0018.
 */

public class AddSecurityDeviceListAdapter extends BaseAdapter {

    private Context mContext;


    /**
     * 光电烟雾探测器        011
     漏水探测器               012
     帘幕探测器               013
     燃气探测器               014
     燃气泄漏报警器        015
     烟雾探测器               016
     紧急按钮                   017

     */
    private String[] SECURITYSUBTYPE = {"011" , "012", "014", "015", "016", "017", "018"};

    private String[] SECURITYSUBTYPENAME = {"光电烟雾探测器" , "漏水探测器", "帘幕探测器", "燃气探测器", "燃气泄漏报警器", "烟雾探测器", "紧急按钮"};


    public AddSecurityDeviceListAdapter(Context context ) {
        mContext = context;

    }

    @Override
    public int getCount() {
        return SECURITYSUBTYPENAME.length;
    }

    @Override
    public Object getItem(int position) {
        return SECURITYSUBTYPE[position];
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

        textview.setText(SECURITYSUBTYPENAME[position]);

        return  convertView;

    }




}
