package com.rise.youtubeapi;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.Subscription;
import com.google.api.services.youtube.model.SubscriptionSnippet;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

/**
 * Created by RupeshCHAVAN on 06-02-2017.
 */

public class AddSubscription {

    final HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
    final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube service;
    private static final String PREF_ACCOUNT_NAME = "accountName";

    /**
     * Subscribe the user's YouTube account to a user-selected channel.
     *
     */
    public void subscribeChannel(Context mContext, String channelId) {

        // This OAuth 2.0 access scope allows for full read/write access to the
        // authenticated user's account.
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");

        try {
            // Authorize the request.
//            Credential credential = Auth.authorize(mContext, scopes, "addsubscription");

            GoogleAccountCredential credential =
                    GoogleAccountCredential.usingOAuth2(mContext, scopes);
            SharedPreferences settings = ((Activity) mContext).getPreferences(Context.MODE_PRIVATE);
            credential.setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
            service =
                    new com.google.api.services.youtube.YouTube.Builder(httpTransport, jsonFactory, credential)
                            .setApplicationName("youtubeapi").build();


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

        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
    }
}
