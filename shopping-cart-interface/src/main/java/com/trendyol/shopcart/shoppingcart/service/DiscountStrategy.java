package com.trendyol.shopcart.shoppingcart.service;

import java.math.BigDecimal;

@FunctionalInterface
public interface DiscountStrategy {
  BigDecimal calculate(BigDecimal amount , BigDecimal decreasedValue);
}
