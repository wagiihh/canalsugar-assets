package com.example.canalsugar2.Controllers;

import org.springframework.context.ApplicationEvent;

public class AssetDeletedEvent extends ApplicationEvent {
    private final Integer assetId;

    public AssetDeletedEvent(Object source, Integer assetId) {
        super(source);
        this.assetId = assetId;
    }

    public Integer getAssetId() {
        return assetId;
    }
}
