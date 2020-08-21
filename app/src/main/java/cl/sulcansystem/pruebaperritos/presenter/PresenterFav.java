package cl.sulcansystem.pruebaperritos.presenter;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import cl.sulcansystem.pruebaperritos.model.IFirebaseModel;
import cl.sulcansystem.pruebaperritos.model.IPresenterDB;

public class PresenterFav implements IPresenterFav, IPresenterDB {

    private List<String> favoritas = new ArrayList<String>();
    private static final String TAG = "AAA";
    private IFirebaseModel iFirebaseModel;
    private IPresenterFavView iPresenterFavView;

    public PresenterFav() {
    }

    public PresenterFav(IPresenterFavView iPresenterFavView) {
        this.iPresenterFavView = iPresenterFavView;
    }

    public void setiFirebaseModel(IFirebaseModel iFirebaseModel) {
        this.iFirebaseModel = iFirebaseModel;
    }

    @Override
    public void showFavBreedImages() {
        Log.d(TAG, "En PresenterFav, showFavBreedImages()");
        iFirebaseModel.getFavImagenes();
    }

    @Override
    public void addFavBreedImagen(String url) {
        Log.d(TAG, "En PresenterFav, addFavBreedImagen() " + url);
        iFirebaseModel.addFavImagenes(url);
    }

    @Override
    public void notificarFavImagenes(List<DocumentSnapshot> imagenesFav) {
        for (DocumentSnapshot doc:imagenesFav){
            for (String key : doc.getData().keySet()){
                favoritas.add(doc.getData().get(key).toString());
            }
        }

        Log.d(TAG, "En PresenterFav, notificarFavImagenes() " + favoritas);
        iPresenterFavView.notificarFav(favoritas);
    }

    public interface IPresenterFavView{
        void notificarFav(List<String> urls);
    }
}
