package com.android.librarytest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.librarytest.adapter.Grid1Adapter;
import com.android.librarytest.adapter.Grid2Adapter;
import com.android.librarytest.adapter.PubuAdapter;
import com.android.librarytest.entity.Book;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/8/22.
 */
public class RecommendeFragment extends Fragment {
    DisplayImageOptions options;//imageloader的设置
    ImageAdapter adapter;//广告viewpager的adapter
    List<ImageView> images;//存储了3个imageview，这三个是轮播的图片

    View v;//fragment-》oncreateView

    /**
     * 操作轮播广告区域
     */

    ViewPager pager;//advertisement pager
    ImageView dot1;
    ImageView dot2;
    ImageView dot3;//three dots
    List<ImageView> dots;



    Grid1Adapter adapter1;
    Grid2Adapter adapter2;
    PubuAdapter adapter3;
    GridView gridView1;//小编推荐
    GridView gridView2;//人气爆炸
    RecyclerView recyclerView;//随机区域的瀑布流recyclerview
    ImageButton button1;
    ImageButton button2;
    ImageButton button3;

    class Myhandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            for (int i = 0; i <= 2; i++) {
                if (i == msg.arg1)
                    dots.get(i).setImageBitmap(selected);
                else dots.get(i).setImageBitmap(unselected);
            }
        }
    }
    class AutoHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int p = msg.arg1%3;
            pager.setCurrentItem(p);
            if(!autoPageStoped) {
                Message message = Message.obtain();
                message.arg1 = p + 1;
                autoHandler.sendMessageDelayed(message,3000);
            }
        }
    }
    boolean autoPageStoped = false;
    AutoHandler autoHandler = new AutoHandler();
    Myhandler myhandler = new Myhandler();
    Bitmap selected;
    Bitmap unselected;

    @Override
    public void onDestroy() {
        super.onDestroy();
        autoHandler = null;
        myhandler = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("APP_TAG4","ON STOP");
        autoPageStoped = true;
    }

    private class GuanggaoHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int index = msg.arg1;
            Toast.makeText(getActivity(), "图片" + (index + 1) + "加载完成", Toast.LENGTH_SHORT).show();
        }
    }

    GuanggaoHandler guanggaoHandler = new GuanggaoHandler();

    @Override
    public void onPause() {
        super.onPause();
        Log.d("APP_TAG4","ON PAUSE");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("APP_TAG4","ON RESUME");
        //Message message = Message.obtain();
        //message.arg1 = 0;
        //autoHandler.sendMessage(message);
    }

    class ImageAdapter extends PagerAdapter {
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(images.get(position));
        }

        @Override
        public int getCount() {
            Log.d("APP_TAG", "getcount");
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(images.get(position));
            Log.d("APP_TAG", "instantiate");
            return images.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            Log.d("APP_TAG", "is from object");
            return view == object;
        }
    }

    /**
     * @param inflater here在进行重要的初始化！！！
     */
    private void init(LayoutInflater inflater) {//initialize th views
        images = new ArrayList<ImageView>();
        pager = (ViewPager) v.findViewById(R.id.guanggao);
        dot1 = (ImageView) v.findViewById(R.id.dot_1);
        dot2 = (ImageView) v.findViewById(R.id.dot_2);
        dot3 = (ImageView) v.findViewById(R.id.dot_3);
        button1 = (ImageButton) v.findViewById(R.id.forMore1);
        button2 = (ImageButton) v.findViewById(R.id.forMore2);
        button3 = (ImageButton) v.findViewById(R.id.forMore3);
        gridView1 = (GridView) v.findViewById(R.id.suggestGridView);
        gridView2 = (GridView) v.findViewById(R.id.baozhaGridView);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        /*
        测试阶段，在gridview中插入一部分数据看看
         */
        List<Book> suggestedBooks = new ArrayList<Book>();
        suggestedBooks.add(new Book("空知英秋","银魂",
                "http://img3.imgtn.bdimg.com/it/u=4267713403,3699394674&fm=206&gp=0.jpg"));
        suggestedBooks.add(new Book("尾田荣一郎","海贼王",
                "http://img3.imgtn.bdimg.com/it/u=3414263902,2423521515&fm=206&gp=0.jpg"));
        suggestedBooks.add(new Book("岸本齐史","火影忍者",
                "http://img5.imgtn.bdimg.com/it/u=3365047629,1765321410&fm=206&gp=0.jpg"));
        suggestedBooks.add(new Book("久保带人","死神",
                "http://img2.imgtn.bdimg.com/it/u=3055310692,38719234&fm=206&gp=0.jpg"));
        suggestedBooks.add(new Book("久保带人","死神",
                "http://img2.imgtn.bdimg.com/it/u=3055310692,38719234&fm=206&gp=0.jpg"));
        suggestedBooks.add(new Book("久保带人","死神",
                "http://img5.imgtn.bdimg.com/it/u=3869914869,82784498&fm=206&gp=0.jpg"));
        adapter1 = new Grid1Adapter(suggestedBooks,getActivity());
        adapter2 = new Grid2Adapter(suggestedBooks,getActivity());
        adapter3 = new PubuAdapter(suggestedBooks);
        gridView1.setAdapter(adapter1);
        gridView2.setAdapter(adapter2);
        recyclerView.setAdapter(adapter3);
        dots = new ArrayList<ImageView>();
        dots.add(dot1);
        dots.add(dot2);
        dots.add(dot3);
        selected = BitmapFactory.decodeResource(getResources(), R.drawable.selected);
        unselected = BitmapFactory.decodeResource(getResources(), R.drawable.unselected);
        Matrix m = new Matrix();
        m.setScale(2.0f, 2.0f);
        selected = Bitmap.createBitmap(selected, 0, 0, selected.getWidth(), selected.getHeight(), m, false);
        unselected = Bitmap.createBitmap(unselected, 0, 0, unselected.getWidth(), unselected.getHeight(), m, false);
        dot1.setImageBitmap(selected);
        dot2.setImageBitmap(unselected);
        dot3.setImageBitmap(unselected);
        for (int i = 0; i <= 2; i++) {
            ImageView image = (ImageView) inflater.inflate(R.layout.guanggaoimage, null);
            images.add(image);
        }
    }

    class ImageListener implements ImageLoadingListener {
        private int getIndexByUri(String url) {
            int index = -1;
            for (int i = 0; i < urls.length; i++) {
                if (urls[i].equals(url))
                    return i;
            }
            return -1;
        }

        @Override
        public void onLoadingStarted(String imageUri, View view) {

        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            int index = getIndexByUri(imageUri);
            images.get(index).setImageBitmap(loadedImage);
            Message msg = Message.obtain();
            msg.arg1 = index;
            guanggaoHandler.sendMessage(msg);
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {

        }
    }

    ImageListener listener = new ImageListener();
    boolean loaded = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.recommended_layout, null);
        init(inflater);
        Log.d("APP_TAG4", "------------->ON CREATE VIEW!");
        adapter = new ImageAdapter();
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % 3;
                Message msg = Message.obtain();
                msg.arg1 = position;
                myhandler.sendMessage(msg);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        options = new DisplayImageOptions.Builder().cacheInMemory(true).build();
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(getActivity());
        ImageLoader.getInstance().init(configuration);
        //ImageLoader.getInstance().loadImage("http://img5.imgtn.bdimg.com/it/u=3869914869,82784498&fm=206&gp=0.jpg",options,new ImageListener());
        loadGuanggao();
        Message message = Message.obtain();
        message.arg1=0;
        autoHandler.sendMessage(message);
        autoPageStoped = false;
        return v;
    }

    //    http://cdn.duitang.com/uploads/item/201402/10/20140210125745_fAZ2A.thumb.600_0.jpeg
    //此处urls用于轮播图片加载
    String[] urls = new String[]{"http://img5.imgtn.bdimg.com/it/u=3869914869,82784498&fm=206&gp=0.jpg",
            "http://cdn.duitang.com/uploads/item/201402/10/20140210125745_fAZ2A.thumb.600_0.jpeg",
            "http://files.18touch.com/uploads/2014/06/172_20140609114120800.jpeg"};

    private void loadGuanggao() {
        ImageLoader.getInstance().loadImage(urls[0], options, listener);
        ImageLoader.getInstance().loadImage(urls[1], options, listener);
        ImageLoader.getInstance().loadImage(urls[2], options, listener);
        Log.d("APP_TAG2", "READY TO DOWNLOAD");
    }
}
