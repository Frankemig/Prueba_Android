package cl.sulcansystem.pruebaperritos.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class FirebaseModel  implements IFirebaseModel{

    private static final String TAG = "AAA";
    protected IPresenterDB iPresenterDB;
    protected FirebaseFirestore db = FirebaseFirestore.getInstance();
    protected CollectionReference midb = db.collection("DBPruebaAndroid");

    public FirebaseModel(IPresenterDB iPresenterDB) {
        this.iPresenterDB = iPresenterDB;
    }

    @Override
    public void addFavImagenes(String url) {
        Log.d(TAG, "En FirebaseModel, addFavImagenes(), url " + url);
        ImagenesFavoritas imagen = new ImagenesFavoritas(url);
        midb.add(imagen).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "En FirebaseModel, addFavImagenes(), falla " + e);
            }
        });
    }

    @Override
    public void getFavImagenes() {
        /*
            En el siguiente fragmento de código se obtiene la información del a base de datos.
            Al ponder midb.get() se realiza una query que le pide a la base de datos los documentos
            debido a que es una query, esta se realiza de forma asíncrona, es decir, se ejecuta en
            paralelo con el resto del código, por lo que se tiene que esperar a que se complete.
            Para poder obtener el resultado de de esa query, se agrega un listener, en este caso
            corresponde al addOnCompleteListener que se ejecuta al completar la operación
         */
        midb.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                /*
                   Al completar la operación se obtiene un objeto del tipo Task, que corresponde a la tarea
                   que se debe realizar, en este caso, la query que contiene los datos, eso es el objeto  QuerySnapshot
                   contenido dentro del Task. Para extraer el resultado se aplica el método getResult(), que a su vez
                   se le extrae el contenido con getDocuments(), que contiene una lista de documentos. Los documentos
                   son objetos que cotienen la información pedida a la base de datos.
                   A esta lista se le saca el primer elemento para mostrar que se obtuvo la data con get(0),
                   que a su vez se le extrae la información con getData(), lo cual muestra un mapa con la información
                   obtenida de la base de datos.
                 */
                Log.d(TAG, "En FirebaseModel, getFavImagenes(), Data " + task.getResult().getDocuments().get(0).getData());
                iPresenterDB.notificarFavImagenes(task.getResult().getDocuments());
            }
        });

    }

}
