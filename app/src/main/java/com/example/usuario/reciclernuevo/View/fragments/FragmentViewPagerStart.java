package com.example.usuario.reciclernuevo.View.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.reciclernuevo.R;
import com.example.usuario.reciclernuevo.View.activities.MainActivity;
import com.example.usuario.reciclernuevo.View.adapters.ViewPagerAdapterTab;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.List;

public class FragmentViewPagerStart extends Fragment {

    public static final String CATEGORIASELECCIONADA2 = "categoria";
    private String categoriaSeleccionada;
    public static final String LOGGEDUSER = "loggedUser";
    //private String loggedUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_view_pager_start, container, false);

        categoriaSeleccionada = getArguments().getString(CATEGORIASELECCIONADA2);
        //loggedUser = getArguments().getString(LOGGEDUSER);

        List<MainActivity.LatestTop> sortedByList = new ArrayList<>();
        sortedByList.add(new MainActivity.LatestTop("top"));
        sortedByList.add(new MainActivity.LatestTop("latest"));

        FragmentManager fragmentManager = getChildFragmentManager();
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        // Adapter
        ViewPagerAdapterTab miAdapter = new ViewPagerAdapterTab(fragmentManager, sortedByList, categoriaSeleccionada);

        // View Pager
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(miAdapter);
//        viewPager.setCurrentItem(1);
//        viewPager.setPageMargin(30);
//        viewPager.setClipToPadding(false);
        tabLayout.setupWithViewPager(viewPager);
        miAdapter.notifyDataSetChanged();

        return view;
    }

}