package cl.sulcansystem.pruebaperritos.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import cl.sulcansystem.pruebaperritos.R;
import cl.sulcansystem.pruebaperritos.databinding.ItemImagenesBinding;

/*
    El Adapter es una clase que extiende de RecyclerView.Adapter< aquí va el ViewHolder > que
     a su vez recibe un Objeto denominado ViewHolder.Queda como se ve abajo

    public class DogPhotoRecycleViewAdapter  extends RecyclerView.Adapter<DogPhotoRecycleViewAdapter.ViewHolderImages>

    En este caso se denomina al ViewHolder "ViewHolderImages" y dice que es parte de la clase DogPhotoRecycleViewAdapter
    por lo tanto Android Studio nos obligará a crear la clase ViewHolderImages dentro de esta clase. Además por extender
    de RecyclerView.Adapter, nos obligará a implementar 3 clases más, que son onCreateViewHolder, onBindViewHolder y getItemCount.
    Además en la clase ViewHolderImages nos obligará a extender de RecyclerView.ViewHolder para que sea considerado un ViewHolder
    y por extender de ViewHolder nos va a obligar a implementar un constructor.

    Por último tenemos que crear un constructor para la clase adapter y con eso queda lista la estructura del Adapter
 */
public class DogPhotoRecycleViewAdapter  extends RecyclerView.Adapter<DogPhotoRecycleViewAdapter.ViewHolderImages> {

    private ImageView imagenes;
    private final String TAG = "DDD";
    private List<String> mUrls;
    DetailFragment.OnLongClickPerritos mListener;

    public DogPhotoRecycleViewAdapter(List<String> mUrls, DetailFragment.OnLongClickPerritos mListener) {
        this.mUrls = mUrls;
        this.mListener = mListener;
    }

    @Override
    public String toString() {
        return "DogPhotoRecycleViewAdapter{" +
                "mUrls=" + mUrls +
                ", mListener=" + mListener +
                '}';
    }

    @NonNull
    @Override
    public DogPhotoRecycleViewAdapter.ViewHolderImages onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImagenesBinding itemImagenesBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_imagenes, parent, false);

        return new ViewHolderImages(itemImagenesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DogPhotoRecycleViewAdapter.ViewHolderImages holder, int position) {
        Log.d(TAG, "onBindDetail");
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(holder.perrito);

        Glide.with(holder.itemImagenesBinding.getRoot().getContext()) //3
                .load(mUrls.get(position))
                .centerCrop()
                .into(holder.itemImagenesBinding.imageView);


    }

    @Override
    public int getItemCount() {
        return mUrls.size();
    }

    private String getUrl(int position){
        return position != RecyclerView.NO_POSITION ? mUrls.get(position) : "No";
    }

    public class ViewHolderImages extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        public final ItemImagenesBinding itemImagenesBinding;
        public ViewHolderImages(@NonNull ItemImagenesBinding itemImagenesBinding) {
            super(itemImagenesBinding.getRoot());
            this.itemImagenesBinding = itemImagenesBinding;
            this.itemImagenesBinding.getRoot().setOnLongClickListener(this);
            this.itemImagenesBinding.imageView.setOnLongClickListener(this);
        }



        @Override
        public boolean onLongClick(View v) {
            Log.d(TAG, "onLongClick entrando...");
            Log.d(TAG, getUrl(getAdapterPosition()));
            mListener.OnLongClickPerritos(getUrl(getAdapterPosition()));
            return false;
        }
    }

}
