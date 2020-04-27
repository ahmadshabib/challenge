package com.trendyol.shopcart.campaign.utils;

import com.trendyol.shopcart.campaign.model.Campaign;
import com.trendyol.shopcart.common.model.DiscountType;
import com.trendyol.shopcart.common.utils.DiscountUtils;
import com.trendyol.shopcart.product.model.Category;
import com.trendyol.shopcart.product.model.Product;
import com.trendyol.shopcart.shoppingcart.model.ShoppingCart;
import com.trendyol.shopcart.shoppingcart.service.AmountDiscountStrategy;
import com.trendyol.shopcart.shoppingcart.service.DiscountStrategy;
import com.trendyol.shopcart.shoppingcart.service.RatioDiscountStrategy;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

@UtilityClass
public class CampaignUtils {

  public static boolean isApplicable(Campaign campaign, Product product, Integer numberOfProducts) {
    return numberOfProducts >= campaign.getMinimumNumberOfProducts()
        && isSimilarCategory(product.getCategory(), campaign.getCategory());
  }

  private static boolean isSimilarCategory(Category productCategory, Category category) {
    if (productCategory.equals(category)) return true;
    else if (Objects.nonNull(category.getParentCategory())) {
      return isSimilarCategory(productCategory, category.getParentCategory());
    } else if (Objects.nonNull(productCategory.getParentCategory())) {
      return isSimilarCategory(productCategory.getParentCategory(), category);
    }
    return false;
  }

  public static void applyDiscounts(ShoppingCart shoppingCart, Campaign... discounts) {
    shoppingCart
        .getProducts()
        .forEach(
            (product, amount) ->
                applyDiscountOnProductPack(shoppingCart, product, amount, discounts));
  }

  private static void applyDiscountOnProductPack(
      ShoppingCart shoppingCart, Product product, Integer amount, Campaign... discounts) {
    BigDecimal productsValue = product.getPrice().multiply(new BigDecimal(amount));
    Arrays.stream(discounts)
        .filter(campaign -> CampaignUtils.isApplicable(campaign, product, amount))
        .forEach(campaign -> applyCampaign(shoppingCart, product, amount, campaign, productsValue));
  }

  private static void applyCampaign(
      ShoppingCart shoppingCart,
      Product product,
      Integer amount,
      Campaign campaign,
      BigDecimal productsValue) {
    BigDecimal valueToDecrease = BigDecimal.ZERO;
    if (campaign.getDiscountType() == DiscountType.RATIO) {
      valueToDecrease =
          applyCampaignDiscount(shoppingCart, productsValue, campaign, new RatioDiscountStrategy());
    } else if (campaign.getDiscountType() == DiscountType.AMOUNT) {
      valueToDecrease =
          applyCampaignDiscount(
              shoppingCart, productsValue, campaign, new AmountDiscountStrategy());
    }
    product.applyDiscount(
        valueToDecrease.divide(new BigDecimal(amount), 2, BigDecimal.ROUND_HALF_UP));
  }

  private static BigDecimal applyCampaignDiscount(
      ShoppingCart shoppingCart,
      BigDecimal amount,
      Campaign campaign,
      DiscountStrategy discountStrategy) {
    BigDecimal valueToDecrease =
        DiscountUtils.applyDiscount(shoppingCart, amount, campaign, discountStrategy);
    shoppingCart.increaseCampaignDiscount(valueToDecrease);
    return valueToDecrease;
  }
}
