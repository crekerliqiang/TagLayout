package com.cerkerli.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.cerkerli.library.utils.RandomColor;

import static com.cerkerli.library.utils.Constants.CIRCLE_WIDTH;

public class TagView extends AppCompatTextView {

    /**
     * 绘制文本框
     */
    private Paint mPaint;
    /**
     * 文本框的宽度
     */
    private int widthSize;
    /**
     * 文本框的高度
     */
    private int heightSize;
    /**
     * 文本框的圆弧半径
     */
    private int circleRadius;
    /**
     * 文本框的Rect
     */
    private Rect textRect;


    /**
     * Tag 是否被选中
     */
    private boolean isSelected = false;

    /**
     * 构造方法
     * @param context
     * @param attrs
     */
    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置圆弧背景
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setPaintSelected(false);

        textRect = new Rect();
    }

    /**
     * 选择状态监控
     */
    private Listener.TagViewClickListener clickListener;

    /**
     * set 方法
     * @param clickListener 监听参数
     */
    public void setOnClickListener(Listener.TagViewClickListener clickListener){
        this.clickListener = clickListener;
    }

    /**
     * 触摸反馈方法
     * @param event event
     * @return boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //捕获事件
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
            return true;
        }
        //消费事件
        if(event.getActionMasked() == MotionEvent.ACTION_UP){
            if(!isSelected){
                isSelected = true;
                setPaintSelected(true);

            }else {
                isSelected = false;
                setPaintSelected(false);
            }
            //监听回调
            if(clickListener != null){
                clickListener.onClick(getText().toString(),isSelected);
            }
            invalidate();
        }
        return false;
    }


    /**
     * 设置不同状态的Paint
     * @param isSelected
     */
    private void setPaintSelected(boolean isSelected){
        if(isSelected){
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAlpha(100);
            mPaint.setColor(RandomColor.getColor());
            mPaint.setStrokeWidth(CIRCLE_WIDTH);
        }else{
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setAlpha(0);
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(CIRCLE_WIDTH);
        }
    }


    //重新测量View的width和height
    //强制使用 wrap_content
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //测量文字长度
        getPaint().getTextBounds(getText().toString(), 0, getText().toString().length(), textRect);
        widthSize = textRect.right - textRect.left;
        //测量文字的推荐高度
        //这个问题被卡的比较久，之前一个用的是 textRect的top和bottom
        Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        heightSize = (int)(fontMetrics.bottom - fontMetrics.top);
        //重新测量长和宽
        setMeasuredDimension(widthSize + heightSize * 2,
                heightSize + heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //计算外圆弧的半径
        circleRadius = heightSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(CIRCLE_WIDTH, CIRCLE_WIDTH,
                getWidth() - (CIRCLE_WIDTH << 1),
                getHeight() - (CIRCLE_WIDTH << 1), circleRadius, circleRadius, mPaint);
        canvas.save();
        canvas.translate(heightSize,   heightSize >> 1);
        super.onDraw(canvas);
        canvas.restore();
    }

    /**
     * 是否被选中
     * @return
     */
    public boolean getSelected(){
        return isSelected;
    }

}
