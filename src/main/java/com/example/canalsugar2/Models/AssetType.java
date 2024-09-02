package com.example.canalsugar2.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "assetType")
public class AssetType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assettypeid;
    
    @NotBlank(message = "Name is required")
    @Column(name = "name")
    private String name;


    public AssetType() {
    }

    public AssetType(int assettypeid, String name) {
        this.assettypeid = assettypeid;
        this.name = name;
    }

    public AssetType getAssetType()
    {
        return this;
    }

    public int getAssettypeid() {
        return this.assettypeid;
    }

    public void setAssettypeid(int assettypeid) {
        this.assettypeid = assettypeid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AssetType assettypeid(int assettypeid) {
        setAssettypeid(assettypeid);
        return this;
    }

    public AssetType name(String name) {
        setName(name);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AssetType)) {
            return false;
        }
        AssetType assetType = (AssetType) o;
        return assettypeid == assetType.assettypeid && Objects.equals(name, assetType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assettypeid, name);
    }

    @Override
    public String toString() {
        return "{" +
            " assettypeid='" + getAssettypeid() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
    
}
