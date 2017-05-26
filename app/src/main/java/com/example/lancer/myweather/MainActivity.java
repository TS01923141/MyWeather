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
/*
    public void fetchData(final String weatherLocation) {


        MyRetrofit.MyDataService myDataService;
        myDataService = MyRetrofit.getmInstance().getAPI();
        Call<JSON_Data> call = myDataService.getData(""+weatherLocation,KEY_API,"metric","3");
        call.enqueue(new Callback<JSON_Data>() {
            @Override
            public void onResponse(Call<JSON_Data> call, final Response<JSON_Data> response) {
                Log.i("connect","Success");



                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if(checkIfDataExists()) {
                            updateData(response);
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
                Log.i("connect","fail:"+ String.valueOf(t));
            }
        });
    }
*/

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
    }

    @Override
    protected void onStart(){
        super.onStart();

        initPresenter();

        //fetchData(location1);

        MyRetrofit.MyDataService myDataService;
        myDataService = MyRetrofit.getmInstance().getAPI();
        Call<JSON_Data> call = myDataService.getData(""+location1,KEY_API,"metric","3");
        call.enqueue(new Callback<JSON_Data>() {
            @Override
            public void onResponse(Call<JSON_Data> call, final Response<JSON_Data> response) {
                Log.i("connect","Success");


                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if(checkIfDataExists()) {
                            updateData(response);
                        } else {
                            initDataObject();
                            Log.i("responseJsonData", String.valueOf(json_data.getList()));
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

            if(getArguments().getInt(ARG_SECTION_NUMBER)==1) {
                View rootView = inflater.inflate(R.layout.fragment_main, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText(location1);
/*
                Log.i("tttttt", String.valueOf(realm.where(JSON_Data.class).findAll()));
                TextView weather = (TextView) rootView.findViewById(R.id.weather);
                //RealmResults<JSON_Data> data = realm.where(JSON_Data.class).equalTo("location",location1).findAll();
                RealmResults<JSON_Data> data = realm.where(JSON_Data.class).equalTo("location",location1).findAll();
                //weather.setText(data.get(0).getList().get(0).getWeather().get(0).getMain());
*/
                //weather.setText(data.getList().get(0).getWeather().get(0).getMain());
                //weather.setText(data.get(0).getList().get(0).getWeather().get(0).getMain());
                /*TextView weather = (TextView) rootView.findViewById(R.id.weather);
                textView.setText(json_data.getList()[0].getWeather()[0].getMain());
                TextView now = (TextView) rootView.findViewById(R.id.now);
                textView.setText((int) json_data.getList()[0].getTemp().getDay());
                TextView max = (TextView) rootView.findViewById(R.id.max);
                textView.setText((int) json_data.getList()[0].getTemp().getMax());
                TextView min = (TextView) rootView.findViewById(R.id.min);
                textView.setText((int) json_data.getList()[0].getTemp().getMin());*/



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

    //新增資料庫檔案
    private void initDataObject(){
        json_data = realm.createObject(JSON_Data.class,location1);
        list = realm.createObject(List.class);
        temp = realm.createObject(Temp.class);
        weather = realm.createObject(Weather.class);
        realmList=new RealmList<List>(list);
        weatherList = new RealmList<Weather>(weather);
        int i;
        for (i=0;i<json_data.getList().size();i++) {
            json_data.setList(json_data.getList());
            json_data.getList().get(i).setTemp(temp);
            json_data.getList().get(i).setWeather(weatherList);
        }
        Log.i("json_data", String.valueOf(json_data));
        Log.i("realmList", String.valueOf(realmList));
        Log.i("list", String.valueOf(list));
        Log.i("temp", String.valueOf(temp));
        Log.i("weather", String.valueOf(weather));
    }

    private boolean checkIfDataExists() {
        return realm.where(JSON_Data.class).equalTo("location", location1).findFirst() != null;
    }
    //更新realm
    private void updateData(Response<JSON_Data> response) {
        realm.copyToRealmOrUpdate(response.body());
    }
    private void initPresenter() {
        presenter = new Presenterful();
        //presenter.setView(this);
    }

}
