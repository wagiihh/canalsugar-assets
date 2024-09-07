package com.example.canalsugar2.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.canalsugar2.Models.Asset;
import com.example.canalsugar2.Models.AssetType;

import java.util.List;


public interface AssetRepository extends JpaRepository<Asset,Integer> {
    Asset findByAssetid(int assetid);
    Asset findByAssetType(AssetType assetType);
    Asset findByAssetserial(String assetserial);
        long countByAssetType(AssetType assetType);

    @Query("SELECT DISTINCT a.assetType FROM Asset a")
    List<AssetType> findDistinctAssetTypes();

    @Query(value = "SELECT a FROM Asset a ORDER BY a.assetid DESC")
    List<Asset> findTop4ByOrderByAssetidDesc();
    
}
