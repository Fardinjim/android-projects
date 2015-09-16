package sathish.sjbitalumni.howtouse;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sathish.sjbitalumni.R;

public class HowToUseActivity extends AppCompatActivity {
    LinearLayout content1, content2, content3, content4;
    ImageView img1, img2, img3, img4;
    private android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);



        content1 = (LinearLayout) findViewById(R.id.content1);
        img1 = (ImageView) findViewById(R.id.img1);
        content1.setVisibility(View.GONE);

        content2 = (LinearLayout) findViewById(R.id.content2);
        img2 = (ImageView) findViewById(R.id.img2);
        content2.setVisibility(View.GONE);

        content3 = (LinearLayout) findViewById(R.id.content3);
        img3 = (ImageView) findViewById(R.id.img3);
        content3.setVisibility(View.GONE);

        content4 = (LinearLayout) findViewById(R.id.content4);
        img4 = (ImageView) findViewById(R.id.img4);
        content4.setVisibility(View.GONE);



    }
    /**
     * onClick handler
     */
    public void toggle_contents1(View v){ toggle(content1, img1); }
    public void toggle_contents2(View v){ toggle(content2, img2); }
    public void toggle_contents3(View v){ toggle(content3, img3); }
    public void toggle_contents4(View v){ toggle(content4, img4); }


    public void toggle(LinearLayout content, ImageView img)
    {
        if(content.isShown()){
            Animate.slide_up(this, content);
            content.setVisibility(View.GONE);
            img.setImageResource(R.mipmap.tick_above);
        }
        else{
            content.setVisibility(View.VISIBLE);
            Animate.slide_down(this, content);
            img.setImageResource(R.mipmap.tick_below);
        }
    }

}
