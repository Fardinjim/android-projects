package sathish.com.materialdesign;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Transition1 extends ActionBarActivity implements View.OnClickListener {

    private ViewGroup root;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transition1);

        root = (ViewGroup) findViewById(R.id.container_a);
        button1 = (Button) findViewById(R.id.button_1);
        button2 = (Button) findViewById(R.id.button_2);
        button3 = (Button) findViewById(R.id.button_3);
        button4 = (Button) findViewById(R.id.button_4);
        root.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transition1, menu);
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
    public void onClick(View v) {
/*
        if (Build.VERSION.SDK_INT >= 21) {
            Explode fade = new Explode();
            fade.setDuration(5000);
            TransitionManager.beginDelayedTransition(root, fade);
        }
*/
        Intent intent = new Intent(getApplicationContext(), Transition2.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
/*
        if (Build.VERSION.SDK_INT >= 19) {
            Fade fade = new Fade();
            fade.setDuration(5000);
            TransitionManager.beginDelayedTransition(root, fade);
        }

        toggleVisibility(button1, button2, button3, button4);
*/
    }

    public void toggleVisibility(View... views) {
        for (View current : views) {
            if (current.getVisibility() == View.VISIBLE) {
                current.setVisibility(View.INVISIBLE);
            }
        }
    }
}
