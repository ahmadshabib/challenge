package com.trendyol.shopcart.command;

import com.trendyol.shopcart.campaign.service.CampaignServiceDemoImpl;
import com.trendyol.shopcart.coupon.service.CouponServiceDemoImpl;
import com.trendyol.shopcart.product.service.ProductServiceDemoImpl;
import com.trendyol.shopcart.shell.ShellHelper;
import com.trendyol.shopcart.utils.ListPrintHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
public class ListCommand {

  private final ShellHelper shellHelper;

  private ProductServiceDemoImpl productService = new ProductServiceDemoImpl();
  private CampaignServiceDemoImpl campaignService = new CampaignServiceDemoImpl();
  private CouponServiceDemoImpl couponService = new CouponServiceDemoImpl();

  @Autowired
  public ListCommand(ShellHelper shellHelper) {
    this.shellHelper = shellHelper;
  }

  @ShellMethod("Displays greeting message to the user whose name is supplied")
  public String product(@ShellOption(arity = 0, defaultValue = "list") String list) {
    return getMessageToPrint(new ListPrintHelper(), productService.retrieveAll(), Type.PRODUCT);
  }

  @ShellMethod("Displays greeting message to the user whose name is supplied")
  public String campaign(@ShellOption(arity = 0, defaultValue = "list") String list) {
    return getMessageToPrint(new ListPrintHelper(), campaignService.retrieveAll(), Type.CAMPAIGN);
  }

  @ShellMethod("Displays greeting message to the user whose name is supplied")
  public String coupon(@ShellOption(arity = 0, defaultValue = "list") String list) {
    return getMessageToPrint(new ListPrintHelper(), couponService.retrieveAll(), Type.COUPON);
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
