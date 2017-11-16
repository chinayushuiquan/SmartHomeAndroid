package kap.com.smarthome.android.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.ui.allinterface.DevicesFragmentChangeRoomListener;
import kap.com.smarthome.android.ui.utils.ScreenUtils;

/**
 * Created by Administrator on 2017/8/24 0024.
 */
public class MyTopPagerIndicator  extends HorizontalScrollView  {

    private final static String  TAG = "MyTopPagerIndicator";

    private LayoutInflater mLayoutInflater;

    private LinearLayout mTabsLinearLayout;
    private int mTabsCount;

    private ViewPager mContentPager;

    //每一个tabView的布局参数
    private LinearLayout.LayoutParams defaultTabLayoutParams;

    private Rect indicatorRect;
    private Drawable indicator;
    private Drawable indictorBottomLine;


    private final MyPageListener pageListener = new MyPageListener();
    private int currentPosition = 0;
    private float currentPositionOffset = 0f;

    private int lastScrollX = 0;
    private int scrollOffset = 10;
    private int leftI , topI, rightI, bottomI;
    private DevicesFragmentChangeRoomListener mChangeRoomListener;


    private boolean isDrawTextColor = true;
    private boolean isOpenFirst = true;



    private Paint mOrangePaint = null;
    private Paint mGrayPaint = null;

    public MyTopPagerIndicator(Context context) {
        super(context);
        init(context);

    }

