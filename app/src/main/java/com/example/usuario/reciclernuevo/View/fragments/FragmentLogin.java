package com.example.usuario.reciclernuevo.View.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usuario.reciclernuevo.R;
import com.example.usuario.reciclernuevo.View.activities.Logins.MyAplicationTwitter;
import com.example.usuario.reciclernuevo.View.activities.Logins.MyResultReceiverTwitter;
import com.example.usuario.reciclernuevo.View.activities.MainActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.concurrent.Executor;

public class FragmentLogin extends Fragment {

    private NotifyActivitiesFromLogin notifyActivitiesFromLogin;
    TwitterLoginButton loginButton;
    LoginButton loginButtonFb;
    CallbackManager callbackManager;
    Button fb;
    Button tw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize Firebase Auth
        //fb = (Button) view.findViewById(R.id.login_Facebook_button2);
        loginButtonFb = (LoginButton) view.findViewById(R.id.login_Facebook_button);

        fb = (Button) view.findViewById(R.id.customizeButtonFb);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButtonFb.performClick();
            }
        });
        callbackManager = CallbackManager.Factory.create();
        loginButtonFb.setReadPermissions("email","public_profile");
        loginButtonFb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //notifyActivitiesFromLogin.receiveMessaje2();
                notifyActivitiesFromLogin.facebookLoginNotify(loginResult);
//                Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();
                notifyActivitiesFromLogin.backToPreviousFromLogin();
            }

            @Override
            public void onCancel() {
//                Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
            }
        });


        //tw = (Button) view.findViewById(R.id.login_Twitter_button2);
        loginButton = (TwitterLoginButton) view.findViewById(R.id.login_Twitter_button);
        tw = (Button) view.findViewById(R.id.customizeButtonTw);
        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
//                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

                notifyActivitiesFromLogin.twitterLoginNotify(result.data);
                //notifyActivitiesFromLogin.receiveMessaje2();
                notifyActivitiesFromLogin.backToPreviousFromLogin();
            }

            @Override
            public void failure(TwitterException exception) {
//                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
                exception.printStackTrace();
            }
        });

        return view;
    }
    public interface NotifyActivitiesFromLogin {
        public void receiveMessaje2();
        public void backToPreviousFromLogin();
        public void twitterLoginNotify(TwitterSession session);
        public void facebookLoginNotify(LoginResult result);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notifyActivitiesFromLogin = (NotifyActivitiesFromLogin) context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode , data);
    }
}
