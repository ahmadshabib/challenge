package com.challenge.shopcart.shoppingcart.service;

import lombok.Getter;

import java.math.BigDecimal;

public abstract class DiscountReason<T> {
  protected @Getter BigDecimal discountPercentage;
  protected @Getter T discountType;

}
