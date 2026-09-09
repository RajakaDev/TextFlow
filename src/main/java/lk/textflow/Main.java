package lk.textflow;

import lk.textflow.dao.InventoryAdjustmentDAO;
import lk.textflow.model.InventoryAdjustment;

public class Main {

    public static void main(String[] args) {

        InventoryAdjustmentDAO dao =
                new InventoryAdjustmentDAO();

        InventoryAdjustment adjustment =
                new InventoryAdjustment(
                        3,
                        1,
                        10,
                        "Stock correction"
                );

        boolean success =
                dao.adjustStock(adjustment);

        if (success) {
            System.out.println(
                    "Stock adjusted successfully!"
            );
        } else {
            System.out.println(
                    "Stock adjustment failed!"
            );
        }
    }
}