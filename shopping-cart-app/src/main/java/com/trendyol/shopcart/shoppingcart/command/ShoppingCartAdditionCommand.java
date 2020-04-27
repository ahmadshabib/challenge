package com.trendyol.shopcart.shoppingcart.command;

import com.trendyol.shopcart.campaign.model.Campaign;
import com.trendyol.shopcart.campaign.service.CampaignServiceImpl;
import com.trendyol.shopcart.common.exception.ElementNotFoundException;
import com.trendyol.shopcart.coupon.model.Coupon;
import com.trendyol.shopcart.coupon.service.CouponServiceImpl;
import com.trendyol.shopcart.product.model.Product;
import com.trendyol.shopcart.product.service.ProductServiceImpl;
import com.trendyol.shopcart.shell.utils.ShellHelper;
import com.trendyol.shopcart.shoppingcart.arg.ShoppingCartArgs;
import com.trendyol.shopcart.shoppingcart.service.ShoppingCliServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ShoppingCartAdditionCommand {

  @Autowired private ShellHelper shellHelper;

  @Autowired private ShoppingCliServiceImpl shoppingCliService;

  @Autowired private ProductServiceImpl productService;

  @Autowired private CouponServiceImpl couponService;

  @Autowired private CampaignServiceImpl campaignService;

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
