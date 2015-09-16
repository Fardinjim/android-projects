package mun.sathish.sjbitfest;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;


public class V_Dance extends ActionBarActivity {

    FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v__dance);
        manager = getFragmentManager();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_v__dance, menu);
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

    public void dance1(View v)
    {
        FragmentB f2 = new FragmentB();
        FragmentTransaction transaction = manager.beginTransaction();
        FragmentTransactionExtended fm = new FragmentTransactionExtended(this,transaction,null,f2,R.id.dance);
        fm.addTransition(FragmentTransactionExtended.FADE);
        fm.commit();
       // transaction.replace(R.id.dance, f2, "Dance1");
       // transaction.addToBackStack("dance1");
       // transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
       // transaction.commit();
    }

    public void dance2(View v)
    {FragmentB f2 = new FragmentB();
        FragmentC f3 = new FragmentC();
        FragmentTransaction transaction = manager.beginTransaction();
        FragmentTransactionExtended fm = new FragmentTransactionExtended(this,transaction,null,f3,R.id.dance);
        fm.addTransition(FragmentTransactionExtended.STACK);
        fm.commit();
        //transaction.replace(R.id.dance, f3, "Dance2");
        //transaction.addToBackStack("dance2");
        //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //transaction.commit();
    }

    public static class FragmentA extends android.app.Fragment  //JAVA CLASS FOR FRAGMENT 1
    {

        @Override
        public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.v__dance, container,false);

            return layout;
        }


    }






    public static class FragmentB extends android.app.Fragment  //JAVA CLASS FOR FRAGMENT 2
    {

        @Override
        public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragmentc, container,false);

            return layout;
        }
    }







    public static class FragmentC extends android.app.Fragment  //JAVA CLASS FOR FRAGMENT 3
    {

        @Override
        public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragmenta, container,false);

            return layout;
        }
    }

}
