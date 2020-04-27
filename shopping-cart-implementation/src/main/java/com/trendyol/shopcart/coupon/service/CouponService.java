package com.trendyol.shopcart.coupon.service;

import com.trendyol.shopcart.common.model.DiscountType;
import com.trendyol.shopcart.common.utils.DiscountUtils;
import com.trendyol.shopcart.coupon.model.Coupon;
import com.trendyol.shopcart.product.model.Product;
import com.trendyol.shopcart.shoppingcart.model.ShoppingCart;
import com.trendyol.shopcart.shoppingcart.service.AmountDiscountStrategy;
import com.trendyol.shopcart.shoppingcart.service.DiscountStrategy;
import com.trendyol.shopcart.shoppingcart.service.RatioDiscountStrategy;

import java.math.BigDecimal;
import java.util.Arrays;

public class CouponService {

  public static void applyCoupons(ShoppingCart shoppingCart, Coupon... coupons) {
    Arrays.stream(coupons)
        .filter(coupon -> isApplicable(shoppingCart.getTotalAmount(), coupon))
        .forEach(coupon -> applyCoupon(shoppingCart, coupon));
  }

  private static boolean isApplicable(BigDecimal total, Coupon coupon) {
    return total.compareTo(coupon.getMinimumAmountToApply()) > -1;
  }

  private static void applyCoupon(ShoppingCart shoppingCart, Coupon coupon) {
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

  private static void applyDiscountOnProductsAfterCoupon(
      ShoppingCart shoppingCart, BigDecimal valueToDecrease) {
    shoppingCart
        .getProducts()
        .forEach(
            (product, quantity) ->
                product.applyDiscount(
                    calculateCouponDiscountPerProduct(shoppingCart, valueToDecrease, product)));
  }

  private static BigDecimal calculateCouponDiscountPerProduct(
      ShoppingCart shoppingCart, BigDecimal valueToDecrease, Product product) {
    return valueToDecrease
        .multiply(product.getPrice())
        .divide(shoppingCart.getTotalAmount(), 2, BigDecimal.ROUND_HALF_UP);
  }

  private static BigDecimal applyCouponDiscount(
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
