package mun.sathish.sjbitfest;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment implements RecylerAdaptor.ClickListener {


    private RecyclerView recyclerView;
    private View containerView;
    public static final String PREF_FILE_NAME = "testpref";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private RecylerAdaptor adaptor;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerlist);
        adaptor = new RecylerAdaptor(getActivity(), getData());
        adaptor.setClickListener(this);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public static List<DrawerItem> getData() {
        List<DrawerItem> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,
                R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};

        String[] titles = {"Back Coming Activity", "Tab Ordinary", "Scroll Tab", "Slide in Button",
                "Fragment Horizontal","IconTab", "Buttons"};
        for (int i = 0; i < titles.length && i < icons.length; i++) {
            DrawerItem current = new DrawerItem();
            current.iconId = icons[i];
            current.title = titles[i];
            data.add(current);
        }
        return data;
    }

    public void setUp(int fragmentId, DrawerLayout drawerlayout, final Toolbar toolbar) {

        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerlayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                drawerlayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }
            }
        };
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });


    }

    public static void saveToPreferences(Context context, String preferanceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferanceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String prefereceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(prefereceName, defaultValue);
    }

    @Override
    public void itemClicked(View view, final int position) {
/*
        Thread t = new Thread() {

            public void run() {
                try {
                    Thread.sleep(220);
                    if (position == 0) {
                        startActivity(new Intent(getActivity(), Subactivity.class));
                        mDrawerLayout.closeDrawer(containerView);
                    }

                    if (position == 1) {
                        startActivity(new Intent(getActivity(), Tabs.class));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
*/
        if (position == 1) {
            //startActivity(new Intent(getActivity(), Subactivity.class));

            mDrawerLayout.closeDrawer(containerView);

        }

        if (position == 2) {
           // startActivity(new Intent(getActivity(), Tabs.class));
            mDrawerLayout.closeDrawer(containerView);
        }

        if (position == 3) {
           // startActivity(new Intent(getActivity(), Scrolltab.class));
            mDrawerLayout.closeDrawer(containerView);
        }

        if (position == 4) {
           // startActivity(new Intent(getActivity(), Transition1.class));
            mDrawerLayout.closeDrawer(containerView);
        }

        if (position == 5) {
          //  startActivity(new Intent(getActivity(), sathish.com.materialdesign.Fragment.class));
            mDrawerLayout.closeDrawer(containerView);
        }

        if (position == 6) {
           // startActivity(new Intent(getActivity(), TabIcon.class));
            mDrawerLayout.closeDrawer(containerView);
        }

        if (position == 7) {
           // startActivity(new Intent(getActivity(), Buttons.class));
            mDrawerLayout.closeDrawer(containerView);
        }

    }
}
