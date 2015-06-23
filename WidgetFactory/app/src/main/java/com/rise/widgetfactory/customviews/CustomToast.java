package com.rise.widgetfactory.customviews;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rise.widgetfactory.R;

/**
 * Created by rise on 21/6/15.
 */
public class CustomToast {

    public static void showToast(Context mContext, String message) {

        LinearLayout  layout = new LinearLayout(mContext);
        layout.setBackgroundResource(R.drawable.toast_background);

        TextView  tv = new TextView(mContext);
        // set the TextView properties like color, size etc
        tv.setTextColor(Color.RED);
        tv.setTextSize(15);

        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setPadding(mContext.getResources().getDimensionPixelSize(R.dimen.toast_left_padding),
                mContext.getResources().getDimensionPixelSize(R.dimen.toast_top_padding),
                mContext.getResources().getDimensionPixelSize(R.dimen.toast_left_padding),
                mContext.getResources().getDimensionPixelSize(R.dimen.toast_top_padding));

        // set the text you want to show in  Toast
        tv.setText(message);

        layout.addView(tv);

        Toast toast = new Toast(mContext);
        toast.setView(layout);

        // Position you toast here toast position is 50 dp from bottom you can give any integral value
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.show();
    }
}
