CREATE DATABASE IF NOT EXISTS textflow_db;
USE textflow_db;

-- =====================================================
-- 1. USERS
-- =====================================================

CREATE TABLE IF NOT EXISTS users (
                                     user_id INT PRIMARY KEY AUTO_INCREMENT,
                                     name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('OWNER', 'MANAGER', 'EMPLOYEE') NOT NULL,
    position VARCHAR(100),
    contact_number VARCHAR(20),
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
    );

-- =====================================================
-- 2. ATTENDANCE
-- =====================================================

CREATE TABLE IF NOT EXISTS attendance (
                                          attendance_id INT PRIMARY KEY AUTO_INCREMENT,
                                          user_id INT NOT NULL,
                                          attendance_date DATE NOT NULL,
                                          time_in TIME,
                                          time_out TIME,
                                          status ENUM('PRESENT', 'ABSENT', 'HALF_DAY') DEFAULT 'PRESENT',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_attendance_user
    FOREIGN KEY (user_id)
    REFERENCES users(user_id),

    CONSTRAINT uq_attendance_user_date
    UNIQUE (user_id, attendance_date)
    );

-- =====================================================
-- 3. CATEGORIES
-- =====================================================

CREATE TABLE IF NOT EXISTS categories (
                                          category_id INT PRIMARY KEY AUTO_INCREMENT,
                                          category_name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE'
    );

-- =====================================================
-- 4. PRODUCTS
-- =====================================================

CREATE TABLE IF NOT EXISTS products (
                                        product_id INT PRIMARY KEY AUTO_INCREMENT,
                                        category_id INT NOT NULL,
                                        product_name VARCHAR(150) NOT NULL,
    barcode VARCHAR(100) UNIQUE,
    unit_price DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    cost_price DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    stock_quantity INT NOT NULL DEFAULT 0,
    reorder_level INT NOT NULL DEFAULT 10,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_product_category
    FOREIGN KEY (category_id)
    REFERENCES categories(category_id),

    CONSTRAINT chk_product_unit_price
    CHECK (unit_price >= 0),

    CONSTRAINT chk_product_cost_price
    CHECK (cost_price >= 0),

    CONSTRAINT chk_product_stock
    CHECK (stock_quantity >= 0),

    CONSTRAINT chk_product_reorder
    CHECK (reorder_level >= 0)
    );

-- =====================================================
-- 5. INVENTORY ADJUSTMENTS
-- =====================================================

CREATE TABLE IF NOT EXISTS inventory_adjustments (
                                                     adjustment_id INT PRIMARY KEY AUTO_INCREMENT,
                                                     product_id INT NOT NULL,
                                                     user_id INT NOT NULL,
                                                     quantity_change INT NOT NULL,
                                                     reason VARCHAR(255) NOT NULL,
    adjustment_date DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_adjustment_product
    FOREIGN KEY (product_id)
    REFERENCES products(product_id),

    CONSTRAINT fk_adjustment_user
    FOREIGN KEY (user_id)
    REFERENCES users(user_id)
    );

-- =====================================================
-- 6. EXPENSE CATEGORIES
-- =====================================================

CREATE TABLE IF NOT EXISTS expense_categories (
                                                  expense_category_id INT PRIMARY KEY AUTO_INCREMENT,
                                                  category_name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE'
    );

-- =====================================================
-- 7. EXPENSES
-- =====================================================

CREATE TABLE IF NOT EXISTS expenses (
                                        expense_id INT PRIMARY KEY AUTO_INCREMENT,
                                        expense_category_id INT NOT NULL,
                                        user_id INT NOT NULL,
                                        related_user_id INT NULL,
                                        expense_date DATE NOT NULL,
                                        description VARCHAR(255),
    amount DECIMAL(12,2) NOT NULL,
    payment_method ENUM('CASH', 'CARD', 'BANK', 'OTHER') DEFAULT 'CASH',
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_expense_category
    FOREIGN KEY (expense_category_id)
    REFERENCES expense_categories(expense_category_id),

    CONSTRAINT fk_expense_user
    FOREIGN KEY (user_id)
    REFERENCES users(user_id),

    CONSTRAINT fk_expense_related_user
    FOREIGN KEY (related_user_id)
    REFERENCES users(user_id),

    CONSTRAINT chk_expense_amount
    CHECK (amount > 0)
    );

-- =====================================================
-- 8. SUPPLIERS
-- =====================================================

CREATE TABLE IF NOT EXISTS suppliers (
                                         supplier_id INT PRIMARY KEY AUTO_INCREMENT,
                                         supplier_name VARCHAR(150) NOT NULL,
    contact_number VARCHAR(20),
    address VARCHAR(255),
    email VARCHAR(100),
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
    );

-- =====================================================
-- 9. PURCHASES
-- =====================================================

