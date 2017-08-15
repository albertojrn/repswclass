package com.pipeway.swipeclass;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

/**
 * Created by Pipeway on 09/08/2017.
 */

public class SwipeContainerContent extends RelativeLayout {
    
    SwipeContainerContent mSelf = this;
    SwipeOverLayer mParent;
    
    public SwipeContainerContent(Context context, AttributeSet attrs){
        super(context, attrs);
    
        /*final ViewTreeObserver vto = this.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mParent = (SwipeOverLayer)getParent();
                mSelf.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                
            }
        });*/
    }
    
    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mParent != null) {
            *//*this.setMeasuredDimension((int) (mParent.getMeasuredWidth() * mParent.getScaleX()), heightMeasureSpec);
            this.setScaleX(1.0f);*//*
            for(int i=0; i<mSelf.getChildCount(); i++){
                float scale = mSelf.getChildAt(i).getScaleX();
                mSelf.getChildAt(i).setScaleX(1.0f);
            }
        }
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for(int i=0; i<mSelf.getChildCount(); i++){
            float scale = mSelf.getChildAt(i).getScaleX();
            mSelf.getChildAt(i).setScaleX(1.0f);
        }
    }*/
}
