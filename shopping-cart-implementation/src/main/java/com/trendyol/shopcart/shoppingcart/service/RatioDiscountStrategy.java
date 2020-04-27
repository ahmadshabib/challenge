package com.trendyol.shopcart.shoppingcart.service;

import java.math.BigDecimal;

public class RatioDiscountStrategy implements DiscountStrategy {
  @Override
  public BigDecimal calculate(BigDecimal amount, BigDecimal ratioToIncrease) {
    return amount
        .multiply(ratioToIncrease)
        .divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
  }
}
