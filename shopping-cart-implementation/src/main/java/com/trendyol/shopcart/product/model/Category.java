package com.trendyol.shopcart.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor()
@AllArgsConstructor
public class Category {
  private @NonNull String title;
  private Category parentCategory;

  @Override
  public String toString() {
    return "Title: '" + this.title + "'";
  }
}
