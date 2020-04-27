package com.trendyol.shopcart.campaign.service;

public interface CampaignService<T, C, P, I> {

  boolean isApplicable(C campaign, P product, I id);

  void applyDiscounts(T shoppingCart, C... discounts);
}
