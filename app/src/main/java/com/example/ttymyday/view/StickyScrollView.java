package com.example.ttymyday.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by zhaoshaohe on 2019.4.1
 */
@Deprecated
public class StickyScrollView extends ScrollView {
    public static final String TAG = StickyScrollView.class.getSimpleName();
    public static final int PAGE_TOP = 0;
    public static final int PAGE_BOTTOM = 1;
    public static final double PERCENT = 0.4;
    public static final int ANIMATION_DURATION = 180;
    public static final int TOUCH_DURATION = 150;

    private ViewGroup mChildLayout;
    private View mTopChildView;

    private Context mContext;
    private OnPageChangeListener onPageChangeListener;

    private boolean isScrollAuto;           //判断是否自由滚动
    private Scroller mScroller;             //滑动类
    private int screenHeight;               //屏幕高度
    private int offsetDistance;             //topview的高度与屏幕的高度差
    private int topChildHeight;             //topview的高度
    private boolean isTouch;                //用户是否在触控屏幕
    private int currentPage;                //值为0时屏幕显示topview，值为1时屏幕显示bottomview
    private long downTime;                  //用户按下屏幕的时间戳
    private long upTime;                    //用户抬起时的时间戳
    private int downY;                      //用户按下屏幕的y坐标
    private int upY;                        //用户抬起的y坐标
    private boolean isPageChange;           //页面是否切换

    public StickyScrollView(Context context) {
        super(context);
        this.mContext = context;
    }

    public StickyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public StickyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        mChildLayout = (ViewGroup) getChildAt(0);
        mTopChildView = mChildLayout.getChildAt(0);
        topChildHeight = mTopChildView.getMeasuredHeight();
        screenHeight = getMeasuredHeight();
        offsetDistance = topChildHeight - screenHeight;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                downY = (int) ev.getY();
                downTime = System.currentTimeMillis();
                if (mScroller != null) {
                    mScroller.forceFinished(true);
                    mScroller = null;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                upY = (int) ev.getY();
                upTime = System.currentTimeMillis();
                //用户手指在屏幕上的时间
                long duration = upTime - downTime;

                //这里要确保点击事件不失效
                if (Math.abs(upY - downY) > 50) {
                    Log.e(TAG, ">>>ISN_T CLICK:" + Math.abs(upY - downY));
                    if (currentPage == PAGE_TOP) {
                        //下面的判断已经能确定用户是否往上滑
                        if (getScrollY() > offsetDistance) {
                            mScroller = new Scroller(mContext);
                            if (getScrollY() < (screenHeight * PERCENT + offsetDistance) && duration > TOUCH_DURATION) {
                                isPageChange = false;
                                scrollToTarget(PAGE_TOP);
                            } else {
                                //切换到下界面
                                isPageChange = true;
                                isScrollAuto = duration < TOUCH_DURATION ? true : false;
                                scrollToTarget(PAGE_BOTTOM);
                                currentPage = PAGE_BOTTOM;
                            }
                            return false;
                        }
                    } else {
                        if (getScrollY() < topChildHeight) {
                            mScroller = new Scroller(mContext);
                            if (getScrollY() < (topChildHeight - screenHeight * PERCENT) || duration < TOUCH_DURATION) {
                                //切换到上界面
                                isPageChange = true;
                                isScrollAuto = duration < TOUCH_DURATION ? true : false;
                                scrollToTarget(PAGE_TOP);
                                currentPage = PAGE_TOP;
                            } else {
                                isPageChange = false;
                                scrollToTarget(PAGE_BOTTOM);
                            }
                            return false;
                        }
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 滚动到指定位置
     */
    private void scrollToTarget(int currentPage) {
        int delta;
        if (currentPage == PAGE_TOP) {
            delta = getScrollY() - offsetDistance;
            mScroller.startScroll(0, getScrollY(), 0, -delta, isScrollAuto == true ? ANIMATION_DURATION : (int) (Math.abs(delta) * 0.4));
        } else if (currentPage == PAGE_BOTTOM) {
            delta = getScrollY() - topChildHeight;
            mScroller.startScroll(0, getScrollY(), 0, -delta, isScrollAuto == true ? ANIMATION_DURATION : (int) (Math.abs(delta) * 0.4));
        }
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        // 调用startScroll的时候scroller.computeScrollOffset()返回true
        super.computeScroll();
        if (mScroller == null) {
            return;
        }
        if (mScroller.computeScrollOffset()) {
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
            if (mScroller.isFinished()) {
                mScroller = null;
                if (onPageChangeListener != null && isPageChange) onPageChangeListener.OnPageChange(currentPage);
            }
        }
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //滚动时的监听,当用户触屏滑动时不监听，t == getScrollY
        if (currentPage == PAGE_TOP) {
            if (getScrollY() > offsetDistance && !isTouch) {
                if (mScroller == null) {
                    //用于控制当滑动到分界线时停止滚动
                    scrollTo(0, offsetDistance);
                } else {
                    scrollToTarget(PAGE_TOP);
                }
            }
        } else if (currentPage == PAGE_BOTTOM) {
            if (getScrollY() < topChildHeight && !isTouch) {
                if (mScroller == null) {
                    scrollTo(0, topChildHeight);
                } else {
                    scrollToTarget(PAGE_BOTTOM);
                }
            }
        }
    }

    /**
     * 切换页面完成后的回调
     */
    public interface OnPageChangeListener {
        void OnPageChange(int currentPage);
    }

}