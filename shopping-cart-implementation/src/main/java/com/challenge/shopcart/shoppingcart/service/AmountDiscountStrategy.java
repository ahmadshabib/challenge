package com.challenge.shopcart.shoppingcart.service;

import java.math.BigDecimal;

public class AmountDiscountStrategy implements DiscountStrategy {

  @Override
  public BigDecimal calculate(BigDecimal amount, BigDecimal amountToDecrease) {
    return amountToDecrease;
  }
}
