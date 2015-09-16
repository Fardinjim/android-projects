package sathish.sjbitalumni.login;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sathish.sjbitalumni.AppController;
import sathish.sjbitalumni.R;
import sathish.sjbitalumni.Supporting_Files.ProgressDialogUtil;
import sathish.sjbitalumni.addalumni.AddAlumni;
import sathish.sjbitalumni.browsealumni.BrowseAlumni;
import sathish.sjbitalumni.dto.BrowseAlumniDTO;
import sathish.sjbitalumni.updatealumni.UpdateAlumni;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    public static final String URL_BASE = "http://www.sathishmun.comule.com/AlumniPHP/readAllJson.php";
    public static final String ERROR_JSON_PARSER = "Error! Please try later.";
    public static final String ERROR_SERVER = "Unable to process your request,please try again later";

    private int LOAD_FROM = 0;
    private final int LOAD_THRESHOLD = 100000;

    private List<BrowseAlumniDTO> itemDetailsList = new ArrayList<>();
    BrowseAlumniDTO details;

    TextView incorrect;
    EditText registerNo;
    EditText password;
    Button login;
    Button signup;

    TextView oops_text;
    ImageView oops_image;
    RelativeLayout oops_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        incorrect = (TextView) findViewById(R.id.login_incorrect);
        registerNo = (EditText) findViewById(R.id.login_regno);
        password = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.login_login);
        signup = (Button) findViewById(R.id.login_signup);

        oops_text = (TextView) findViewById(R.id.oops_text);
        oops_image = (ImageView) findViewById(R.id.oops_image);
        oops_layout = (RelativeLayout) findViewById(R.id.oops_layout);



        populateJson();
        incorrect.setText("");

        oops_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateJson();
                incorrect.setText("");
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String reg = registerNo.getText().toString().toUpperCase();
                String pass = password.getText().toString();
                Boolean found = false;

                reg = reg.replace(" ","");

                if(!reg.isEmpty() && !pass.isEmpty()) {
                    for(int i=0; i<itemDetailsList.size(); i++) {

                        if (itemDetailsList.get(i).getRegisterNo().toUpperCase().equals(reg) &&
                                itemDetailsList.get(i).getPassword().equals(pass)  ){
                            found = true;

                            Intent t = new Intent(getApplicationContext(), BrowseAlumni.class);

                            t.putExtra("loginRegno", reg);
                            startActivity(t);
                            registerNo.setText("");
                            password.setText("");

                        }
                    }
                    if(!found)
                    {
                        incorrect.setText("Incorrect Register No or Password !!! ");
                    }
                    else {
                        incorrect.setText("");
                    }


                }
                else
                {
                    Toast.makeText(getBaseContext(),"Field is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(getApplicationContext(), AddAlumni.class);
                startActivity(t);

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

                                //registr no
                                if (sourceObj1.has("reg_no")) {
                                    details.setRegisterNo(sourceObj1.getString("reg_no"));
                                }

                                //password
                                if (sourceObj1.has("passwd")) {
                                    details.setPassword(sourceObj1.getString("passwd"));
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


}
