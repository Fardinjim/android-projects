package sathish.sjbitalumni;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import sathish.sjbitalumni.addalumni.AddAlumni;
import sathish.sjbitalumni.browsealumni.BrowseAlumni;
import sathish.sjbitalumni.developer.DeveloperActivity;
import sathish.sjbitalumni.howtouse.HowToUseActivity;
import sathish.sjbitalumni.login.LoginActivity;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent t = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(t);
        finish();


    }


}
