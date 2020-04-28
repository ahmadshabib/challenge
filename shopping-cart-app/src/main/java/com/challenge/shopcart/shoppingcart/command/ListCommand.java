package com.challenge.shopcart.shoppingcart.command;

import com.challenge.shopcart.campaign.service.CampaignServiceImpl;
import com.challenge.shopcart.coupon.service.CouponServiceImpl;
import com.challenge.shopcart.product.service.ProductServiceImpl;
import com.challenge.shopcart.shell.utils.ShellHelper;
import com.challenge.shopcart.shoppingcart.utils.ListPrintHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
public class ListCommand {

  private final ShellHelper shellHelper;

  @Autowired
  CampaignServiceImpl campaignService;

  @Autowired
  CouponServiceImpl couponService;

  @Autowired
  ProductServiceImpl productService;

  @Autowired
  public ListCommand(ShellHelper shellHelper) {
    this.shellHelper = shellHelper;
  }

  @ShellMethod("Displays greeting message to the user whose name is supplied")
  public String product(@ShellOption(arity = 0, defaultValue = "list") String list) {
    return getMessageToPrint(
        new ListPrintHelper(), productService.retrieveAll(), Type.PRODUCT);
  }

  @ShellMethod("Displays greeting message to the user whose name is supplied")
  public String campaign(@ShellOption(arity = 0, defaultValue = "list") String list) {
    return getMessageToPrint(
        new ListPrintHelper(), campaignService.retrieveAll(), Type.CAMPAIGN);
  }

  @ShellMethod("Displays greeting message to the user whose name is supplied")
  public String coupon(@ShellOption(arity = 0, defaultValue = "list") String list) {
    return getMessageToPrint(
        new ListPrintHelper(), campaignService.retrieveAll(), Type.COUPON);
  }

  public String getMessageToPrint(ListPrintHelper listPrintHelper, List list, Type type) {
    String output = shellHelper.getSuccessMessage(listPrintHelper.print(list));
    return output.concat("List of available " + type.toString());
  }

  enum Type {
    PRODUCT,
    COUPON,
    CAMPAIGN
  }
}
