package com.challenge.shopcart.coupon.service;

public interface CouponService<T, C> {

  void applyCoupons(T shoppingCart, C... discounts);
}
