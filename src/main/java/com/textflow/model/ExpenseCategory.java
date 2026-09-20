package com.textflow.model;

public class ExpenseCategory {

    private int expenseCategoryId;
    private String categoryName;
    private String description;
    private String status;

    public ExpenseCategory() {
    }

    public ExpenseCategory(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
        this.status = "ACTIVE";
    }

    public int getExpenseCategoryId() {
        return expenseCategoryId;
    }

    public void setExpenseCategoryId(int expenseCategoryId) {
        this.expenseCategoryId = expenseCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}