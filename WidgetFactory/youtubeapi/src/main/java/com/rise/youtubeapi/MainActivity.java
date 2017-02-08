package com.rise.youtubeapi;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.Subscription;
import com.google.api.services.youtube.model.SubscriptionSnippet;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSubscribeChannel;
    private Button btnGetVideos = null;
    private GoogleAccountCredential credential;
    final HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
    final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube service;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 0;
    static final int REQUEST_AUTHORIZATION = 1;
    static final int REQUEST_ACCOUNT_PICKER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSubscribeChannel = (Button) findViewById(R.id.btn_subscribe_channel);
        btnGetVideos = (Button) findViewById(R.id.btn_get_videos);

        btnSubscribeChannel.setOnClickListener(this);
        btnGetVideos.setOnClickListener(this);

        // This OAuth 2.0 access scope allows for full read/write access to the
        // authenticated user's account.
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");

        // Authorize the request.
        credential =
                GoogleAccountCredential.usingOAuth2(this, scopes);
        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
        credential.setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
        service =
                new com.google.api.services.youtube.YouTube.Builder(httpTransport, jsonFactory, credential)
                        .setApplicationName("youtubeapi").build();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) {
        runOnUiThread(new Runnable() {
            public void run() {
                Dialog dialog =
                        GooglePlayServicesUtil.getErrorDialog(connectionStatusCode, MainActivity.this,
                                REQUEST_GOOGLE_PLAY_SERVICES);
                dialog.show();
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_get_videos: {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new Search().getVideos();
                    }
                }).start();

                break;
            }
            case R.id.btn_subscribe_channel: {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (checkGooglePlayServicesAvailable()) {
                            haveGooglePlayServices();
                        }
                    }
                }).start();
                break;
            }
        }
    }

    /**
     * Subscribe the user's YouTube account to a user-selected channel.
     *
     */
    public void subscribeChannel(String channelId) {

        try {
            // Create a resourceId that identifies the channel ID.
            ResourceId resourceId = new ResourceId();
            resourceId.setChannelId(channelId);
            resourceId.setKind("youtube#channel");

            // Create a snippet that contains the resourceId.
            SubscriptionSnippet snippet = new SubscriptionSnippet();
            snippet.setResourceId(resourceId);

            // Create a request to add the subscription and send the request.
            // The request identifies subscription metadata to insert as well
            // as information that the API server should return in its response.
            Subscription subscription = new Subscription();
            subscription.setSnippet(snippet);
            YouTube.Subscriptions.Insert subscriptionInsert =
                    service.subscriptions().insert("snippet,contentDetails", subscription);
            Subscription returnedSubscription = subscriptionInsert.execute();

            // Print information from the API response.
            System.out.println("\n================== Returned Subscription ==================\n");
            System.out.println("  - Id: " + returnedSubscription.getId());
            System.out.println("  - Title: " + returnedSubscription.getSnippet().getTitle());
        } catch (GoogleJsonResponseException e) {
            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
            e.printStackTrace();

        } catch (UserRecoverableAuthIOException e) {
            startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
    }

    /** Check that Google Play services APK is installed and up to date. */
    private boolean checkGooglePlayServicesAvailable() {
        final int connectionStatusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
            return false;
        }
        return true;
    }

    private void haveGooglePlayServices() {
        // check if there is already an account selected
        if (credential.getSelectedAccountName() == null) {
            // ask user to choose account
            chooseAccount();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    subscribeChannel(Constants.CHANNEL_ID);
                }
            }).start();
        }
    }

    private void chooseAccount() {
        startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode == Activity.RESULT_OK) {
                    haveGooglePlayServices();
                } else {
                    checkGooglePlayServicesAvailable();
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == Activity.RESULT_OK) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            subscribeChannel(Constants.CHANNEL_ID);
                        }
                    }).start();
                } else {
                    chooseAccount();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        credential.setSelectedAccountName(accountName);
                        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.commit();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                subscribeChannel(Constants.CHANNEL_ID);
                            }
                        }).start();
                    }
                }
                break;
        }
    }
}
