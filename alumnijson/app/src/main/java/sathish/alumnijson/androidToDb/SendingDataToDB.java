package sathish.alumnijson.androidToDb;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sathish.alumnijson.R;
import sathish.alumnijson.Supporting_Files.ProgressDialogUtil;

public class SendingDataToDB extends AppCompatActivity {

    EditText name;
    EditText regno;
    EditText dept;
    Button submit;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_sending_data_to_db);

context = this;
        name = (EditText) findViewById(R.id.editName);
        regno = (EditText) findViewById(R.id.editRegno);
        dept = (EditText) findViewById(R.id.editDept);

        submit = (Button) findViewById(R.id.submit);



        submit.setOnClickListener(new View.OnClickListener() {
            InputStream is = null;
            @Override
            public void onClick(View v) {

                String etname = ""+name.getText().toString();
                String etregno = ""+regno.getText().toString();
                String etdept = ""+dept.getText().toString();


                if(!etname.isEmpty() && !etregno.isEmpty() && !etdept.isEmpty()) {


                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

                    nameValuePairs.add(new BasicNameValuePair("name", etname));
                    nameValuePairs.add(new BasicNameValuePair("regno", etregno));
                    nameValuePairs.add(new BasicNameValuePair("dept", etdept));


                    try {

                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://www.sathishmun.comule.com/login.php");
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();



                        name.setText("");
                        regno.setText("");
                        dept.setText("");


                        String msg = "data enterend successfully";
                        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();


                    } catch (ClientProtocolException e) {

                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    Toast.makeText(getBaseContext(),"Some Fields are Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sending_data_to_db, menu);
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
