package com.example.usuario.reciclernuevo.View.adapters;

import android.content.Context;
import android.graphics.Typeface;
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
import com.example.usuario.reciclernuevo.View.fragments.FragmentFavorites;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.util.List;


public class AdapterRecyclerFav extends RecyclerView.Adapter implements View.OnClickListener {

    private List<News> listaNewsFavorites;
    private Context context;
    private View.OnClickListener myListener;
    private ControllerNews controllerNews;

    //constructor
    public AdapterRecyclerFav(List<News> listaNewsFavorites, Context context, View.OnClickListener myListener) {
        this.listaNewsFavorites = listaNewsFavorites;
        this.context = context;
        this.myListener = myListener;
    }

    //setters, getters
    public void setListaNewsFavorites(List<News> listaNewsFavorites) {
        this.listaNewsFavorites = listaNewsFavorites;
    }

    public void setListener (View.OnClickListener listener) {
        myListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.fragment_recycler_view_news,parent,false);
        ViewHolderFavoritos viewHolder = new ViewHolderFavoritos(view);
        view.setOnClickListener(myListener);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        News news = getItem(position);
        ViewHolderFavoritos myviewHolder = (ViewHolderFavoritos) holder;
        myviewHolder.cargarDatos(news);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listaNewsFavorites.size();
    }
    public News getItem(Integer position) {
        //aqui llega la posicion sin problemas
        return listaNewsFavorites.get(position);
    }

    @Override
    public void onClick(View v) {
        if (myListener != null) {
            myListener.onClick(v);
        }
    }

    public class ViewHolderFavoritos extends RecyclerView.ViewHolder{

        private TextView textViewNombre;
        private TextView textViewCategoria;
        private TextView textViewDescripcion;
        private ImageView imageViewFoto;
        private MaterialFavoriteButton favorite;

        public ViewHolderFavoritos(final View itemView) {
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
            if (controllerNews.chequeadorFavoritos(context, unNews) == true) {
                favorite.setFavorite(true);
            } else {
                favorite.setFavorite(false);
            }

            favoriteActions(favorite,unNews);
            Picasso.with(itemView.getContext())
                    .load(unNews.getImagen())
                    .placeholder(R.drawable.loading1)
                    .into(imageViewFoto);
        }
    }

    public void favoriteActions(MaterialFavoriteButton favorite, final News unNews) {
        favorite.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (favorite) {
                        } else {
                            listaNewsFavorites.remove(unNews);
                            notifyDataSetChanged();
                            unNews.setFavorited(false);
                            controllerNews.favoriteador(context, unNews);
                        }
                    }
                });
    }

}
