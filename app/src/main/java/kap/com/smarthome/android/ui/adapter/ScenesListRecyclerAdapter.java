package kap.com.smarthome.android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.Scenes;
import kap.com.smarthome.android.data.bean.ScenesDevice;
import kap.com.smarthome.android.data.bean.ScenesTrigger;
import kap.com.smarthome.android.presenter.constants.AllConstants;

/**
 * Created by yushq on 2017/9/1 0001.
 */

public class ScenesListRecyclerAdapter extends RecyclerView.Adapter<ScenesListRecyclerAdapter.ScenesViewHold> {


    private Context mContext;
    private boolean isEditStateFlag;


    private int[]  scenesDrawableDefault = {R.drawable.scenes_home_white, R.drawable.scenes_get_bed_white,
            R.drawable.scenes_sleep_white, R.drawable.scenes_work_white};


    private int[] scenesRoundImage = {R.drawable.scenes_round_yellow, R.drawable.scenes_round_blue,
            R.drawable.scenes_round_red, R.drawable.scenes_round_green};

    //场景数据集合
    private List<Scenes> mALLScenesList;

    //场景控制设备数据集合
    private List<ScenesDevice> mALLScenesDevicesList;

    //场景触发条件数据集合
    private List<ScenesTrigger> mALLScenesTriggerList;


    private View view;

    private List<View> mAnimView = new ArrayList<>();

    private Animation  clickAnimation;

    private ScenesControlClickListen scenesControlClickListen;

    public ScenesListRecyclerAdapter(Context context , List<Scenes> scenes , List<ScenesDevice> scenesDevices , List<ScenesTrigger> scenesTriggers) {
             mContext = context;
             mALLScenesList = scenes;
             mALLScenesDevicesList = scenesDevices;
             mALLScenesTriggerList = scenesTriggers;
    }


