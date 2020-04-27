package com.trendyol.shopcart.product.repository;

import com.trendyol.shopcart.common.constants.Constants;
import com.trendyol.shopcart.common.exception.ElementNotFoundException;
import com.trendyol.shopcart.common.service.DaoRepository;
import com.trendyol.shopcart.product.model.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class ProductRepositoryDemoImpl implements DaoRepository<Product, Integer> {

  private static AtomicReference<HashSet<Product>> products =
      new AtomicReference<>(new HashSet<>());

  static {
    products.get().add(new Product(1, "bread", new BigDecimal("10.00"), Constants.FOOD_CATEGORY));
    products
        .get()
        .add(new Product(2, "cheap pants", new BigDecimal("20.00"), Constants.PANTS_CATEGORY));
    products
        .get()
        .add(new Product(3, "pants", new BigDecimal("100.00"), Constants.FASHION_CATEGORY));
    products.get().add(new Product(4, "apple", new BigDecimal("5.00"), Constants.FOOD_CATEGORY));
  }

  @Override
  public void create(Product product) {
    products.get().add(product);
  }

  @Override
  public Product retrieve(Integer id) throws ElementNotFoundException {
    return products.get().stream()
        .filter(product -> product.getId().equals(id))
        .findAny()
        .orElseThrow(() -> new ElementNotFoundException("Product Not Found"));
  }

  @Override
  public List<Product> retrieveAll() {
    return new ArrayList<>(products.get());
  }

  @Override
  public void delete(Product product) {
    products.get().remove(product);
  }
}
