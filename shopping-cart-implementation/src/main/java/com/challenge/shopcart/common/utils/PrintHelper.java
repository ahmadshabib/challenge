package com.challenge.shopcart.common.utils;

import com.challenge.shopcart.product.model.Category;
import com.challenge.shopcart.product.model.Product;
import com.challenge.shopcart.shoppingcart.model.ShoppingCart;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrintHelper {

  private PrintHelper() {}

  private static String getBigDecimalFormatted(BigDecimal bigDecimal) {
    DecimalFormat df = new DecimalFormat("#,###.00");
    return df.format(bigDecimal);
  }

  public static void printShoppingCart(
      ShoppingCart shoppingCart, BigDecimal totalAmount, BigDecimal deliveryCost) {
    shoppingCart.getProducts().entrySet().stream()
        .collect(Collectors.groupingBy(entry -> entry.getKey().getCategory()))
        .forEach(PrintHelper::printFormatted);
    System.out.println(
        String.format(
            "Total Price: %s -- Shipping Price: %s",
            PrintHelper.getBigDecimalFormatted(totalAmount),
            PrintHelper.getBigDecimalFormatted(deliveryCost)));
  }

  private static void printFormatted(
      Category category, List<Map.Entry<Product, Integer>> listOfProducts) {
    listOfProducts.forEach(
        productIntegerEntry -> System.out.println(formattedString(category, productIntegerEntry)));
  }

  private static String formattedString(
      Category category, Map.Entry<Product, Integer> productIntegerEntry) {
    return String.format(
        "Category Title: %s -- Product Name: %s -- Product Quantity: %d -- Original Price: %s -- Price After Discount: %s",
        category.getTitle(),
        productIntegerEntry.getKey().getTitle(),
        productIntegerEntry.getValue(),
        PrintHelper.getBigDecimalFormatted(
            productIntegerEntry
                .getKey()
                .getPrice()
                .multiply(new BigDecimal(productIntegerEntry.getValue()))),
        PrintHelper.getBigDecimalFormatted(
            productIntegerEntry
                .getKey()
                .getPriceAfterDiscount()
                .multiply(new BigDecimal(productIntegerEntry.getValue()))));
  }
}
