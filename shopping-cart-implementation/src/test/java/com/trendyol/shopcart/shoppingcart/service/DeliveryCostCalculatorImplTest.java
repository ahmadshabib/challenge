package com.trendyol.shopcart.shoppingcart.service;

import com.trendyol.shopcart.common.constants.Constants;
import com.trendyol.shopcart.product.model.Category;
import com.trendyol.shopcart.product.model.Product;
import com.trendyol.shopcart.shoppingcart.model.ShoppingCart;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class DeliveryCostCalculatorImplTest {

  private DeliveryCostCalculatorServiceImpl deliveryCostCalculator;
  private ShoppingCart shoppingCart;

  @Before
  public void setup() {
    shoppingCart = new ShoppingCart();
    Category foodCategory = new Category("food");
    Category fashionCategory = new Category("fashion");
    Product bread = new Product(1, "bread", new BigDecimal("10"), foodCategory);
    Product pants = new Product(2, "pants", new BigDecimal("100"), fashionCategory);
    shoppingCart.addItem(bread, 2);
    shoppingCart.addItem(pants, 1);
    deliveryCostCalculator =
        new DeliveryCostCalculatorServiceImpl(
            Constants.DELIVERY_COST_PER_DELIVERY,
            Constants.DELIVERY_COST_PER_PRODUCT,
            Constants.DELIVERY_FIXED_COST);
  }

  @Test
  public void deliveryCostCalculation() {
    BigDecimal costForDelivery = deliveryCostCalculator.calculate(shoppingCart);
    Assert.assertEquals(new BigDecimal("3.22"), costForDelivery);
  }
}
