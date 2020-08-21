package cl.sulcansystem.pruebaperritos.presenter;

import android.util.Log;

import java.util.List;
import cl.sulcansystem.pruebaperritos.model.IModel;
import cl.sulcansystem.pruebaperritos.model.IPresenterModel;

public class PresenterDetail implements IPresenterDetail, IPresenterModel {

    IPresenterViewImages iPresenterViewImages;
    IModel imodel;

    public PresenterDetail(IPresenterViewImages presenter) {
        this.iPresenterViewImages = presenter;
    }

    public void setImodel(IModel imodel) {
        this.imodel = imodel;
    }

    @Override
    public void loadBreedImages(String breed) {
        imodel.loadImages(breed);
        Log.d("AAA", "loadIMages En Presenter " + breed);
    }

    @Override
    public void loadSubBreedImages(String breed, String subBreed) {
        imodel.loadImagesSubraza(breed, subBreed);
        Log.d("AAA", "loadSubBreedIMages En Presenter");
    }
    /*
    Implementación de IPresenterModel
     */
    @Override
    public void notificar(List<String> urls) {
        Log.d("DDD", "En Presentador" + urls.toString());
        iPresenterViewImages.notificar(urls);
    }

    //Interfaz de comunicación con las vistas
    public interface IPresenterViewImages {
        void notificar(List<String> lista);
    }
}
