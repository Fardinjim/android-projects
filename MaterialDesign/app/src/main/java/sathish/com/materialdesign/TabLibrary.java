package sathish.com.materialdesign;

import android.support.annotation.Nullable;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class TabLibrary extends ActionBarActivity implements MaterialTabListener{
    private android.support.v7.widget.Toolbar toolbar;
    private MaterialTabHost tabHost;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_library);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
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
        getMenuInflater().inflate(R.menu.menu_tab_library, menu);
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
        viewPager.setCurrentItem(materialTab.getPosition());

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
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            //  MyFragment myFragment = MyFragment.getInstance(position);
            Fragment myFragment = null;
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
            if(position == 3)
            {
                myFragment = new FragmentA();
            }
            if(position == 4)
            {
                myFragment = new FragmentB();
            }
            if(position == 5)
            {
                myFragment = new FragmentC();
            }

            return myFragment;
        }

        @Override
        public int getCount() {
            return 6;
        }
    }









    public static class FragmentA extends Fragment  //JAVA CLASS FOR FRAGMENT 1
    {
        private TextView textView;
        public static FragmentA getInstance(int position)
        {
            FragmentA myFragment = new FragmentA();
            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);
            return myFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment1, container,false);
            textView = (TextView) layout.findViewById(R.id.textView);
            Bundle bundle = getArguments();
            if(bundle != null)
            {
                textView.setText("The Page selected is "+bundle.getInt("position"));
            }
            return layout;
        }
    }






    public static class FragmentB extends Fragment  //JAVA CLASS FOR FRAGMENT 2
    {
        private TextView textView;
        public static FragmentB getInstance(int position)
        {
            FragmentB myFragment = new FragmentB();
            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);
            return myFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment2, container,false);
            textView = (TextView) layout.findViewById(R.id.textView);
            Bundle bundle = getArguments();
            if(bundle != null)
            {
                textView.setText("The Page selected is "+bundle.getInt("position"));
            }
            return layout;
        }
    }







    public static class FragmentC extends Fragment  //JAVA CLASS FOR FRAGMENT 3
    {
        private TextView textView;
        public static FragmentC getInstance(int position)
        {
            FragmentC myFragment = new FragmentC();
            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);
            return myFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment3, container,false);
            textView = (TextView) layout.findViewById(R.id.textView);
            Bundle bundle = getArguments();
            if(bundle != null)
            {
                textView.setText("The Page selected is "+bundle.getInt("position"));
            }
            return layout;
        }
    }
}
