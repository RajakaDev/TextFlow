package lk.textflow;

import lk.textflow.sales.dao.SaleDAO;
import lk.textflow.sales.model.Sale;

public class Main {

    public static void main(String[] args) {

        SaleDAO saleDAO = new SaleDAO();

        Sale sale = saleDAO.getSaleById(1);

        if (sale == null) {
            System.out.println("SaleDAO connection test successful!");
            System.out.println("No sale with ID 1 found.");
        } else {
            System.out.println("Sale found!");
            System.out.println("Sale ID: " + sale.getSaleId());
            System.out.println("Total: " + sale.getTotalAmount());
        }
    }
}