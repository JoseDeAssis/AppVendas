package com.example.appvendas.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvendas.Adapter.OrderListRVAdapter;
import com.example.appvendas.Entity.ItemWithOrder;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.Interface.OnOrderDetailsListener;
import com.example.appvendas.Helpers.Interface.OnProductDetailsListener;
import com.example.appvendas.R;
import com.example.appvendas.Repository.ItemWithOrderRepository;
import com.example.appvendas.Repository.OrderWithItemsAndProductsRepository;
import com.example.appvendas.Repository.ProductRepository;

import java.util.List;

public class AppVendasHistoricoTab extends Fragment implements OnOrderDetailsListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historico_tab, container, false);

        //Criando Recycler view de histórico
        RecyclerView appVendasProdutosRecyclerView = view.findViewById(R.id.historicoTabRecyclerView);

        //Criando adapter de histórico
        final OrderListRVAdapter adapter = new OrderListRVAdapter(getContext(), this);

        appVendasProdutosRecyclerView.setAdapter(adapter);
        appVendasProdutosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Criando os repositórios de ItemWithOrder e Product
        ItemWithOrderRepository itemWithOrderRepository = new ItemWithOrderRepository(getActivity().getApplication());

        itemWithOrderRepository.getItemSummary().observe(this, new Observer<List<ItemWithOrder>>() {
            @Override
            public void onChanged(List<ItemWithOrder> items) {
                adapter.setItemsList(items);
            }
        });

        return view;
    }

    @Override
    public void getOrderDetails(ItemWithOrder itemWithOrder) {

    }
}
