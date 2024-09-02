package com.example.canalsugar2.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "assignedasset")
public class AssignedAsset {
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int asid;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "assetid", referencedColumnName = "assetid", insertable = true, updatable = true)
    private Asset asset;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID", insertable = true, updatable = true)
    private User user;

    public AssignedAsset() {
    }

    public AssignedAsset(int asid, Asset asset, User user) {
        this.asid = asid;
        this.asset = asset;
        this.user = user;
    }
    public AssignedAsset getAssignedAsset()
    {
        return this;
    }
    public int getAsid() {
        return this.asid;
    }

    public void setAsid(int asid) {
        this.asid = asid;
    }

    public Asset getAsset() {
        return this.asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AssignedAsset asid(int asid) {
        setAsid(asid);
        return this;
    }

    public AssignedAsset asset(Asset asset) {
        setAsset(asset);
        return this;
    }

    public AssignedAsset user(User user) {
        setUser(user);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AssignedAsset)) {
            return false;
        }
        AssignedAsset assignedAsset = (AssignedAsset) o;
        return asid == assignedAsset.asid && Objects.equals(asset, assignedAsset.asset) && Objects.equals(user, assignedAsset.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(asid, asset, user);
    }

    @Override
    public String toString() {
        return "{" +
            " asid='" + getAsid() + "'" +
            ", asset='" + getAsset() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }
    
}
