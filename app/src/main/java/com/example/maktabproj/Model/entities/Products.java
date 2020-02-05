package com.example.maktabproj.Model.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity (nameInDb = "buying_products")
public class Products {

    @Id (autoincrement = true)
    private Long _proId;
    @Property (nameInDb = "site_id")
    private int mIdInSite;
    @Property (nameInDb = "number_of_products")
    private int mCounts;

    public Products(int idInSite, int counts) {
        mIdInSite = idInSite;
        mCounts = counts;
    }

    @Generated(hash = 551746497)
    public Products(Long _proId, int mIdInSite, int mCounts) {
        this._proId = _proId;
        this.mIdInSite = mIdInSite;
        this.mCounts = mCounts;
    }

    @Generated(hash = 1068248053)
    public Products() {
    }

    public Long get_proId() {
        return _proId;
    }

    public int getIdInSite() {
        return mIdInSite;
    }

    public void setIdInSite(int idInSite) {
        this.mIdInSite = idInSite;
    }

    public int getCounts() {
        return mCounts;
    }

    public void setCounts(int counts) {
        this.mCounts = counts;
    }

    public void set_proId(Long _proId) {
        this._proId = _proId;
    }

    public int getMIdInSite() {
        return this.mIdInSite;
    }

    public void setMIdInSite(int mIdInSite) {
        this.mIdInSite = mIdInSite;
    }

    public int getMCounts() {
        return this.mCounts;
    }

    public void setMCounts(int mCounts) {
        this.mCounts = mCounts;
    }
}
