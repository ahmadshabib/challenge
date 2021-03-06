package com.challenge.shopcart.shoppingcart.service;

public interface ShoppingCartService<T> {

  void calculateDeliveryCost();

  T getTotalAmountAfterDiscounts();

  T getCouponDiscount();

  T getCampaignDiscount();

  T getDeliveryCost();
}
