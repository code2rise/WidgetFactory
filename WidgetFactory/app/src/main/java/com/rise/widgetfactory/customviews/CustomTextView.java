package com.rise.widgetfactory.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.rise.widgetfactory.util.FontManager;

/**
 * Created by rise on 23/6/15.
 */
public class CustomTextView extends TextView {

    private FontManager fontManager = null;

    public CustomTextView(Context context) {
        super(context);
        setCustomFont(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context);
    }

    private void setCustomFont(Context mContext) {

        FontManager.init(mContext.getAssets());
        fontManager = FontManager.getInstance();
        Typeface newsGothicFontRegular = fontManager.getFont("news-gothic-mt.ttf");
        Typeface newsGothicFontBold = fontManager.getFont("news-gothic-mt-bold.ttf");

        if(getTypeface() != null) {

            int fontStyle = getTypeface().getStyle();
            if(fontStyle == Typeface.BOLD || fontStyle == Typeface.BOLD_ITALIC) {

                setTypeface(newsGothicFontBold);
                return;
            }
        }

        setTypeface(newsGothicFontRegular);
    }
}