    @Override
    public ScenesViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.scenes_list_recycler_item, parent ,false);
        return new ScenesViewHold(view);
    }

    @Override
    public void onBindViewHolder(final ScenesViewHold holder, final int position) {

         if(isEditStateFlag){
            holder.mDeleteIv.setVisibility(View.VISIBLE);
         }else{
             holder.mDeleteIv.setVisibility(View.GONE);
         }

         holder.mScenesNameTv.setText(mALLScenesList.get(position).getSCENE_NAME());

         final int iconPosition = mALLScenesList.get(position).getSCENE_ICON();
         holder.mExecutionIv.setImageResource(scenesDrawableDefault[iconPosition]);


         for (int i = 0 ; i < mALLScenesTriggerList.size() ; i++){
             if(mALLScenesTriggerList.get(i).getDEVICE_ID().equals("0000040000000000")){
                 holder.mNoteTv.setText(mALLScenesTriggerList.get(i).getTRIGGER_INFO());
             }
         }


        //立即场景执行
         holder.mExecutionRl.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(scenesControlClickListen != null)
                 scenesControlClickListen.onExecute(v, position);

                 //设置动画
                 holder.mExecutionRl.setBackgroundResource(scenesRoundImage[iconPosition]);

                 clickAnimation = AnimationUtils.loadAnimation(mContext, R.anim.scenes_round_anim);
                 LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
                 clickAnimation.setInterpolator(interpolator);

                 if (clickAnimation != null) {
                     holder.mExecutionRl.startAnimation(clickAnimation);  //开始动画
                     mAnimView.add(holder.mExecutionIv);
                     holder.mExecutionIv.setVisibility(View.GONE);
                 }


                 //添加动画状态监听
                 clickAnimation.setAnimationListener(new Animation.AnimationListener() {
                     @Override
                     public void onAnimationStart(Animation animation) {

                     }

                     @Override
                     public void onAnimationEnd(Animation animation) {
                         holder.mExecutionRl.setBackgroundResource(R.drawable.scenes_control_icon_bg);
                         mAnimView.remove(holder.mExecutionIv);
                         holder.mExecutionIv.setVisibility(View.VISIBLE);
                     }

                     @Override
                     public void onAnimationRepeat(Animation animation) {

                     }
                 });

             }
         });


         //条件开关
         if(mALLScenesList.get(position).getTRIGGER_STATUS() == AllConstants.SCENES_TRIGGER_STATUS_OPEN){

                   holder.mConditionIv.setImageResource(R.drawable.scenes_condition_open);

         }else{

                  holder.mConditionIv.setImageResource(R.drawable.scenes_condition_close);

         }

        //触发条件总开关
         holder.mConditionIv.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(scenesControlClickListen != null)
                 scenesControlClickListen.onTriggerControl(v,position);
             }
         });


         holder.linearLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(scenesControlClickListen != null)
                     scenesControlClickListen.onEdit(v,position);
             }
         });


         holder.mDeleteIv.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(scenesControlClickListen != null)
                     scenesControlClickListen.onDelete(v,position);
             }
         });


         String scenes_id = mALLScenesList.get(position).getSCENE_ID();

         List<ScenesDevice>  thisScenesDevices = new ArrayList<>();

         for (int i = 0 ; i < mALLScenesDevicesList.size() ; i++){
             if(scenes_id.equals(mALLScenesDevicesList.get(i).getSCENES_ID())){
                 thisScenesDevices.add(mALLScenesDevicesList.get(i));
             }
         }

        Log.e("DATA", "thisScenesDevices:  = " + thisScenesDevices.size());

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);

        float density = dm.density;
        int allWidth = (int) (60 * 10 * density);
        int itemWidth = (int) (60 * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(allWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        holder.mScenesLinkDevicesGv.setLayoutParams(params);// 设置GirdView布局参数
        holder.mScenesLinkDevicesGv.setColumnWidth(itemWidth);// 列表项宽
        holder.mScenesLinkDevicesGv.setHorizontalSpacing(1);// 列表项水平间距
        holder.mScenesLinkDevicesGv.setStretchMode(GridView.NO_STRETCH);
        holder.mScenesLinkDevicesGv.setNumColumns(10);//总长度

        holder.mScenesLinkDevicesGv.setAdapter(new ScenesControlDevicesGvAdapter(mContext, thisScenesDevices));

    }

    @Override
    public int getItemCount() {
        return mALLScenesList.size();

    }


    class  ScenesViewHold extends RecyclerView.ViewHolder{
        private TextView mScenesNameTv;
        private TextView mNoteTv;

        private RelativeLayout mExecutionRl;
        private ImageView mExecutionIv;
        private ImageView mConditionIv;
        private GridView  mScenesLinkDevicesGv;
        private ImageView mDeleteIv;

        private LinearLayout linearLayout;


        public ScenesViewHold(View itemView) {
            super(itemView);
            mScenesNameTv = (TextView) itemView.findViewById(R.id.scenes_item_name_tv);
            mNoteTv = (TextView) itemView.findViewById(R.id.scenes_state_note_tv);

            mExecutionRl = (RelativeLayout) itemView.findViewById(R.id.scenes_execute_item_rl);
            mExecutionIv = (ImageView) itemView.findViewById(R.id.scenes_execute_item_iv);
            mConditionIv = (ImageView) itemView.findViewById(R.id.scenes_condition_item_iv);
            mScenesLinkDevicesGv = (GridView) itemView.findViewById(R.id.scenes_link_devices_gv);
            mDeleteIv = (ImageView) itemView.findViewById(R.id.scenes_delete_iv);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.scenes_linear_layout);
        }
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
    public boolean getEditState(){
        return  isEditStateFlag;
    }


    public  interface  ScenesControlClickListen{
        void onExecute(View view, int position);
        void onTriggerControl(View view, int position);
        void onEdit(View view, int position);
        void onDelete(View view , int position);
    }


    public ScenesControlClickListen getScenesControlClickListen() {
        return scenesControlClickListen;
    }

    public void setScenesControlClickListen(ScenesControlClickListen scenesControlClickListen) {
        this.scenesControlClickListen = scenesControlClickListen;
    }
}
