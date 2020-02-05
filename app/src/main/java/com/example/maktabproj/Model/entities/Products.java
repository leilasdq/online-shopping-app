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
    private Long mIdInSite;
    @Property (nameInDb = "number_of_products")
    private int mCounts;

    public Products(Long idInSite, int counts) {
        mIdInSite = idInSite;
        mCounts = counts;
    }

    @Generated(hash = 163754644)
    public Products(Long _proId, Long mIdInSite, int mCounts) {
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

    public Long getIdInSite() {
        return mIdInSite;
    }

    public void setIdInSite(Long idInSite) {
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

    public Long getMIdInSite() {
        return this.mIdInSite;
    }

    public void setMIdInSite(Long mIdInSite) {
        this.mIdInSite = mIdInSite;
    }

    public int getMCounts() {
        return this.mCounts;
    }

    public void setMCounts(int mCounts) {
        this.mCounts = mCounts;
    }
}
