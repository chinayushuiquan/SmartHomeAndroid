package kap.com.smarthome.android.ui.adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.IRKey;
import kap.com.smarthome.android.data.bean.ScenesDevice;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.DeviceHandleUtils;
import kap.com.smarthome.android.ui.allinterface.ItemSlidingViewClickListener;
import kap.com.smarthome.android.ui.allinterface.ThreeSwitchButtonListen;
import kap.com.smarthome.android.ui.utils.ScreenUtils;
import kap.com.smarthome.android.ui.view.SlidingButtonView;
import kap.com.smarthome.android.ui.view.ThreeSwitchButton;


/**
 * Created by yushq on 2017/9/4 0004.
 */

public class ScenesAddDevicesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements  SlidingButtonView.IonSlidingButtonListener {

    private Context mContext;

    private ItemSlidingViewClickListener itemSlidingViewClickListener;
    private SlidingButtonView mMenu = null;


    private List<ScenesDevice>  mEditScenesDeviceList;

    public ScenesAddDevicesRecyclerAdapter(Context context , List<ScenesDevice> scenesDevices) {
        mContext = context;
        mEditScenesDeviceList = scenesDevices;
    }

    @Override
    public int getItemCount() {
        return mEditScenesDeviceList.size();
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder  itemViewHolder = (ItemViewHolder) holder;
            //设置内容布局的宽为屏幕宽度
            itemViewHolder.layout_content.getLayoutParams().width = ScreenUtils.getWidth(mContext);
            itemViewHolder.layout_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //判断是否有删除菜单打开
                    if (menuIsOpen()) {
                        closeMenu();//关闭菜单
                    } else {
                        int n = holder.getLayoutPosition();
                        itemSlidingViewClickListener.onItemClick(v, n);
                    }
                }
            });

            itemViewHolder.btn_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int n = holder.getLayoutPosition();
                    itemSlidingViewClickListener.onDeleteBtnClick(v, n);
                }
            });

            /**
             * 判断设备的类型
             * 选中相应的设备图标 , 类型type设备的名称
             */
            initDeviceType(itemViewHolder, position);
        }
    }


    /**
     * 适配设备的初始化方法
     * @param holder
     * @param position
     */
    private void initDeviceType(ItemViewHolder holder, int position) {
        final ScenesDevice  scenesDevice = mEditScenesDeviceList.get(position);
            switch (scenesDevice.getTYPE()) {
                case DeviceHandleUtils.ADJUST_LAMP_SQL: //可调灯
                    holder.iconIv.setBackgroundResource(R.drawable.new_device_adjust_lamp);
                    holder.nameTv.setText(scenesDevice.getDEVICE_NAME());
                    holder.editIv.setVisibility(View.GONE);
                    holder.infoTv.setVisibility(View.GONE);
                    //String value_lamp = scenesDevice.getDEVICE_VALUE();
                    /*if(value_lamp != null && !value_lamp.isEmpty()){
                        if(value_lamp.equalsIgnoreCase("0000")){
                            holder.switchCompat.setChecked(true);
                        }else if(value_lamp.equalsIgnoreCase("00ff")){
                            holder.switchCompat.setChecked(false);
                        }
                    }
*/
                    holder.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                scenesDevice.setDEVICE_VALUE("00ff");
                            }else{
                                scenesDevice.setDEVICE_VALUE("0000");
                            }
                        }
                    });
                    break;
                case DeviceHandleUtils.UNADJUST_LAMP_SQL: //不可调灯

                    holder.iconIv.setBackgroundResource(R.drawable.new_device_unadjust_lamp);
                    holder.nameTv.setText(R.string.un_adjust_lamp);
                    holder.editIv.setVisibility(View.GONE);
                    holder.infoTv.setVisibility(View.GONE);

                    break;
                case DeviceHandleUtils.WIRELESS_SOCKET_SQL: //无线插座



                    break;
                case DeviceHandleUtils.DEFENCE_SQL: //安防设备



                    break;
                case DeviceHandleUtils.WIRELESS_CURTAIN_SQL: //无线窗帘

                    holder.iconIv.setBackgroundResource(R.drawable.new_device_curtain);
                    holder.nameTv.setText(R.string.curtain);
                    holder.switchCompat.setVisibility(View.GONE);
                    holder.editIv.setVisibility(View.GONE);
                    holder.infoTv.setVisibility(View.GONE);
                    holder.threeSwitchButton.setVisibility(View.VISIBLE);

                    String value_current = scenesDevice.getDEVICE_VALUE();

                    if(value_current != null && !value_current.isEmpty()){
                        if(value_current.equalsIgnoreCase("0000")){
                            holder.threeSwitchButton.setSelectPosition(1);
                        } else if(value_current.equalsIgnoreCase("0088")){
                            holder.threeSwitchButton.setSelectPosition(2);
                        } else if(value_current.equalsIgnoreCase("00ff")){
                            holder.threeSwitchButton.setSelectPosition(3);
                        }
                    }

                    holder.threeSwitchButton.setChangeSelectListener(new ThreeSwitchButtonListen() {
                        @Override
                        public void firstTextClick() {
                            scenesDevice.setDEVICE_VALUE("0000");
                        }

                        @Override
                        public void secondTextClick() {
                            scenesDevice.setDEVICE_VALUE("0088");
                        }

                        @Override
                        public void threeTextClick() {
                            scenesDevice.setDEVICE_VALUE("00ff");
                        }
                    });

                    break;
                case DeviceHandleUtils.DEFENCE_PROBE_SQL: //探头性质


                    break;
                case DeviceHandleUtils.DEFENCE_TYPE_SQL: //探头类型

                    break;

                case DeviceHandleUtils.CAMERA_SQL: //摄像头


                    break;
                case DeviceHandleUtils.SCENE_SWITCH_SQL: //灯光联动控制器

                    break;
                case DeviceHandleUtils.IR_TV_SQL: //电视

                    holder.iconIv.setBackgroundResource(R.drawable.new_device_unadjust_lamp);
                    holder.nameTv.setText(scenesDevice.getDEVICE_NAME());
                    holder.editIv.setVisibility(View.GONE);
                    holder.infoTv.setVisibility(View.GONE);

                    holder.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        List<IRKey> irKeyList = DataBaseHandle.queryOneIrDeviceAllKeys(scenesDevice.getDEVICE_ID());
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                scenesDevice.setDEVICE_VALUE(irKeyList.get(0).getKEY1()+"");
                                Log.e("CHRIS", " 电视机红外码值开: " + scenesDevice.getDEVICE_VALUE());
                            }else{
                                scenesDevice.setDEVICE_VALUE(irKeyList.get(0).getKEY2()+"");
                                Log.e("CHRIS", " 电视机红外码值关: " + scenesDevice.getDEVICE_VALUE());
                            }
                        }
                    });

                    break;
                case DeviceHandleUtils.IR_DVD_SQL: //DVD/VCD



                    break;
                case DeviceHandleUtils.IR_SOUND_SQL: //音响


                    break;
                case DeviceHandleUtils.IR_AIR_CONDITION_SQL: //空调


                    break;
                case DeviceHandleUtils.BACKGROUND_MUSIC_SQL: //红外背景音乐

                    break;
        }
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyler_item_view, arg0, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }




    public  class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView btn_Delete;
        public TextView nameTv;
        public TextView infoTv;
        public ImageView iconIv;
        public SwitchCompat switchCompat;
        public ImageView editIv;
        public ThreeSwitchButton threeSwitchButton;
        public ViewGroup layout_content;


        public ItemViewHolder(View itemView) {
            super(itemView);
            btn_Delete = (TextView) itemView.findViewById(R.id.tv_delete);
            nameTv = (TextView) itemView.findViewById(R.id.scenes_add_devices_name_tv);
            infoTv = (TextView) itemView.findViewById(R.id.scenes_add_devices_info_tv);
            iconIv = (ImageView) itemView.findViewById(R.id.scenes_add_devices_icon_iv);
            switchCompat = (SwitchCompat) itemView.findViewById(R.id.scenes_add_devices_set_open_btn);
            editIv = (ImageView) itemView.findViewById(R.id.scenes_edit_devices_attribute);
            threeSwitchButton = (ThreeSwitchButton) itemView.findViewById(R.id.three_switch_button);

            layout_content = (ViewGroup) itemView.findViewById(R.id.layout_content);

            ((SlidingButtonView) itemView).setSlidingButtonListener(ScenesAddDevicesRecyclerAdapter.this);
        }
    }


    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    /**
     * 滑动或者点击了Item监听
     * @param slidingButtonView
     */
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if(menuIsOpen()){
            if(mMenu != slidingButtonView){
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;
    }


    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if(mMenu != null){
            return true;
        }
        return false;
    }


    public ItemSlidingViewClickListener getItemSlidingViewClickListener() {
        return itemSlidingViewClickListener;
    }


    public void setItemSlidingViewClickListener(ItemSlidingViewClickListener itemSlidingViewClickListener) {
        this.itemSlidingViewClickListener = itemSlidingViewClickListener;
    }



}