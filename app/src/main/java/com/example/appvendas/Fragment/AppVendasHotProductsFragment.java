package com.example.appvendas.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appvendas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppVendasHotProductsFragment extends Fragment {


    public AppVendasHotProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_vendas_hot_products, container, false);
    }

}
