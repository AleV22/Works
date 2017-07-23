package com.example.usuario.reciclernuevo.View.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.usuario.reciclernuevo.R;
import com.example.usuario.reciclernuevo.View.fragments.FragmentnewsDetail;
import com.example.usuario.reciclernuevo.View.fragments.FragmentViewPager;
import com.example.usuario.reciclernuevo.View.fragments.FragmentnewsDetail;

public class ActivityDetalle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        FragmentViewPager fragmentViewPager = new FragmentViewPager();
        FragmentManager fragmentManager = getSupportFragmentManager();

        Intent anIntent = getIntent();
        Bundle bundle = anIntent.getExtras();
        fragmentViewPager.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_segunda, fragmentViewPager);
        fragmentTransaction.commit();
    }
}
