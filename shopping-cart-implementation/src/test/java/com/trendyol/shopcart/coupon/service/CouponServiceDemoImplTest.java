package com.trendyol.shopcart.coupon.service;

import com.trendyol.shopcart.common.exception.ElementNotFoundException;
import com.trendyol.shopcart.common.model.DiscountType;
import com.trendyol.shopcart.coupon.model.Coupon;
import com.trendyol.shopcart.coupon.repository.CouponRepositoryDemoImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class CouponServiceDemoImplTest {

  private CouponServiceImpl couponService;
  private Coupon thirtyTLCoupon, twentyPercentCoupon;

  @Before
  public void setup() {
    couponService = new CouponServiceImpl();
    couponService.couponRepositoryDemo = new CouponRepositoryDemoImpl();
    thirtyTLCoupon =
        new Coupon(5, new BigDecimal("30.00"), new BigDecimal("20.00"), DiscountType.AMOUNT);
    twentyPercentCoupon =
        new Coupon(6, new BigDecimal("20.00"), new BigDecimal("10.00"), DiscountType.RATIO);
  }

  @Test
  public void numberOfProductsTest() {
    Assert.assertEquals(2, couponService.retrieveAll().size());
  }

  @Test
  public void addProductTest() throws ElementNotFoundException {
    couponService.create(thirtyTLCoupon);
    Assert.assertEquals(thirtyTLCoupon, couponService.retrieve(thirtyTLCoupon.getId()));
  }

  @Test(expected = ElementNotFoundException.class)
  public void retrieveNonExcitedProductTest() throws ElementNotFoundException {
    couponService.delete(twentyPercentCoupon);
    Assert.assertEquals(twentyPercentCoupon, couponService.retrieve(twentyPercentCoupon.getId()));
  }

  @After
  public void removedAdditionalProducts() {
    couponService.delete(thirtyTLCoupon);
    couponService.delete(twentyPercentCoupon);
  }
}
