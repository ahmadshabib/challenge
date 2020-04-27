package com.trendyol.shopcart.shoppingcart.command;

import com.trendyol.shopcart.campaign.repository.CampaignRepositoryDemoImpl;
import com.trendyol.shopcart.coupon.repository.CouponRepositoryDemoImpl;
import com.trendyol.shopcart.product.repository.ProductRepositoryDemoImpl;
import com.trendyol.shopcart.shell.utils.ShellHelper;
import com.trendyol.shopcart.shoppingcart.utils.ListPrintHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
public class ListCommand {

  private final ShellHelper shellHelper;

  private ProductRepositoryDemoImpl productRepositoryDemo = new ProductRepositoryDemoImpl();
  private CampaignRepositoryDemoImpl campaignRepositoryDemo = new CampaignRepositoryDemoImpl();
  private CouponRepositoryDemoImpl couponRepositoryDemo = new CouponRepositoryDemoImpl();

  @Autowired
  public ListCommand(ShellHelper shellHelper) {
    this.shellHelper = shellHelper;
  }

  @ShellMethod("Displays greeting message to the user whose name is supplied")
  public String product(@ShellOption(arity = 0, defaultValue = "list") String list) {
    return getMessageToPrint(new ListPrintHelper(), productRepositoryDemo.retrieveAll(), Type.PRODUCT);
  }

  @ShellMethod("Displays greeting message to the user whose name is supplied")
  public String campaign(@ShellOption(arity = 0, defaultValue = "list") String list) {
    return getMessageToPrint(new ListPrintHelper(), campaignRepositoryDemo.retrieveAll(), Type.CAMPAIGN);
  }

  @ShellMethod("Displays greeting message to the user whose name is supplied")
  public String coupon(@ShellOption(arity = 0, defaultValue = "list") String list) {
    return getMessageToPrint(new ListPrintHelper(), couponRepositoryDemo.retrieveAll(), Type.COUPON);
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
