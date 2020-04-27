package com.trendyol.shopcart.shoppingcart.command;

import com.trendyol.shopcart.shoppingcart.arg.ShoppingCartArgs;
import com.trendyol.shopcart.campaign.model.Campaign;
import com.trendyol.shopcart.campaign.repository.CampaignRepositoryDemoImpl;
import com.trendyol.shopcart.common.exception.ElementNotFoundException;
import com.trendyol.shopcart.coupon.model.Coupon;
import com.trendyol.shopcart.coupon.repository.CouponRepositoryDemoImpl;
import com.trendyol.shopcart.product.model.Product;
import com.trendyol.shopcart.product.repository.ProductRepositoryDemoImpl;
import com.trendyol.shopcart.shoppingcart.service.ShoppingCliServiceImpl;
import com.trendyol.shopcart.shell.utils.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ShoppingCartAdditionCommand {

  private final ShellHelper shellHelper;
  private final ShoppingCliServiceImpl shoppingCliService;
  private ProductRepositoryDemoImpl productRepositoryDemo = new ProductRepositoryDemoImpl();
  private CouponRepositoryDemoImpl couponRepositoryDemo = new CouponRepositoryDemoImpl();
  private CampaignRepositoryDemoImpl campaignRepositoryDemo = new CampaignRepositoryDemoImpl();

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
      Product product = productRepositoryDemo.retrieve(args.id);
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
      Coupon coupon = couponRepositoryDemo.retrieve(args.id);
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
      Campaign campaign = campaignRepositoryDemo.retrieve(args.id);
      shoppingCliService.selectCampaign(campaign);
      return shellHelper.getSuccessMessage("Campaign has been added");
    } catch (ElementNotFoundException e) {
      return shellHelper.getErrorMessage(e.getMessage());
    }
  }
}
