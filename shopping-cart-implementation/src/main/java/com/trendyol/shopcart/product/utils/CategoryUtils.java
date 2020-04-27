package com.trendyol.shopcart.product.utils;

import com.trendyol.shopcart.product.model.Product;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class CategoryUtils {

  public static Integer calculateNumberOfDistinctCategories(Set<Product> products) {
    return products.stream()
        .map(Product::getCategory)
        .distinct()
        .collect(Collectors.toList())
        .size();
  }
}
