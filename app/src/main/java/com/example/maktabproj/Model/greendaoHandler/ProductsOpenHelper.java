package com.example.maktabproj.Model.greendaoHandler;

import android.content.Context;

import com.example.maktabproj.Model.entities.DaoMaster;

public class ProductsOpenHelper extends DaoMaster.OpenHelper {
    public static final String DATABASE_NAME = "buy_card_db";

    public ProductsOpenHelper(Context context) {
        super(context, DATABASE_NAME);
    }
}
