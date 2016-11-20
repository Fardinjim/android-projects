package mun.sathish.sjbitfest;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;


public class SecondScreen extends ActionBarActivity implements View.OnClickListener {

    private android.support.v7.widget.Toolbar toolbar;
    private ViewPager mPager;
    private static final String TAG_A = "a";
    private static final String TAG_B = "b";
    private static final String TAG_C = "c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_screen);


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));


        //Typeface e1 = Typeface.createFromAsset(getAssets(),"Kavoon.otf");
        //TextView event1 = (TextView) findViewById(R.id.event1);
        //event1.setTypeface(e1);


        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.ic_fab_star);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.button_action_red_selector)
                .build();


        ImageView a = new ImageView(this);
        a.setImageResource(R.mipmap.ic_launcher);
        ImageView b = new ImageView(this);
        b.setImageResource(R.mipmap.ic_launcher);
        ImageView c = new ImageView(this);
        c.setImageResource(R.mipmap.ic_launcher);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_action_blue_selector));

        SubActionButton buttona = itemBuilder.setContentView(a).build();
        SubActionButton buttonb = itemBuilder.setContentView(b).build();
        SubActionButton buttonc = itemBuilder.setContentView(c).build();

        buttona.setTag(TAG_A);
        buttonb.setTag(TAG_B);
        buttonc.setTag(TAG_C);

        buttona.setOnClickListener(this);
        buttonb.setOnClickListener(this);
        buttonc.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttona)
                .addSubActionView(buttonb)
                .addSubActionView(buttonc)
                .attachTo(actionButton)
                .build();


    }

    @Override
    public void onClick(View v) {
        android.support.v4.app.Fragment myFragment = null;
        if(v.getTag().equals(TAG_A))
        {
            Toast.makeText(getApplicationContext(),"am 1", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(getActivity(), Scrolltab.class));
            //Intent intent = new Intent(this, Scrolltab.class);
            //startActivity(intent);
            myFragment = new FragmentA();

        }

        if(v.getTag().equals(TAG_B))
        {
            Toast.makeText(getApplicationContext(),"am 2", Toast.LENGTH_SHORT).show();
        }

        if(v.getTag().equals(TAG_C))
        {
            Toast.makeText(getApplicationContext(),"am 3", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second_screen, menu);
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
            Toast.makeText(getApplicationContext(),"hey hitted settings",Toast.LENGTH_LONG).show();
            return true;
        }
        if(id == R.id.navigate)
        {
            //Intent intent = new Intent(this, TabLibrary.class);
           // startActivity(intent);
             Toast.makeText(getApplicationContext(),"You can create a common activity " +
                    "for this button",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
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


        @Override
        public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragmenta, container,false);
            TextView event1 = (TextView) layout.findViewById(R.id.textView);
            Typeface e1 = Typeface.createFromAsset(getActivity().getAssets(),"Ceviche.otf");
            event1.setTypeface(e1);
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
        public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragmentc, container,false);
            textView = (TextView) layout.findViewById(R.id.textView);
            Bundle bundle = getArguments();
            if(bundle != null)
            {
                textView.setText("The Page selected is "+bundle.getInt("position"));
            }
            return layout;
        }
    }











    public void varchasva(View v)
    {Thread t = new Thread() {

        public void run() {
            try {
                Thread.sleep(450);

                Intent intent = new Intent(getApplicationContext(), V_Main.class);
                startActivity(intent);
                //  overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
        t.start();

    }






}
