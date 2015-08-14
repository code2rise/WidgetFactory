package com.rise.widgetfactory.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rise.widgetfactory.R;
import com.rise.widgetfactory.customviews.CustomTextView;
import com.rise.widgetfactory.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rise on 14/8/15.
 */
public class SimpleListViewAdapter extends BaseAdapter {

    private JSONArray dataItemsJsonArray = null;
    private Context mContext = null;
    private LayoutInflater inflater = null;

    public SimpleListViewAdapter(Context mContext, JSONArray dataItemsJsonArray) {

        this.mContext = mContext;
        this.dataItemsJsonArray = dataItemsJsonArray;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {

        return dataItemsJsonArray.length();
    }

    @Override
    public Object getItem(int i) {

        JSONObject dataItemJson = new JSONObject();

        try {
            dataItemJson = dataItemsJsonArray.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataItemJson;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {
        public CustomTextView tvTitle = null;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if(view == null) {

            view = inflater.inflate(R.layout.simple_list_view_item, null);

            holder = new ViewHolder();
            holder.tvTitle = (CustomTextView) view.findViewById(R.id.tvTitle);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        JSONObject listItemDataJson = (JSONObject) getItem(i);

        try {
            holder.tvTitle.setText(listItemDataJson.getString(Constants.TITLE));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
}
