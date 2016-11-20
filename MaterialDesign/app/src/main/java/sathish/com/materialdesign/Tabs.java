package sathish.com.materialdesign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import sathish.com.materialdesign.tabs.SlidingTabLayout;



public class Tabs extends ActionBarActivity {

    private android.support.v7.widget.Toolbar toolbar;

    private ViewPager mPager;
    private SlidingTabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,(android.support.v4.widget.DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setViewPager(mPager);



        //Typeface e1 = Typeface.createFromAsset(getAssets(),"Kavoon.otf");
        //TextView event1 = (TextView) findViewById(R.id.event1);
        //event1.setTypeface(e1);

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
            Toast.makeText(getApplicationContext(), "hey hit settings", Toast.LENGTH_LONG).show();
            return true;
        }
        if(id == R.id.navigate)
        {
            Toast.makeText(getApplicationContext(),"You can create a common activity " +
                    "for this button",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
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
        public Fragment getItem(int position) {
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

            return myFragment;
        }

        @Override
        public int getCount() {
            return 3;
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