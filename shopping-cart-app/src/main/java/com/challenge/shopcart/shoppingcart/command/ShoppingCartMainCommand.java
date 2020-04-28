package com.challenge.shopcart.shoppingcart.command;

import com.challenge.shopcart.shoppingcart.service.ShoppingCliServiceImpl;
import com.challenge.shopcart.shell.utils.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ShoppingCartMainCommand {
  private final ShellHelper shellHelper;
  private final ShoppingCliServiceImpl shoppingCliService;

  @Autowired
  public ShoppingCartMainCommand(
      ShellHelper shellHelper, ShoppingCliServiceImpl shoppingCliService) {
    this.shellHelper = shellHelper;
    this.shoppingCliService = shoppingCliService;
  }

  @ShellMethod("Show items selected")
  public String show() {
    return shellHelper.getInfoMessage(shoppingCliService.show());
  }

  @ShellMethod("Checkout")
  public String checkout() {
    shoppingCliService.checkout();
    return shellHelper.getInfoMessage("Checkout Done !!! Thanks for buying");
  }
}
