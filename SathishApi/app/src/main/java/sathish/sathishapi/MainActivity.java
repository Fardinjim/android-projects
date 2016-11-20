package sathish.sathishapi;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void fb(View v)
    {
        Intent intent = new Intent(this,FacebookActivity.class);
        startActivity(intent);
    }

    public void google(View v)
    {

        Intent intent = new Intent(this,GoogleActivity.class);
        startActivity(intent);
    }
}
