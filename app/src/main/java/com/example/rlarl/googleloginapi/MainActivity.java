package com.example.rlarl.googleloginapi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends FragmentActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mgGoogleApiClient;
    private int SIGN_IN = 101;
    private final String TAG = "메인";
    private TextView statusTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleLogin();
    }

    private void GoogleLogin(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mgGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        SignInButton signInButton = findViewById(R.id.bt_login);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());

        signInButton.setOnClickListener(this);
        statusTv = findViewById(R.id.tv1);
    }

    @Override
    public void onClick(View v) {
        signIn();
    }
    private void signIn(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mgGoogleApiClient);
        startActivityForResult(intent, SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();

                Log.i(TAG, "email" + account.getEmail());
                Log.i(TAG, "id" + account.getId());
                Log.i(TAG, "profile" + account.getPhotoUrl());
                Log.i(TAG, "name" + account.getDisplayName());

            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