    public MyTopPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public MyTopPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }


    private void init(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mTabsLinearLayout = new LinearLayout(context);
        mTabsLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        mTabsLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

        indicatorRect = new Rect();
        indicator = getResources().getDrawable(R.drawable.bg_category_indicator);
        indictorBottomLine = getResources().getDrawable(R.drawable.gray_line_shape);



        defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        setHorizontalScrollBarEnabled(false);
        addView(mTabsLinearLayout);

        /**
         * Test
         */
        mOrangePaint = new Paint(); //设置一个笔刷大小是3的黄色的画笔
        mOrangePaint.setColor(context.getResources().getColor(R.color.orange));
        int paintWidth = ScreenUtils.dipToPx(context,4);
        mOrangePaint.setStrokeWidth(paintWidth);

        mGrayPaint = new Paint();
        mGrayPaint.setColor(context.getResources().getColor(R.color.dividing_line));
        int paintWidth1 = ScreenUtils.dipToPx(context,1);

        mGrayPaint.setStrokeWidth(paintWidth1);
    }


    /**
     * 传入一个ViewPager对象
     * @param pager
     */
    public void setViewPager(ViewPager  pager){
        mContentPager = pager;
        setPageListener();
    }


    /**
     * 给ViewPager对象添加监听
     */
    private void setPageListener() {
        if(mContentPager.getAdapter() == null){
            throw new IllegalStateException("ViewPager does not have adapter instance!");
        }
        mContentPager.setOnPageChangeListener(pageListener);
        initToptabs();
    }

    /**
     * 添加根据传入的pager对象信息 进行顶部导航tabs的添加
     */
    private void  initToptabs(){
        mTabsLinearLayout.removeAllViews();
        mTabsCount = mContentPager.getAdapter().getCount();
        for (int i = 0 ; i < mTabsCount ; i++){
             addTab(i , mContentPager.getAdapter().getPageTitle(i).toString());
        }
    }

    /**
     *
     */
    private void addTab(final int position , String title){
         View tabView = mLayoutInflater.inflate(R.layout.devices_too_tab_item , this, false);
         TextView  tabText = (TextView) tabView.findViewById(R.id.devices_top_tab_tv);
         tabText.setText(title);
         tabView.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 mContentPager.setCurrentItem(position);
             }
         });
        mTabsLinearLayout.addView(tabView , position , defaultTabLayoutParams);
    }



    /**
     * 内部类，ViewPager的监听
     */
    private class MyPageListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            currentPosition = position;
            currentPositionOffset = positionOffset;
            scrollToChild(position, (int) (positionOffset * mTabsLinearLayout.getChildAt(position).getWidth()));
            invalidate();
            //正在滑动，不进行字体颜色的绘制 ，防止字体颜色闪变
            isDrawTextColor = false;
        }

        @Override
        public void onPageSelected(int position) {
            if(mChangeRoomListener != null){
                mChangeRoomListener.changeRoomCallback(position);
            }
            currentPosition = position;
            //活动结束才进行标题颜色的绘制
            isDrawTextColor = true;
            invalidate();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            scrollToChild(mContentPager.getCurrentItem(), 0);
        }
    }

    /**
     * 偏移当前选中的tab到适当的位置
     * @param position
     * @param offset
     */
    private void scrollToChild(int position , int offset){
        if(mTabsCount  == 0){//如果没有子选项就退出
            return;
        }

        calculateIndicatorRect(indicatorRect);

        int newScrollX = lastScrollX;

        if (indicatorRect.left < getScrollX() + scrollOffset) {
            newScrollX = indicatorRect.left - scrollOffset;
        }else if(indicatorRect.right > getScrollX() + getWidth() - scrollOffset){
            newScrollX = indicatorRect.right - getWidth() + scrollOffset;
        }

        if(newScrollX != lastScrollX){
            lastScrollX = newScrollX;
            scrollTo(lastScrollX, 0);
        }
    }

    /**
     * 计算rect的高度和宽度
     * @param rect
     */
    private void calculateIndicatorRect(Rect rect) {

        //计算滑动过程中导航条 矩形Rect的X起始位置和宽度
        View  currentTab  = mTabsLinearLayout.getChildAt(currentPosition);
        TextView  tabTv = (TextView) currentTab.findViewById(R.id.devices_top_tab_tv);

        float left = (float) (currentTab.getLeft() + tabTv.getLeft());
        float width = ((float) tabTv.getWidth()) + left;

        if(currentPositionOffset > 0f  && currentPosition < mTabsCount -1){
            View nextTab = mTabsLinearLayout.getChildAt(currentPosition + 1);
            TextView  nextTabTv= (TextView) currentTab.findViewById(R.id.devices_top_tab_tv);

            float next_left = (float) (nextTab.getLeft() + nextTabTv.getLeft());
            //根据滑动的比例 更新左边的边距
            left = left * (1.0f - currentPositionOffset) + next_left * currentPositionOffset;

            width = width * (1.0f - currentPositionOffset) + currentPositionOffset * (((float) nextTabTv.getWidth()) + next_left);

        }

        //设置Rect的绘制位置
        rect.set(
                ((int) left) + getPaddingLeft(),
                getHeight(),
                ((int) width) + getPaddingLeft(),
                getHeight()
        );


        leftI  = ((int) left) + getPaddingLeft();
        topI = getHeight()-2;
        rightI = ((int) width) + getPaddingLeft();
        bottomI =  topI;
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

         /*if(indictorBottomLine != null){
            indictorBottomLine.setBounds(0,topI,mScreenWidth,bottomI+5);
            indictorBottomLine.draw(canvas);
        }*/

        //划出下面的横线
        canvas.drawLine(0, topI , mTabsLinearLayout.getWidth() , bottomI , mGrayPaint);

        //划出下面的横线
        canvas.drawLine(leftI, topI , rightI , bottomI , mOrangePaint);

        if(isOpenFirst){
            //重新改变字体颜色
            drawTextColor();
        }else{
            if(isDrawTextColor){
                drawTextColor();
            }
        }

    }

    private void drawTextColor() {
        //重新改变字体颜色
        for (int i = 0; i < mTabsCount ; i++ ){
            View tablast = mTabsLinearLayout.getChildAt(i);
            TextView tabTv = (TextView) tablast.findViewById(R.id.devices_top_tab_tv);
            if(i == currentPosition){
                tabTv.setTextColor(getResources().getColor(R.color.orange));
            }else {
                tabTv.setTextColor(getResources().getColor(R.color.text_color));
            }
        }
        isOpenFirst = false;
    }

    /**
     * 设置滑动切换房间的Listener
     */
    public void setChangeRoomListener(DevicesFragmentChangeRoomListener  l){
        mChangeRoomListener = l;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }


    public boolean isOpenFirst() {
        return isOpenFirst;
    }

    public void setOpenFirst(boolean openFirst) {
        isOpenFirst = openFirst;
        invalidate();
    }
}
