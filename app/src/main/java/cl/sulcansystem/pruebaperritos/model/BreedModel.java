package cl.sulcansystem.pruebaperritos.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cl.sulcansystem.pruebaperritos.model.api.IDogDataBase;
import cl.sulcansystem.pruebaperritos.model.api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
    El BreedModel es la clase que realiza las llamadas para obtener los datos de la API a través de Retrofit
 */
public class BreedModel implements IModel {
    //Esta es la interfaz que se utiliza como Callback para enviar información al Presenter y está implementado en Presenter
    IPresenterModel iPresenterModel;
    private static final String TAG = "AAA";

    //Constructor de clase BreedModel para asignarlo una instancia al Presenter
    public BreedModel(IPresenterModel iPresenterModel) {
        this.iPresenterModel = iPresenterModel;
    }

    //Implementación de métodos de IModel, en este caso este método se utiliza para obtener todas las razas de perros
    @Override
    public void loadBreeds() {
        /*
        Este método usa Retrofit para obtener los datos, para esto implementa una llamada de Retrofit
        de la forma estándar, inst
         */
        Log.d(TAG, "En BreedModel loadBreeds");
        //Se crea una instancia de la clase autogenerada de llamadas IDogDataBase llamada servicio a través de la interfaz
        IDogDataBase servicio = RetrofitClient.getRetrofitInstance().create(IDogDataBase.class);
        //Se crea una instancia de la clase Call llamada listCall que recibe como parámetros la clase Pojo RazasLista
        Call<RazasLista> listCall = servicio.listaRazas();
        //Se crea una lista para recibir los datos
        List<String> listaPerros = new ArrayList<>();
        //Se en cola la llamada a través de listCall
        listCall.enqueue(new Callback<RazasLista>() {
            @Override
            public void onResponse(Call<RazasLista> call, Response<RazasLista> response) {
                Log.d(TAG, "En BreedModel loadBreeds respuesta correcta");
                RazasLista listaRazas = response.body();
                Map<String, List<String>> mapa = listaRazas.getMessage();
                for (String key : mapa.keySet()) {
                    if (mapa.get(key).isEmpty()) {
                        listaPerros.add(key);
                    } else {
                        for (String subRaza : mapa.get(key)) {
                            listaPerros.add(key + " " + subRaza);
                        }
                    }

                }
                Log.d(TAG, "En BreedModel loadBreeds respuesta correcta lista" + listaPerros);
                iPresenterModel.notificar(listaPerros);
            }

            @Override
            public void onFailure(Call<RazasLista> call, Throwable t) {
                Log.d(TAG, "En BreedModel loadBreeds fallamos", t);
            }
        });


    }

    @Override
    public void loadImages(String raza) {
        Log.d("DDD", "Entrando al load images En el BreedModel"+raza);
        IDogDataBase servicio = RetrofitClient.getRetrofitInstance().create(IDogDataBase.class);

        Call<RazaImagen> listCall = servicio.listaImagenes(raza);
        List<String> listaFotosPerros = new ArrayList<>();

        listCall.enqueue(new Callback<RazaImagen>() {
            @Override
            public void onResponse(Call<RazaImagen> call, Response<RazaImagen> response) {
                RazaImagen listaRazas = response.body();
                List<String> lista = listaRazas.getMessage();

                Log.d("DDD", "En modelo "+lista.toString());
                iPresenterModel.notificar(lista);

            }

            @Override
            public void onFailure(Call<RazaImagen> call, Throwable t) {
                Log.d("DDD", "Fallamos");
                Log.e("DDD", t.toString());
            }
        });
    }

    @Override
    public void loadImagesSubraza(String raza, String subRaza) {
        Log.d("DDD", "Entrando al load images subRaza En el BreedModel "+raza);
        IDogDataBase servicio = RetrofitClient.getRetrofitInstance().create(IDogDataBase.class);

        Call<RazaImagen> listCall = servicio.listaImagenesSubraza(raza, subRaza);
        List<String> listaFotosPerros = new ArrayList<>();

        listCall.enqueue(new Callback<RazaImagen>() {
            @Override
            public void onResponse(Call<RazaImagen> call, Response<RazaImagen> response) {
                RazaImagen listaRazas = response.body();
                List<String> lista = listaRazas.getMessage();

                Log.d("DDD", "En modelo "+lista.toString());
                iPresenterModel.notificar(lista);

            }

            @Override
            public void onFailure(Call<RazaImagen> call, Throwable t) {
                Log.d("DDD", "Fallamos");
                Log.e("DDD", t.toString());
            }
        });
    }

}
