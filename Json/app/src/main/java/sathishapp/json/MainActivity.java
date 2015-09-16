package sathishapp.json;

import android.app.DownloadManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import sathishapp.json.network.VolleySingleton;
import static sathishapp.json.Product_info.Keys.*;

public class MainActivity extends ActionBarActivity implements MaterialTabListener{

    private android.support.v7.widget.Toolbar toolbar;
    private MaterialTabHost tabHost;
    private ViewPager viewPager;

//    public static final String URL_JSON = "http://www.json-generator.com/api/json/get/bPFtDOleaa?indent=2";
    public static final String URL_JSON = "http://localhost/json_parson.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);
            }
        });

        for(int i=0; i < adapter.getCount(); i++){
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this));
        }
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {

    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }


    class MyPagerAdapter extends FragmentPagerAdapter  //for tabs
    {
        String[] tabs;
        public MyPagerAdapter(android.support.v4.app.FragmentManager fm) { //fragmentchange
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {//fragmentchange
            //  MyFragment myFragment = MyFragment.getInstance(position);

            android.support.v4.app.Fragment myFragment = null;
            if(position == 0)
            {
                myFragment = new FragmentA();
            }
            if(position == 1)
            {
                myFragment = new FragmentB();
            }
            if(position == 2)
            {
                myFragment = new FragmentC();
            }

            return myFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


    public static class FragmentA extends android.support.v4.app.Fragment  //JAVA CLASS FOR FRAGMENT 1 //fragmentchange
    {
        ImageLoader imageLoader;
        RequestQueue requestQueue;
        VolleySingleton volleySingleton;

        public static String geturlRequest()
        {
            return URL_JSON;
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            volleySingleton = VolleySingleton.getInstance();
            requestQueue = volleySingleton.getmRequestQueue();
            sendJsonRequest();

        }


        private void sendJsonRequest()
        {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, URL_JSON, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    Toast.makeText(getActivity().getApplicationContext(), "RESPONSE\n\n\n\n" +
                            response.toString(), Toast.LENGTH_LONG).show();
                   // parseJSONResponse(response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getActivity().getApplicationContext(), "Error\n\n\n\n" +
                            error.toString(), Toast.LENGTH_LONG).show();
                }
            });
       /*
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_JSON,

                    new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getActivity().getApplicationContext(), "RESPONSE\n\n\n\n" +
                            response.toString(), Toast.LENGTH_LONG).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(), "Error\n\n\n\n" +
                            error.toString(), Toast.LENGTH_LONG).show();

                }
            });  */

            requestQueue.add(req);
        }


        private void parseJSONResponse(JSONArray response)
        {
            if(response == null || response.length()==0)
            {
                return;
            }

            try {       StringBuilder data2 = new StringBuilder();

                StringBuilder data = new StringBuilder();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject curObject = response.getJSONObject(i);
                    String company = curObject.getString(COMPANY);
                    data.append(company + "\n");

             //       StringBuilder data2 = new StringBuilder();
                    JSONArray friends = curObject.getJSONArray(FRIENDS);
                    for (int j=0 ; j < friends.length(); j++)
                    {
                        JSONObject curFrndObj = friends.getJSONObject(j);
                        String name = curFrndObj.getString(NAME);
                        data2.append(name + "\n");
                    }
                }
                Toast.makeText(getActivity().getApplicationContext(), data2, Toast.LENGTH_LONG).show();

            }
            catch (JSONException e)
            {

            }
        }


        @Override
        public View onCreateView(LayoutInflater inflater,  ViewGroup container,
                                 Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragmenta, container, false);


/*
            requestQueue = VolleySingleton.getInstance().getmRequestQueue();


            StringRequest request = new StringRequest(Request.Method.GET, "http://php.net/",
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getActivity().getApplicationContext(), "RESPONSE\n" +
                            response, Toast.LENGTH_LONG).show();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getActivity().getApplicationContext(), "ERROR\n" + error,
                            Toast.LENGTH_LONG).show();

                }
            });
            requestQueue.add(request);
            */

            return layout;
        }
    }






    public static class FragmentB extends android.support.v4.app.Fragment  //JAVA CLASS FOR FRAGMENT 2
    {


        @Override
        public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragmentb, container,false);



            return layout;
        }


    }







    public static class FragmentC extends android.support.v4.app.Fragment  //JAVA CLASS FOR FRAGMENT 3
    {

        public static FragmentC getInstance(int position)
        {
            FragmentC myFragment = new FragmentC();
            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);
            return myFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragmentc, container, false);

            return layout;
        }
    }








}
