package com.example.usuario.reciclernuevo.View.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.usuario.reciclernuevo.View.activities.MainActivity;
import com.example.usuario.reciclernuevo.View.fragments.FragmentRecyclerViewNews;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapterTab extends FragmentStatePagerAdapter {

    private List<Fragment> listaDeFragments = new ArrayList<>();
    private List<MainActivity.LatestTop> sortedByList;

    public ViewPagerAdapterTab(FragmentManager fm, List<MainActivity.LatestTop> aSortedByList, String categoriaSeleccionada) {
        super(fm);
        this.sortedByList = aSortedByList;
        for (MainActivity.LatestTop latestTop : aSortedByList) {
            listaDeFragments.add(FragmentRecyclerViewNews.RecyclerFragmentFactory(latestTop.getSortedBy(), categoriaSeleccionada));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return listaDeFragments.get(position);
    }

    @Override
    public int getCount() {
        return listaDeFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return sortedByList.get(position).getSortedBy();
    }
}



