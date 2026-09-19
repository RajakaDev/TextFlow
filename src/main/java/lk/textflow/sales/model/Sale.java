package lk.textflow.sales.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Sale {

    private int saleId;
    private Integer customerId;
    private int userId;
    private LocalDateTime saleDate;
    private BigDecimal totalAmount;
    private BigDecimal amountGiven;
    private BigDecimal balance;
    private String paymentMethod;
    private String paymentStatus;
    private String status;

    public Sale() {
    }

    public Sale(int saleId, Integer customerId, int userId,
                LocalDateTime saleDate, BigDecimal totalAmount,
                BigDecimal amountGiven, BigDecimal balance,
                String paymentMethod, String paymentStatus,
                String status) {

        this.saleId = saleId;
        this.customerId = customerId;
        this.userId = userId;
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
        this.amountGiven = amountGiven;
        this.balance = balance;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.status = status;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAmountGiven() {
        return amountGiven;
    }

    public void setAmountGiven(BigDecimal amountGiven) {
        this.amountGiven = amountGiven;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}