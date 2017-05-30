package com.example.lancer.myweather;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lancer.myweather.Model.JSON_Data;
import com.example.lancer.myweather.Model.List;
import com.example.lancer.myweather.Model.Temp;
import com.example.lancer.myweather.Model.Weather;
import com.example.lancer.myweather.Presentation.Presenter;
import com.example.lancer.myweather.Presentation.Presenterful;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final static String KEY_API = "0899273c10ddbaae0059f020ae69e421";
    private JSON_Data json_data;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    static String location1 = "Taipei";static String location2 = "Tokyo";static String location3 = "NewYork";
    private static Realm realm;
    private List list;private Temp temp;private Weather weather;
    private RealmList<List> realmList;private RealmList<Weather> weatherList;
    private Presenter presenter;

    public void fetchData(final String weatherLocation) {

        MyRetrofit.MyDataService myDataService;
        myDataService = MyRetrofit.getmInstance().getAPI();
        Call<JSON_Data> call = myDataService.getData(""+weatherLocation,KEY_API,"metric","3");
        call.enqueue(new Callback<JSON_Data>() {
            @Override
            public void onResponse(Call<JSON_Data> call, final Response<JSON_Data> response) {
                Log.i("connect","Success");
                Log.i("RetrofitResponse1", String.valueOf(response.body().getList().get(0).getTemp()));
                Log.i("RetrofitResponse2", String.valueOf(response.body().getList().get(1).getTemp()));
                Log.i("RetrofitResponse3", String.valueOf(response.body().getList().get(2).getTemp()));
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if(checkIfDataExists(weatherLocation)) {
                            updateData(response);
                            Log.i("updatDate", String.valueOf(response));
                        } else {
                            initDataObject(weatherLocation);
                            Log.i("RetrofitJsonData", String.valueOf(json_data));
                            presenter.setData(json_data);
                            presenter.setResponse(response);
                            presenter.setRealmObjectData();
                        }
                    }
                });
            }
            @Override
            public void onFailure(Call<JSON_Data> call, Throwable t) {
                Log.i("connect","fail:"+ String.valueOf(t));
            }
        });
    }


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        Log.i("onCreate","onCreate");
        /*if(!realm.isEmpty()){
            Log.i("RealmIs","Null");
            initPresenter();
            fetchData(location1);
            fetchData(location2);
            fetchData(location3);
            Log.i("CreateFetach", String.valueOf(realm.where(JSON_Data.class).findFirst()));
        }*/
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i("onStart","onStart");
        initPresenter();

        fetchData(location1);
        fetchData(location2);
        fetchData(location3);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Log.i("Fragment","onCreateView");
            if(getArguments().getInt(ARG_SECTION_NUMBER)==1) {
                View rootView = inflater.inflate(R.layout.fragment_main, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText(location1);
                View subView = inflater.inflate(R.layout.recent_weather, container, false);
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
        private void setView(RealmResults<JSON_Data> data){
            TextView weather = (TextView) getView().findViewById(R.id.weather);
            weather.setText(getString(R.string.weather)+ data.get(0).getList().get(0).getWeather().get(0).getMain());
            TextView day = (TextView) getView().findViewById(R.id.day);
            day.setText(getString(R.string.Day)+String.valueOf(data.get(0).getList().get(0).getTemp().getDay())+getString(R.string.degree));
            TextView max = (TextView) getView().findViewById(R.id.max);
            max.setText(getString(R.string.Max)+String.valueOf(data.get(0).getList().get(0).getTemp().getMax())+getString(R.string.degree));
            TextView min = (TextView) getView().findViewById(R.id.min);
            min.setText(getString(R.string.Min)+String.valueOf(data.get(0).getList().get(0).getTemp().getMin())+getString(R.string.degree));


            TextView subMax1 = (TextView) getView().findViewById(R.id.textView4);
            subMax1.setText(getString(R.string.Max)+String.valueOf(data.get(0).getList().get(1).getTemp().getMax())+getString(R.string.degree));
            TextView subMin1 = (TextView) getView().findViewById(R.id.textView5);
            subMin1.setText(getString(R.string.Min)+String.valueOf(data.get(0).getList().get(1).getTemp().getMin())+getString(R.string.degree));

            TextView subMax2 = (TextView) getView().findViewById(R.id.textView6);
            subMax2.setText(getString(R.string.Max)+String.valueOf(data.get(0).getList().get(2).getTemp().getMax())+getString(R.string.degree));
            TextView subMin2 = (TextView) getView().findViewById(R.id.textView7);
            subMin2.setText(getString(R.string.Min)+String.valueOf(data.get(0).getList().get(2).getTemp().getMin())+getString(R.string.degree));
        }
        @Override
        public void onStart() {
            super.onStart();
            Log.i("Fragment","onStart");
            if(getArguments().getInt(ARG_SECTION_NUMBER)==1) {
                Log.i("tttttt", String.valueOf(realm.where(JSON_Data.class).findAll()));
                RealmResults<JSON_Data> data = realm.where(JSON_Data.class).equalTo("location",location1).findAll();
                Log.i("dataaaaaa", String.valueOf(data));
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




    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
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
                    return location1;
                case 1:
                    return location2;
                case 2:
                    return location3;
            }
            return null;
        }
    }

    //初始化資料庫
    private void initDataObject(String weatherLocation){
        json_data = realm.createObject(JSON_Data.class,weatherLocation);
        realmList=new RealmList<List>();
        /*
        //list = realm.createObject(List.class);
        temp = realm.createObject(Temp.class);
        weather = realm.createObject(Weather.class);
        weatherList = new RealmList<Weather>(weather);
        realmList=new RealmList<List>();

        int j;
        for (j=0;j<2;j++) {
            realmList.add(j,list);
        }


        Log.i("realm.size", String.valueOf(realmList.size()));
        json_data.setList(realmList);
        Log.i("json_data.List", String.valueOf(json_data.getList()));
        Log.i("json_data.Size", String.valueOf(json_data.getList().size()));
        int i;
        for (i=0;i<3;i++) {
            json_data.getList().get(i).setTemp(temp);
            json_data.getList().get(i).setWeather(weatherList);
        }
        */
        int j;
        for (j=0;j<3;j++) {
            temp = realm.createObject(Temp.class);
            weather = realm.createObject(Weather.class);
            weatherList = new RealmList<Weather>(weather);
            list = realm.createObject(List.class);
            realmList.add(j,list);
            realmList.get(j).setTemp(temp);
            realmList.get(j).setWeather(weatherList);
        }
        json_data.setList(realmList);
        Log.i("json_data.List2", String.valueOf(json_data.getList()));
        Log.i("json_data", String.valueOf(json_data));
        Log.i("realmList", String.valueOf(realmList));
        Log.i("list", String.valueOf(list));
        Log.i("temp", String.valueOf(temp));
        Log.i("weather", String.valueOf(weather));
        Log.i("temp.day", String.valueOf(temp.getDay()));
    }

    private boolean checkIfDataExists(String weatherLocation) {
        return realm.where(JSON_Data.class).equalTo("location", weatherLocation).findFirst() != null;
    }
    //更新realm
    private void updateData(Response<JSON_Data> response) {
        realm.copyToRealmOrUpdate(response.body());
    }
    private void initPresenter() {
        presenter = new Presenterful();
    }



}
