package lk.textflow.model;

import java.math.BigDecimal;

public class Product {

    private int productId;
    private int categoryId;
    private String productName;
    private String barcode;
    private BigDecimal unitPrice;
    private BigDecimal costPrice;
    private int stockQuantity;
    private int reorderLevel;
    private String status;

    public Product() {
    }

    public Product(int productId, int categoryId,
                   String productName, String barcode,
                   BigDecimal unitPrice, BigDecimal costPrice,
                   int stockQuantity, int reorderLevel,
                   String status) {

        this.productId = productId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.barcode = barcode;
        this.unitPrice = unitPrice;
        this.costPrice = costPrice;
        this.stockQuantity = stockQuantity;
        this.reorderLevel = reorderLevel;
        this.status = status;
    }

    public Product(int categoryId, String productName,
                   String barcode, BigDecimal unitPrice,
                   BigDecimal costPrice, int stockQuantity,
                   int reorderLevel, String status) {

        this.categoryId = categoryId;
        this.productName = productName;
        this.barcode = barcode;
        this.unitPrice = unitPrice;
        this.costPrice = costPrice;
        this.stockQuantity = stockQuantity;
        this.reorderLevel = reorderLevel;
        this.status = status;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}