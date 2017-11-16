package kap.com.smarthome.android.ui.view;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kap.com.smarthome.android.R;


/**
 * Created by Administrator on 2017/8/18 0018.
 */

public class MyTopBarBuilder {
    /**
     * 各个控件
     */
    private RelativeLayout top_rl;

    private ImageView ivLeft;
    private ImageView ivRight;
    private ImageView ivMiddle;

    private TextView tvTitle;
    private TextView tvLeft;
    private TextView tvRight;

    private Activity mContext;
    private View mView;

    public static MyTopBarBuilder builder(Activity context){
        return new MyTopBarBuilder(context);
    }

    public static MyTopBarBuilder builder(View context){
        return new MyTopBarBuilder(context);
    }

    //适应于Activity的构造器
    private MyTopBarBuilder(Activity context) {
        mContext = context;
        top_rl = (RelativeLayout) context.findViewById(R.id.top_relative_layout);
        initTopBar(mContext);
    }

    private MyTopBarBuilder(View context) {
        mView = context;
        top_rl = (RelativeLayout) context.findViewById(R.id.top_relative_layout);
        initTopBar(mView);
    }

    //适合Activity初始化
    private void initTopBar(Activity context) {
        tvLeft = (TextView) context.findViewById(R.id.top_left_tv_1);
        tvTitle = (TextView) context.findViewById(R.id.top_middle_tv_1);
        tvRight = (TextView) context.findViewById(R.id.top_right_tv_1);

        ivLeft = (ImageView) context.findViewById(R.id.top_left_iv_1);
        ivMiddle = (ImageView) context.findViewById(R.id.top_middle_iv_1);
        ivRight = (ImageView) context.findViewById(R.id.top_right_iv_1);
    }


    //提供给Fragment调用
    private void initTopBar(View view) {
        tvLeft = (TextView) view.findViewById(R.id.top_left_tv_1);
        tvTitle = (TextView) view.findViewById(R.id.top_middle_tv_1);
        tvRight = (TextView) view.findViewById(R.id.top_right_tv_1);

        ivLeft = (ImageView) view.findViewById(R.id.top_left_iv_1);
        ivMiddle = (ImageView) view.findViewById(R.id.top_middle_iv_1);
        ivRight = (ImageView) view.findViewById(R.id.top_right_iv_1);
    }


    //设置整个顶部导航栏的背景
    public MyTopBarBuilder setTitleBgRes(int resid) {
        top_rl.setBackgroundResource(resid);
        return this;
    }

    //设置中间标题栏的文字
    public MyTopBarBuilder setTitleText(String text) {
        tvTitle.setText(text);
        return this;
    }

    //设置中间标题栏的文字
    public MyTopBarBuilder setTitleText(int text) {
        if(mContext != null ) {
            tvTitle.setText(mContext.getResources().getString(text));
        }else  if(mView != null){
            tvTitle.setText(mView.getResources().getString(text));
        }
        return this;
    }

    // left
    public MyTopBarBuilder setLeftImage(int resId) {
        ivLeft.setBackgroundResource(resId);
        return this;
    }

    public MyTopBarBuilder setLeftText(String text) {
        tvLeft.setText(text);
        return this;
    }


    public MyTopBarBuilder setLeftText(int text) {
        if(mContext != null ) {
            tvLeft.setText(mContext.getResources().getString(text));
        }else  if(mView != null){
            tvLeft.setText(mView.getResources().getString(text));
        }
        return this;
    }

    public MyTopBarBuilder setLeftImageOnClickListener(View.OnClickListener listener) {
        if (ivLeft.getVisibility() == View.VISIBLE) {
            ivLeft.setOnClickListener(listener);
        }
        return this;
    }


    public MyTopBarBuilder setLeftTextOnClickListener(View.OnClickListener listener) {
        if (tvLeft.getVisibility() == View.VISIBLE) {
            tvLeft.setOnClickListener(listener);
        }
        return this;
    }


    // right
    public MyTopBarBuilder setRightImage(int resId) {
        ivRight.setBackgroundResource(resId);
        return this;
    }

    public MyTopBarBuilder setRightText(String text) {
        tvRight.setText(text);
        return this;
    }

    public MyTopBarBuilder setRightText(int text) {
        if(mContext != null ) {
            tvRight.setText(mContext.getResources().getString(text));
        }else  if(mView != null){
            tvRight.setText(mView.getResources().getString(text));
        }
        return this;
    }


    public MyTopBarBuilder setRightImageOnClickListener(View.OnClickListener listener) {
        if (ivRight.getVisibility() == View.VISIBLE) {
            ivRight.setOnClickListener(listener);
        }
        return this;
    }


    public MyTopBarBuilder setRightTextOnClickListener(View.OnClickListener listener) {
        if (tvRight.getVisibility() == View.VISIBLE) {
            tvRight.setOnClickListener(listener);
        }
        return this;
    }


    public MyTopBarBuilder setTitleColor(int color) {
        if(mContext != null ) {
            tvTitle.setTextColor(mContext.getResources().getColor(color));
        }else  if(mView != null){
            tvTitle.setTextColor(mView.getResources().getColor(color));
        }
        return this;
    }


    public MyTopBarBuilder setRightTextColor(int color) {
        if(mContext != null ) {
            tvRight.setTextColor(mContext.getResources().getColor(color));
        }else  if(mView != null){
            tvRight.setTextColor(mView.getResources().getColor(color));
        }
        return this;
    }

}
