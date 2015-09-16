package sathish.sjbitalumni.browsealumni;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sathish.sjbitalumni.AppController;
import sathish.sjbitalumni.R;
import sathish.sjbitalumni.Supporting_Files.ProgressDialogUtil;
import sathish.sjbitalumni.addalumni.AddAlumni;
import sathish.sjbitalumni.browsealumni.BrowseAlumniAdapter.onCallListener;
import sathish.sjbitalumni.browsealumni.BrowseAlumniAdapter.onInsertContactListener;
import sathish.sjbitalumni.dto.BrowseAlumniDTO;
import sathish.sjbitalumni.navigation.NavigationDrawerFragment;
import sathish.sjbitalumni.updatealumni.UpdateAlumni;

public class BrowseAlumni extends AppCompatActivity implements onInsertContactListener, onCallListener {


    private static final String TAG = BrowseAlumni.class.getSimpleName();
    public static final String URL_BASE = "http://www.sathishmun.comule.com/AlumniPHP/readLimitedJson.php";
    public static final String ERROR_JSON_PARSER = "Error! Please try later.";
    public static final String ERROR_SERVER = "Unable to process your request,please try again later";

    private TextView loadMore;
    private int LOAD_FROM = 0;
    private final int LOAD_THRESHOLD = 17;

    private UltimateRecyclerView recyclerView;
    private BrowseAlumniAdapter adapter;
    private List<BrowseAlumniDTO> itemDetailsList = new ArrayList<>();
    private List<BrowseAlumniDTO> tempitemDetailsList = new ArrayList<>();
    BrowseAlumniDTO details;

    Toolbar toolbar;

    EditText search;
    String searchStr;

    Spinner year;
    Spinner branch;
    private Dialog dialog = null;


    String checkYear;
    String checkBranch;
    int count = 0;
    int addCount = 0;

    int curLength=0;
    int prevLength=0;
    int searchTextCount = 0;

    String signupRegno;
    String loginRegno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_alumni);

        Bundle bundle = getIntent().getExtras();
        signupRegno = bundle.getString("signupRegno");
        loginRegno = bundle.getString("loginRegno");




        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        search = (EditText) findViewById(R.id.edittext_search_alumni);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                prevLength = count;

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                curLength = count;
                if(s.toString().equals("")){
                    //reset
                    populateJson("refresh");
                }
                else {

                    if (prevLength > curLength)
                        search.setText("");
                    String cmp1 = s.toString().toLowerCase();

                    for (int i = 0; i < itemDetailsList.size(); i++) {
                        if (!itemDetailsList.get(i).getName().toString().toLowerCase().contains(cmp1) &&
                                !itemDetailsList.get(i).getRegisterNo().toString().toLowerCase().contains(cmp1)) {
                            itemDetailsList.remove(i);

                        }
                    }
                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ProgressDialogUtil.showDialog(this, "Friends... a moment away!");
        initRecyclerView();
        filter_layout();
        dialog.hide();
        populateJson("refresh");
    }


    private void initRecyclerView() {   //set recycler view


        recyclerView = (UltimateRecyclerView) findViewById(R.id.listView_products);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        adapter = new BrowseAlumniAdapter(this, itemDetailsList, this, this);
        //set adapter
        recyclerView.setAdapter(adapter);
        //custom header view
        //   View header = LayoutInflater.from(getActivity())
        //           .inflate(R.layout.list_row_custom_header, null);
        UltimateRecyclerView.CustomRelativeWrapper wrapper = new UltimateRecyclerView.CustomRelativeWrapper(this.getApplicationContext());
        //   wrapper.addView(header);
        adapter.setCustomHeaderView(wrapper);
        recyclerView.enableLoadmore();
        //custom progressbar
        adapter.setCustomLoadMoreView(LayoutInflater.from(this)
                .inflate(R.layout.custom_bottom_progressbar, null));
        loadMore = (TextView) adapter.getCustomLoadMoreView().findViewById(R.id.load_more);
        recyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(final int i, final int lastRow) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                            if (lastRow > 1) {
                                //Set params
                                LOAD_FROM = count;
                                populateJson("run");
                            }

                    }
                }, 1000);
            }
        });

        //show or hide views on scroll
        recyclerView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {


            }

            @Override
            public void onDownMotionEvent() {
            }

            @Override
            public void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState) {

                if (observableScrollState == ObservableScrollState.DOWN) {


                } else if (observableScrollState == ObservableScrollState.UP) {


                } else if (observableScrollState == ObservableScrollState.STOP) {

                }
            }
        });
    }



