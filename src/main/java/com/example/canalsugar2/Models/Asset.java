package com.example.canalsugar2.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "Asset")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assetid;

    @NotBlank(message = "Asset Model is required")
    @Column(name = "assetmodel")
    private String assetmodel;

    @NotBlank(message = "Asset serial number is required")
    @Column(name = "assetserial")
    private String assetserial;

    @NotBlank(message = "Asset name is required")
    @Column(name = "assetname")
    private String assetname;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "assettypeid", referencedColumnName = "assettypeid", insertable = true, updatable = true)
    private AssetType assetType;


    public Asset() {
    }

    public Asset(int assetid, String assetmodel, String assetserial, String assetname, AssetType assetType) {
        this.assetid = assetid;
        this.assetmodel = assetmodel;
        this.assetserial = assetserial;
        this.assetname = assetname;
        this.assetType = assetType;
    }

    public int getAssetid() {
        return this.assetid;
    }

    public Asset getAsset()
    {
        return this;
    }

    public void setAssetid(int assetid) {
        this.assetid = assetid;
    }

    public String getAssetmodel() {
        return this.assetmodel;
    }

    public void setAssetmodel(String assetmodel) {
        this.assetmodel = assetmodel;
    }

    public String getAssetserial() {
        return this.assetserial;
    }

    public void setAssetserial(String assetserial) {
        this.assetserial = assetserial;
    }

    public String getAssetname() {
        return this.assetname;
    }

    public void setAssetname(String assetname) {
        this.assetname = assetname;
    }

    public AssetType getAssetType() {
        return this.assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public Asset assetid(int assetid) {
        setAssetid(assetid);
        return this;
    }

    public Asset assetmodel(String assetmodel) {
        setAssetmodel(assetmodel);
        return this;
    }

    public Asset assetserial(String assetserial) {
        setAssetserial(assetserial);
        return this;
    }

    public Asset assetname(String assetname) {
        setAssetname(assetname);
        return this;
    }

    public Asset assetType(AssetType assetType) {
        setAssetType(assetType);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Asset)) {
            return false;
        }
        Asset asset = (Asset) o;
        return assetid == asset.assetid && Objects.equals(assetmodel, asset.assetmodel) && Objects.equals(assetserial, asset.assetserial) && Objects.equals(assetname, asset.assetname) && Objects.equals(assetType, asset.assetType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assetid, assetmodel, assetserial, assetname, assetType);
    }

    @Override
    public String toString() {
        return "{" +
            " assetid='" + getAssetid() + "'" +
            ", assetmodel='" + getAssetmodel() + "'" +
            ", assetserial='" + getAssetserial() + "'" +
            ", assetname='" + getAssetname() + "'" +
            ", assetType='" + getAssetType() + "'" +
            "}";
    }
    
    
    
}
