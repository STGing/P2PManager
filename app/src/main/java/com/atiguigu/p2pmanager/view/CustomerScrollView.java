package com.atiguigu.p2pmanager.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * 自定义的ScrollView，主要用于实现主页面的滑动及回弹效果
 */

public class CustomerScrollView extends ScrollView {

    private float lastY;//记录每次触发onTouchevent事件的Y坐标

    private View childViwe;//当前控件中要移动的View
    private Rect rect = new Rect();//使用rect保存chileView的左上和右下坐标
    private static final int maxDistanceY = 4;//只允许拖动屏幕的1/4
    private int lastX;//记录Down的时候的X
    private int last2Y;//记录拦截事件Down的时候的Y


    public CustomerScrollView(Context context) {
        super(context);
    }

    public CustomerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //如果当前视图中有子View(ScrollView中只允许有一个子View）
        if(getChildCount() > 0) {

            childViwe = getChildAt(0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    /**
     * 对点击事件进行监听，判断滑动的处理。
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //如果视图中子View为空，直接跳过
        if(childViwe == null) {
            return super.onTouchEvent(ev);
        } else {
            //否则进行处理
            processOnTouchEnvent(ev);
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 自定义ScrollView的onTouchEnvent事件的处理，带回弹
     * @param ev
     */
    private void processOnTouchEnvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                //Down的时候，获取Down的Y位置
                lastY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE :
                final float preY = lastY;

                //获取移动的时候的Y的位置
                float nowY = ev.getY();
                /**
                 * maxDistanceY=4 表示 拖动的距离为屏幕的高度的1/4
                 */
//                int deltaY = (int) (preY - nowY) / maxDistanceY;
                int deltaY = (int) (preY - nowY) / 3;
                // 滚动
                // scrollBy(0, deltaY);

                lastY = nowY;
                // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                if (isNeedMove()) {
                    if (rect.isEmpty()) {
                        // 保存正常的布局位置
                        rect.set(childViwe.getLeft(), childViwe.getTop(),
                                childViwe.getRight(), childViwe.getBottom());
                        return;
                    }
                    int yy = childViwe.getTop() - deltaY;

                    // 移动布局
                    childViwe.layout(childViwe.getLeft(), yy, childViwe.getRight(),
                            childViwe.getBottom() - deltaY);
                }
                break;

            case MotionEvent.ACTION_UP :
                if (isNeedAnimation()) {
                    //如果rect中不是空，进行动画.
                    animation();
                }
                break;
        }    
    }

    /**
     * 平移动画
     */
    private void animation() {
        // 开启移动动画
        TranslateAnimation ta = new TranslateAnimation(0, 0, childViwe.getTop(),
                rect.top);
        ta.setDuration(200);
        childViwe.startAnimation(ta);
        // 设置回到正常的布局位置
        childViwe.layout(rect.left, rect.top, rect.right, rect.bottom);
        rect.setEmpty();
    }

    // 是否需要移动布局
    public boolean isNeedMove() {
        int offset = childViwe.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否要开启动画
     */
    public boolean isNeedAnimation() {
        return !rect.isEmpty();
    }

    /**
     * 重写拦截事件：解决和banner的冲突
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //是否拦截
        boolean isOnIntercept = false;

        int eventy = (int) ev.getY();
        int eventx = (int) ev.getX();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                last2Y = eventy;
                lastX = eventx;
                break;
            case MotionEvent.ACTION_MOVE:
                //y轴的偏移量
                int dy = Math.abs(eventy - last2Y);
                //x轴的偏移量
                int dx = Math.abs(eventx - lastX);
                //如果y轴的偏移量大于x轴的偏移量就拦截
                if (dy > dx && dy > 20){
                    isOnIntercept = true;
                }
                lastX = eventx;
                lastY = eventy;
                break;
        }
        return isOnIntercept;
    }
}
