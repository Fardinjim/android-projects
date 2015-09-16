package sathish.sjbitalumni.addalumni;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sathish.sjbitalumni.AppController;
import sathish.sjbitalumni.R;
import sathish.sjbitalumni.Supporting_Files.ProgressDialogUtil;
import sathish.sjbitalumni.browsealumni.BrowseAlumni;
import sathish.sjbitalumni.dto.BrowseAlumniDTO;

public class AddAlumni extends AppCompatActivity implements OnItemSelectedListener {

    public static final String URL_BASE = "http://www.sathishmun.comule.com/AlumniPHP/readAllJson.php";
    public static final String ERROR_JSON_PARSER = "Error! Please try later.";
    public static final String ERROR_SERVER = "Unable to process your request,please try again later";

    private int LOAD_FROM = 0;
    private final int LOAD_THRESHOLD = 100000;

    private List<BrowseAlumniDTO> itemDetailsList = new ArrayList<>();
    BrowseAlumniDTO details;

    TextView oops_text;
    ImageView oops_image;
    RelativeLayout oops_layout;

    private android.support.v7.widget.Toolbar toolbar;

    EditText name, regno, password, repeat_pass, phone, email;
    TextView year_text, branch_text;
    Spinner year, branch;
    Button signup;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_add_alumni);


        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        oops_text = (TextView) findViewById(R.id.oops_text);
        oops_image = (ImageView) findViewById(R.id.oops_image);
        oops_layout = (RelativeLayout) findViewById(R.id.oops_layout);


        context = this;
        name = (EditText) findViewById(R.id.addalumni_name);
        regno = (EditText) findViewById(R.id.addalumni_registerno);
        password = (EditText) findViewById(R.id.addalumni_password);
        repeat_pass = (EditText) findViewById(R.id.addalumni_repeat_pass);
        phone = (EditText) findViewById(R.id.addalumni_phone);
        email = (EditText) findViewById(R.id.addalumni_email);

        year_text = (TextView) findViewById(R.id.addalumni_year);
        branch_text = (TextView) findViewById(R.id.addalumni_branch);

        year = (Spinner) findViewById(R.id.addalumni_year_spinner);
        branch = (Spinner) findViewById(R.id.addalumni_branch_spinner);

        signup = (Button) findViewById(R.id.addalumni_signup);


        ArrayAdapter yearAdapter=ArrayAdapter.createFromResource(this, R.array.add_year,android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter branchAdapter= ArrayAdapter.createFromResource(this, R.array.add_branch, android.R.layout.simple_spinner_dropdown_item);

        year.setAdapter(yearAdapter);
        branch.setAdapter(branchAdapter);

        populateJson();

        oops_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateJson();

            }
        });

        year.setOnItemSelectedListener(this);
        branch.setOnItemSelectedListener(this);





        signup.setOnClickListener(new View.OnClickListener() {
            InputStream is = null;

            @Override
            public void onClick(View v) {
                Boolean found = false;
                int yr = Integer.parseInt(year.getSelectedItem().toString());
                yr = yr + 4;

                String etname = "" + name.getText().toString().toUpperCase();
                String etregno = "" + "1JB" + year.getSelectedItem().toString() + branch.getSelectedItem().toString().toUpperCase() + regno.getText().toString().toUpperCase();
                String etdept = "" + branch_text.getText().toString().toUpperCase();
                String etyear = "" + year_text.getText().toString();
                String etphone = "" + phone.getText().toString();
                String etpasswd = "" + password.getText().toString();
                String etrepasswd = "" + repeat_pass.getText().toString();
                String etmail = "" + email.getText().toString().toLowerCase();

                etmail = etmail.replace(" ","");

                if (!etname.isEmpty() && !regno.getText().toString().isEmpty() && !etdept.isEmpty() && !etyear.isEmpty() && !etphone.isEmpty()
                        && !etpasswd.isEmpty() && !etrepasswd.isEmpty() && !etmail.isEmpty()  ) {





                        for (int i = 0; i < itemDetailsList.size(); i++) {

                            if (itemDetailsList.get(i).getRegisterNo().toUpperCase().equals(etregno)) {
                                found = true;

                            }
                        }


                    if(!found){
                        if (etpasswd.equals(etrepasswd)) {
                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

                            nameValuePairs.add(new BasicNameValuePair("name", etname));
                            nameValuePairs.add(new BasicNameValuePair("regno", etregno));
                            nameValuePairs.add(new BasicNameValuePair("dept", etdept));
                            nameValuePairs.add(new BasicNameValuePair("year", etyear));
                            nameValuePairs.add(new BasicNameValuePair("phone", etphone));
                            nameValuePairs.add(new BasicNameValuePair("passwd", etpasswd));
                            nameValuePairs.add(new BasicNameValuePair("repasswd", etrepasswd));
                            nameValuePairs.add(new BasicNameValuePair("email", etmail));

                            try {

                                HttpClient httpclient = new DefaultHttpClient();
                                HttpPost httppost = new HttpPost("http://www.sathishmun.comule.com/AlumniPHP/signupHttp.php");
                                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                                HttpResponse response = httpclient.execute(httppost);
                                HttpEntity entity = response.getEntity();
                                is = entity.getContent();


                                String msg = "data enterend successfully";
                                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();



                                Intent intent = new Intent(getApplicationContext(), BrowseAlumni.class);
                                intent.putExtra("signupRegno", etregno);
                                startActivity(intent);
                                finish();


                            } catch (ClientProtocolException e) {

                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getBaseContext(), "Password not matched", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getBaseContext(), "Register No already exists !!!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getBaseContext(), "Some Fields are Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void populateJson() {

        itemDetailsList.clear();

        // Creating volley request obj
        JsonArrayRequest getItemDetailsReq = new JsonArrayRequest(Request.Method.POST,
                URL_BASE    , null,
                new Response.Listener<JSONArray>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(JSONArray response) {
                        oops_layout.setBackgroundColor(getResources().getColor(R.color.Transparent));
                        oops_layout.setClickable(false);
                        oops_text.setText("");
                        oops_image.setImageDrawable(null);
                        try {
                            JSONArray hitsArray = response;

                            //parse all incoming results of the query
                            for (int i = 0; i < hitsArray.length(); i++ ) {

                                JSONObject sourceObj1 = hitsArray.getJSONObject(i);


                                details = new BrowseAlumniDTO();

                                //registr no
                                if (sourceObj1.has("reg_no")) {
                                    details.setRegisterNo(sourceObj1.getString("reg_no"));

                                }

                                itemDetailsList.add(details);

                            } //end for loop

                        } catch (JSONException error) {

                            Toast.makeText(getBaseContext(),ERROR_JSON_PARSER, Toast.LENGTH_SHORT).show();

                            //     loadMore.setText(ERROR_JSON_PARSER);

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                oops_layout.setBackgroundColor(getResources().getColor(R.color.White));
                oops_image.setImageResource(R.mipmap.oops);
                oops_text.setText("There is a connection problem!\n\t\t\t\tPlease try again later!");


            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(getItemDetailsReq);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_alumni, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

           int yr = Integer.parseInt(year.getSelectedItem().toString());
            yr = yr + 4;
            String bran = branch.getSelectedItem().toString().toUpperCase();

            if(bran.equals("CS")) bran="CSE";
            else if(bran.equals("IS")) bran="ISE";
        else if(bran.equals("MEC")) bran = "MECH";
        else if(bran.equals("CIV")) bran="CIVIL";
        else if(bran.equals("EC")) bran="EC";
        else if(bran.equals("EE")) bran="EE";


        year_text.setText("20" + String.valueOf(yr));
        branch_text.setText(bran);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
