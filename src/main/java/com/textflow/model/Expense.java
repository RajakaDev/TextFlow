package com.textflow.model;

public class Expense {

    private int expenseId;
    private int expenseCategoryId;
    private int userId;
    private int relatedUserId;

    private String expenseDate;
    private String description;
    private double amount;
    private String paymentMethod;
    private String status;

    public Expense() {
    }

    public Expense(
            int expenseId,
            int expenseCategoryId,
            int userId,
            int relatedUserId,
            String expenseDate,
            String description,
            double amount,
            String paymentMethod,
            String status
    ) {
        this.expenseId = expenseId;
        this.expenseCategoryId = expenseCategoryId;
        this.userId = userId;
        this.relatedUserId = relatedUserId;
        this.expenseDate = expenseDate;
        this.description = description;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public int getExpenseCategoryId() {
        return expenseCategoryId;
    }

    public void setExpenseCategoryId(int expenseCategoryId) {
        this.expenseCategoryId = expenseCategoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRelatedUserId() {
        return relatedUserId;
    }

    public void setRelatedUserId(int relatedUserId) {
        this.relatedUserId = relatedUserId;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}