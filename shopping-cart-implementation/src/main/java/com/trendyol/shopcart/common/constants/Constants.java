package com.trendyol.shopcart.common.constants;

import com.trendyol.shopcart.product.model.Category;

import java.math.BigDecimal;

public class Constants {
  public static final BigDecimal DELIVERY_FIXED_COST = new BigDecimal("2.99");
  public static final BigDecimal DELIVERY_COST_PER_DELIVERY = new BigDecimal("0.1");
  public static final BigDecimal DELIVERY_COST_PER_PRODUCT = new BigDecimal("0.01");

  public static final Category FOOD_CATEGORY = new Category("Food");
  public static final Category FASHION_CATEGORY = new Category("Fashion");
  public static final Category PANTS_CATEGORY = new Category("Pants", FOOD_CATEGORY);
  public static final Category ELECTRONIC_CATEGORY = new Category("Electronic");
}
