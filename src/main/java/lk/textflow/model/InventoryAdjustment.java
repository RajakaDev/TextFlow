package lk.textflow.model;

import java.time.LocalDateTime;

public class InventoryAdjustment {

    private int adjustmentId;
    private int productId;
    private int userId;
    private int quantityChange;
    private String reason;
    private LocalDateTime adjustmentDate;

    public InventoryAdjustment() {
    }

    public InventoryAdjustment(
            int productId,
            int userId,
            int quantityChange,
            String reason) {

        this.productId = productId;
        this.userId = userId;
        this.quantityChange = quantityChange;
        this.reason = reason;
    }

    public int getAdjustmentId() {
        return adjustmentId;
    }

    public void setAdjustmentId(int adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(int quantityChange) {
        this.quantityChange = quantityChange;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getAdjustmentDate() {
        return adjustmentDate;
    }

    public void setAdjustmentDate(LocalDateTime adjustmentDate) {
        this.adjustmentDate = adjustmentDate;
    }
}