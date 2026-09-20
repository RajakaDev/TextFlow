package lk.textflow.sales.model;

import java.math.BigDecimal;

public class SalesReturn {

    private int returnId;
    private int saleId;
    private int productId;
    private int quantity;
    private BigDecimal returnAmount;

    public SalesReturn() {
    }

    public SalesReturn(int returnId, int saleId, int productId,
                       int quantity, BigDecimal returnAmount) {
        this.returnId = returnId;
        this.saleId = saleId;
        this.productId = productId;
        this.quantity = quantity;
        this.returnAmount = returnAmount;
    }

    public int getReturnId() {
        return returnId;
    }

    public void setReturnId(int returnId) {
        this.returnId = returnId;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(BigDecimal returnAmount) {
        this.returnAmount = returnAmount;
    }
}