package kap.com.smarthome.android.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.ui.activity.AddRelayBoxActivity;

/**
 * Created by yushq on 2017/9/6 0006.
 */

public class RelayAddListViewAdapter extends BaseAdapter {

    private Context mContext;

    //搜索到的中继盒子的列表
    private List<RelayBox>  mNewSearchBoxList;

    //已经存在本地数据库的中继盒子的数目
    private int mAlreadyBoxAmount;

    private List<RelayBox> mSelectRelayBox;

    /**
     * @param context
     * @param newSearchBoxList  新搜索到的中继盒子的列表
     * @param amount           数据库中已经存在的中继盒子的数目
     */
    public RelayAddListViewAdapter(Context context , List<RelayBox>  newSearchBoxList , int amount) {
        this.mContext = context;
        mNewSearchBoxList = newSearchBoxList;
        mAlreadyBoxAmount = amount;
        mSelectRelayBox = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mNewSearchBoxList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewSearchBoxList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.add_relay_listview_item, null);
        }

        final CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.relay_add_checkbox);

        TextView textview = (TextView) convertView.findViewById(R.id.relay_add_id_tv);

        textview.setText(mNewSearchBoxList.get(position).getNAME());

        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox.isChecked()){
                    checkbox.setChecked(false);
                }else{
                    checkbox.setChecked(true);
                }
            }
        });

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mSelectRelayBox.add(mNewSearchBoxList.get(position));
                }else{
                    mSelectRelayBox.remove(mNewSearchBoxList.get(position));
                }
            }
        });

        /*for (int i = 0 ; i < mSelectRelayBox.size() ; i++){
            if(mNewSearchBoxList.get(position).getBOX_ID() == mSelectRelayBox.get(i).getBOX_ID()){
                checkbox.setChecked(true);
                Log.e("DATABASE", "getView: ---------判断哪些中继盒子被选中----------");
            }
        }*/

        return convertView;
    }

    /**
     * 获取选中的中继盒子
     * 由AddRelayBoxActivity 按下完成按钮调用
     * @return
     */
    public List<RelayBox> getmSelectRelayBox() {
        return mSelectRelayBox;
    }


    /**
     * 全选
     */
    public void selectAllRelayBoxes(){
        mSelectRelayBox.clear();
        for (int i = 0 ; i < mNewSearchBoxList.size(); i++ ){
            mSelectRelayBox.add(mNewSearchBoxList.get(i));
        }
        notifyDataSetChanged();
    }


    /**
     * 全选
     */
    public void cancelSelectAllRelayBoxes(){
        mSelectRelayBox.clear();
        notifyDataSetChanged();
    }

}
