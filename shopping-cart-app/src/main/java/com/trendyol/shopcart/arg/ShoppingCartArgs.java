package com.trendyol.shopcart.arg;

import com.beust.jcommander.Parameter;

public class ShoppingCartArgs {

  @Parameter(
      names = {"-a", "--add"},
      arity = 1,
      description = "Add using id")
  public Integer id;

  @Parameter(
      names = {"-q", "--quantity"},
      description = "Comma-separated list of group names to be run")
  public Integer quantity = 1;
}
