package com.rise.widgetfactory.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.rise.widgetfactory.R;
import com.rise.widgetfactory.adapters.SimpleListViewAdapter;
import com.rise.widgetfactory.customviews.CustomTextView;
import com.rise.widgetfactory.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SimpleListViewActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener {

    private ListView lvDataItems = null;
    private CustomTextView tvEmptyView = null;
    private JSONArray listViewItemData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list_view);

        lvDataItems = (ListView) findViewById(R.id.lvDataItems);
        tvEmptyView = (CustomTextView) findViewById(R.id.tvEmptyView);
        lvDataItems.setEmptyView(tvEmptyView);
        lvDataItems.setOnItemClickListener(this);

        try {
            listViewItemData = getSimpleListViewData();
            SimpleListViewAdapter adapter = new SimpleListViewAdapter(this, listViewItemData);
            lvDataItems.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple_list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private JSONArray getSimpleListViewData() throws JSONException {
        JSONArray simpleListViewData = new JSONArray();

        JSONObject item1 = new JSONObject();
        item1.put(Constants.ID, "1");
        item1.put(Constants.TITLE, "KL Rahul");

        JSONObject item2 = new JSONObject();
        item2.put(Constants.ID, "2");
        item2.put(Constants.TITLE, "Shikhar Dhawan");

        JSONObject item3 = new JSONObject();
        item3.put(Constants.ID, "3");
        item3.put(Constants.TITLE, "Rohit Sharma");

        JSONObject item4 = new JSONObject();
        item4.put(Constants.ID, "4");
        item4.put(Constants.TITLE, "Virat Kohli");

        JSONObject item5 = new JSONObject();
        item5.put(Constants.ID, "5");
        item5.put(Constants.TITLE, "Ajinkya Rahane");

        JSONObject item6 = new JSONObject();
        item6.put(Constants.ID, "6");
        item6.put(Constants.TITLE, "Wriddhiman Saha");

        JSONObject item7 = new JSONObject();
        item7.put(Constants.ID, "7");
        item7.put(Constants.TITLE, "Ravi Ashwin");

        simpleListViewData.put(item1);
        simpleListViewData.put(item2);
        simpleListViewData.put(item3);
        simpleListViewData.put(item4);
        simpleListViewData.put(item5);
        simpleListViewData.put(item6);
        simpleListViewData.put(item7);

        return simpleListViewData;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        JSONObject selectedItem = null;
        try {
            selectedItem = listViewItemData.getJSONObject(i);
            Toast.makeText(getApplicationContext(), selectedItem.getString(Constants.ID),
                    Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
