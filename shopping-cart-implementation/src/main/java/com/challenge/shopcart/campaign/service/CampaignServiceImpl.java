package com.challenge.shopcart.campaign.service;

import com.challenge.shopcart.campaign.model.Campaign;
import com.challenge.shopcart.campaign.repository.CampaignRepositoryDemoImpl;
import com.challenge.shopcart.common.exception.ElementNotFoundException;
import com.challenge.shopcart.common.model.DiscountType;
import com.challenge.shopcart.common.utils.DiscountUtils;
import com.challenge.shopcart.product.model.Category;
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
import java.util.Objects;

@Component
public class CampaignServiceImpl
    implements CampaignService<ShoppingCart, Campaign, Product, Integer> {

  @Autowired CampaignRepositoryDemoImpl campaignRepositoryDemo;

  public void create(Campaign campaign) {
    campaignRepositoryDemo.create(campaign);
  }

  public Campaign retrieve(Integer id) throws ElementNotFoundException {
    return campaignRepositoryDemo.retrieve(id);
  }

  public List<Campaign> retrieveAll() {
    return campaignRepositoryDemo.retrieveAll();
  }

  void delete(Campaign campaign) {
    campaignRepositoryDemo.delete(campaign);
  }

  @Override
  public boolean isApplicable(Campaign campaign, Product product, Integer numberOfProducts) {
    return numberOfProducts >= campaign.getMinimumNumberOfProducts()
        && isSimilarCategory(product.getCategory(), campaign.getCategory());
  }

  private boolean isSimilarCategory(Category productCategory, Category category) {
    if (productCategory.equals(category)) return true;
    else if (Objects.nonNull(category.getParentCategory())) {
      return isSimilarCategory(productCategory, category.getParentCategory());
    } else if (Objects.nonNull(productCategory.getParentCategory())) {
      return isSimilarCategory(productCategory.getParentCategory(), category);
    }
    return false;
  }

  @Override
  public void applyDiscounts(ShoppingCart shoppingCart, Campaign... discounts) {
    shoppingCart
        .getProducts()
        .forEach(
            (product, amount) ->
                applyDiscountOnProductPack(shoppingCart, product, amount, discounts));
  }

  private void applyDiscountOnProductPack(
      ShoppingCart shoppingCart, Product product, Integer amount, Campaign... discounts) {
    BigDecimal productsValue = product.getPrice().multiply(new BigDecimal(amount));
    Arrays.stream(discounts)
        .filter(campaign -> this.isApplicable(campaign, product, amount))
        .forEach(campaign -> applyCampaign(shoppingCart, product, amount, campaign, productsValue));
  }

  private void applyCampaign(
      ShoppingCart shoppingCart,
      Product product,
      Integer amount,
      Campaign campaign,
      BigDecimal productsValue) {
    BigDecimal valueToDecrease = BigDecimal.ZERO;
    if (campaign.getDiscountType() == DiscountType.RATIO) {
      valueToDecrease =
          applyCampaignDiscount(shoppingCart, productsValue, campaign, new RatioDiscountStrategy());
    }
    if (campaign.getDiscountType() == DiscountType.AMOUNT) {
      valueToDecrease =
          applyCampaignDiscount(
              shoppingCart, productsValue, campaign, new AmountDiscountStrategy());
    }
    product.applyDiscount(
        valueToDecrease.divide(new BigDecimal(amount), 2, BigDecimal.ROUND_HALF_UP));
  }

  private BigDecimal applyCampaignDiscount(
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
