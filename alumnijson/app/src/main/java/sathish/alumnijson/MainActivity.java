package sathish.alumnijson;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import sathish.alumnijson.alumnijson.AlumniJsonActivity;
import sathish.alumnijson.alumnijsonDB.AlumniJsonUsingDBActivity;
import sathish.alumnijson.androidToDb.SendingDataToDB;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void json(View v) {
        //Toast.makeText(getApplicationContext(),"YOu pressed succesfully", Toast.LENGTH_SHORT).show();
        Intent y = new Intent(getApplicationContext(), AlumniJsonActivity.class);
        startActivity(y);
    }

    public void dataToDB(View v) {
        //Toast.makeText(getApplicationContext(),"YOu pressed succesfully", Toast.LENGTH_SHORT).show();
        Intent y = new Intent(getApplicationContext(), SendingDataToDB.class);
        startActivity(y);
    }

    public void jsonDB(View v) {
        //Toast.makeText(getApplicationContext(),"YOu pressed succesfully", Toast.LENGTH_SHORT).show();
        Intent t = new Intent(getApplicationContext(), AlumniJsonUsingDBActivity.class);
        startActivity(t);

    }
}