package com.trendyol.shopcart.utils;

import com.trendyol.shopcart.shell.utils.CollectionPrintHelper;

import java.util.Map;

public class MapPrintHelper implements CollectionPrintHelper<Map<?, ?>> {
  @Override
  public String print(Map<?, ?> map) {
    String message = "Number of elements: " + map.size() + "\n";
    for (Map.Entry t : map.entrySet()) {
      message = message.concat(t.getKey().toString() + " " + t.getValue().toString() + "\n");
    }
    return message;
  }
}
