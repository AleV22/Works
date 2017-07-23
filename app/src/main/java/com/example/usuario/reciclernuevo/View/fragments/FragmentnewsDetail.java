package com.example.usuario.reciclernuevo.View.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usuario.reciclernuevo.R;
import com.squareup.picasso.Picasso;


public class FragmentnewsDetail extends Fragment {

    public static final String NOMBREKEY = "nombreKey";
    public static final String CHANNEL = "channelKey";
    public static final String DESCRIPTIONKEY = "descriptionKey";
    public static final String PHOTOKEY = "photoKey";
    public static final String FAVORITEDKEY = "favoritedkey";
    public static final String URL = "urlKey";

    public static FragmentnewsDetail fabricaDeFragments(String nombre, String url, String description, String imagen, String channel){

        Bundle unBundle = new Bundle();
        unBundle.putString(NOMBREKEY, nombre);
        unBundle.putString(CHANNEL, channel);
        unBundle.putString(DESCRIPTIONKEY, description);
        unBundle.putString(PHOTOKEY, imagen);
        unBundle.putString(URL, url);
        FragmentnewsDetail fragmentnewsDetail = new FragmentnewsDetail();
        fragmentnewsDetail.setArguments(unBundle);
        return fragmentnewsDetail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        Bundle bundle = getArguments();

        String aChannel = bundle.getString(CHANNEL);
        String aDescription = bundle.getString(DESCRIPTIONKEY);
        String aPhoto = bundle.getString(PHOTOKEY);
        final String aNombre = bundle.getString(NOMBREKEY);
        final String aUrl = bundle.getString(URL);

        TextView textViewChannel = (TextView) view.findViewById(R.id.textViewCategoriaDetalle);
        TextView textViewDescription = (TextView) view.findViewById(R.id.textViewDescripcionDetalle);
        ImageView imageViewPhoto = (ImageView) view.findViewById(R.id.imageViewFotoDetalle);
        TextView textViewNombre = (TextView) view.findViewById(R.id.textViewNombreDetalle);

        textViewChannel.setText(aChannel);
        textViewDescription.setText(aDescription);
        textViewNombre.setText(aNombre);
        Picasso.with(view.getContext())
                .load(aPhoto)
                .placeholder(R.drawable.loading1)
                .into(imageViewPhoto);

        //sets fonts for this fragment
        final Typeface robotoItalic = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Italic.ttf");
        final Typeface robotoRegular = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
        textViewNombre.setTypeface(robotoRegular);
        textViewChannel.setTypeface(robotoRegular);
        textViewDescription.setTypeface(robotoRegular);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = aUrl;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, aNombre);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.share_email_subject));

        return view;
    }


}
