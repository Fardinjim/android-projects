package mun.sathish.sjbitfest;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class WelcomeSjbit extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_sjbit);

        Thread t = new Thread() {

            public void run() {
                try {
                    Thread.sleep(300);

                    Intent i = new Intent();
                    i.setClassName("mun.sathish.sjbitfest", "mun.sathish.sjbitfest.SecondScreen");
                    startActivity(i);
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
       // Intent intent = new Intent(this, SecondScreen.class);
        //startActivity(intent);
    }



}
