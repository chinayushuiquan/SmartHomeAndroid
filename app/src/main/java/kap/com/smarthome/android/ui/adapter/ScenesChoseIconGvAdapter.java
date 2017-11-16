package kap.com.smarthome.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import kap.com.smarthome.android.R;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class ScenesChoseIconGvAdapter extends BaseAdapter{


    private Context mContext;

    private int[]  scenesDrawable = {R.drawable.scenes_add_go_home , R.drawable.scenes_add_get_up,
                                     R.drawable.scenes_add_go_to_bed, R.drawable.scenes_add_go_to_work};

    private int[]  scenesDrawableDefault = {R.drawable.scenes_add_go_home_default, R.drawable.scenes_add_get_up_default,
            R.drawable.scenes_add_go_to_bed_default, R.drawable.scenes_add_go_to_work_default};


    //被选中的图标的标号
    private int mSelectIconIndex = -1;


    public ScenesChoseIconGvAdapter(Context context){
        mContext = context;
    }


    @Override
    public int getCount() {
        return scenesDrawable.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.commuse_scenes_gv_item, null);
            viewHolder.devicesView = (ImageView) convertView.findViewById(R.id.gv_item_iv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //场景图标
        if(position == mSelectIconIndex){
            viewHolder.devicesView.setImageResource(scenesDrawable[position]);
        }else{
            viewHolder.devicesView.setImageResource(scenesDrawableDefault[position]);
        }

        return convertView;
    }

    class ViewHolder{
        ImageView  devicesView;
    }


    public int getSelectIconIndex() {
        return mSelectIconIndex;
    }

    public void setSelectIconIndex(int selectIconIndex){
        this.mSelectIconIndex = selectIconIndex;
    }
}
