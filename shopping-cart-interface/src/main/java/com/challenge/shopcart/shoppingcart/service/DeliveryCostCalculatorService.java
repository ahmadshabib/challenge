package com.challenge.shopcart.shoppingcart.service;

public interface DeliveryCostCalculatorService<T, K> {
  T calculate(K object);
}
