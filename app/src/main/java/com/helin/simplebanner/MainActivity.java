package com.helin.simplebanner;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.helin.simplebanner.holder.SimpleHolder;
import com.helin.simplebanner.holder.SimpleHolderCreator;
import com.helin.simplebanner.view.SimpleBannerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SimpleBannerView viewpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    /**
     * 测试资源图片
     */
    ArrayList<Integer> localImages = new ArrayList<>();

    private void initview() {
        viewpage = (SimpleBannerView) findViewById(R.id.simple_viewpage);

        if(localImages.size()==0){
            localImages.add(R.drawable.test_banner1);
            localImages.add(R.drawable.test_banner2);
            localImages.add(R.drawable.test_banner3);
        }
        viewpage.setPages(
                new SimpleHolderCreator() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages);
//                .startTurning(4000)
//                //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});

    }

    public class LocalImageHolderView implements SimpleHolder<Integer> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
        }
    }
}
