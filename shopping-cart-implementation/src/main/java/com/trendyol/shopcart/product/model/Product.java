package com.trendyol.shopcart.product.model;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;

@Data
public class Product {
  private @Getter @Setter @NonNull Integer id;
  private @Getter @Setter @NonNull String title;
  private @Getter @Setter @NonNull BigDecimal price;
  private @Getter @Setter @NonNull Category category;
  private @Getter @Setter BigDecimal priceAfterDiscount;

  public Product(Integer id, String title, BigDecimal price, Category category) {
    this.id = id;
    this.title = title;
    this.price = price;
    this.category = category;
    this.priceAfterDiscount = price;
  }

  public void applyDiscount(BigDecimal discount) {
    priceAfterDiscount = priceAfterDiscount.subtract(discount);
  }

  @Override
  public String toString() {
    return "ID: '"
        + this.id
        + "', Title: '"
        + this.title
        + "', Price: '"
        + this.price
        + "', Category: '"
        + this.category
        + "'";
  }
}
