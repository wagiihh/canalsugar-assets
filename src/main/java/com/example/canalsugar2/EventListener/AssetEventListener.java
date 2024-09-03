package com.example.canalsugar2.EventListener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.canalsugar2.Controllers.AssetsController;
import com.example.canalsugar2.Controllers.AssetDeletedEvent;

@Component
public class AssetEventListener {

    private final AssetsController assetsController;

    public AssetEventListener(AssetsController assetsController) {
        this.assetsController = assetsController;
    }

    @EventListener
    public void handleAssetDeletedEvent(AssetDeletedEvent event) {
        assetsController.sendStockWarning();
    }
}
