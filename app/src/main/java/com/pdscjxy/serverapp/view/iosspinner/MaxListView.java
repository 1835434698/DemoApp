package com.pdscjxy.serverapp.view.iosspinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ListView;

import com.pdscjxy.serverapp.R;


/**
 * Created by dell on 2017/8/28.
 */

public class MaxListView extends ListView {
    private float ratio = -1f;
    public MaxListView(Context context) {
        super(context);
    }

    public MaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FixedRatioImageView);
        ratio = a.getFloat(R.styleable.FixedRatioImageView_ratio, -1f);
        a.recycle();
    }

    public MaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FixedRatioImageView);
        ratio = a.getFloat(R.styleable.FixedRatioImageView_ratio, -1f);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = (int) (((float)widthSize)*ratio);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
