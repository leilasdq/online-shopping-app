package com.example.maktabproj.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.maktabproj.Model.entities.DaoMaster;
import com.example.maktabproj.Model.entities.DaoSession;
import com.example.maktabproj.Model.entities.Products;
import com.example.maktabproj.Model.entities.ProductsDao;
import com.example.maktabproj.Model.greendaoHandler.ProductsOpenHelper;

import java.util.List;

public class BuyingCardRepository {

    private static BuyingCardRepository ourInstance;
    private Context mContext;
    private static ProductsDao mProductsDao;

    public static BuyingCardRepository getInstance(Context context) {
        if (ourInstance == null){
            ourInstance = new BuyingCardRepository(context);
        }
        return ourInstance;
    }

    private BuyingCardRepository(Context context){
        mContext = context.getApplicationContext();

        SQLiteDatabase database =new ProductsOpenHelper(mContext).getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        mProductsDao = daoSession.getProductsDao();
    }

    public List<Products> getAllBuyingProducts(){
        List<Products> allProducts = mProductsDao.queryBuilder().list();
        return allProducts;
    }

    public void addTodoItem (Products products) {
        mProductsDao.insert(products);
    }

    public void deleteItem (int id){
        List<Products> allItems = this.getAllBuyingProducts();
        Products products = null;
        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).getIdInSite() == id){
                products = allItems.get(i);
            }
        }
        if (products!=null)
        mProductsDao.delete(products);
    }

    public void editItem(int id, int number){
        List<Products> allItems = this.getAllBuyingProducts();
        Products products = null;
        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).getIdInSite() == id){
                products = allItems.get(i);
            }
        }
        if (products!=null)
            products.setCounts(number);
            mProductsDao.update(products);
    }
}
