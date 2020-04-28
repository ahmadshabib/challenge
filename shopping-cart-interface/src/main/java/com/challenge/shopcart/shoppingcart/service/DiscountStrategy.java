package com.challenge.shopcart.shoppingcart.service;

import java.math.BigDecimal;

@FunctionalInterface
public interface DiscountStrategy {
  BigDecimal calculate(BigDecimal amount , BigDecimal decreasedValue);
}
