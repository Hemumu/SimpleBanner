package com.helin.bannerview.holder;

import android.content.Context;
import android.view.View;

/**
 * Created by helin on 2016/10/20 11:37.
 */
public interface SimpleHolder<T> {
    /**
     * 创建view
     * @param context
     * @return
     */
    View createView(Context context);

    /**
     * 更新UI
     * @param context
     * @param position
     * @param data
     */
    void UpdateUI(Context context, int position, T data);
}
