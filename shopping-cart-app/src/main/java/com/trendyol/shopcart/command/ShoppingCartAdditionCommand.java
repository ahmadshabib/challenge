package com.trendyol.shopcart.command;

import com.trendyol.shopcart.arg.ShoppingCartArgs;
import com.trendyol.shopcart.campaign.model.Campaign;
import com.trendyol.shopcart.campaign.service.CampaignServiceDemoImpl;
import com.trendyol.shopcart.common.exception.ElementNotFoundException;
import com.trendyol.shopcart.coupon.model.Coupon;
import com.trendyol.shopcart.coupon.service.CouponServiceDemoImpl;
import com.trendyol.shopcart.product.model.Product;
import com.trendyol.shopcart.product.service.ProductServiceDemoImpl;
import com.trendyol.shopcart.service.ShoppingCliServiceImpl;
import com.trendyol.shopcart.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ShoppingCartAdditionCommand {

  private final ShellHelper shellHelper;
  private final ShoppingCliServiceImpl shoppingCliService;
  private ProductServiceDemoImpl productService = new ProductServiceDemoImpl();
  private CouponServiceDemoImpl couponService = new CouponServiceDemoImpl();
  private CampaignServiceDemoImpl campaignService = new CampaignServiceDemoImpl();

  @Autowired
  public ShoppingCartAdditionCommand(
      ShellHelper shellHelper, ShoppingCliServiceImpl shoppingCliService) {
    this.shellHelper = shellHelper;
    this.shoppingCliService = shoppingCliService;
  }

  @ShellMethod("Add items to basket")
  public String additem(@ShellOption(optOut = true) ShoppingCartArgs args) {
    if (args.id == null) {
      return shellHelper.getErrorMessage("Please specify a product");
    }
    try {
      Product product = productService.retrieve(args.id);
      shoppingCliService.selectItem(product, args.quantity);
      return shellHelper.getSuccessMessage("Product has been added");
    } catch (ElementNotFoundException e) {
      return shellHelper.getErrorMessage(e.getMessage());
    }
  }

  @ShellMethod("Add coupon to basket")
  public String addcoupon(@ShellOption(optOut = true) ShoppingCartArgs args) {
    if (args.id == null) {
      return shellHelper.getErrorMessage("Please specify a coupon");
    }
    try {
      Coupon coupon = couponService.retrieve(args.id);
      shoppingCliService.selectCoupon(coupon);
      return shellHelper.getSuccessMessage("Coupon has been added");
    } catch (ElementNotFoundException e) {
      return shellHelper.getErrorMessage(e.getMessage());
    }
  }

  @ShellMethod("Add campaign to basket")
  public String addcampaign(@ShellOption(optOut = true) ShoppingCartArgs args) {
    if (args.id == null) {
      return shellHelper.getErrorMessage("Please specify a campaign");
    }
    try {
      Campaign campaign = campaignService.retrieve(args.id);
      shoppingCliService.selectCampaign(campaign);
      return shellHelper.getSuccessMessage("Campaign has been added");
    } catch (ElementNotFoundException e) {
      return shellHelper.getErrorMessage(e.getMessage());
    }
  }
}
