package com.cerkerli.library.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.cerkerli.library.utils.Constants;
import com.cerkerli.library.utils.LLog;

import java.util.ArrayList;

public class TagLayout extends ViewGroup {

    private static final String TAG = TagLayout.class.getSimpleName();

    private static ArrayList<Rect> mChildBounds;
    private static Rect mRect;



    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    private void initView(){
        mChildBounds = new ArrayList<>();
    }

    /**
     * 点击监听
     */
    private Listener.TagLayoutClickListener clickListener;
    public void setOnClickListener(Listener.TagLayoutClickListener clickListener){
        this.clickListener = clickListener;
    }

    /**
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     * 常用api
     * measureChildWithMargins 相当于 LayoutParams + child.measure()
     * child.getMeasuredWidth() child.getMeasuredHeight() 获取子 View 的宽高
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //当前行高
        int LineWidthUsed = 0;
        int LineHeightUsed = 0;
        //总行高
        int HeightUsed = 0;
        int WidthUsed = 0;
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        for(int i = 0;i < getChildCount();i++){
            //获取子 View 以及布局子 View
            View child = getChildAt(i);
            measureChildWithMargins(child,widthMeasureSpec,LineWidthUsed,heightMeasureSpec,HeightUsed);
            if(LineWidthUsed + child.getMeasuredWidth() + Constants.WIDTH_NAP > widthSize &&
                    widthMode != MeasureSpec.UNSPECIFIED){
                //是否需要换行
                WidthUsed = LineWidthUsed;
                LineWidthUsed = 0;
                HeightUsed += LineHeightUsed;
                HeightUsed += Constants.HEIGHT_NAP;
                measureChildWithMargins(child,widthMeasureSpec,LineWidthUsed,heightMeasureSpec,HeightUsed);

                LLog.d(TAG,"换行 at " + i + " WidthUsed " + WidthUsed + " HeightUsed " + HeightUsed);
            }
            //数据写入List中 供Layout使用
            mRect = new Rect();
            mRect.set(LineWidthUsed,HeightUsed,LineWidthUsed + child.getMeasuredWidth(),HeightUsed + child.getMeasuredHeight());
            mChildBounds.add(mRect);

            LLog.d(TAG,"i == "+i);
            LLog.d(TAG,mRect);

            //数据增加
            LineWidthUsed += child.getMeasuredWidth();
            LineWidthUsed += Constants.WIDTH_NAP;
            LineHeightUsed = Math.max(LineHeightUsed,child.getMeasuredHeight());
        }
        HeightUsed+=LineHeightUsed;
        setMeasuredDimension(widthSize,  HeightUsed);

        LLog.d(TAG,"setMeasuredDimension "+ widthSize + ":" + HeightUsed);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for(int i = 0;i < getChildCount();i++){
            final TagView child = (TagView)getChildAt(i);
            Rect childBond = mChildBounds.get(i);
            child.layout(childBond.left,childBond.top,childBond.right,childBond.bottom);

            //设置点击监听
            child.setOnClickListener(new Listener.TagViewClickListener() {
                @Override
                public void onClick(String text,boolean isSelected) {
                    if(clickListener != null){
                        clickListener.onClick(child.getId(),text,isSelected);
                    }
                }
            });
        }
    }


    //重写以支持 measureChildWithMargins 方法
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


}
