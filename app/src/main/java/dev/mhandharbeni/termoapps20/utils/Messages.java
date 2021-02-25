package dev.mhandharbeni.termoapps20.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.andrognito.flashbar.Flashbar;
import com.andrognito.flashbar.anim.FlashAnim;

import dev.mhandharbeni.termoapps20.R;

public class Messages {
    /**
     * @param activity
     * @param title
     * @param message
     */
    public static void showAlertMessage(Activity activity, String title, String message){
        new Flashbar.Builder(activity)
                .gravity(Flashbar.Gravity.TOP)
                .title(title)
                .message(message)
                .backgroundColorRes(R.color.alert)
                .showIcon(0.8f, ImageView.ScaleType.CENTER_CROP)
                .duration(3000)
                .icon(R.drawable.ic_alert)
                .enableSwipeToDismiss()
                .enterAnimation(FlashAnim.with(activity.getApplicationContext())
                        .animateBar()
                        .duration(550L)
                        .alpha()
                        .overshoot())
                .exitAnimation(FlashAnim.with(activity.getApplicationContext())
                        .animateBar()
                        .duration(550L)
                        .alpha()
                        .overshoot())
                .build()
                .show();
    }

    /**
     * @param activity
     * @param title
     * @param message
     */
    public static void showSuccessMessage(Activity activity, String title, String message){
        new Flashbar.Builder(activity)
                .gravity(Flashbar.Gravity.TOP)
                .title(title)
                .message(message)
                .backgroundColorRes(R.color.success)
                .showIcon(0.8f, ImageView.ScaleType.CENTER_CROP)
                .duration(3000)
                .icon(R.drawable.ic_check)
                .enableSwipeToDismiss()
                .enterAnimation(FlashAnim.with(activity.getApplicationContext())
                        .animateBar()
                        .duration(550L)
                        .alpha()
                        .overshoot())
                .exitAnimation(FlashAnim.with(activity.getApplicationContext())
                        .animateBar()
                        .duration(550L)
                        .alpha()
                        .overshoot())
                .build()
                .show();
    }

    /**
     * @param activity
     * @param title
     * @param message
     */
    public static void showWarningMessage(Activity activity, String title, String message){
        new Flashbar.Builder(activity)
                .gravity(Flashbar.Gravity.TOP)
                .title(title)
                .message(message)
                .backgroundColorRes(R.color.warning)
                .showIcon(0.8f, ImageView.ScaleType.CENTER_CROP)
                .duration(3000)
                .icon(R.drawable.ic_cross)
                .enableSwipeToDismiss()
                .enterAnimation(FlashAnim.with(activity.getApplicationContext())
                        .animateBar()
                        .duration(550L)
                        .alpha()
                        .overshoot())
                .exitAnimation(FlashAnim.with(activity.getApplicationContext())
                        .animateBar()
                        .duration(550L)
                        .alpha()
                        .overshoot())
                .build()
                .show();
    }
}
