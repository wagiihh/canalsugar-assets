package com.example.canalsugar2.Models;

public class AssetStat  {
    private AssetType assetType;
    private long totalAssets;
    private long usedAssets;
    private long availableAssets;

    public AssetStat(AssetType assetType, long totalAssets, long usedAssets, long availableAssets) {
        this.assetType = assetType;
        this.totalAssets = totalAssets;
        this.usedAssets = usedAssets;
        this.availableAssets = availableAssets;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public long getTotalAssets() {
        return totalAssets;
    }

    public long getUsedAssets() {
        return usedAssets;
    }

    public long getAvailableAssets() {
        return availableAssets;
    }
}
