package com.rise.widgetfactory.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rise.widgetfactory.R;
import com.rise.widgetfactory.customviews.CustomTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rise on 25/6/15.
 */
public class DropdownListAdapter extends BaseAdapter {

    private Context mContext = null;
    private JSONArray itemList = null;
    private LayoutInflater inflater = null;

    public DropdownListAdapter(Context mContext, JSONArray itemList) {

        this.mContext = mContext;
        this.itemList = itemList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return itemList.length();
    }

    @Override
    public Object getItem(int i) {

        JSONObject itemJson = new JSONObject();

        try {
            itemJson = itemList.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return itemJson;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.dropdown_list_item, null);
        }

        JSONObject itemJson = (JSONObject) getItem(i);
        CustomTextView tvDropdownListItem = (CustomTextView) view.findViewById(R.id.tvDropdown);
        try {
            tvDropdownListItem.setText(itemJson.get("name").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
}
