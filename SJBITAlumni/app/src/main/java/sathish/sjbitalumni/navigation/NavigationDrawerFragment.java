package sathish.sjbitalumni.navigation;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ShareActionProvider;

import sathish.sjbitalumni.R;
import sathish.sjbitalumni.browsealumni.BrowseAlumni;
import sathish.sjbitalumni.developer.DeveloperActivity;
import sathish.sjbitalumni.howtouse.HowToUseActivity;
import sathish.sjbitalumni.login.LoginActivity;
import sathish.sjbitalumni.updatealumni.UpdateAlumni;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

    private View containerView;
    public static final String PREF_FILE_NAME = "testpref";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    ImageView developer, howToUse, share;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        developer = (ImageView) layout.findViewById(R.id.developer);
        howToUse = (ImageView) layout.findViewById(R.id.howToUse);
        share = (ImageView) layout.findViewById(R.id.share);

        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                Intent t = new Intent(getActivity().getApplicationContext(), DeveloperActivity.class);
                startActivity(t);

            }
        });

        howToUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                Intent t = new Intent(getActivity().getApplicationContext(), HowToUseActivity.class);
                startActivity(t);

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();

                Intent i=new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_SUBJECT, "SJBIT Alumni");
                i.putExtra(android.content.Intent.EXTRA_TEXT, "Hey!!! Check out our college Android Alumni Application\n\nhttp://www.sathishmun.comule.com/");
                startActivity(Intent.createChooser(i,"Share via"));

            }
        });
        return layout;
    }


}
