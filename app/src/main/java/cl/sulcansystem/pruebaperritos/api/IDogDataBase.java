package cl.sulcansystem.pruebaperritos.api;

import cl.sulcansystem.pruebaperritos.model.RazasLista;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IDogDataBase {
    @GET("api/breeds/list/all")
    Call<RazasLista> listaRazas();
}
