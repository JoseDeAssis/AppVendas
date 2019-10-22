package com.example.appvendas.Model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import com.example.appvendas.Entity.ProductGroup;
import com.example.appvendas.R;
import com.example.appvendas.Repository.ProductGroupRepository;

public class ProductGroupViewModel extends AndroidViewModel {

    private ProductGroup[] productGroupList = {new ProductGroup("Livros", R.drawable.product_group_book_icon, false),
            new ProductGroup("Celulares", R.drawable.product_group_phone_icon, false),
            new ProductGroup("TV, Áudio e Home Theater", R.drawable.product_group_tv_icon, false),
            new ProductGroup("Informática", R.drawable.product_group_computer_icon, false),
            new ProductGroup("Pet", R.drawable.product_group_pet_icon, false),
            new ProductGroup("Esporte e lazer", R.drawable.product_group_sport_icon, false),
            new ProductGroup("Eletrodomésticos e casa", R.drawable.product_group_eletrodomestico_icon, false),
            new ProductGroup("Games", R.drawable.product_group_games_icon, false),
            new ProductGroup("Viagens", R.drawable.product_group_viagens_icon, false),
            new ProductGroup("Automóveis", R.drawable.product_group_automovel_icon, false),
            new ProductGroup("Comida e bebida", R.drawable.product_group_food_icon, false),
            new ProductGroup("Bebês e crianças", R.drawable.product_group_child_icon, false),
            new ProductGroup("Outros", R.drawable.product_group_others_icon, false)};

    public ProductGroupViewModel(Application application) {
        super(application);
    }

    public ProductGroup[] getAllProducts() {
        return productGroupList;
    }

}
