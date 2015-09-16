package sathish.sjbitalumni.updatealumni;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class UpdateAlumni extends ActionBarActivity {

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

    EditText name, oldPassword, newPass, phone, email;
    TextView year_text, branch_text, regno;
    Button update;
    Context context;

    String browseAlumniRegnologin;
    String browseAlumniRegnosignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_update_alumni);

        Bundle bundle = getIntent().getExtras();
        browseAlumniRegnologin = bundle.getString("browseAlumniRegnologin");
        browseAlumniRegnosignup = bundle.getString("browseAlumniRegnosignup");




        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


        oops_text = (TextView) findViewById(R.id.oops_text);
        oops_image = (ImageView) findViewById(R.id.oops_image);
        oops_layout = (RelativeLayout) findViewById(R.id.oops_layout);


        context = this;
        name = (EditText) findViewById(R.id.updatealumni_name);
        oldPassword = (EditText) findViewById(R.id.updatealumni__old_password);
        newPass = (EditText) findViewById(R.id.updatealumni_new_pass);
        phone = (EditText) findViewById(R.id.updatealumni_phone);
        email = (EditText) findViewById(R.id.updatealumni_email);

        year_text = (TextView) findViewById(R.id.updatealumni_year);
        branch_text = (TextView) findViewById(R.id.updatealumni_branch);
        regno = (TextView) findViewById(R.id.updatealumni_registerno);


        update = (Button) findViewById(R.id.updatealumni_update);

        populateJson();


        oops_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateJson();

            }
        });






        update.setOnClickListener(new View.OnClickListener() {
            InputStream is = null;

            @Override
            public void onClick(View v) {
                Boolean found = false;
                Boolean passKeySate = false;
                String passKey="";

                String etname = "" + name.getText().toString().toUpperCase();
                String etregno = "" + regno.getText().toString().toUpperCase();
                String etdept = "" + branch_text.getText().toString().toUpperCase();
                String etyear = "" + year_text.getText().toString();
                String etphone = "" + phone.getText().toString();
                String etoldpasswd = "" + oldPassword.getText().toString();
                String etnewpasswd = "" + newPass.getText().toString();
                String etmail = "" + email.getText().toString().toLowerCase();

                etmail = etmail.replace(" ", "");

                if (!etname.isEmpty() && !regno.getText().toString().isEmpty() && !etdept.isEmpty() && !etyear.isEmpty() && !etphone.isEmpty()
                        && !etmail.isEmpty()) {

                    if (!etoldpasswd.isEmpty() && !etnewpasswd.isEmpty())
                    {
                        passKeySate = true;
                        for (int i = 0; i < itemDetailsList.size(); i++) {

                            if (itemDetailsList.get(i).getRegisterNo().equals(etregno) &&
                                    itemDetailsList.get(i).getPassword().equals(etoldpasswd) ) {
                                found = true;

                            }
                        }
                    }
                    else if(etoldpasswd.isEmpty() && etnewpasswd.isEmpty())
                    {

                        for (int i = 0; i < itemDetailsList.size(); i++) {

                            if (itemDetailsList.get(i).getRegisterNo().equals(etregno) ) {
                                passKey = itemDetailsList.get(i).getPassword();
                            }
                        }
                        found = true;
                    }
                    else{ found = false; }






                        if (found) {


                            String finalPassKey;
                            if(passKeySate){
                                finalPassKey = etnewpasswd;
                            }else{
                                finalPassKey = passKey;
                            }

                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

                            nameValuePairs.add(new BasicNameValuePair("name", etname));
                            nameValuePairs.add(new BasicNameValuePair("regno", etregno));
//                            nameValuePairs.add(new BasicNameValuePair("dept", etdept));
//                            nameValuePairs.add(new BasicNameValuePair("year", etyear));
                            nameValuePairs.add(new BasicNameValuePair("phone", etphone));
                            nameValuePairs.add(new BasicNameValuePair("passwd", finalPassKey));
//                            nameValuePairs.add(new BasicNameValuePair("repasswd", etrepasswd));
                            nameValuePairs.add(new BasicNameValuePair("email", etmail));

                            try {

                                HttpClient httpclient = new DefaultHttpClient();
                                HttpPost httppost = new HttpPost("http://www.sathishmun.comule.com/AlumniPHP/updateHttp.php");
                                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                                HttpResponse response = httpclient.execute(httppost);
                                HttpEntity entity = response.getEntity();
                                is = entity.getContent();





                                String msg = "Updated successfully";
                                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();



                                finish();


                            } catch (ClientProtocolException e) {

                                e.printStackTrace();


                            } catch (IOException e) {
                                e.printStackTrace();
                                ProgressDialogUtil.hidePDialog();

                            }
                    } else {
                        Toast.makeText(getBaseContext(), "Password error !!!", Toast.LENGTH_SHORT).show();
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
                URL_BASE  , null,
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

                                //name
                                if (sourceObj1.has("name")) {
                                    details.setName(sourceObj1.getString("name"));
                                 }
                                //phone
                                if (sourceObj1.has("phone")) {
                                    details.setPhone(sourceObj1.getString("phone"));
                                }
                                //email
                                if (sourceObj1.has("email")) {
                                    details.setEmail(sourceObj1.getString("email"));
                                }
                                //registr no
                                if (sourceObj1.has("reg_no")) {
                                    details.setRegisterNo(sourceObj1.getString("reg_no"));
                                }
                                //password
                                if (sourceObj1.has("passwd")) {
                                    details.setPassword(sourceObj1.getString("passwd"));
                                }
                                //dept
                                if (sourceObj1.has("dept")) {
                                    details.setDepartment(sourceObj1.getString("dept"));
                                }
                                //year
                                if (sourceObj1.has("year")) {
                                    details.setYear(sourceObj1.getString("year"));
                                }


                                itemDetailsList.add(details);




                                    if (sourceObj1.getString("reg_no").equals(browseAlumniRegnologin) ||
                                            sourceObj1.getString("reg_no").equals(browseAlumniRegnosignup) ) {

                                        name.setText(itemDetailsList.get(i).getName());
                                        regno.setText(itemDetailsList.get(i).getRegisterNo());
                                        branch_text.setText(itemDetailsList.get(i).getDepartment());
                                        year_text.setText(itemDetailsList.get(i).getYear());
                                        phone.setText(itemDetailsList.get(i).getPhone());
                                        email.setText(itemDetailsList.get(i).getEmail());


                                    }




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




}
