package sathish.com.materialdesign;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;


public class Fragment extends ActionBarActivity  {

    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_horizontal);
        manager = getFragmentManager();
    }

    public void fragment1(View v)
    {
        FragmentA f1 = new FragmentA();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.group, f1, "A");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    public void fragment2(View v)
    {
        FragmentB f2 = new FragmentB();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.group, f2, "B");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);


        transaction.commit();
    }

    public void remove1(View v)
    {
        FragmentA f1 = (FragmentA) manager.findFragmentByTag("A");
        FragmentTransaction transaction = manager.beginTransaction();

        if(f1 != null)
        {
            transaction.remove(f1);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.commit();
        }

        else
        {
            Toast.makeText(getApplicationContext(),"Fragment1 is empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void remove2(View v)
    {
        FragmentB f2 = (FragmentB) manager.findFragmentByTag("B");
        FragmentTransaction transaction = manager.beginTransaction();

        if(f2 != null)
        {
            transaction.remove(f2);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.commit();
        }

        else
        {
            Toast.makeText(getApplicationContext(),"Fragment2 is empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void remove12(View v)
    {
        FragmentA f1 = (FragmentA) manager.findFragmentByTag("A");
        FragmentB f2 = (FragmentB) manager.findFragmentByTag("B");
        FragmentTransaction transaction = manager.beginTransaction();

        if(f1 != null)
        {
            transaction.remove(f1);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.commit();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Fragments are empty", Toast.LENGTH_SHORT).show();
        }
         if(f2 != null)
        {
            transaction.remove(f2);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.commit();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Fragments are empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void replaceab(View v) {
        FragmentA f1 = new FragmentA();
        FragmentB f2 = new FragmentB();
        FragmentTransaction transaction = manager.beginTransaction();
        FragmentTransactionExtended fm = new FragmentTransactionExtended(this,transaction,f1,f2,R.id.group);
        fm.addTransition(FragmentTransactionExtended.CUBE);
        fm.commit();
       // transaction.replace(R.id.group,f2,"B");
        //transaction.setCustomAnimations(0,0,50,50);
       // transaction.commit();
    }

    public void replaceba(View v) {
        FragmentA f1 = new FragmentA();
        FragmentB f2 = new FragmentB();
        FragmentTransaction transaction = manager.beginTransaction();
        FragmentTransactionExtended fm = new FragmentTransactionExtended(this,transaction,f2,f1,R.id.group);
        fm.addTransition(FragmentTransactionExtended.GLIDE);
        fm.commit();
        //transaction.replace(R.id.group,f1,"A");
       // transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //transaction.commit();
    }


    public static class FragmentA extends android.app.Fragment  //JAVA CLASS FOR FRAGMENT 1
    {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment1, container,false);

            return layout;
        }
    }






    public static class FragmentB extends android.app.Fragment  //JAVA CLASS FOR FRAGMENT 2
    {


        @Override
        public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment2, container,false);

            return layout;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fragment, menu);
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
}
