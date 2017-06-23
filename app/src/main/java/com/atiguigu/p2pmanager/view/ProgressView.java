package com.atiguigu.p2pmanager.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.atiguigu.p2pmanager.R;

/**
 * 自定义一个Progress的View
 * 效果：0.一个大的圆
 *      1.最外面有粗边框，蓝色
 *      2.有一个弧度（宽度和边框一致）在开始的时候，不断的增加进度，更新圆里面的文字百分比
 *      3.圆的中心有一个文字
 */

public class ProgressView extends View {

    private int arcPaintColor;//圆弧度的颜色
    private int circlePaintColor;//圆外面描边的颜色
    private Paint paint;
    private int width;//控件本身的宽
    private int height;//控件本身的宽
    private int strokeWidth = 40;//外面描边的宽度;
    private int sweepAngle;//滑动的角度
    private String textString = "90";//圆中间显示的文字
    private int textPaintColor;//文字的颜色

    /**
     * 2个构造器
     * @param context
     */
    public ProgressView(Context context) {
        super(context);
        initPaint();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();

        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        //圆中间显示的文字颜色
        int tColor = typedArray.getColor(R.styleable.ProgressView_textPaintColor, Color.BLACK);
        textPaintColor = tColor;

        //圆描边的颜色
        int paintColor = typedArray.getColor(R.styleable.ProgressView_circlePaintColor,Color.BLUE);
        this.circlePaintColor = paintColor;

        //圆弧度的颜色
        int arcColor = typedArray.getColor(R.styleable.ProgressView_arcPaintColor,Color.RED);
        this.arcPaintColor = arcColor;
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿

        /*
        * 三种样式
        * stroke 描边
        * fill 填充
        * stroke and fill 即填充又描边
        *
        * */
        paint.setStyle(Paint.Style.STROKE);//描边（只画圆的边框，中心不画）
//        paint.setStyle(Paint.Style.FILL);//描边
//        paint.setStyle(Paint.Style.FILL_AND_STROKE);//描边
    }

    /**
     * 测量方法
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //在测量方法中，获取自身所处的宽和高
        width = getWidth();
        height = getHeight();
    }

    /**
     * 绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1.画最外面的圆:
        //设置画笔
        paint.setStrokeWidth(strokeWidth);//描边宽度
        paint.setColor(circlePaintColor);//颜色
        // 需要参数：圆心坐标，半径，画笔
        int cx = width / 2;
        int cy = height / 2;
        //半径:整个View的宽/2 - 外面描边的宽的/2
        int radius = width / 2 - strokeWidth / 2;
        canvas.drawCircle(cx,cy,radius,paint);

        //2.画进度样式（即弧度）
        //设置画笔
        paint.setColor(arcPaintColor);
        //画弧度：参数：Rectf的集合（存放左上和右下坐标），开始度数，结束度数，(未知)默认false，画笔
        RectF rectF = new RectF();
        rectF.set(strokeWidth/2,strokeWidth/2,width-strokeWidth/2,height-strokeWidth/2);
        canvas.drawArc(rectF,0,sweepAngle,false,paint);

        //3.画圆中间的文字
        //设置画笔
        paint.setColor(textPaintColor);
        paint.setStrokeWidth(0);//去掉描边，否则导致文字不显示
        paint.setTextSize(40);
        //画文字：参数：显示的文字，文字左下角的坐标x,y,画笔
        String text = textString + "%";

        Rect rect = new Rect();
        paint.getTextBounds(text,0,text.length(),rect);

        int textWidth = rect.width();//文字的宽
        int textHdeght = rect.height();//文字的高

        //文字左下角的坐标为
        float x = width/2 - textWidth/2;
        float y = height/2 + textHdeght/2;

        canvas.drawText(text,x,y,paint);

    }


    /**
     * 动态的设置弧度的变化值(根据数据)
     */
    public void setSweepAngle(int sweepAngle){
        //使用属性动画，达到逐次变化（渐变）的效果
        ValueAnimator animator = new ValueAnimator().ofInt(0,sweepAngle);
        //设置持续时间
        animator.setDuration(5000);
        //设置变化的监听器
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //这个方法内的数据是不断的变动的
                int sweep = (int) animation.getAnimatedValue();
                ProgressView.this.sweepAngle = sweep;
                invalidate();//重新绘制
//                postInvalidate();//分线程重新绘制
            }
        });

        //最后启动动画
        animator.start();
    }


}
