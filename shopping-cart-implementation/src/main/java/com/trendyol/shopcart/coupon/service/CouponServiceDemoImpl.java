package com.trendyol.shopcart.coupon.service;

import com.trendyol.shopcart.common.exception.ElementNotFoundException;
import com.trendyol.shopcart.common.service.DaoService;
import com.trendyol.shopcart.common.model.DiscountType;
import com.trendyol.shopcart.coupon.model.Coupon;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CouponServiceDemoImpl implements DaoService<Coupon, Integer> {

  private static AtomicReference<HashSet<Coupon>> coupons = new AtomicReference<>(new HashSet<>());

  static {
    coupons
        .get()
        .add(new Coupon(1,new BigDecimal("50.00"), new BigDecimal("20.00"), DiscountType.AMOUNT));
    coupons
        .get()
        .add(new Coupon(2,new BigDecimal("70.00"), new BigDecimal("10.00"), DiscountType.RATIO));
  }

  @Override
  public void create(Coupon coupon) {
    coupons.get().add(coupon);
  }

  @Override
  public Coupon retrieve(Integer id) throws ElementNotFoundException {
    return coupons.get().stream()
        .filter(coupon -> coupon.getId().equals(id))
        .findAny()
        .orElseThrow(() -> new ElementNotFoundException("Coupon Not Found"));
  }

  @Override
  public List<Coupon> retrieveAll() {
    return new ArrayList<>(coupons.get());
  }

  @Override
  public void delete(Coupon coupon) {
    coupons.get().remove(coupon);
  }
}
