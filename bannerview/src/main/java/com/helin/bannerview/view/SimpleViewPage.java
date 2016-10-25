package com.helin.bannerview.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.helin.bannerview.adapter.SimplePageAdapter;
import com.helin.bannerview.listener.OnItemClickListener;


/**
 * Created by helin on 2016/10/19 16:23.
 */
public class SimpleViewPage extends ViewPager {
    private OnPageChangeListener mOuterPageChangeListener;
    private SimplePageAdapter mAdapter;
    /**
     * item点击事件
     */
    private OnItemClickListener onItemClickListener;
    /**
     * 是否循环翻页
     */
    private boolean canLoop = true;
    /**
     * 是否可以手动翻页
     */
    private boolean isCanScroll = true;
    private float oldX = 0, newX = 0;
    /**
     * 手指点击的偏移距离
     */
    private static final float sens = 5;
    public SimpleViewPage(Context context) {
        super(context);
        init();
    }

    public SimpleViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 获取最后一个postion
     * @return
     */
    public int getLastItem() {
        return mAdapter.getRealCount() - 1;
    }

    /**
     * 初始化添加PageChange事件
     */
    private void init() {
        super.addOnPageChangeListener(onPageChangeListener);
    }

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private float mPreviousPosition = -1;
        @Override
        public void onPageSelected(int position) {
            //转化为真实的Postiin
            int realPosition = mAdapter.toRealPosition(position);
            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;
                //如果设置了PageChangeListener就调用onPageSelected方法
                if (mOuterPageChangeListener != null) {
                    mOuterPageChangeListener.onPageSelected(realPosition);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            int realPosition = position;
            if (mOuterPageChangeListener != null) {
                //如果postion不是最后一个直接回调
                if (realPosition != mAdapter.getRealCount() - 1) {
                    mOuterPageChangeListener.onPageScrolled(realPosition,
                            positionOffset, positionOffsetPixels);
                } else {
                    if (positionOffset > .5) {
                        mOuterPageChangeListener.onPageScrolled(0, 0, 0);
                    } else {
                        mOuterPageChangeListener.onPageScrolled(realPosition,
                                0, 0);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mOuterPageChangeListener != null) {
                mOuterPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };

    /**
     * 触摸事件
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            if (onItemClickListener != null) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        oldX = ev.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        newX = ev.getX();
                        if (Math.abs(oldX - newX) < sens) {
                            onItemClickListener.onItemClick((getRealItem()));
                        }
                        oldX = 0;
                        newX = 0;
                        break;
                }
            }
            return super.onTouchEvent(ev);
        } else
            return false;
    }

    public boolean isCanScroll() {
        return isCanScroll;
    }

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    /**
     * 事件拦截
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanScroll)
            return super.onInterceptTouchEvent(ev);
        else
            return false;
    }

    /**
     * 设置适配器
     * @param adapter
     * @param canLoop
     */
    public void setAdapter(PagerAdapter adapter, boolean canLoop) {
        mAdapter = (SimplePageAdapter) adapter;
        mAdapter.setCanLoop(canLoop);
        mAdapter.setViewPager(this);
        super.setAdapter(mAdapter);
        //设置当前的Item为页面的数量
        setCurrentItem(getFristItem(), false);
    }

    public boolean isCanLoop() {
        return canLoop;
    }

    /**
     * 设置是否循环翻页
     * @param canLoop
     */
    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        if (!canLoop) {
            setCurrentItem(getRealItem(), false);
        }
        if (mAdapter == null) return;
        mAdapter.setCanLoop(canLoop);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 获取item数量
     * @return
     */
    public int getFristItem() {
        return canLoop ? mAdapter.getRealCount() : 0;
    }

    /**
     * 获取真实的count
     * @return
     */
    public int getRealItem() {
        return mAdapter != null ? mAdapter.toRealPosition(super.getCurrentItem()) : 0;
    }

    /**
     * 添加页面改变监听
     * @param listener
     */
    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        mOuterPageChangeListener = listener;
    }

    /**
     * 设置单击事件
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



}
