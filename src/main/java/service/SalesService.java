package service;

import lk.textflow.sales.model.SaleItem;

import java.math.BigDecimal;
import java.util.List;

public class SalesService {

    public BigDecimal calculateTotal(List<SaleItem> items) {

        BigDecimal total = BigDecimal.ZERO;

        for (SaleItem item : items) {
            BigDecimal itemTotal = item.getUnitPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            total = total.add(itemTotal);
        }

        return total;
    }

    public BigDecimal calculateBalance(BigDecimal totalAmount, BigDecimal amountGiven) {

        return totalAmount.subtract(amountGiven);
    }

    public String determinePaymentStatus(BigDecimal totalAmount, BigDecimal amountGiven) {

        if (amountGiven.compareTo(BigDecimal.ZERO) <= 0) {
            return "PENDING";
        }

        if (amountGiven.compareTo(totalAmount) >= 0) {
            return "PAID";
        }

        return "PARTIAL";
    }
}