CREATE TABLE IF NOT EXISTS purchases (
                                         purchase_id INT PRIMARY KEY AUTO_INCREMENT,
                                         supplier_id INT NOT NULL,
                                         user_id INT NOT NULL,
                                         purchase_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                                         total_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    status ENUM('PENDING', 'CONFIRMED', 'CANCELLED') DEFAULT 'PENDING',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_purchase_supplier
    FOREIGN KEY (supplier_id)
    REFERENCES suppliers(supplier_id),

    CONSTRAINT fk_purchase_user
    FOREIGN KEY (user_id)
    REFERENCES users(user_id),

    CONSTRAINT chk_purchase_total
    CHECK (total_amount >= 0)
    );

-- =====================================================
-- 10. PURCHASE ITEMS
-- =====================================================

CREATE TABLE IF NOT EXISTS purchase_items (
                                              purchase_item_id INT PRIMARY KEY AUTO_INCREMENT,
                                              purchase_id INT NOT NULL,
                                              product_id INT NOT NULL,
                                              quantity INT NOT NULL,
                                              unit_cost DECIMAL(12,2) NOT NULL,
    total_cost DECIMAL(12,2) GENERATED ALWAYS AS (quantity * unit_cost) STORED,

    CONSTRAINT fk_purchase_item_purchase
    FOREIGN KEY (purchase_id)
    REFERENCES purchases(purchase_id)
    ON DELETE CASCADE,

    CONSTRAINT fk_purchase_item_product
    FOREIGN KEY (product_id)
    REFERENCES products(product_id),

    CONSTRAINT chk_purchase_item_qty
    CHECK (quantity > 0),

    CONSTRAINT chk_purchase_item_cost
    CHECK (unit_cost >= 0)
    );

-- =====================================================
-- 11. CUSTOMERS
-- =====================================================

CREATE TABLE IF NOT EXISTS customers (
                                         customer_id INT PRIMARY KEY AUTO_INCREMENT,
                                         customer_name VARCHAR(150) NOT NULL,
    contact_number VARCHAR(20),
    address VARCHAR(255),
    loyalty_points INT NOT NULL DEFAULT 0,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_customer_points
    CHECK (loyalty_points >= 0)
    );

-- =====================================================
-- 12. SALES
-- =====================================================

CREATE TABLE IF NOT EXISTS sales (
                                     sale_id INT PRIMARY KEY AUTO_INCREMENT,
                                     customer_id INT NULL,
                                     user_id INT NOT NULL,
                                     sale_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                                     total_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    amount_given DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    balance DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    payment_method ENUM('CASH', 'CARD', 'BANK', 'OTHER') DEFAULT 'CASH',
    payment_status ENUM('PAID', 'PARTIAL', 'PENDING') DEFAULT 'PAID',
    status ENUM('PENDING', 'CONFIRMED', 'CANCELLED') DEFAULT 'PENDING',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_sale_customer
    FOREIGN KEY (customer_id)
    REFERENCES customers(customer_id),

    CONSTRAINT fk_sale_user
    FOREIGN KEY (user_id)
    REFERENCES users(user_id),

    CONSTRAINT chk_sale_total
    CHECK (total_amount >= 0),

    CONSTRAINT chk_sale_amount_given
    CHECK (amount_given >= 0)
    );

-- =====================================================
-- 13. SALE ITEMS
-- =====================================================

CREATE TABLE IF NOT EXISTS sale_items (
                                          sale_item_id INT PRIMARY KEY AUTO_INCREMENT,
                                          sale_id INT NOT NULL,
                                          product_id INT NOT NULL,
                                          quantity INT NOT NULL,
                                          unit_price DECIMAL(12,2) NOT NULL,
    total_price DECIMAL(12,2) GENERATED ALWAYS AS (quantity * unit_price) STORED,

    CONSTRAINT fk_sale_item_sale
    FOREIGN KEY (sale_id)
    REFERENCES sales(sale_id)
    ON DELETE CASCADE,

    CONSTRAINT fk_sale_item_product
    FOREIGN KEY (product_id)
    REFERENCES products(product_id),

    CONSTRAINT chk_sale_item_qty
    CHECK (quantity > 0),

    CONSTRAINT chk_sale_item_price
    CHECK (unit_price >= 0)
    );

-- =====================================================
-- INDEXES
-- =====================================================

CREATE INDEX idx_product_name
    ON products(product_name);

CREATE INDEX idx_product_barcode
    ON products(barcode);

CREATE INDEX idx_supplier_name
    ON suppliers(supplier_name);

CREATE INDEX idx_customer_name
    ON customers(customer_name);

CREATE INDEX idx_customer_contact
    ON customers(contact_number);

CREATE INDEX idx_purchase_date
    ON purchases(purchase_date);

CREATE INDEX idx_sale_date
    ON sales(sale_date);

CREATE INDEX idx_expense_date
    ON expenses(expense_date);