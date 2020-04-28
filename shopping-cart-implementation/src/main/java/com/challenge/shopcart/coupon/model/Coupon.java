package com.challenge.shopcart.coupon.model;

import com.challenge.shopcart.common.model.DiscountType;
import com.challenge.shopcart.shoppingcart.service.DiscountReason;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Coupon extends DiscountReason<DiscountType> {
  private @NonNull Integer id;
  private @NonNull BigDecimal minimumAmountToApply;

  public Coupon(
      Integer id,
      BigDecimal minimumAmountToApply,
      BigDecimal discountAmount,
      DiscountType discountType) {
    this.id = id;
    this.minimumAmountToApply = minimumAmountToApply;
    this.discountType = discountType;
    this.discountPercentage = discountAmount;
  }

  @Override
  public String toString() {
    return "ID: '"
        + this.id
        + "', Minimum Amount To Apply: '"
        + this.minimumAmountToApply
        + "', DiscountType: '"
        + this.discountType
        + "', DiscountAmount: '"
        + this.discountPercentage.toString()
        + "'";
  }
}
