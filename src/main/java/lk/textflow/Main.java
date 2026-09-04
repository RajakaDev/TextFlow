package lk.textflow;

import lk.textflow.dao.ProductDAO;
import lk.textflow.model.Product;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        ProductDAO productDAO = new ProductDAO();

        Product product = new Product(
                1,
                "Atlas Blue Pen",
                "479100100001",
                new BigDecimal("100.00"),
                new BigDecimal("70.00"),
                50,
                10,
                "ACTIVE"
        );

        boolean success =
                productDAO.addProduct(product);

        if (success) {
            System.out.println(
                    "Product added successfully!"
            );
        } else {
            System.out.println(
                    "Failed to add product."
            );
        }
    }
}