package com.helin.simplebanner.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.helin.simplebanner.adapter.SimplePageAdapter;

/**
 * Created by helin on 2016/10/19 16:23.
 */
public class SimpleViewPage extends ViewPager {
    private OnPageChangeListener mOuterPageChangeListener;
    private SimplePageAdapter mAdapter;

    public SimpleViewPage(Context context) {
        super(context);
        init();
    }

    public SimpleViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public int getLastItem() {
        return mAdapter.getRealCount() - 1;
    }

    private void init() {
        
    }
    private boolean canLoop = true;
    public void setAdapter(PagerAdapter adapter, boolean canLoop) {
        mAdapter = (SimplePageAdapter) adapter;
        mAdapter.setCanLoop(canLoop);
        mAdapter.setViewPager(this);
        super.setAdapter(mAdapter);

        setCurrentItem(getFristItem(), false);
    }

    public boolean isCanLoop() {
        return canLoop;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        if (canLoop == false) {
            setCurrentItem(getRealItem(), false);
        }
        if (mAdapter == null) return;
        mAdapter.setCanLoop(canLoop);
        mAdapter.notifyDataSetChanged();
    }
    public int getFristItem() {
        return canLoop ? mAdapter.getRealCount() : 0;
    }

    public int getRealItem() {
        return mAdapter != null ? mAdapter.toRealPosition(super.getCurrentItem()) : 0;
    }
    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        super.addOnPageChangeListener(listener);
    }
}
