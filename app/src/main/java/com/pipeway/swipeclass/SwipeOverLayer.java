package com.pipeway.swipeclass;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Pipeway on 04/08/2017.
 */

public class SwipeOverLayer extends RelativeLayout {

    Context mContext;
    SwipeOverLayer mSelf = this;
    float mDx;
    float mDownX;
    float mCurrentScale;
    float mLastDx = 0.0f;
    boolean mSwipeRight = false;
    boolean mCanMove = false;
    float mMenuWidth;
    float mOverLayerWidth;
    ViewGroup mParent;
    SwipeContainerContent mContainerContent;


    public SwipeOverLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mOverLayerWidth = (float)this.getMeasuredWidth();
    
        final ViewTreeObserver vto = this.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                float tempWidth = 0;
                mParent = (ViewGroup)getParent();
                for(int i=0; i<mParent.getChildCount(); i++){
                    if (mParent.getChildAt(i).getClass().equals(SwipeMenuContainer.class)){
                        tempWidth = ((ViewGroup)getParent()).getChildAt(i).getMeasuredWidth();
                    } else if (mParent.getChildAt(i).getClass().equals(SwipeContainerContent.class)){
                        mContainerContent = (SwipeContainerContent)((ViewGroup)getParent())
                                .getChildAt(i);
                    }
                }
    
                mMenuWidth = tempWidth;
                mSelf.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Captura a referência (downx) para o cálculo de dx assim que o usuário
                        // toca na tela
                        mDownX = motionEvent.getRawX();
                        mCurrentScale = mSelf.getScaleX();
                        if (mParent != null){
                            animateAlpha(mParent, 0.5f, 150);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // Impede que a escala seja maior do que a tela.
                        if (mCurrentScale > 1.0f) mCurrentScale = 1.0f;
                        
                        mDx = motionEvent.getRawX() - mDownX;
                        mOverLayerWidth = mSelf.getMeasuredWidth();
    
                        if (!mSwipeRight && mDx > mLastDx) {
                            // Muda o sentido
                            // Começa ir para a direita
                            // Atualiza a referência (downx) para o cálculo de dx

                            mDownX = motionEvent.getRawX();
                            mCurrentScale = mSelf.getScaleX();
                            mSwipeRight = true;
                            break;
                        } else if (mSwipeRight && mDx < mLastDx) {
                            // Muda o sentido
                            // Começa ir para a esquerda
                            // Atualiza a referência (downx) para o cálculo de dx
    
                            mDownX = motionEvent.getRawX();
                            mCurrentScale = mSelf.getScaleX();
                            mSwipeRight = false;
                            break;
                        }
    
                        // Delay para iniciar o movimento
                        // Evita por exemplo de o usuário estar dando scroll vertical na lista e
                        // o menu começar a ser mostrado, já que ao se fazer o scroll vertical,
                        // não é apenas a coordenada y que varia
                        if (Math.abs(mDx) > mOverLayerWidth*0.1f && !mCanMove){
                            mCanMove = true;
                            mDownX = motionEvent.getRawX();
                            break;
                        }

                        if (mCanMove) {
                            float scaleFactor = mCurrentScale + (mDx / mOverLayerWidth * mCurrentScale);
                            animateScaleX(scaleFactor, 0);
                        }
                        
                        mLastDx = mDx;

                        break;
                    case MotionEvent.ACTION_UP:
                        if (mOverLayerWidth * mSelf.getScaleX() < (mOverLayerWidth - mMenuWidth * 0.4f)){
                            animateScaleX((mOverLayerWidth - mMenuWidth) / mOverLayerWidth, 150);
                        } else if (mOverLayerWidth * mSelf.getScaleX() > (mOverLayerWidth - mMenuWidth * 0.4f)){
                            animateScaleX(1.0f, 150);
                            mCanMove = false;
                        }
                        if (mParent != null){
                            animateAlpha(mParent, 1.0f, 100);
                        }
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }
    
    private void animateScaleX(float scaleFactor, int duration){
        animate()
                .scaleX(scaleFactor)
                .setDuration(duration)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
        
                    }
    
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        mContainerContent.setLayoutParams(new LayoutParams(
                                (int)(mOverLayerWidth * mSelf.getScaleX()),
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ));
                    }
    
                    @Override
                    public void onAnimationCancel(Animator animator) {
        
                    }
    
                    @Override
                    public void onAnimationRepeat(Animator animator) {
        
                    }
                })
                .start();
    }
    
    private void animateAlpha(View view, float alphaFactor, int duration){
        view.animate()
                .alpha(alphaFactor)
                .setDuration(duration)
                .start();
    }
    
    /*@Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for(int i=0; i<mSelf.getChildCount(); i++){
            if (mSelf.getChildAt(i).getClass().equals(SwipeContainerContent.class)){
                mContainerContent = (SwipeContainerContent) mSelf.getChildAt(i);
                break;
            }
        }
    }*/
}
