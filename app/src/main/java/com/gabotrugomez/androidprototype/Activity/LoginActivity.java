package com.gabotrugomez.androidprototype.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gabotrugomez.androidprototype.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity
{
    private CallbackManager callbackManager;

    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;
    private boolean currentlySinginIn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        currentlySinginIn = false;

        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.facebook_login_btn_login_activity);
        loginButton.setReadPermissions(Arrays.asList("email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                // App code
                if (AccessToken.getCurrentAccessToken() != null)
                {
                    accessToken = AccessToken.getCurrentAccessToken();
                    currentlySinginIn = true;
                    checkIfUserAcceptedPermissions();
                }
                else
                {
                    Log.e("APP-INFO", "ACCESS TOKEN WAS NULL");
                }
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException error) {}
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
            {
                accessToken = currentAccessToken;
                if (accessToken != null && !accessToken.isExpired() && !currentlySinginIn)
                {
                    checkIfUserAcceptedPermissions();
                }
            }
        };

        accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired() && !currentlySinginIn)
        {
            checkIfUserAcceptedPermissions();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void checkIfUserAcceptedPermissions()
    {
        if (accessToken.getDeclinedPermissions().contains("email"))
        {
            // Log out user and show msg telling them to permit email permission
            LoginManager.getInstance().logOut();
            Toast.makeText(this, "Please accept EMAIL permission.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            getFBData();
        }
    }

    private void getFBData()
    {
        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback()
                {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response)
                    {
                        try
                        {
                            Log.i("APP-INFO", object.toString(4));
                            startMenuActivity();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name, email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void startMenuActivity()
    {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
