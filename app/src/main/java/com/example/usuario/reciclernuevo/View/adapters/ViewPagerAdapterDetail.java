package com.example.usuario.reciclernuevo.View.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.example.usuario.reciclernuevo.Model.POJO.News;
import com.example.usuario.reciclernuevo.View.fragments.FragmentnewsDetail;


import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapterDetail extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();

    public ViewPagerAdapterDetail(FragmentManager fm){
        super (fm);
    }

    public void setListaDeNews(List<News> listaDeNews){
        for(News news : listaDeNews){
            FragmentnewsDetail unFragmentnewsDetail = FragmentnewsDetail.fabricaDeFragments(news.getTitulo(),news.getUrl(),news.getDescripcion(),news.getImagen(), news.getChannel());
            fragmentList.add(unFragmentnewsDetail);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}

