package com.android.librarytest;

import android.content.Context;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.handmark.pulltorefresh.library.LoadingLayoutProxy;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    String[] strings; /**= new String[]{"http://img3.imgtn.bdimg.com/it/u=4267713403,3699394674&fm=206&gp=0.jpg",
            "http://img4.duitang.com/uploads/item/201505/27/20150527083119_cwXSy.thumb.700_0.png",
    "http://img4.duitang.com/uploads/item/201505/31/20150531003318_hSi5K.jpeg",
    "http://i0.qhimg.com/t0135249f28c59ed150.jpg",
    "http://img5.duitang.com/uploads/item/201603/21/20160321170505_Mzwhf.thumb.700_0.jpeg",
    "http://img5.duitang.com/uploads/item/201603/15/20160315192010_XWsYk.thumb.700_0.jpeg",
    "http://img4.duitang.com/uploads/item/201509/08/20150908084925_YQacV.thumb.700_0.jpeg"};

 */
    int index = 0;


    String address = "10.0.2.2";
    byte[] addresses;
    DisplayImageOptions options;
    final String TIME_CONSUME = "timeconsume";
    long t1;
    long t2;
    final ImageLoadingListener listener = new ImageLoadingListener() {
        @Override
        public void onLoadingStarted(String imageUri, View view) {

        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            imageView.setImageBitmap(loadedImage);
            t2 = System.currentTimeMillis();
            Log.d(TIME_CONSUME,""+(t2-t1));
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {

        }
    };
    final String PIC_URL = "http://img5.imgtn.bdimg.com/it/u=3869914869,82784498&fm=206&gp=0.jpg";
    ImageView imageView;
    Button next;
    Button previous;
    ImageLoaderConfiguration configuration;

    private void Load(){
        t1 = System.currentTimeMillis();
        ImageLoader.getInstance().loadImage(strings[index],options,listener);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next:
                if(index<6) {
                    index++;
                    Load();
                }
                break;
            case R.id.previous:
                if(index>=1){
                    index--;
                    Load();
                }
                Load();
                break;
        }
    }

    class MyTask extends AsyncTask<String,Void,Bitmap>{
        Bitmap result;
        ImageView imageView2;
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url= new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                result = BitmapFactory.decodeStream(connection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView2.setImageBitmap(result);
            t2 = System.currentTimeMillis();
            Log.d(TIME_CONSUME,"normal"+(t2-t1));
        }
    }
    class PicPagesTask extends AsyncTask<Void,Void,Void>{
        String urlS ="http://10.0.2.2:8080/ProvidePic/PicProvider";
        JSONObject object;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Load();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(urlS);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedInputStream inputStream=new BufferedInputStream(connection.getInputStream());
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int len = -1;
                while((len = inputStream.read(buffer))!=-1) {
                    baos.write(buffer,0,len);
                }
                inputStream.close();
                baos.flush();
                baos.close();
                String jsonString = new String(baos.toByteArray());
                List<String> m = null;
                try {
                    object = new JSONObject(jsonString);
                    JSONArray array = object.getJSONArray("pages");
                    m = new ArrayList<String>();
                    for(int i=0;i<=6;i++){
                        m.add(array.getJSONObject(i).getString("uri"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                strings = (String[]) m.toArray(new String[7]);
                Log.d("timeconsume","hhhhh");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private class RefreshAdapter extends BaseAdapter{
        List<Bitmap> contentImages;
        Context context;
        private class ViewHolder{
            ImageView imageView;
        }
        public RefreshAdapter(Context context,List<Bitmap> contentImages) {
            this.context = context;
            this.contentImages = contentImages;
        }

        @Override
        public int getCount() {
            return contentImages.size();
        }

        @Override
        public Object getItem(int position) {
            return contentImages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.listitem,null);
                holder.imageView = (ImageView) convertView.findViewById(R.id.listitem);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.imageView.setImageBitmap(contentImages.get(position));
            return convertView;
        }
    }

    /**
     *
     *
     *
     *
     *
     *
     */


    class MyFragmentAdapter extends FragmentPagerAdapter{
        List<Fragment> fragments;
        public MyFragmentAdapter(FragmentManager fm,List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }




    List<Fragment> fragments;
    private final String[] TITLE = new String[]{"分类","排行","动画化","我的"};
    int currentPage;
    Fragment home;
    Fragment chat;
    Fragment settings;
    public void pageChanging(int page){
        if(currentPage!=page) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(fragments.get(page).isAdded()){
                transaction.hide(fragments.get(currentPage)).show(fragments.get(page)).commit();
                currentPage = page;
            }
            else{
                transaction.hide(fragments.get(currentPage)).add(R.id.fragcontainer,fragments.get(page)).commit();
                currentPage = page;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        fragments = new ArrayList<Fragment>();
        setContentView(R.layout.viewpagerwithindicator);
        home = new HomePageFragment();
        chat = new ChatFragment();
        settings = new SettingsFragment();
        fragments.add(home);
        fragments.add(chat);
        fragments.add(settings);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragcontainer,home);
        transaction.commit();
        currentPage=0;


        /*
        pager = (ViewPager) findViewById(R.id.viewpager);
        indicator = (TabPageIndicator) findViewById(R.id.title);
        fragments = new ArrayList<Fragment>();
        fragments.add(new CollectFragment());
        fragments.add(new SettingsFragment());
        fragments.add(new ThirdFragment());
        MyFragmentAdapter adapter= new MyFragmentAdapter(getSupportFragmentManager(),fragments);
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
        indicator.setCurrentItem(0);
        */
        /*


        new PicPagesTask().execute();
        next = (Button) findViewById(R.id.next);
        previous = (Button)findViewById(R.id.previous);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).showImageOnLoading(R.mipmap.ic_launcher).build();
        configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
        imageView = (ImageView) findViewById(R.id.downloaded);
        //Load();
        */
    }
}
