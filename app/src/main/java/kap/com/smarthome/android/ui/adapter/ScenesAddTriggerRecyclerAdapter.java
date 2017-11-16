package kap.com.smarthome.android.ui.adapter;

import android.content.Context;
import android.content.SyncStatusObserver;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.ScenesTrigger;
import kap.com.smarthome.android.presenter.control.DeviceHandleUtils;
import kap.com.smarthome.android.ui.allinterface.ItemSlidingViewClickListener;
import kap.com.smarthome.android.ui.utils.ScreenUtils;
import kap.com.smarthome.android.ui.view.MyPopupWindow;
import kap.com.smarthome.android.ui.view.SlidingButtonView;
import kap.com.smarthome.android.ui.view.ThreeSwitchButton;


/**
 * Created by yushq on 2017/9/4 0004.
 */

public class ScenesAddTriggerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements  SlidingButtonView.IonSlidingButtonListener {

    private Context mContext;

    private ItemSlidingViewClickListener  itemSlidingViewClickListener;
    private SlidingButtonView mMenu = null;


    private List<ScenesTrigger>  mEditScenesTriggerList;


    int mHour;
    int mMinute;
    boolean isAdd1 , isAdd2, isAdd3, isAdd4, isAdd5, isAdd6, isAdd7;


    public ScenesAddTriggerRecyclerAdapter(Context context , List<ScenesTrigger> scenesTriggers) {
        mContext = context;
        mEditScenesTriggerList = scenesTriggers;
    }

