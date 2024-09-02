package com.example.canalsugar2.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.canalsugar2.Models.Asset;
import com.example.canalsugar2.Models.AssetType;

import java.util.List;


public interface AssetRepository extends JpaRepository<Asset,Integer> {
    Asset findByAssetid(int assetid);
    Asset findByAssetType(AssetType assetType);
    Asset findByAssetserial(String assetserial);
    
}
