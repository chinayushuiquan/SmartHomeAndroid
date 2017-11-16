package kap.com.smarthome.android.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2017/10/26 0026.
 */

public class SetHeightGridView  extends GridView {
    public SetHeightGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SetHeightGridView(Context context) {
        super(context);
    }

    public SetHeightGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
