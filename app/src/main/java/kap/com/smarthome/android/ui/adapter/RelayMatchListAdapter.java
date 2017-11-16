package kap.com.smarthome.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.RelayBox;

/**
 * Created by yushq on 2017/9/6 0006.
 */

public class RelayMatchListAdapter extends BaseAdapter {

    private Context mContext;

    private List<RelayBox> mSelectRelayBox;

    private LayoutInflater mInflater;

    private int selectPosition = -1;//用于记录用户选择的变量

    /**
     * @param context
     * @param allRealyboxList  新搜索到的中继盒子的列表
     */
    public RelayMatchListAdapter(Context context , List<RelayBox>  allRealyboxList) {
        this.mContext = context;
        mSelectRelayBox = allRealyboxList;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mSelectRelayBox.size();
    }

    @Override
    public Object getItem(int position) {
        return mSelectRelayBox.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_relay_match_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)convertView.findViewById(R.id.id_name);
            viewHolder.select = (RadioButton)convertView.findViewById(R.id.id_select);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.name.setText(mSelectRelayBox.get(position).getNAME());

        if(selectPosition == position){

            viewHolder.select.setChecked(true);
        }
        else{

            viewHolder.select.setChecked(false);

        }
        return convertView;
    }

    public class ViewHolder{
    TextView name;
    RadioButton select;
   }


    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }
}
