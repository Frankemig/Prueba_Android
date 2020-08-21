package cl.sulcansystem.pruebaperritos.view;

import android.content.Context;
import android.os.Bundle;

import android.app.Fragment;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cl.sulcansystem.pruebaperritos.databinding.FragmentPerritosListBinding;
import cl.sulcansystem.pruebaperritos.model.BreedModel;
import cl.sulcansystem.pruebaperritos.presenter.Presenter;
import cl.sulcansystem.pruebaperritos.R;

public class ListDogFragment extends Fragment implements Presenter.IPresenterViewList {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private static final String TAG = "AAA";
    RecyclerView recyclerView;
    private FragmentPerritosListBinding listBiding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListDogFragment() {
    }

    public static ListDogFragment newInstance(int columnCount) {
        ListDogFragment fragment = new ListDogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        Log.d(TAG, "En newInstance de ListDogFragment");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listBiding = DataBindingUtil.inflate(inflater, R.layout.fragment_perritos_list,container, false);

        // Set the adapter
        Log.d(TAG, "En onCreateView de ListDogFragment");
        if (listBiding.getRoot() instanceof RecyclerView) {
            Context context = listBiding.getRoot().getContext();
            mListener = (OnListFragmentInteractionListener) context;
            recyclerView = (RecyclerView) listBiding.getRoot();
            if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            Log.d(TAG, "En onCreateView de ListDogFragment después de crear el recycler");
            Presenter presentador = new Presenter(this);
            presentador.setImodel(new BreedModel(presentador));
            presentador.loadBreeds();
            Log.d(TAG, "En onCreateView de ListDogFragment después de presentador.loadBreeds()");
        }
        Log.d(TAG, "En onCreateView de ListDogFragment antes del return");
        return listBiding.getRoot();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void notificar(List<String> lista) {
        Log.d(TAG, ""+lista);
        MyDogRecyclerViewAdapter myAdaptador = new MyDogRecyclerViewAdapter(lista, mListener );
        recyclerView.setAdapter(myAdaptador);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(String raza);
    }
}
