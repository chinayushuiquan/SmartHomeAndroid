package kap.com.smarthome.android.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import kap.com.smarthome.android.R;


/**
 * Created by Administrator on 2017/9/22 0022.
 *
 * 创建自定义View用于登录界面中密码登录和短信登录的切换
 *
 * 1.构造方法，实例化类
 * 2.开始测量 measure(int , int) -> onMeasure();
 * 如果当前view是一个ViewGroup,还有义务测量自己的孩子，孩子有建议权
 * 3. 指定位置-layout（）--> onlayout();
 * 指定控件的位置，一般view不用写这个方法，ViewGroup的时候才需要，一般View不需要重写该方法
 * 4. 绘制视图 -- draw() --> onDraw(Canvas)
 * 根据上面两个方法的参数，进行绘制
 */

public class SwitchButton extends View implements View.OnClickListener{

    private Bitmap backgroundBitmap;
    private Bitmap slidingBitmap;

    private int slidMarginLeftMax;
    private Paint mBitMapPaint;
    private Paint mDefaultTextPaint;
    private Paint mSelectTextPaint;

    private int slidingMarginTop;
    private int slidingMarginLeft = 5;
    private int textVerticalLocation;
    private int textLeftMargin = 20;

    //是否选择左边
    private boolean isSelectLeft = true;

    //默认点击事件生效 ，滑动事件不生效
    private boolean isEnableClick = true;

    //横向滑动的X轴起点坐标
    private float startX;
    private float isStartX;

    private String passwordLoginStr;
    private String smsLoginStr;


    private ChangeSelectListener  mChangeSelectListener;

    //判断与之监听的 ViewPager 是否滑动结束 , 只有当滑动结束的时候文字才变化。
    /*private boolean isChangeComplete = false ;
    private boolean isFirstDraw = true;*/

    public SwitchButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.login_select_liner_bg);
        slidingBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.login_selected_text_bg);

        passwordLoginStr = getResources().getString(R.string.password_login);
        smsLoginStr = getResources().getString(R.string.sms_login);

        slidMarginLeftMax = backgroundBitmap.getWidth() - slidingBitmap.getWidth() - 5;
        slidingMarginTop = (backgroundBitmap.getHeight() - slidingBitmap.getHeight()) /2 - 2;
        textVerticalLocation = backgroundBitmap.getHeight()/2+5;

        mBitMapPaint = new Paint();
        mBitMapPaint.setAntiAlias(true);

        mDefaultTextPaint = new Paint();
        mDefaultTextPaint.setAntiAlias(true);
        mDefaultTextPaint.setTextSize(32);
        mDefaultTextPaint.setStyle(Paint.Style.FILL);
        mDefaultTextPaint.setTextAlign(Paint.Align.LEFT);
        mDefaultTextPaint.setColor(Color.WHITE);

        mSelectTextPaint = new Paint();
        mSelectTextPaint.setAntiAlias(true);
        mSelectTextPaint.setTextSize(32);
        mSelectTextPaint.setStyle(Paint.Style.FILL);
        mSelectTextPaint.setTextAlign(Paint.Align.LEFT);
        mSelectTextPaint.setColor(getResources().getColor(R.color.orange));

        setOnClickListener(this);
    }

    /**
     * 测量这个view的大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
    }

    /**
     * 进行绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(backgroundBitmap,0 ,0, mBitMapPaint);
        canvas.drawBitmap(slidingBitmap,slidingMarginLeft,slidingMarginTop, mBitMapPaint);

        drawText(canvas);
    }

    /**
     * 绘制文本
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        if (isSelectLeft) {
            canvas.drawText(passwordLoginStr, textLeftMargin, textVerticalLocation, mSelectTextPaint);
            canvas.drawText(smsLoginStr, slidMarginLeftMax + textLeftMargin, textVerticalLocation, mDefaultTextPaint);
        } else {
            canvas.drawText(passwordLoginStr, textLeftMargin, textVerticalLocation, mDefaultTextPaint);
            canvas.drawText(smsLoginStr, slidMarginLeftMax + textLeftMargin, textVerticalLocation, mSelectTextPaint);
        }
    }


    //实现控件的点击事件
    @Override
    public void onClick(View v) {
        if(isEnableClick) {
            isSelectLeft = !isSelectLeft;
            if (isSelectLeft) {
                slidingMarginLeft = 5;
            } else {
                slidingMarginLeft = slidMarginLeftMax;
            }

            if(mChangeSelectListener != null && isEnableClick) {
                mChangeSelectListener.changeSelect(isSelectLeft);
            }

            invalidate();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //计算按下的坐标
                isStartX = startX = event.getX();
                isEnableClick = true;
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float distanceX = endX - startX;
                slidingMarginLeft += distanceX;
                //屏蔽非法值
                if(slidingMarginLeft > slidMarginLeftMax){
                    slidingMarginLeft = slidMarginLeftMax;
                }else if(slidingMarginLeft < 5){
                    slidingMarginLeft = 5;
                }
                //isChangeComplete = false;
                invalidate();
                //数据还原
                startX = event.getX();

                if(Math.abs(endX - isStartX) > 5){
                    //已经滑动了 就不再执行点击事件
                    isEnableClick = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(!isEnableClick) {
                    if (slidingMarginLeft > slidMarginLeftMax / 2) {
                        slidingMarginLeft = slidMarginLeftMax;
                        isSelectLeft = false;
                    } else {
                        slidingMarginLeft = 5;
                        isSelectLeft = true;
                    }
                    invalidate();
                }
                break;
        }
        return true;
    }



    public boolean isSelectLeft() {
        return isSelectLeft;
    }

    public void setSelectLeft(boolean selectLeft) {
        isSelectLeft = selectLeft;
        if (isSelectLeft) {
            slidingMarginLeft = 5;
        } else {
            slidingMarginLeft = slidMarginLeftMax;
        }
        invalidate();
    }


    public void setSlidingMarginLeft(float scaleX){
        if(scaleX != 0) {
            slidingMarginLeft = (int) (slidMarginLeftMax * scaleX);
            invalidate();
        }
    }

    public   interface   ChangeSelectListener{
       void changeSelect(boolean isSelectLeft);
    }

    public void setChangeSelectListener(ChangeSelectListener changeSelectListener){
        mChangeSelectListener = changeSelectListener;
    }



}


