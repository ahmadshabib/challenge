package com.challenge.shopcart.common.utils;

import com.challenge.shopcart.shoppingcart.model.ShoppingCart;
import com.challenge.shopcart.shoppingcart.service.DiscountReason;
import com.challenge.shopcart.shoppingcart.service.DiscountStrategy;

import java.math.BigDecimal;

public class DiscountUtils {

  private DiscountUtils() {}

  public static BigDecimal applyDiscount(
      ShoppingCart shoppingCart,
      BigDecimal amount,
      DiscountReason reason,
      DiscountStrategy discountStrategy) {
    BigDecimal valueToDecrease;
    valueToDecrease = discountStrategy.calculate(amount, reason.getDiscountPercentage());
    shoppingCart.increaseTotalDiscount(valueToDecrease);
    return valueToDecrease;
  }
}
