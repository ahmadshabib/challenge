package com.trendyol.shopcart.product.service;

import com.trendyol.shopcart.common.exception.ElementNotFoundException;
import com.trendyol.shopcart.product.model.Product;
import com.trendyol.shopcart.product.repository.ProductRepositoryDemoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService<Product,Integer> {

  @Autowired
  ProductRepositoryDemoImpl productRepositoryDemo;

  @Override
  public void create(Product product) {
    productRepositoryDemo.create(product);
  }

  @Override
  public Product retrieve(Integer id) throws ElementNotFoundException {
    return productRepositoryDemo.retrieve(id);
  }

  @Override
  public List<Product> retrieveAll() {
    return productRepositoryDemo.retrieveAll();
  }
}
