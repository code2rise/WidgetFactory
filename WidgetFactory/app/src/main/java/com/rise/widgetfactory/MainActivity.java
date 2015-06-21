package com.rise.widgetfactory;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    PopupView popupView;

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

                popupView.showAlert("Test", "Test message for popup", getCurrentFocus(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        switch (view.getId()) {
                            case R.id.ok_btn: {
                                Toast.makeText(MainActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                                popupView.dismiss();
                                break;
                            }
                            case R.id.cancel_btn: {
                                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                                popupView.dismiss();
                                break;
                            }
                        }

                        popupView.dismiss();
                        System.out.println("Clicked!!");
                    }
                });
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
}
