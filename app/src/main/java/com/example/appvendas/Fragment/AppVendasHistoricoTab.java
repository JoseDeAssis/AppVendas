package com.example.appvendas.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvendas.Activitity.AppVendasOrderDetailsActivity;
import com.example.appvendas.Activitity.AppVendasProductDetailsActivity;
import com.example.appvendas.Adapter.OrderListRVAdapter;
import com.example.appvendas.Entity.ItemWithOrder;
import com.example.appvendas.Helpers.Interface.OnOrderDetailsListener;
import com.example.appvendas.R;
import com.example.appvendas.Repository.ItemWithOrderRepository;

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
    public void getOrderOptionsDetails(final ItemWithOrder itemWithOrder, View view) {
        Context wrapper = new ContextThemeWrapper(getActivity(), R.style.app_vendas_popup_menu_style);

        PopupMenu popupMenu = new PopupMenu(wrapper, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.productOrderDetail:
                        intent = new Intent(getActivity(), AppVendasProductDetailsActivity.class);
                        intent.putExtra("productId", itemWithOrder.getProduct().getId());
                        intent.putExtra("parentName", this.getClass().toString());
                        startActivity(intent);
                        return true;

                    case R.id.orderDetail:
                        intent = new Intent(getActivity(), AppVendasOrderDetailsActivity.class);
                        intent.putExtra("orderId", itemWithOrder.getOrder().getId());
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
        popupMenu.getMenuInflater().inflate(R.menu.app_vendas_product_order_menu, popupMenu.getMenu());
        popupMenu.show();
    }
}