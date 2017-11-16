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
import kap.com.smarthome.android.ui.allinterface.ThreeSwitchButtonListen;

/**
 * Created by yushq on 2017/10/27 0027.
 */

public class ThreeSwitchButton  extends View {


    private Bitmap backgroundBitmap;
    private Bitmap slidingBitmap;

    //第一个按钮偏移的x值
    private int firstSlidMarginLeft;

    //第二个按钮偏移的x值
    private int secondSlidMarginLeft;

    //第三个按钮偏移的x值
    private int threeSlidMarginLeft;

    //画笔
    private Paint mBitMapPaint;
    private Paint mDefaultTextPaint;
    private Paint mSelectTextPaint;

    //白色按钮距离顶部
    private int slidingMarginTop;

    //滑块距离左边距  默认为5
    private int slidingMarginLeft = 5;

    //文字距离上边距
    private int textVerticalLocation;

    private int textLeftMargin = 10;

    //默认选择第一个
    private  int  selectPosition = 1;

    //横向滑动的X轴起点坐标
    private float startX;
    private float isStartX;

    private String firstStr;

    private String sconedStr;

    private String threeStr;


    private ThreeSwitchButtonListen mChangeSelectListener;



    public ThreeSwitchButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){

        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.three_switch_bg);
        slidingBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.three_switch_check);

        firstStr = getResources().getString(R.string.open_string);
        sconedStr = getResources().getString(R.string.stop_string);
        threeStr = getResources().getString(R.string.close_string);

        firstSlidMarginLeft = slidingMarginLeft;


        secondSlidMarginLeft = (backgroundBitmap.getWidth()-slidingBitmap.getWidth())/2;


        threeSlidMarginLeft = backgroundBitmap.getWidth() - slidingBitmap.getWidth()-5;


        slidingMarginTop = (backgroundBitmap.getHeight() - slidingBitmap.getHeight()) /2;


        textVerticalLocation = backgroundBitmap.getHeight()/2+10;

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

        canvas.drawBitmap(slidingBitmap, slidingMarginLeft, slidingMarginTop , mBitMapPaint);

        drawText(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


    /**
     * 绘制文本
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        if (selectPosition == 1) {
            canvas.drawText(firstStr, textLeftMargin, textVerticalLocation, mSelectTextPaint);
            canvas.drawText(sconedStr, secondSlidMarginLeft + textLeftMargin, textVerticalLocation, mDefaultTextPaint);
            canvas.drawText(threeStr, threeSlidMarginLeft + textLeftMargin, textVerticalLocation, mDefaultTextPaint);
        } else if(selectPosition == 2) {
            canvas.drawText(firstStr, textLeftMargin, textVerticalLocation, mDefaultTextPaint);
            canvas.drawText(sconedStr, secondSlidMarginLeft + textLeftMargin, textVerticalLocation, mSelectTextPaint);
            canvas.drawText(threeStr, threeSlidMarginLeft + textLeftMargin, textVerticalLocation, mDefaultTextPaint);
        }else {
            canvas.drawText(firstStr, textLeftMargin, textVerticalLocation, mDefaultTextPaint);
            canvas.drawText(sconedStr, secondSlidMarginLeft + textLeftMargin, textVerticalLocation, mDefaultTextPaint);
            canvas.drawText(threeStr, threeSlidMarginLeft + textLeftMargin, textVerticalLocation, mSelectTextPaint);
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                //计算按下的坐标
                isStartX = startX = event.getX();

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                if( 0 < startX && startX < secondSlidMarginLeft){

                    slidingMarginLeft = firstSlidMarginLeft;

                    selectPosition = 1;
                    if(mChangeSelectListener != null){
                        mChangeSelectListener.firstTextClick();
                    }

                }else if(secondSlidMarginLeft < startX && startX < threeSlidMarginLeft ){

                    slidingMarginLeft = secondSlidMarginLeft;
                    selectPosition = 2;
                    if(mChangeSelectListener != null){
                        mChangeSelectListener.secondTextClick();
                    }

                }else if( threeSlidMarginLeft < startX) {
                    slidingMarginLeft = threeSlidMarginLeft;
                    selectPosition = 3;
                    if(mChangeSelectListener != null){
                        mChangeSelectListener.threeTextClick();
                    }
                }

                invalidate();

                break;
        }
        return true;
    }




    public void setChangeSelectListener(ThreeSwitchButtonListen changeSelectListener){
        mChangeSelectListener = changeSelectListener;
    }


    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        if(selectPosition == 1){
            slidingMarginLeft = firstSlidMarginLeft;
        }else if(selectPosition == 2){
            slidingMarginLeft = secondSlidMarginLeft;
        }else{
            slidingMarginLeft = threeSlidMarginLeft;
        }
        invalidate();
    }
}