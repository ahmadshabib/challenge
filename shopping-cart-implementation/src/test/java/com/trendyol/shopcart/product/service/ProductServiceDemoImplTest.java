package com.trendyol.shopcart.product.service;

import com.trendyol.shopcart.common.exception.ElementNotFoundException;
import com.trendyol.shopcart.product.model.Category;
import com.trendyol.shopcart.product.model.Product;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class ProductServiceDemoImplTest {

  private ProductServiceDemoImpl productService;
  private Product apple, pants;

  @Before
  public void setup() {
    productService = new ProductServiceDemoImpl();
    Category fashionCategory = new Category("Fashion");
    Category foodCategory = new Category("Food");
    apple = new Product(10, "apple", new BigDecimal("10.00"), foodCategory);
    pants = new Product(23, "pants", new BigDecimal("100.00"), fashionCategory);
  }

  @Test
  public void numberOfProductsTest() {
    Assert.assertEquals(4, productService.retrieveAll().size());
  }

  @Test
  public void addProductTest() throws ElementNotFoundException {
    productService.create(pants);
    Assert.assertEquals(pants, productService.retrieve(pants.getId()));
  }


  @Test(expected = ElementNotFoundException.class)
  public void retrieveNonExcitedProductTest() throws ElementNotFoundException {
    productService.delete(apple);
    Assert.assertEquals(apple, productService.retrieve(apple.getId()));
  }

  @After
  public void removedAdditionalProducts(){
    productService.delete(apple);
    productService.delete(pants);
  }
}
