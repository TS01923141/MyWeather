package com.example.lancer.myweather.View;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lancer.myweather.Model.ImageCheck;
import com.example.lancer.myweather.Model.JSON_Data;
import com.example.lancer.myweather.Model.List;
import com.example.lancer.myweather.Model.MyRetrofit;
import com.example.lancer.myweather.Model.Temp;
import com.example.lancer.myweather.Model.Weather;
import com.example.lancer.myweather.Presentation.Presenter;
import com.example.lancer.myweather.Presentation.Presenterful;
import com.example.lancer.myweather.R;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final static String KEY_API = "0899273c10ddbaae0059f020ae69e421";
    private JSON_Data json_data;private Presenter presenter;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    static String location1 = "Taipei";static String location2 = "Tokyo";static String location3 = "NewYork";
    private static Realm realm;
    private List list;private Temp temp;private Weather weather;
    private RealmList<List> realmList;private RealmList<Weather> weatherList;

//實例化retrofit連上webServer抓資料
//透過checkIfDataExists查詢該地區是否有建立資料
//若無，創建新資料
//若有，更新資料
    public void fetchData(final String weatherLocation) {

        MyRetrofit.MyDataService myDataService;
        myDataService = MyRetrofit.getmInstance().getAPI();
        Call<JSON_Data> call = myDataService.getData(""+weatherLocation,KEY_API,"metric","3");
        call.enqueue(new Callback<JSON_Data>() {
            @Override
            public void onResponse(Call<JSON_Data> call, final Response<JSON_Data> response) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if(checkIfDataExists(weatherLocation)) {
                            updateData(response,weatherLocation);
                        } else {
                            initDataObject(weatherLocation);
                            presenter.setData(json_data);
                            presenter.setResponse(response);
                            presenter.setRealmObjectData();
                        }
                    }
                });
            }
            @Override
            public void onFailure(Call<JSON_Data> call, Throwable t) {
            }
        });
    }

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        //初始化並取得Realm實例
        Realm.init(this);
        realm = Realm.getDefaultInstance();

    }

    @Override
    protected void onStart(){
        super.onStart();
        //實例化Presenter並開始抓取資料
        initPresenter();
        fetchData(location1);
        fetchData(location2);
        fetchData(location3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        private ImageCheck imageCheck = new ImageCheck();
        private static final String ARG_SECTION_NUMBER = "section_number";
        public PlaceholderFragment() {
        }
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
        //初始化fragment的Layout
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if(getArguments().getInt(ARG_SECTION_NUMBER)==1) {
                View rootView = inflater.inflate(R.layout.fragment_main, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText(location1);
                return rootView;
            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==2){
                View rootView = inflater.inflate(R.layout.fragment_main, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText(location2);
                return rootView;
            }else{
                View rootView = inflater.inflate(R.layout.fragment_main, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText(location3);
                //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                return rootView;
            }
        }
        //setView:將傳入的資料庫查詢set到fragment的Layout上
        private void setView(RealmResults<JSON_Data> data){
            TextView weather = (TextView) getView().findViewById(R.id.weather);
            weather.setText(getString(R.string.weather)+ data.get(0).getList().get(0).getWeather().get(0).getMain());
            TextView day = (TextView) getView().findViewById(R.id.day);
            day.setText(getString(R.string.Day)+String.valueOf(data.get(0).getList().get(0).getTemp().getDay())+getString(R.string.degree));
            TextView max = (TextView) getView().findViewById(R.id.max);
            max.setText(getString(R.string.Max)+String.valueOf(data.get(0).getList().get(0).getTemp().getMax())+getString(R.string.degree));
            TextView min = (TextView) getView().findViewById(R.id.min);
            min.setText(getString(R.string.Min)+String.valueOf(data.get(0).getList().get(0).getTemp().getMin())+getString(R.string.degree));
            ImageView imageView = (ImageView)getView().findViewById(R.id.imageView);
            imageView.setImageResource(imageCheck.getIconName(data.get(0).getList().get(0).getWeather().get(0).getIcon()));

            TextView subMax1 = (TextView) getView().findViewById(R.id.textView4);
            subMax1.setText(getString(R.string.Max)+String.valueOf(data.get(0).getList().get(1).getTemp().getMax())+getString(R.string.degree));
            TextView subMin1 = (TextView) getView().findViewById(R.id.textView5);
            subMin1.setText(getString(R.string.Min)+String.valueOf(data.get(0).getList().get(1).getTemp().getMin())+getString(R.string.degree));
            ImageView subImageView1 = (ImageView)getView().findViewById(R.id.imageView5);
            subImageView1.setImageResource(imageCheck.getIconName(data.get(0).getList().get(1).getWeather().get(0).getIcon()));

            TextView subMax2 = (TextView) getView().findViewById(R.id.textView6);
            subMax2.setText(getString(R.string.Max)+String.valueOf(data.get(0).getList().get(2).getTemp().getMax())+getString(R.string.degree));
            TextView subMin2 = (TextView) getView().findViewById(R.id.textView7);
            subMin2.setText(getString(R.string.Min)+String.valueOf(data.get(0).getList().get(2).getTemp().getMin())+getString(R.string.degree));
            ImageView subImageView2 = (ImageView)getView().findViewById(R.id.imageView6);
            subImageView2.setImageResource(imageCheck.getIconName(data.get(0).getList().get(2).getWeather().get(0).getIcon()));
        }
        //查詢資料庫並呼叫setView
        @Override
        public void onStart() {
            super.onStart();
            if(getArguments().getInt(ARG_SECTION_NUMBER)==1) {
                RealmResults<JSON_Data> data = realm.where(JSON_Data.class).equalTo("location",location1).findAll();
                if (realm.where(JSON_Data.class).equalTo("location", location1).findFirst() != null) {
                    setView(data);
                }
            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==2){
                RealmResults<JSON_Data> data = realm.where(JSON_Data.class).equalTo("location",location2).findAll();
                if (realm.where(JSON_Data.class).equalTo("location", location2).findFirst() != null) {
                    setView(data);
                }
            }else{
                RealmResults<JSON_Data> data = realm.where(JSON_Data.class).equalTo("location",location3).findAll();
                if (realm.where(JSON_Data.class).equalTo("location", location3).findFirst() != null) {
                    setView(data);
                }
            }
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "地區1";
                case 1:
                    return "地區2";
                case 2:
                    return "地區3";
            }
            return null;
        }
    }

    //初始化資料庫
    private void initDataObject(String weatherLocation){
        json_data = realm.createObject(JSON_Data.class,weatherLocation);
        realmList=new RealmList<List>();

        int j;
        for (j=0;j<3;j++) {
            temp = realm.createObject(Temp.class);
            weather = realm.createObject(Weather.class);
            weatherList = new RealmList<>(weather);
            list = realm.createObject(List.class);
            realmList.add(j,list);
            realmList.get(j).setTemp(temp);
            realmList.get(j).setWeather(weatherList);
        }
        json_data.setList(realmList);
    }
//藉由查詢資料庫資料來判斷資料庫是否有建立該筆資料
    private boolean checkIfDataExists(String weatherLocation) {
        return realm.where(JSON_Data.class).equalTo("location", weatherLocation).findFirst() != null;
    }
    //更新realm
    private void updateData(Response<JSON_Data> response,String weatherLocation) {
        JSON_Data json_data = response.body();
        json_data.setLocation(weatherLocation);
        realm.copyToRealmOrUpdate(json_data);
    }
    private void initPresenter() {
        presenter = new Presenterful();
    }
}
