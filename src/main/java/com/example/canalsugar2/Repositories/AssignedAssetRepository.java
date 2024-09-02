package com.example.canalsugar2.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.canalsugar2.Models.Asset;
import com.example.canalsugar2.Models.AssignedAsset;
import com.example.canalsugar2.Models.User;

import java.util.List;


public interface AssignedAssetRepository extends JpaRepository<AssignedAsset,Integer> {
    AssignedAsset findByAsid(int asid);
    AssignedAsset findByAsset(Asset asset);
    AssignedAsset findByUser(User user);
}