public boolean checkString(){

    if(search.getText().toString().isEmpty())
        return false;
    else
        return true;
}





    private void populateJson(String cmd) {

        if(cmd.equals("run"))
        {

        }
        else if(cmd.equals("refresh"))
        {
            itemDetailsList.clear();
            adapter.notifyDataSetChanged();
            LOAD_FROM = 0;
            count = 0;
            addCount = 0;
            recyclerView.enableLoadmore();
        }

        // Creating volley request obj
        JsonArrayRequest getItemDetailsReq = new JsonArrayRequest(Request.Method.POST,
                URL_BASE + "?from=" + LOAD_FROM + "&" + "size=" + LOAD_THRESHOLD   , null,
                new Response.Listener<JSONArray>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(JSONArray response) {

                        ProgressDialogUtil.hidePDialog();
                        // Parsing json
                        //TO DO make it work for just one JSON result
                        try {
                            JSONArray hitsArray = response;
                            // JSONArray hitsArray = hitsJSON.getJSONArray("hits");




                            if (hitsArray.length() < LOAD_THRESHOLD) {
                                recyclerView.disableLoadmore();
                            }


                            int j = 0;

                            //parse all incoming results of the query
                            for (int i = 0; i < hitsArray.length(); ) {

                                JSONObject sourceObj1 = hitsArray.getJSONObject(i);


                                //     JSONObject sourceObj1 = singleObj1.getJSONObject("result");
                                //   JSONObject sourceObj2 = singleObj2.getJSONObject("result");

                                details = new BrowseAlumniDTO();

                                //get images
                                if (sourceObj1.has("image_url")) {
                                    if(!sourceObj1.getString("image_url").isEmpty())
                                        details.setImageUrl(sourceObj1.getString("image_url"));
                                    else
                                        details.setImageUrl("http://www.sathishmun.comule.com/wp-content/uploads/2015/08/na.png");
                                }


                                //name
                                if (sourceObj1.has("name") ) {
                                    details.setName(sourceObj1.getString("name"));



                                }

                                //registr no
                                if (sourceObj1.has("reg_no")) {
                                    details.setRegisterNo(sourceObj1.getString("reg_no"));

                                }

                                //department
                                if (sourceObj1.has("dept")) {
                                    details.setDepartment(sourceObj1.getString("dept"));

                                }

                                //year
                                if (sourceObj1.has("year")) {
                                    details.setYear(sourceObj1.getString("year"));

                                }

                                //phone
                                if (sourceObj1.has("phone")) {
                                    details.setPhone(sourceObj1.getString("phone"));
                                }
                                    //email
                                    if (sourceObj1.has("email")) {
                                        details.setEmail(sourceObj1.getString("email"));

                                }


                                 checkYear =  year.getSelectedItem().toString() ;
                                 checkBranch =  branch.getSelectedItem().toString() ;

                                // adding item details to to itemDetails array
                                if (LOAD_FROM == 0) {
//                                if (params.getInt("from") == 0){
                                   if(checkBranch.equals("Any") && checkYear.equals("Any"))
                                   {
                                       itemDetailsList.add(details);
                                       addCount++;

                                   }
                                    else if(checkBranch.equals("Any") && !checkYear.equals("Any"))
                                   {
                                       if(sourceObj1.getString("year").equals(checkYear))
                                       {
                                           itemDetailsList.add(details);
                                           addCount++;

                                       }
                                   }
                                   else if(!checkBranch.equals("Any") && checkYear.equals("Any"))
                                   {
                                       if(sourceObj1.getString("dept").equals(checkBranch))
                                       {
                                           itemDetailsList.add(details);
                                           addCount++;

                                       }
                                   }
                                   else if(!checkBranch.equals("Any") && !checkYear.equals("Any"))
                                   {
                                       if(sourceObj1.getString("dept").equals(checkBranch) && sourceObj1.getString("year").equals(checkYear))
                                       {
                                           itemDetailsList.add(details);
                                           addCount++;

                                       }
                                   }



                                        adapter.notifyDataSetChanged();
                                } else {

                                  if(checkBranch.equals("Any") && checkYear.equals("Any"))
                                    {
                                        adapter.insert(itemDetailsList, details, addCount + (j++));

                                    }
                                    else if(checkBranch.equals("Any") && !checkYear.equals("Any"))
                                    {
                                        if(sourceObj1.getString("year").equals(checkYear))
                                        {
                                            adapter.insert(itemDetailsList, details, addCount + (j++));
                                        }
                                    }
                                    else if(!checkBranch.equals("Any") && checkYear.equals("Any"))
                                    {
                                        if(sourceObj1.getString("dept").equals(checkBranch))
                                        {
                                            adapter.insert(itemDetailsList, details, addCount + (j++));
                                        }
                                    }
                                    else if(!checkBranch.equals("Any") && !checkYear.equals("Any"))
                                    {
                                        if(sourceObj1.getString("dept").equals(checkBranch) && sourceObj1.getString("year").equals(checkYear))
                                        {
                                            adapter.insert(itemDetailsList, details, addCount + (j++));
                                        }
                                    }


                                }
                                count++;
                                i = i + 1;
                            } //end for loop

                        } catch (JSONException error) {

                            adapter.getCustomLoadMoreView().findViewById(R.id.bottom_progress_bar).setVisibility(View.GONE);
                            loadMore.setText(ERROR_JSON_PARSER);

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        //TODO modify to prevent entire adapter refresh


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_LONG).show();


                loadMore.setText(ERROR_SERVER);
                loadMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.getCustomLoadMoreView().findViewById(R.id.bottom_progress_bar).
                                setVisibility(View.VISIBLE);
                        loadMore.setText("");
                        populateJson("run");
                    }
                });

                Log.e(TAG, "Error: " + error.getMessage());
                ProgressDialogUtil.hidePDialog();

            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(getItemDetailsReq);


    }

    public  void filter(View v)
    {
        dialog.show();
    }

    public void refresh(View v)
    {
        search.setText("");

//           recyclerView.scrollTo(0, View.FOCUS_DOWN);
//recyclerView.getParent().requestChildFocus(recyclerView,recyclerView);

    }

    public void filter_layout()
    {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.DialogSlideAnim);
        }
        dialog.setContentView(R.layout.fragment_filter);
        initDialogView();


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.show();
        lp.dimAmount = 0.7f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

    }


