package com.example.appvendas.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.appvendas.Activitity.AppVendasMainActivity;
import com.example.appvendas.Fragment.AppVendasDestaquesTab;
import com.example.appvendas.Fragment.AppVendasHistoricoTab;
import com.example.appvendas.Fragment.AppVendasProdutosTab;

public class AppVendasTabAdapter extends FragmentPagerAdapter {

    public AppVendasTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AppVendasDestaquesTab();

            case 1:
                return new AppVendasProdutosTab();

            case 2:
                return new AppVendasHistoricoTab();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Destaques";

            case 1:
                return "Produtos";

            case 2:
                return "Histórico";

            default:
                return null;
        }
    }
}
