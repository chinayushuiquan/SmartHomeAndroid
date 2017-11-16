package kap.com.smarthome.android.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import kap.com.smarthome.android.R;

/**
 * Created by yushq on 2017/7/4 0004.
 */

public class SlidingButtonView extends HorizontalScrollView{

    private TextView mTextView_Delete;

    private int mScrollWidth;

    private IonSlidingButtonListener mIonSlidingButtonListener;

    private Boolean isOpen = false;

    private Boolean once = false;



    public SlidingButtonView(Context context) {
        super(context);

    }

    public SlidingButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(!once){
            mTextView_Delete = (TextView) findViewById(R.id.tv_delete);
            once = true;
        }
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed){
            this.scrollTo(0,0);
            //获取水平滚动条可以滑动的范围，即右侧按钮的宽度
            mScrollWidth = mTextView_Delete.getWidth();
        }

    }

    public void setSlidingButtonListener(IonSlidingButtonListener listener){
        mIonSlidingButtonListener = listener;
    }

    public interface IonSlidingButtonListener{
        void onMenuIsOpen(View view);
        void onDownOrMove(SlidingButtonView slidingButtonView);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mIonSlidingButtonListener.onDownOrMove(this);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                changeScrollx();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 按滚动条被拖动距离判断关闭或打开菜单
     */
    public void changeScrollx(){
        if(getScrollX() >= (mScrollWidth/2)){
            this.smoothScrollTo(mScrollWidth, 0);
            isOpen = true;
            mIonSlidingButtonListener.onMenuIsOpen(this);
        }else{
            this.smoothScrollTo(0, 0);
            isOpen = false;
        }
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mTextView_Delete.setTranslationX(l - mScrollWidth);
    }

   public void  closeMenu(){
       this.smoothScrollTo(0, 0);
       isOpen = false;
   }

}
