package com.trendyol.shopcart.shoppingcart.service;

public interface DeliveryCostCalculatorService<T, K> {
  T calculate(K object);
}
