package com.example.appvendas.Fragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appvendas.Activitity.AppVendasMainActivity;
import com.example.appvendas.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppVendasHomeFragment extends Fragment {


    public AppVendasHomeFragment() {
        // Required empty public constructor
    }


    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_vendas_home, container, false);

        return view;
    }

}
