package com.challenge.shopcart.product.service;

import com.challenge.shopcart.common.exception.ElementNotFoundException;

import java.util.List;

public interface ProductService<T, IDT> {
  void create(T obj);

  T retrieve(IDT id) throws ElementNotFoundException;

  List<T> retrieveAll();
}
