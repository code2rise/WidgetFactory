package com.rise.widgetfactory.customviews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.rise.widgetfactory.R;
import com.rise.widgetfactory.adapters.DropdownListAdapter;

import org.json.JSONArray;

/**
 * Created by ashahapurkar on 6/19/2015.
 */
public class PopupView {

    private PopupWindow popupWindow;
    private Context context;
    private int backgroundColor;

    public PopupView(Context context){
        this.context = context;
        popupWindow = new PopupWindow(context);
        backgroundColor = Color.TRANSPARENT;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBackgroundDrawable(Drawable drawable){
        popupWindow.setBackgroundDrawable(drawable);
    }

    public void showMessage(String message, View parent, View.OnClickListener listener){

        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.popup_message, null);
        popupWindow.setContentView(contentView);

        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        contentView.setBackgroundColor(backgroundColor);

        contentView.findViewById(R.id.title_layout).setVisibility(View.GONE);
        contentView.findViewById(R.id.cancel_btn).setVisibility(View.GONE);

        TextView messageView = (TextView)contentView.findViewById(R.id.message_txt);
        messageView.setText(message);

        Button okButton = (Button) contentView.findViewById(R.id.ok_btn);
        okButton.setOnClickListener(listener);

        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    public void showMessageWithTitle(String title, String message, View parent, View.OnClickListener listener){

        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.popup_message, null);
        popupWindow.setContentView(contentView);

        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        contentView.setBackgroundColor(backgroundColor);

        contentView.findViewById(R.id.cancel_btn).setVisibility(View.GONE);

        TextView titleView = (TextView)contentView.findViewById(R.id.title_text);
        titleView.setText(title);

        TextView messageView = (TextView)contentView.findViewById(R.id.message_txt);
        messageView.setText(message);

        Button okButton = (Button) contentView.findViewById(R.id.ok_btn);
        okButton.setOnClickListener(listener);

        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    public void showAlert(String title, String message, View parent, View.OnClickListener responseHandler){

        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.popup_message, null);
        contentView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        popupWindow.setContentView(contentView);

        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        contentView.setBackgroundColor(backgroundColor);

        TextView titleView = (TextView)contentView.findViewById(R.id.title_text);
        titleView.setText(title);

        TextView messageView = (TextView)contentView.findViewById(R.id.message_txt);
        messageView.setText(message);

        Button okButton = (Button) contentView.findViewById(R.id.ok_btn);
        okButton.setOnClickListener(responseHandler);

        Button cancelButton = (Button) contentView.findViewById(R.id.cancel_btn);
        cancelButton.setOnClickListener(responseHandler);

        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    public void dismiss(){
        popupWindow.dismiss();
    }

    public void showDropdown(View anchorView, JSONArray dropdownListItems) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.dropdown_layout, null);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.setMargins(15, 0, 15, 0);
        contentView.setLayoutParams(layoutParams);

        popupWindow.setContentView(contentView);


//        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
//        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
//        popupWindow.setHeight(context.getResources().getDimensionPixelSize(R.dimen.poppup_height));
//        popupWindow.setWidth(context.getResources().getDimensionPixelSize(R.dimen.poppup_width));
        popupWindow.setWindowLayoutMode(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        contentView.setBackgroundColor(backgroundColor);

        ListView lvDropdownList = (ListView) contentView.findViewById(R.id.lvDropdownList);
        DropdownListAdapter adapter = new DropdownListAdapter(context, dropdownListItems);
        lvDropdownList.setAdapter(adapter);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(anchorView);
    }
}
