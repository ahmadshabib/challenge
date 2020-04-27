package com.trendyol.shopcart.common.service;

import com.trendyol.shopcart.common.exception.ElementNotFoundException;

import java.util.List;

public interface DaoService<T, IDT> {

  void create(T obj);

  T retrieve(IDT id) throws ElementNotFoundException;

  List<T> retrieveAll();

  void delete(T obj);
}
