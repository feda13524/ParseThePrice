package com.parsetheprice.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.parsetheprice.R;
import com.parsetheprice.ui.parsing.MainParse;
import com.parsetheprice.ui.piggybank.MainPrice;

public class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private final Activity activity;

    public SwipeGestureListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float diffY = e2.getY() - e1.getY();

        if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
            if (diffY > 0) {
                Intent intent = new Intent(activity, MainParse.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up);
                return true;
            } else {
                Intent intent = new Intent(activity, MainPrice.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
                return true;
            }
        }
        return false;
    }
}