package kap.com.smarthome.android.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class HorizontalGridView  extends GridView {


    public HorizontalGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public HorizontalGridView(Context context) {
        super(context);
    }

    public HorizontalGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int childWidth = 88;
        int childHeight = 120;
        int lastPadding = 10;
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(expandSpec , heightMeasureSpec);
        //设置GridView的宽度
        setMeasuredDimension(childCount * childWidth + lastPadding, childHeight);
    }


}
