package com.pipeway.swipeclass;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Pipeway on 08/08/2017.
 */

public class SwipeMenuItem extends android.support.v7.widget.AppCompatImageButton {
    
    private boolean mSquareItem = false;
    private boolean mFitParentHeight = false;
    
    public SwipeMenuItem(Context context, AttributeSet attrs){
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.SwipeMenuItem, 0, 0);
    
        try {
            mSquareItem = a.getBoolean(R.styleable.SwipeMenuItem_squareItem, false);
            mFitParentHeight = a.getBoolean(R.styleable.SwipeMenuItem_fitParentHeight, false);
        } finally {
            a.recycle();
        }
    }
    
    
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        if (mFitParentHeight){
            // MeasureSpec.getSize(heightMeasureSpec) é a altura o pai
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        if (mSquareItem){
            width = height;
        }
        
        this.setMeasuredDimension(width, height);
        
    }
}
