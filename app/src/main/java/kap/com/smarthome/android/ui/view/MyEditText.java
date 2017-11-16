package kap.com.smarthome.android.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/8/18 0018.
 */

public class MyEditText extends TextInputEditText implements View.OnTouchListener{

    private Context mContext;
    private int x;
    private int y;
    private Rect rect;

    private Drawable mRightDrawable;

    private Paint mPaint;

    //需要实现下面的几个构造函数，不然有可能加载不了这个EditText控件
    public MyEditText(Context context) {
        super(context);
        init(context);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    //初始化控件，绑定监听器
    public void init(Context  context){
            mContext = context;
            setOnTouchListener(this);
            Drawable[] drawables = getCompoundDrawables();
            mRightDrawable  = drawables[2];
            x = this.getWidth() - this.getPaddingRight() - mRightDrawable.getIntrinsicWidth();
            y = mRightDrawable.getIntrinsicHeight()+2;
            mPaint = new Paint();
            mPaint.setColor(getResources().getColor(android.R.color.white));
            mPaint.setStrokeWidth(10);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //如果不是按下操作，就不做处理，如果是按下操作但是没有图片，也不做处理
        if (event.getAction() == MotionEvent.ACTION_UP && this.getCompoundDrawables()[2] != null) {
            //检测点击区域的X坐标是否在图片范围内
            if (event.getX() > this.getWidth() - this.getPaddingRight() - this.getCompoundDrawables()[2].getIntrinsicWidth()) {
                Toast.makeText(mContext, "获取验证码", Toast.LENGTH_SHORT).show();
                 invalidate();
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("获取验证码",x,y,mPaint);
    }

}
