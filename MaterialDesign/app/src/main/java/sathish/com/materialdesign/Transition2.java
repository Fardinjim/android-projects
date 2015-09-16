package sathish.com.materialdesign;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;


public class Transition2 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        setContentView(R.layout.transition2);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
    }


}
