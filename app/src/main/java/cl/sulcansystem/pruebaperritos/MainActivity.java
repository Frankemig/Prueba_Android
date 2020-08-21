package cl.sulcansystem.pruebaperritos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cl.sulcansystem.pruebaperritos.databinding.ActivityMainBinding;
import cl.sulcansystem.pruebaperritos.model.FirebaseModel;
import cl.sulcansystem.pruebaperritos.presenter.PresenterFav;
import cl.sulcansystem.pruebaperritos.view.DetailFragment;
import cl.sulcansystem.pruebaperritos.view.FavoritosFragment;
import cl.sulcansystem.pruebaperritos.view.ListDogFragment;

public class MainActivity extends AppCompatActivity implements ListDogFragment.OnListFragmentInteractionListener, DetailFragment.OnLongClickPerritos {
    //TAG de los Log.d para poder identificarlos
    private static final String TAG = "AAA";
    /*
    Se crea una isntancia del FragmentManager para ser utilizado en todos los métodos del MainActivity
    El FragmentManager es una clase que guarda los fragmentos que se agregan a él y las transacciones
     y permite realizar operaciones
    sobre ellos, tales como removerlos o reemplazar uno por otro en las actividades
    Las transacciones son operaciones de fagmentos que se ejecutan cuando se commitean, luego de eso no se pueden usar.

     */
    FragmentManager fragmentManager = getFragmentManager();
    FloatingActionButton fab;
    //Se crean variables de fragmento de lista de razas y detalle de raza para utilizar las mismas en cada método de la actividad
    ListDogFragment fragLista;
    DetailFragment fragDetalle;
    FavoritosFragment fragFav;
    ActivityMainBinding mainBinding;
    protected PresenterFav presenterFav;


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("paso", "poraqui");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Log.d(TAG, "Listo" + savedInstanceState);

        if (savedInstanceState == null) {
            //Se inicia una nueva trasacción
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //Se crea una isntancia de fragmento de lista de raza y se asigna al atributo de clase
            this.fragLista = ListDogFragment.newInstance(1);
            //Se agrega el fragmento junto con el contenedor asociado y una tag para identificarlo al FragmentManager
            fragmentTransaction.add(R.id.frame_container, fragLista, "lista");
            //Se commitean las operaciones de la transacción y se agrega la misma al FragmentManager con .addToBackStack
            fragmentTransaction.commit();
            Log.d(TAG, "Listo" + fragmentTransaction);
        }
        mainBinding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainBinding.floatingActionButton.setVisibility(View.GONE);

                FragmentTransaction fragmentTransactionFav = fragmentManager.beginTransaction();
                fragFav = FavoritosFragment.newInstance();
                fragmentTransactionFav.replace(R.id.frame_container, fragFav, "listaFav");
                fragmentTransactionFav.addToBackStack("listaFav").commit();

            }
        });
    }

    //Este es el listener de la actividad, este método se ejecuta cuando se hace un click sobre una raza
    @Override
    public void onListFragmentInteraction(String raza) {
        Log.d(TAG, raza);

        //Se crea una nueva transacción
        FragmentTransaction fragmentTransactionListener = fragmentManager.beginTransaction();
        //Se elimina la el fragmento del FragmenManager
        fragmentTransactionListener.remove(this.fragLista);

        fragDetalle = DetailFragment.newInstance(raza);
        //Se agrega el fragmento al FragmentManager
        fragmentTransactionListener.add(R.id.frame_container, fragDetalle, "detalle");
        //Se Agrega la transacción al FragmentManager con el nombre "detalle" para identificarlo y se commitea
        fragmentTransactionListener.addToBackStack("detalle").commit();

    }
    /*
    Aquí se busca programar las acciones que se realizan por la app cuando se apreta el botón voler del celular
    Esta parte del código todavía no me queda muy claro como funciona.
    El método onBackPressed() es parte del ciclo de vida de las actividades por lo que se implementa a partir
    de la clase AppCompatActivity de la cual extiende esta clase, por eso tiene @Override encima.

     */



    @Override
    public void onBackPressed()
    {
        /*Crea variable entera con la cantidad de transacciones en el BackStack.
        La idea es que si tiene más de 0 es porque la applicación se encuentra mostrando el detalle de la raza
        por que ese fragmento se muestra después del otro, si el valor es 0 debería estar en el inicio
        por lo tanto se debería cerrar la aplicación
        */
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            Log.d(TAG, "En fragLista");
            //finish() es para cerrar la aplicación
            finish();
        } else {
            mainBinding.floatingActionButton.setVisibility(View.VISIBLE);
            //En cualquier otro case se asume que se esta mostrando el fragmento detalle,.
            Log.d(TAG, "En detalle");
            //Se muestra vuelve a realizar la última transacción
            getFragmentManager().popBackStack();
            Log.d(TAG, "count" + getFragmentManager().getBackStackEntryCount());
        }
    }

    @Override
    public void OnLongClickPerritos(String urlImagen) {
        Log.d("DDD", "En ONLongClickPerritos en Main");
        Toast.makeText(this, "<3", Toast.LENGTH_LONG).show();
        presenterFav = new PresenterFav();
        presenterFav.setiFirebaseModel(new FirebaseModel(presenterFav));
        presenterFav.addFavBreedImagen(urlImagen);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
