package com.challenge.shopcart.shoppingcart.utils;

import com.challenge.shopcart.shell.utils.CollectionPrintHelper;

import java.util.List;

public class ListPrintHelper implements CollectionPrintHelper<List> {
  @Override
  public String print(List list) {
    String message = "Number of elements: " + list.size() + "\n";
    for (Object t : list) {
      message = message.concat(t.toString() + "\n");
    }
    return message;
  }
}
