package com.challenge.shopcart.shoppingcart.service;

import com.challenge.shopcart.shoppingcart.model.DeliveryCostCalculator;
import com.challenge.shopcart.shoppingcart.model.ShoppingCart;
import com.challenge.shopcart.product.utils.CategoryUtils;

import java.math.BigDecimal;

public class DeliveryCostCalculatorServiceImpl
    implements DeliveryCostCalculatorService<BigDecimal, ShoppingCart> {
  private DeliveryCostCalculator deliveryCostCalculator;

  public DeliveryCostCalculatorServiceImpl(
      BigDecimal costPerDelivery, BigDecimal costPerProduct, BigDecimal fixedCost) {
    deliveryCostCalculator = new DeliveryCostCalculator(costPerDelivery, costPerProduct, fixedCost);
  }

  @Override
  public BigDecimal calculate(ShoppingCart shoppingCart) {
    // Multiplying cost per delivery with ...
    return deliveryCostCalculator
        .getCostPerDelivery()
        .multiply( // ... the number of distinct categories
            BigDecimal.valueOf(
                CategoryUtils.calculateNumberOfDistinctCategories(
                    shoppingCart.getProducts().keySet())))
        .add( // Adding with the result of...
            deliveryCostCalculator
                .getCostPerProduct()
                .multiply( // ... multiplying cost per product with the total number of products
                    new BigDecimal(
                        shoppingCart.getProducts().values().stream()
                            .mapToInt(Integer::intValue)
                            .sum())))
        .add(deliveryCostCalculator.getFixedCost()); // Finally adding the fixed cost
  }
}
