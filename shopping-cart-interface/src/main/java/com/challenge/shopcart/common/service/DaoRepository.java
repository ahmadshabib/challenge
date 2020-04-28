package com.challenge.shopcart.common.service;

import com.challenge.shopcart.common.exception.ElementNotFoundException;

import java.util.List;

public interface DaoRepository<T, IDT> {

  void create(T obj);

  T retrieve(IDT id) throws ElementNotFoundException;

  List<T> retrieveAll();

  void delete(T obj);
}
