package cl.sulcansystem.pruebaperritos.view;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cl.sulcansystem.pruebaperritos.R;
import cl.sulcansystem.pruebaperritos.databinding.FragmentDetailBinding;
import cl.sulcansystem.pruebaperritos.model.BreedModel;
import cl.sulcansystem.pruebaperritos.presenter.PresenterDetail;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment implements PresenterDetail.IPresenterViewImages {
    private static final String ARG_PARAM1 = "raza";

    private String raza;
    private String subRaza;
    private OnLongClickPerritos listener;
    RecyclerView recyclerView;
    private FragmentDetailBinding detailBinding;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String raza) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, raza);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            raza = getArguments().getString(ARG_PARAM1);
            Log.d("DDD", "la raza es En DetailFragment" + raza);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        detailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);

        Context context = detailBinding.getRoot().getContext();
        recyclerView = detailBinding.recyclerViewDetail;
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        listener = (OnLongClickPerritos) context;
        //Enlace entre vista y presentador
        PresenterDetail presentador = new PresenterDetail(this);
        presentador.setImodel(new BreedModel(presentador));


        if (raza.contains(" ")){
            subRaza = raza.split(" ")[1];
            raza = raza.split(" ")[0];
            Log.d("DDD", "raza cortado " + raza);
            Log.d("DDD", "subRaza cortado " + subRaza);
            presentador.loadSubBreedImages(raza, subRaza);
            detailBinding.setRaza(raza);
            detailBinding.setSubraza(subRaza);
        }else{
            presentador.loadBreedImages(raza);
            detailBinding.setRaza(raza);
            detailBinding.setSubraza("");
        }


        return detailBinding.getRoot();
    }

    @Override
    public void notificar(List<String> lista) {
        Log.d("DDD", "En Adapter "+lista);
        DogPhotoRecycleViewAdapter myAdaptador = new DogPhotoRecycleViewAdapter(lista, listener);
        recyclerView.setAdapter(myAdaptador);
    }

    public interface OnLongClickPerritos {
        void OnLongClickPerritos(String urlImagen);
    }
}
