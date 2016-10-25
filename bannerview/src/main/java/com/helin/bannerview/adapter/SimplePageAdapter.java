package com.helin.bannerview.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.helin.bannerview.R;
import com.helin.bannerview.holder.SimpleHolder;
import com.helin.bannerview.holder.SimpleHolderCreator;
import com.helin.bannerview.view.SimpleViewPage;

import java.util.List;

/**
 * Created by helin on 2016/10/19 17:20.
 * PagerAdapter
 */
public class SimplePageAdapter<T> extends PagerAdapter {
    /**
     * page数据
     */
    private final List<T> mDatas;
    /**
     * 翻页最大量
     */
    private final int MULTIPLE_COUNT = 300;
    private final SimpleHolderCreator holderCreator;
    /**
     * 是否循环翻页
     */
    private boolean canLoop =true;
    private SimpleViewPage viewPager;

    public SimplePageAdapter(List<T> data, SimpleHolderCreator holderCreator) {
        this.holderCreator=holderCreator;
        mDatas=data;
    }

    /**
     *当所示页面中的更改已完成时调用。 此时，必须确保所有页面都已实际添加或从容器中删除。
     * 根据当前item设置实际的item实现无限循环
     * @param container
     */
    @Override
    public void finishUpdate(ViewGroup container) {
        int position = viewPager.getCurrentItem();
        if (position == 0) {
            position = viewPager.getFristItem();
        } else if (position == getCount() - 1) {
            position = viewPager.getLastItem();
        }
        try {
            viewPager.setCurrentItem(position, false);
        }catch (IllegalStateException e){}
    }


    /**
     * 设置Count为getRealCount()*MULTIPLE_COUNT
     * @return
     */
    @Override
    public int getCount() {
        return canLoop ? getRealCount()*MULTIPLE_COUNT : getRealCount();
    }

    /**
     * 获取实际的翻页数量
     * @return
     */
    public int getRealCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    /**
     * 创建item
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = toRealPosition(position);

        View view = getView(realPosition, null, container);
        container.addView(view);
        return view;
    }

    /**
     * 获取真实的position
     * @param position
     * @return
     */
    public int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0)
            return 0;
        int realPosition = position % realCount;
        return realPosition;
    }
    public View getView(int position, View view, ViewGroup container) {
        SimpleHolder holder = null;
        if (view == null) {
            holder = (SimpleHolder) holderCreator.createHolder();
            view = holder.createView(container.getContext());
            view.setTag(R.id.cb_item_tag, holder);
        } else {
            holder = (SimpleHolder<T>) view.getTag(R.id.cb_item_tag);
        }
        if (mDatas != null && !mDatas.isEmpty())
            holder.UpdateUI(container.getContext(), position, mDatas.get(position));
        return view;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    public void setViewPager(SimpleViewPage viewPager) {
        this.viewPager = viewPager;
    }

    /**
     * 销毁item
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
