package com.challenge.shopcart.shoppingcart.model;


import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public class DeliveryCostCalculator {
  private @NonNull BigDecimal costPerDelivery;
  private @NonNull BigDecimal costPerProduct;
  private @NonNull BigDecimal fixedCost;
}
