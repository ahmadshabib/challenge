package com.challenge.shopcart.coupon.service;

import com.challenge.shopcart.common.exception.ElementNotFoundException;
import com.challenge.shopcart.common.model.DiscountType;
import com.challenge.shopcart.common.utils.DiscountUtils;
import com.challenge.shopcart.coupon.model.Coupon;
import com.challenge.shopcart.coupon.repository.CouponRepositoryDemoImpl;
import com.challenge.shopcart.product.model.Product;
import com.challenge.shopcart.shoppingcart.model.ShoppingCart;
import com.challenge.shopcart.shoppingcart.service.AmountDiscountStrategy;
import com.challenge.shopcart.shoppingcart.service.DiscountStrategy;
import com.challenge.shopcart.shoppingcart.service.RatioDiscountStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class CouponServiceImpl implements CouponService<ShoppingCart, Coupon> {

  @Autowired CouponRepositoryDemoImpl couponRepositoryDemo;

  public void create(Coupon coupon) {
    couponRepositoryDemo.create(coupon);
  }

  public Coupon retrieve(Integer id) throws ElementNotFoundException {
    return couponRepositoryDemo.retrieve(id);
  }

  public List<Coupon> retrieveAll() {
    return couponRepositoryDemo.retrieveAll();
  }

  void delete(Coupon coupon) {
    couponRepositoryDemo.delete(coupon);
  }

  @Override
  public void applyCoupons(ShoppingCart shoppingCart, Coupon... coupons) {
    Arrays.stream(coupons)
        .filter(coupon -> isApplicable(shoppingCart.getTotalAmount(), coupon))
        .forEach(coupon -> applyCoupon(shoppingCart, coupon));
  }

  private boolean isApplicable(BigDecimal total, Coupon coupon) {
    return total.compareTo(coupon.getMinimumAmountToApply()) > -1;
  }

  private void applyCoupon(ShoppingCart shoppingCart, Coupon coupon) {
    BigDecimal valueToDecrease = BigDecimal.ZERO;
    if (coupon.getDiscountType() == DiscountType.RATIO) {
      valueToDecrease =
          applyCouponDiscount(
              shoppingCart,
              shoppingCart.getTotalAmount().subtract(shoppingCart.getDiscount().getTotalDiscount()),
              coupon,
              new RatioDiscountStrategy());
    }
    if (coupon.getDiscountType() == DiscountType.AMOUNT) {
      valueToDecrease =
          applyCouponDiscount(
              shoppingCart,
              shoppingCart.getTotalAmount().subtract(shoppingCart.getDiscount().getTotalDiscount()),
              coupon,
              new AmountDiscountStrategy());
    }
    applyDiscountOnProductsAfterCoupon(shoppingCart, valueToDecrease);
  }

  private void applyDiscountOnProductsAfterCoupon(
      ShoppingCart shoppingCart, BigDecimal valueToDecrease) {
    shoppingCart
        .getProducts()
        .forEach(
            (product, quantity) ->
                product.applyDiscount(
                    calculateCouponDiscountPerProduct(shoppingCart, valueToDecrease, product)));
  }

  private BigDecimal calculateCouponDiscountPerProduct(
      ShoppingCart shoppingCart, BigDecimal valueToDecrease, Product product) {
    return valueToDecrease
        .multiply(product.getPrice())
        .divide(shoppingCart.getTotalAmount(), 2, BigDecimal.ROUND_HALF_UP);
  }

  private BigDecimal applyCouponDiscount(
      ShoppingCart shoppingCart,
      BigDecimal amount,
      Coupon coupon,
      DiscountStrategy discountStrategy) {
    BigDecimal valueToDecrease =
        DiscountUtils.applyDiscount(shoppingCart, amount, coupon, discountStrategy);
    shoppingCart.increaseCouponDiscount(valueToDecrease);
    return valueToDecrease;
  }
}
