package kap.com.smarthome.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.Scenes;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class MainFragmentScenesGvAdapter extends BaseAdapter {

    private Context mContext;

    //private String[] scencesText = {"回家", "上班" , "睡觉" , "起床"};

    private int[]  scenesDrawabkle ={R.drawable.scenes_go_home , R.drawable.scenes_go_to_work,
                                      R.drawable.scenes_go_to_bed, R.drawable.scenes_get_up};


    private int[] scenesRoundImage = {R.drawable.scenes_round_yellow, R.drawable.scenes_round_blue,
                                      R.drawable.scenes_round_red, R.drawable.scenes_round_green};

    private List<View> mAnimView = new ArrayList<>();


    private Animation  clickAnimation;

    private List<Scenes> mScenesList;


    private MainHomeScenesControlClickListen mainHomeScenesControlClickListen = null;

    public MainFragmentScenesGvAdapter(Context context , List<Scenes> scenesList){
        mScenesList = scenesList;
        mContext = context;
    }


    @Override
    public int getCount() {
        return mScenesList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater  layoutInflater = LayoutInflater.from(mContext);
        final View view = layoutInflater.inflate(R.layout.commuse_scenes_gv_item,null);

        final ImageView iv = (ImageView) view.findViewById(R.id.gv_item_iv);
        TextView tv = (TextView) view.findViewById(R.id.gv_item_tv);

        Scenes scenes = mScenesList.get(position);
        iv.setImageResource(scenesDrawabkle[scenes.getType()]);

        tv.setText(scenes.getSCENE_NAME());

        //点击场景执行点击动画
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mainHomeScenesControlClickListen != null){
                    mainHomeScenesControlClickListen.onExecute(v, position);
                }

                //设置动画
                iv.setImageResource(scenesRoundImage[position]);
                clickAnimation = AnimationUtils.loadAnimation(mContext, R.anim.scenes_round_anim);
                LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
                clickAnimation.setInterpolator(interpolator);
                if (clickAnimation != null) {
                    iv.startAnimation(clickAnimation);//开始动画
                    mAnimView.add(iv);
                }
                //添加动画状态监听
                clickAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        iv.setImageResource(scenesDrawabkle[position]);
                        mAnimView.remove(iv);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
            }
        });
        return view;
    }


    public void  clearPostionAnim(int postion){
           mAnimView.get(postion).clearAnimation();
    }



    public  interface  MainHomeScenesControlClickListen{
        void onExecute(View view, int position);
    }


    public MainHomeScenesControlClickListen getMainHomeScenesControlClickListen() {
        return mainHomeScenesControlClickListen;
    }

    public void setMainHomeScenesControlClickListen(MainHomeScenesControlClickListen mainHomeScenesControlClickListen) {
        this.mainHomeScenesControlClickListen = mainHomeScenesControlClickListen;
    }
}
