package com.rise.widgetfactory.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.rise.widgetfactory.R;
import com.rise.widgetfactory.customviews.CustomToast;
import com.rise.widgetfactory.customviews.PopupView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    PopupView popupView;
    Button btnLoadUrl = null;
    Button btnShowDropdown = null;
    JSONArray itemsJsonArray = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popupView = new PopupView(this);
        Button showButton = (Button)findViewById(R.id.test_popup_btn);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                popupView.setBackgroundColor(Color.MAGENTA);
//                popupView.showMessage("LayoutParams class comes from the package android.view.viewgroup.LayoutParams package.LayoutParams class defines some constants to describe the dimension of various views.\n" +
//                        "\n" +
//                        "Read more: http://mrbool.com/how-to-implement-popup-window-in-android/28285#ixzz3dUuqxqQa\n", getWindow().getCurrentFocus());

//                popupView.showMessageWithTitle("Test" , "Test message for popup", getCurrentFocus());

//                popupView.showAlert("Test", "Test message for popup", getCurrentFocus(), new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        switch (view.getId()) {
//                            case R.id.ok_btn: {
//                                Toast.makeText(MainActivity.this, "Ok", Toast.LENGTH_SHORT).show();
//                                popupView.dismiss();
//                                break;
//                            }
//                            case R.id.cancel_btn: {
//                                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
//                                popupView.dismiss();
//                                break;
//                            }
//                        }
//
//                        popupView.dismiss();
//                        System.out.println("Clicked!!");
//                    }
//                });

                CustomToast.showToast(MainActivity.this, "This is pretty long custom toast message!!");
            }
        });

        btnLoadUrl = (Button) findViewById(R.id.btnLaunchWebView);
        btnLoadUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent launchWebViewIntent = new Intent(MainActivity.this, WebViewActivity.class);
                launchWebViewIntent.putExtra("url", "http://www.youtube.com");
                startActivity(launchWebViewIntent);
            }
        });

        btnShowDropdown = (Button) findViewById(R.id.btnShowDropdown);
        btnShowDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Dropdown displayed!!",
//                        Toast.LENGTH_SHORT).show();

                String[] items = getResources().getStringArray(R.array.drawer_items);
                for(int index=0; index<items.length; index++) {

                    JSONObject itemJson = new JSONObject();
                    try {
                        itemJson.put("name", items[index]);
                        itemsJsonArray.put(itemJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                popupView = new PopupView(MainActivity.this);
                popupView.setBackgroundColor(Color.WHITE);
                popupView.showDropdown(btnShowDropdown, itemsJsonArray, MainActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        try {
            Toast.makeText(this, itemsJsonArray.getString(i), Toast.LENGTH_SHORT).show();
            popupView.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
