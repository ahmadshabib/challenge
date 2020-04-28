package com.challenge.shopcart.shoppingcart.model;

import com.challenge.shopcart.common.model.Discount;
import com.challenge.shopcart.product.model.Product;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;

@Data
public class ShoppingCart {
  private @Getter HashMap<Product, Integer> products = new HashMap<>();
  private @Getter Discount discount = new Discount();
  private BigDecimal totalAmount = BigDecimal.ZERO;

  public void addItem(Product product, Integer quantity) {
    Integer newQuantity =
        products.containsKey(product) ? products.get(product) + quantity : quantity;
    products.put(product, newQuantity);
    totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
  }

  public void removeItem(Product product, Integer quantity) {
    Integer newQuantity = products.containsKey(product) ? products.get(product) - quantity : 0;
    if (newQuantity <= 0) {
      totalAmount =
          totalAmount.subtract(
              product.getPrice().multiply(BigDecimal.valueOf(products.get(product))));
      products.remove(product);
    } else {
      totalAmount = totalAmount.subtract(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
      products.put(product, newQuantity);
    }
  }

  public void increaseCampaignDiscount(BigDecimal valueToDecrease) {
    this.discount.increaseCampaignDiscount(valueToDecrease);
  }

  public void increaseTotalDiscount(BigDecimal valueToDecrease) {
    this.discount.increaseTotalDiscount(valueToDecrease);
  }

  public void increaseCouponDiscount(BigDecimal valueToDecrease) {
    this.discount.increaseCouponDiscount(valueToDecrease);
  }

  public void cleanUpCart() {
    products = new HashMap<>();
    discount = new Discount();
  }
}
