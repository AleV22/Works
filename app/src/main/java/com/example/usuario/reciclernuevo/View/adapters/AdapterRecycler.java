package com.example.usuario.reciclernuevo.View.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.reciclernuevo.Controller.ControllerNews;
import com.example.usuario.reciclernuevo.Model.POJO.News;
import com.example.usuario.reciclernuevo.R;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterRecycler extends RecyclerView.Adapter implements View.OnClickListener{

    private List<News> listaNews;
    private Context context;
    private View.OnClickListener myListener;
    private Favoriteable favoriteable;
    private ControllerNews controllerNews;

    //cosntructor
    public AdapterRecycler(List<News> listaNews, Context context, Fragment fragment) {
        this.listaNews = listaNews;
        this.context = context;
        favoriteable = (Favoriteable) fragment;
    }

    //setters, getters
    public void setListaNews(List<News> listaNews) {
        this.listaNews = listaNews;
    }
    public void setListener (View.OnClickListener listener) {
        myListener = listener;
    }

    //recycler methods
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (viewType < 3){
            View view = layoutInflater.inflate(R.layout.fragment_recycler_view_news,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            view.setOnClickListener(myListener);
            return viewHolder;
        } else {
            View view = layoutInflater.inflate(R.layout.layout_second,parent,false);
            ViewHolder2 viewHolder2 = new ViewHolder2(view);
            view.setOnClickListener(myListener);
            return viewHolder2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        News news = getItem(position);
        if(holder.getItemViewType()<3){
            ViewHolder myviewHolder = (ViewHolder) holder;
            myviewHolder.cargarDatos(news);
        }else{
            ViewHolder2 viewHolder2 = (ViewHolder2) holder;
            viewHolder2.cargarDatos(news);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listaNews.size();
    }

    public News getItem(Integer position) {
        return listaNews.get(position);
    }

    @Override
    public void onClick(View v) {
        if (myListener != null) {
            myListener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewNombre;
        private TextView textViewCategoria;
        private TextView textViewDescripcion;
        private ImageView imageViewFoto;
        private MaterialFavoriteButton favorite;

        //el viewHolder le dice a la activity que haga el cambio de fragment,
        public ViewHolder(final View itemView) {
            super(itemView);
            textViewNombre = (TextView) itemView.findViewById(R.id.textViewNombre);
            textViewCategoria = (TextView) itemView.findViewById(R.id.textViewCategoria);
            textViewDescripcion = (TextView) itemView.findViewById(R.id.textViewDescripcion);
            imageViewFoto = (ImageView) itemView.findViewById(R.id.imageViewFoto);
            favorite = (MaterialFavoriteButton) itemView.findViewById(R.id.materialFavoriteButton);
            final Typeface robotoRegular = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/Roboto-Regular.ttf");
            textViewNombre.setTypeface(robotoRegular);
        }

        public void cargarDatos(final News unNews){
            textViewNombre.setText(unNews.getTitulo());
            textViewCategoria.setText(unNews.getChannel());
            textViewDescripcion.setText(unNews.getDescripcion());

            controllerNews = new ControllerNews();
            //method that sets the star according to DB
            if (controllerNews.chequeadorFavoritos(context, unNews) && FirebaseAuth.getInstance().getCurrentUser()!= null) {
                favorite.setFavorite(true);
            } else {
                favorite.setFavorite(false);
            }

            //this sets the listener for each favoriteButton
            favoriteActions(favorite,unNews);

            if (unNews.getImagen()!=null && unNews.getImagen().length()>0){
            Picasso.with(itemView.getContext())
                    .load(unNews.getImagen())
                    .error(R.drawable.loading1)
                    .placeholder(R.drawable.loading1)
                    .into(imageViewFoto);
            }
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        private TextView textViewNombre;
        private TextView textViewCategoria;
        private ImageView imageViewFoto;
        private MaterialFavoriteButton favorite;

        public ViewHolder2(View itemView) {
            super(itemView);
            textViewNombre = (TextView) itemView.findViewById(R.id.textViewNombre);
            textViewCategoria = (TextView) itemView.findViewById(R.id.textViewCategoria);
            imageViewFoto = (ImageView) itemView.findViewById(R.id.imageViewFoto);
            favorite = (MaterialFavoriteButton) itemView.findViewById(R.id.materialFavoriteButton);

            final Typeface robotoRegular = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/Roboto-Regular.ttf");
            textViewNombre.setTypeface(robotoRegular);
        }
        public void cargarDatos(final News unNews){
            textViewNombre.setText(unNews.getTitulo());
            textViewCategoria.setText(unNews.getChannel());

            controllerNews = new ControllerNews();
            //method that sets the star according to DB
            if (controllerNews.chequeadorFavoritos(context, unNews) && FirebaseAuth.getInstance().getCurrentUser()!= null) {
                favorite.setFavorite(true);
            } else {
                favorite.setFavorite(false);
            }
            //this sets the listener for each favoriteButton
            favoriteActions(favorite,unNews);

            Picasso.with(itemView.getContext())
                    .load(unNews.getImagen())
                    .placeholder(R.drawable.loading1)
                    .into(imageViewFoto);
        }
    }

    public interface Favoriteable{
        public void setFavorito();
        public void setFavorito2(News news);
    }

    //method for Favorite Button, for each ViewHolder
    public void favoriteActions(MaterialFavoriteButton favorite, final News unNews) {
        favorite.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        Favoriteable favoriteable = (Favoriteable) context;
                        //this sets the Boolean favorited in each news
                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                            if (favorite) {
                                unNews.setFavorited(true);
                                controllerNews = new ControllerNews();
                                controllerNews.favoriteador(context, unNews);
                            } else {
                                unNews.setFavorited(false);
                                controllerNews.favoriteador(context, unNews);
                            }
                        } else {
                            //need to login first using the interface
                            favoriteable.setFavorito2(unNews);
                            buttonView.setFavorite(false);
                            Toast.makeText(context, "Log in to enjoy your favorites", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}






