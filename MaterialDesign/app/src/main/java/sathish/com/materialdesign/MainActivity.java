package sathish.com.materialdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private android.support.v7.widget.Toolbar toolbar;

    private static final String TAG_A = "a";
    private static final String TAG_B = "b";
    private static final String TAG_C = "c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2_drawer);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout), toolbar);





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
        a.setImageResource(R.drawable.shop_music);
        ImageView b = new ImageView(this);
        b.setImageResource(R.drawable.shop_dance);
        ImageView c = new ImageView(this);
        c.setImageResource(R.drawable.shop_art);

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
        if(v.getTag().equals(TAG_A))
        {
            Toast.makeText(getApplicationContext(),"am 1", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(getActivity(), Scrolltab.class));
            Intent intent = new Intent(this, Scrolltab.class);
            startActivity(intent);
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
            Toast.makeText(getApplicationContext(),"hey hit settings",Toast.LENGTH_LONG).show();
            return true;
        }
        if(id == R.id.navigate)
        {
            Intent intent = new Intent(this, TabLibrary.class);
            startActivity(intent);
           // Toast.makeText(getApplicationContext(),"You can create a common activity " +
            //        "for this button",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


}
