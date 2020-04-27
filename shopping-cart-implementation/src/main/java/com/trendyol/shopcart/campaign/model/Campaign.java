package com.trendyol.shopcart.campaign.model;

import com.trendyol.shopcart.common.model.DiscountType;
import com.trendyol.shopcart.product.model.Category;
import com.trendyol.shopcart.shoppingcart.service.DiscountReason;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Campaign extends DiscountReason<DiscountType> {
  private @NonNull Integer id;
  private @NonNull Category category;
  private Integer minimumNumberOfProducts;

  public Campaign(
      Integer id,
      Category category,
      BigDecimal discountAmount,
      Integer minimumNumberOfProducts,
      DiscountType discountType) {
    this.id = id;
    this.category = category;
    this.minimumNumberOfProducts = minimumNumberOfProducts;
    this.discountType = discountType;
    this.discountPercentage = discountAmount;
  }

  @Override
  public String toString() {
    return "ID: '"
        + this.id
        + "', Category: '"
        + this.category
        + "', DiscountType: '"
        + this.discountType
        + "', Minimum Number Of Products: '"
        + this.minimumNumberOfProducts
        + "', DiscountAmount: '"
        + this.discountPercentage.toString()
        + "'";
  }
}
