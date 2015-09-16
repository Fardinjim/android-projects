package sathish.alumnijson.alumnijson;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sathish.alumnijson.AppController;
import sathish.alumnijson.R;
import sathish.alumnijson.Supporting_Files.ProgressDialogUtil;
import sathish.alumnijson.dto.BrowseAlumniJsonDTO;

public class AlumniJsonActivity extends AppCompatActivity {


    private static final String TAG = AlumniJsonActivity.class.getSimpleName();
    public static final String URL_BASE = "http://www.sathishmun.comule.com/api/dmskills/all_alumni";
    public static final String ERROR_JSON_PARSER = "Error! Please try later.";
    public static final String ERROR_SERVER = "Unable to process your request,please try again later";

    private TextView loadMore;
    private int LOAD_FROM = 0;
    private final int LOAD_THRESHOLD = 4;

    private UltimateRecyclerView recyclerView;
    private AlumniJsonAdapter adapter;
    private List<BrowseAlumniJsonDTO> itemDetailsList = new ArrayList<>();
    BrowseAlumniJsonDTO details;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_json);


        ProgressDialogUtil.showDialog(this, "Great Products... a moment away!");

        initRecyclerView();





        populateJson();

    }



    private void initRecyclerView() {   //set recycler view


        recyclerView = (UltimateRecyclerView) findViewById(R.id.listView_products);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        adapter = new AlumniJsonAdapter(this, itemDetailsList);
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
                            int pos = 2 * (lastRow);

                            LOAD_FROM = pos;
                            populateJson();
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











    int count = 0;

    private void populateJson() {

        // Creating volley request obj
        JsonObjectRequest getItemDetailsReq = new JsonObjectRequest(Request.Method.POST,
                URL_BASE + "?from=" + LOAD_FROM + "&" + "size=" + LOAD_THRESHOLD   , null,
//                URL_BASE    , params,
                new Response.Listener<JSONObject>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.e(TAG, response.toString());
                        //Toast.makeText(getApplicationContext(), " Internet Connection is avsm", Toast.LENGTH_LONG).show();
                        ProgressDialogUtil.hidePDialog();
                        // Parsing json
                        //TO DO make it work for just one JSON result
                        try {
                            JSONArray hitsArray = response.getJSONArray("alumni");
                            // JSONArray hitsArray = hitsJSON.getJSONArray("hits");


                            if (hitsArray.length() % 2 == 1) {
                                hitsArray.put(hitsArray.get(0));
                            }

                            if (hitsArray.length() < LOAD_THRESHOLD) {
                                recyclerView.disableLoadmore();
                            }


                            int j = 0;

                            //parse all incoming results of the query
                            for (int i = 0; i < hitsArray.length() - 1; ) {

                                JSONObject singleObj1 = hitsArray.getJSONObject(i);
                                JSONObject singleObj2 = hitsArray.getJSONObject(i + 1);

                                JSONObject sourceObj1 = singleObj1.getJSONObject("result");
                                JSONObject sourceObj2 = singleObj2.getJSONObject("result");

                                details = new BrowseAlumniJsonDTO();

                                //get images
                                if (sourceObj1.has("image_url") && sourceObj2.has("image_url")) {

                                    details.getFirstItemInfo().setImageUrl(sourceObj1.getString("image_url"));
                                    details.getSecondItemInfo().setImageUrl(sourceObj2.getString("image_url"));
                                }

                                //name
                                if (sourceObj1.has("name") && sourceObj2.has("name")) {
                                    details.getFirstItemInfo().setName(sourceObj1.getString("name"));
                                    details.getSecondItemInfo().setName(sourceObj2.getString("name"));
                                }

                                //registr no
                                if (sourceObj1.has("reg_no") && sourceObj2.has("reg_no")) {
                                    details.getFirstItemInfo().setRegisterNo(sourceObj1.getString("reg_no"));
                                    details.getSecondItemInfo().setRegisterNo(sourceObj2.getString("reg_no"));
                                }

                                //department
                                if (sourceObj1.has("dept") && sourceObj2.has("dept")) {
                                    details.getFirstItemInfo().setDepartment(sourceObj1.getString("dept"));
                                    details.getSecondItemInfo().setDepartment(sourceObj2.getString("dept"));
                                }






                                // adding item details to to itemDetails array
                                if (LOAD_FROM == 0) {
//                                if (params.getInt("from") == 0){
                                    itemDetailsList.add(details);

                                    adapter.notifyDataSetChanged();
                                } else {
                                    adapter.insert(itemDetailsList, details, LOAD_FROM + (j++));
//                                    adapter.insert(itemDetailsList, details, params.getInt("from") + (j++));

                                    count++;
                                }
                                i = i + 2;
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
                        populateJson();
                    }
                });

                Log.e(TAG, "Error: " + error.getMessage());
                ProgressDialogUtil.hidePDialog();

            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(getItemDetailsReq);


    }













    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alumni_json, menu);
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
