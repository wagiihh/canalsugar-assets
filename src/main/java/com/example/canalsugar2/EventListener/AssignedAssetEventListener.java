package com.example.canalsugar2.EventListener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.canalsugar2.Controllers.AssignedAssetController;
import com.example.canalsugar2.Controllers.AssetAssignedEvent;
import com.example.canalsugar2.Controllers.AssetDeletedEvent;

@Component
public class AssignedAssetEventListener {

    private final AssignedAssetController assignedAssetController;

    public AssignedAssetEventListener(AssignedAssetController assignedAssetController) {
        this.assignedAssetController = assignedAssetController;
    }

    @EventListener
    public void handleAssignedAssetDeletedEvent(AssetAssignedEvent event) {
        assignedAssetController.sendStockWarning();  // Call method from controller
    }
}
