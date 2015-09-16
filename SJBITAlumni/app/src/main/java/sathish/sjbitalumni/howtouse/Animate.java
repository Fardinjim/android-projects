package sathish.sjbitalumni.howtouse;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import sathish.sjbitalumni.R;

/**
 * Created by Sathish Mun on 23-08-2015.
 */
public class Animate {

    public static void slide_down(Context ctx, View v){

        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.sliding_down);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }


    public static void slide_up(Context ctx, View v){

        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.sliding_up);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
}
