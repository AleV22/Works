package com.example.usuario.reciclernuevo.View.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.reciclernuevo.Model.POJO.News;
import com.example.usuario.reciclernuevo.R;
import com.example.usuario.reciclernuevo.View.adapters.AdapterRecycler;
import com.example.usuario.reciclernuevo.View.adapters.ViewPagerAdapterTab;
import com.example.usuario.reciclernuevo.View.fragments.FragmentFavorites;
import com.example.usuario.reciclernuevo.View.fragments.FragmentLogin;
import com.example.usuario.reciclernuevo.View.fragments.FragmentRecyclerViewNews;
import com.example.usuario.reciclernuevo.View.fragments.FragmentViewPager;
import com.example.usuario.reciclernuevo.View.fragments.FragmentViewPagerStart;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity implements FragmentRecyclerViewNews.NotifyActivities, FragmentLogin.NotifyActivitiesFromLogin, AdapterRecycler.Favoriteable, FragmentFavorites.NotifyActivitiesFav {

    private DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setLogo(R.drawable.snblanco);
        toolbar.setTitle("SnapNews");

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                receiveMessaje2();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }
            @Override
            public void onDrawerClosed(View drawerView) {
            }
            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.fortune:
                        navItemSelected(item,"fortune");
                        break;
                    case R.id.dailymail:
                        navItemSelected(item,"daily-mail");
                        break;
                    case R.id.mashable:
                        navItemSelected(item,"the-lad-bible");
                        break;
                    case R.id.foxsport:
                        navItemSelected(item,"fox-sports");
                        break;
                    case R.id.mtvnews:
                        navItemSelected(item,"mtv-news");
                        break;
                    case R.id.techcrunch:
                        navItemSelected(item,"techcrunch");
                        break;
                    case R.id.favoritos:
                        navItemFav(navigationView,item);
                        break;
                    case R.id.signout:
                        signOut();
                        break;
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });

        //default top and latest
        navItemSelected(null,"fortune");
    }

    //method for the choice of favorites in Navigator
    public void navItemFav(NavigationView navigationView, MenuItem item) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Snackbar.make(navigationView, "You need to login first", Snackbar.LENGTH_SHORT).show();

            Fragment fragmentLogin = new FragmentLogin();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragmentLogin, "fragmentlogin").commit();
            fragmentTransaction.addToBackStack(null);

        } else {

            Fragment fragmentFavorites = new FragmentFavorites();
            traveler(fragmentFavorites);
        }
        keepChequed(item);
    }

    //method for the rest of the choices in Navigator
    public void navItemSelected(MenuItem item, String choice) {
        Bundle bundle = new Bundle();
        bundle.putString(FragmentViewPagerStart.CATEGORIASELECCIONADA2,choice);
        FragmentViewPagerStart fragmentViewPagerStart = new FragmentViewPagerStart();
        fragmentViewPagerStart.setArguments(bundle);

        traveler(fragmentViewPagerStart);
        keepChequed(item);
    }

    @Override
    public void twitterLoginNotify(TwitterSession session) {
        handleTwitterSession(session);
    }

    @Override
    public void facebookLoginNotify(LoginResult result) {
        handleFacebookAccessToken(result.getAccessToken());
    }

    //standard replace transaction method
    public void traveler(Fragment fragmentName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (fragmentName instanceof FragmentLogin || fragmentName instanceof FragmentFavorites){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(R.id.fragmentContainer, fragmentName).commit();
    }

    //Activity intent method
    @Override
    public void receiveMessaje(Integer position, String sortedBy, String source) {
        Intent unIntent = new Intent(this, ActivityDetalle.class);
        Bundle bundle = new Bundle();

        bundle.putInt(FragmentViewPager.POSITION, position);
        bundle.putString(FragmentViewPager.KEY_SORTEDBY, sortedBy);
        bundle.putString(FragmentViewPager.KEY_SOURCE, source);

        unIntent.putExtras(bundle);
        startActivity(unIntent);
    }

    //sets user name in menu's header
    @Override
    public void receiveMessaje2() {
        NavigationView navigationView2 = (NavigationView) findViewById(R.id.navigation);
        View headerView = navigationView2.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textViewHeader);
        if (mAuth.getCurrentUser() != null) {
            navUsername.setText(mAuth.getCurrentUser().getDisplayName());
        } else {
            navUsername.setText("Sesion Invitado");
        }
    }

    @Override
    public void backToPreviousFromLogin() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.remove(fragmentManager.findFragmentByTag("fragmentlogin")).commit();

        //Hides the keyboard:
//        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    //not used here
    @Override
    public void setFavorito() {
    }

    //from Fav. Star to login if not logged
    @Override
    public void setFavorito2(News news) {
        Fragment fragmentLogin = new FragmentLogin();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragmentLogin, "fragmentlogin");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void receiveMessajeFav(Integer position) {
        Intent unIntent = new Intent(this, ActivityDetalle.class);
        Bundle bundle = new Bundle();
        bundle.putInt(FragmentViewPager.POSITION,position);
        unIntent.putExtras(bundle);
        startActivity(unIntent);
    }

    //class for viewpager of Latest/Top
    public static class LatestTop {
        private String sortedBy;
        //cosntructor
        public LatestTop(String asortedBy){
            sortedBy = asortedBy;
        }
        //setters, getters
        public String getSortedBy() {
            return sortedBy;
        }
    }

    //method for keeping menu item checked
    public void keepChequed(MenuItem item) {
        if (item != null) {
            if (item.isChecked()) {
                item.setChecked(false);
            } else {
                item.setChecked(true);
            }
        }
    }

    //Twitter
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result to the fragment, which will then pass the result to the login
        // button.
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void handleTwitterSession(TwitterSession session) {
        Log.d("", "handleTwitterSession:" + session);

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }
                });
    }
    @Override
    public void onStart() {
        super.onStart();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//            loggedUser = currentUser.getDisplayName();
        if (currentUser != null) {
            // User is signed in
//            Uri profileUri = currentUser.getPhotoUrl();
            receiveMessaje2();
            }
        }

    //sign out method
    public void signOut() {
        // Firebase sign out
        mAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();

        //default top and latest
        navItemSelected(null,"fortune");
        receiveMessaje2();


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        receiveMessaje2();
        super.onWindowFocusChanged(hasFocus);
    }
}