Button ok;
    ImageButton close;

    private void initDialogView() {

        dialog.setCanceledOnTouchOutside(true);

        close = (ImageButton) dialog.findViewById(R.id.close);
        ok = (Button) dialog.findViewById(R.id.ok);
        year=(Spinner) dialog.findViewById(R.id.year);
        branch=(Spinner) dialog.findViewById(R.id.branch);


        ArrayAdapter yearAdapter=ArrayAdapter.createFromResource(this, R.array.year,android.R.layout.simple_list_item_single_choice);
        ArrayAdapter branchAdapter= ArrayAdapter.createFromResource(this, R.array.branch, android.R.layout.simple_list_item_single_choice);

        year.setAdapter(yearAdapter);
        branch.setAdapter(branchAdapter);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                search.setText("");
                //populateJson("refresh");


            }
        });

    }

    public static void Insert2Contacts(Context ctx, String nameSurname,
                                       String telephone) {

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        int rawContactInsertIndex = ops.size();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(
                        ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, telephone).build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, nameSurname)
                .build());
        try {
            ContentProviderResult[] res = ctx.getContentResolver()
                    .applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browse_alumni, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addAlumniMenu) {
            Intent t = new Intent(getApplicationContext(), UpdateAlumni.class);
            t.putExtra("browseAlumniRegnologin", loginRegno);
            t.putExtra("browseAlumniRegnosignup", signupRegno);

            startActivity(t);


            return true;
        }

        if(id == R.id.website){
            String url = "http://www.sathishmun.comule.com/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void contact_info(String name, String phone) {
        Insert2Contacts(getApplicationContext(),name+" (SJBIT Alumni)",phone);
        Toast.makeText(getBaseContext(),"Contact Added Successfully",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void call_info(String phone) {
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phone));
        startActivity(callIntent);
    }
}
