package com.helin.simplebanner.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.helin.simplebanner.R;
import com.helin.simplebanner.adapter.SimplePageAdapter;
import com.helin.simplebanner.holder.SimpleHolderCreator;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by helin on 2016/10/19 11:30.
 */

public class SimpleBannerView<T> extends LinearLayout{
    private ViewPagerScroller scroller;
    private SimpleViewPage viewPager;
    /**
     * 翻页指示点
     */
    private ViewGroup loPageTurningPoint;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private SimplePageAdapter mAdapter;
    private List<T> mDatas;

    public SimpleBannerView(Context context) {
        super(context);
        init(context);
    }

    public SimpleBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SimpleBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View hView = LayoutInflater.from(context).inflate(
        R.layout.include_viewpage, this, true);
        viewPager = (SimpleViewPage) hView.findViewById(R.id.cbLoopViewPager);
        loPageTurningPoint = (ViewGroup) hView
                .findViewById(R.id.loPageTurningPoint);

        initViewPagerScroll();
    }

    /**
     * 设置翻页监听器
     * @param onPageChangeListener
     * @return
     */
    public SimpleBannerView addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
        //如果有默认的监听器（即是使用了默认的翻页指示器）则把用户设置的依附到默认的上面，否则就直接设置
//        if(pageChangeListener != null)pageChangeListener.setOnPageChangeListener(onPageChangeListener);
//        else viewPager.setOnPageChangeListener(onPageChangeListener);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        return this;
    }


    private boolean canLoop = true;

    /**
     * 设置翻页数据
     * @param datas
     * @return
     */
    public SimpleBannerView setPages(SimpleHolderCreator creator, List<T> datas ){
        this.mDatas = datas;
        mAdapter = new SimplePageAdapter<>(mDatas,creator);
        viewPager.setAdapter(mAdapter,canLoop);
        return  this;
    }


    /**
     * 通过java反射设置ViewPager的滑动速度
     * */
    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            scroller = new ViewPagerScroller(
                    viewPager.getContext());
            mScroller.set(viewPager, scroller);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        viewPager.setCanLoop(canLoop);
    }

}
