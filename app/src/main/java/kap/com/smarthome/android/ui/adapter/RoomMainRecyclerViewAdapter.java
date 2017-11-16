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
import kap.com.smarthome.android.data.bean.Room;

/**
 * Created by Administrator on 2017/8/25 0025.
 */
public class RoomMainRecyclerViewAdapter extends RecyclerView.Adapter<RoomMainRecyclerViewAdapter.MyViewHolder>{

    private Context context;
    private List<Room> roomList;


    private boolean isEditStateFlag = false;

    private OnRecyclerItemClickListener mOnItemClickListener;//单击事件

    public RoomMainRecyclerViewAdapter(Context context , List<Room> list) {
        this.context = context;
        this.roomList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //找到item的布局
        View view= LayoutInflater.from(context).inflate(R.layout.devices_and_room_recycler_item, parent,false);
        return new MyViewHolder(view);//将布局设置给holder
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if(isEditStateFlag){
            holder.mDeleteIconIv.setVisibility(View.VISIBLE);
        }else{
            holder.mDeleteIconIv.setVisibility(View.GONE);
        }

        //设置单击事件
        if(mOnItemClickListener != null){
            holder.mDeleteIconIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击删除按钮 ，删除一个房间
                    mOnItemClickListener.onDeleteIconClick(v , holder.getLayoutPosition());
                }
            });

            holder.mRoomIconIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击房间按钮
                    mOnItemClickListener.onItemClick(v, holder.getLayoutPosition());
                }
            });
        }

        Room room = roomList.get(position);
        switch (room.getTYPE()){
            case 0: //客厅
                holder.mRoomIconIv.setBackgroundResource(R.drawable.room_living);
                break;
            case 1: //卧室
                holder.mRoomIconIv.setBackgroundResource(R.drawable.room_bedroom);
                break;
            case 2: //儿童房
                holder.mRoomIconIv.setBackgroundResource(R.drawable.room_children);
                break;
            case 3: //阳台
                holder.mRoomIconIv.setBackgroundResource(R.drawable.room_balcony);
                break;
            case 4: //厨房
                holder.mRoomIconIv.setBackgroundResource(R.drawable.room_kitchen);
                break;
            case 5: //书房
                holder.mRoomIconIv.setBackgroundResource(R.drawable.room_booking);
                break;
            case 6: //卫生间
                holder.mRoomIconIv.setBackgroundResource(R.drawable.room_toilets);
                break;
        }
        holder.mRoomName.setText(room.getNAME());

    }

    @Override
    public int getItemCount() {
        return this.roomList.size();
    }


      class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mRoomIconIv;
        ImageView mDeleteIconIv;
        TextView  mRoomName;


        public MyViewHolder(View itemView) {
            super(itemView);
            mRoomIconIv = (ImageView) itemView.findViewById(R.id.recycler_view_large_item_iv);
            mDeleteIconIv = (ImageView) itemView.findViewById(R.id.large_delete_iv);
            mRoomName  = (TextView) itemView.findViewById(R.id.recycler_view_large_item_tv);
        }
    }


    /**
     * 处理item的点击事件,因为recycler没有提供单击事件,所以只能自己写了
     */
    public interface OnRecyclerItemClickListener {
        //点击房间
         void onItemClick(View view, int position);

        //点击删除按钮
         void onDeleteIconClick(View view , int position);
    }

    /**
     * 暴露给外面的设置单击事件
     */
    public void setOnItemClickListener(OnRecyclerItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置是否开始编辑状态
     */
    public boolean setEditState(boolean isEditState){
        isEditStateFlag = isEditState;
        notifyDataSetChanged();
        return  isEditStateFlag;
    }

    /**
     *获取当前状态
     */
    public boolean getEditstate(){
        return  isEditStateFlag;
    }


}