    @Override
    public int getItemCount() {
        return mEditScenesTriggerList.size();
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
                    Log.e("CHRIS", "onClick: 关闭菜单");
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
    private void initDeviceType(final ItemViewHolder holder, int position) {
        final ScenesTrigger  scenesTrigger = mEditScenesTriggerList.get(position);
        int   type = scenesTrigger.getTYPE();
        switch (type){
            case  DeviceHandleUtils.SECURITY_PHOTOELECTRIC_SMOKE_DETECTOR_SQL: //光电烟雾探测器

                holder.iconIv.setBackgroundResource(R.drawable.scenes_add_timer);
                holder.nameTv.setText(scenesTrigger.getDEVICE_NAME());
                holder.switchCompat.setVisibility(View.GONE);
                holder.infoTv.setVisibility(View.GONE);

                holder.iconIv.setBackgroundResource(R.drawable.scenes_silding_photoelectric_smoke_detector_1);


                break;

            case DeviceHandleUtils.SECURITY_LEAK_WATER_DETECTOR_SQL: //漏水探测器

                holder.iconIv.setBackgroundResource(R.drawable.scenes_silding_leak_detector_1);

                break;

            case DeviceHandleUtils.SECURITY_CURTAIN_DETECTOR_SQL: //帘幕探测器

                holder.iconIv.setBackgroundResource(R.drawable.scenes_silding_curtain_detector_1);

                break;


            case DeviceHandleUtils.SECURITY_GAS_DETECTOR_SQL: //燃气探测器

                holder.iconIv.setBackgroundResource(R.drawable.scenes_silding_gas_detector_1);

                break;

            case DeviceHandleUtils.SECURITY_SMOKE_DETECTOR_SQL: //烟雾探测器

                holder.iconIv.setBackgroundResource(R.drawable.scenes_silding_smoke_detector_1);
                break;

            case DeviceHandleUtils.SECURITY_GAS_LEAK_ALARM_SQL: //燃气泄漏报警器

                holder.iconIv.setBackgroundResource(R.drawable.scenes_silding_gas_leakage_alarm_1);

                break;

            case DeviceHandleUtils.SECURITY_EMERGENCY_BUTTON_SQL: //紧急按钮

                holder.iconIv.setBackgroundResource(R.drawable.scenes_silding_emergency_button_1);

                break;
            case DeviceHandleUtils.SCENES_TIMER_SQL:
                holder.iconIv.setBackgroundResource(R.drawable.scenes_add_timer);
                holder.nameTv.setText("定时器");
                holder.switchCompat.setVisibility(View.GONE);

                holder.infoTv.setText(scenesTrigger.getTRIGGER_INFO()+"");
                holder.editIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       //弹出一个界面让用户选择需要添加的安防设备

                        View popWindView = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_scenes_timer , null);

                        TimePicker  timePicker = (TimePicker) popWindView.findViewById(R.id.timePicker);
                        ImageView   ok_iv = (ImageView) popWindView.findViewById(R.id.ok);

                        final MyPopupWindow popWindow = new MyPopupWindow(mContext , popWindView , R.style.right_popupWindow_anim);
                        popWindow.showAtLocation(v , Gravity.CENTER_VERTICAL , 0 , 0);

                        final Button button1 = (Button) popWindView.findViewById(R.id.weekday_1);
                        final Button button2 = (Button) popWindView.findViewById(R.id.weekday_2);
                        final Button button3 = (Button) popWindView.findViewById(R.id.weekday_3);
                        final Button button4 = (Button) popWindView.findViewById(R.id.weekday_4);
                        final Button button5 = (Button) popWindView.findViewById(R.id.weekday_5);
                        final Button button6 = (Button) popWindView.findViewById(R.id.weekday_6);
                        final Button button7 = (Button) popWindView.findViewById(R.id.weekday_7);

                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(isAdd1){
                                    button1.setPressed(false);
                                    isAdd1 = false;
                                    button1.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
                                }else{
                                    button1.setPressed(true);
                                    isAdd1 = true;
                                    button1.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                                }
                            }
                        });

                        button2.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                if(isAdd2){
                                    button2.setPressed(false);
                                    isAdd2 = false;
                                    button2.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
                                }else{
                                    button2.setPressed(true);
                                    isAdd2 = true;
                                    button2.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                                }
                            }
                        });

                        button3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(isAdd3){
                                    button3.setPressed(false);
                                    isAdd3 = false;
                                    button3.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
                                }else{
                                    button3.setPressed(true);
                                    isAdd3 = true;
                                    button3.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                                }
                            }
                        });


                        button4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(isAdd4){
                                    button4.setPressed(false);
                                    isAdd4 = false;
                                    button4.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
                                }else{
                                    button4.setPressed(true);
                                    isAdd4 = true;
                                    button4.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                                }
                            }
                        });


                        button5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(isAdd5){
                                    button5.setPressed(false);
                                    isAdd5 = false;
                                    button5.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
                                }else{
                                    button5.setPressed(true);
                                    isAdd5 = true;
                                    button5.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                                }
                            }
                        });


                        button6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(isAdd6){
                                    button6.setPressed(false);
                                    isAdd6 = false;
                                    button6.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
                                }else{
                                    button6.setPressed(true);
                                    isAdd6 = true;
                                    button6.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                                }
                            }
                        });


                        button7.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(isAdd7){
                                    button7.setPressed(false);
                                    isAdd7 = false;
                                    button7.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
                                }else{
                                    button7.setPressed(true);
                                    isAdd7 = true;
                                    button7.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                                }
                            }
                        });

                        // 为TimePicker指定监听器
                        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

                            @Override
                            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                                mHour = hourOfDay;
                                mMinute = minute;
                            }
                        });

                        /**
                         * 场景时间格式定义：
                         \小时\分钟\秒钟\星期一有且重复\星期二有且不重复\星期三没有且重复\星期四有且重复\星期五有且重复\星期六有且重复\星期天有且重复\
                         \18\20\30\11\10\01\11\11\11\11\
                         */
                        ok_iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                StringBuilder  builder = new StringBuilder();
                                StringBuilder  builder_info = new StringBuilder();
                                builder.append("/");
                                builder.append(mHour);//小时
                                builder.append("/");
                                builder.append(mMinute);//分钟
                                builder.append("/");
                                builder.append(mMinute); //秒钟

                                builder_info.append(mHour+"时" + mMinute+"分");

                                if(isAdd1){
                                    builder.append("/");
                                    builder.append("11");
                                    builder_info.append("周一");
                                }else{
                                    builder.append("/");
                                    builder.append("00");
                                }


                                if(isAdd2){
                                    builder.append("/");
                                    builder.append("11");
                                    builder_info.append("周二");
                                }else{
                                    builder.append("/");
                                    builder.append("00");
                                }


                                if(isAdd3){
                                    builder.append("/");
                                    builder.append("11");
                                    builder_info.append("周三");
                                }else{
                                    builder.append("/");
                                    builder.append("00");
                                }


                                if(isAdd4){
                                    builder.append("/");
                                    builder.append("11");
                                    builder_info.append("周四");
                                }else{
                                    builder.append("/");
                                    builder.append("00");
                                }


                                if(isAdd5){
                                    builder.append("/");
                                    builder.append("11");
                                    builder_info.append("周五");
                                }else{
                                    builder.append("/");
                                    builder.append("00");
                                }


                                if(isAdd6){
                                    builder.append("/");
                                    builder.append("11");
                                    builder_info.append("周六");
                                }else{
                                    builder.append("/");
                                    builder.append("00");
                                }


                                if(isAdd7){
                                    builder.append("/");
                                    builder.append("11");
                                    builder_info.append("周天");
                                }else{
                                    builder.append("/");
                                    builder.append("00");
                                    builder.append("/");
                                }

                                scenesTrigger.setTRIGGER_VALUE(builder.toString());
                                scenesTrigger.setTRIGGER_INFO(builder_info.toString());
                                popWindow.dismiss();
                                notifyDataSetChanged();
                            }
                        });

                    }
                });
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
            ((SlidingButtonView) itemView).setSlidingButtonListener(ScenesAddTriggerRecyclerAdapter.this);
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