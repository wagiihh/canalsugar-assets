package com.example.canalsugar2.Controllers;

import org.springframework.context.ApplicationEvent;

public class AssetAssignedEvent extends ApplicationEvent {
    private final Integer assetId;
    private final Integer userId;

    public AssetAssignedEvent(Object source, Integer assetId, Integer userId) {
        super(source);
        this.assetId = assetId;
        this.userId = userId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public Integer getUserId() {
        return userId;
    }
}
