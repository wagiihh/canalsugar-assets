package com.example.canalsugar2.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.canalsugar2.Models.AssetType;
import java.util.List;


public interface AssetTypeRepository extends JpaRepository<AssetType,Integer> {
    AssetType  findByName(String name);
}
