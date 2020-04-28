package com.challenge.shopcart.shoppingcart.service;

import com.challenge.shopcart.campaign.model.Campaign;
import com.challenge.shopcart.coupon.model.Coupon;
import com.challenge.shopcart.product.model.Product;
import com.challenge.shopcart.shoppingcart.utils.ListPrintHelper;
import com.challenge.shopcart.shoppingcart.utils.MapPrintHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;

@Component
public class ShoppingCliServiceImpl {

  private HashSet<Campaign> campaigns = new HashSet<>();
  private HashSet<Coupon> coupons = new HashSet<>();

  @Autowired
  private ShoppingCartServiceImpl shoppingCartService;

  public void selectItem(Product product, Integer quantity) {
    shoppingCartService.addItem(product, quantity);
  }

  public void selectCampaign(Campaign campaign) {
    campaigns.add(campaign);
  }

  public void selectCoupon(Coupon coupon) {
    coupons.add(coupon);
  }

  public void checkout() {
    shoppingCartService.applyDiscounts(campaigns.toArray(new Campaign[campaigns.size()]));
    shoppingCartService.applyCoupons(coupons.toArray(new Coupon[coupons.size()]));
    shoppingCartService.calculateDeliveryCost();
    shoppingCartService.print();
    shoppingCartService.cleanUpCart();
    campaigns = new HashSet<>();
    coupons = new HashSet<>();
  }

  public String show() {
    String message = "";
    message = message.concat("Products :\n");
    message = message.concat(new MapPrintHelper().print(shoppingCartService.getSelectedItems()));
    message = message.concat("Campaigns :\n");
    message = message.concat(new ListPrintHelper().print(new ArrayList<>(campaigns)));
    message = message.concat("Coupons :\n");
    message = message.concat(new ListPrintHelper().print(new ArrayList<>(coupons)));
    return message;
  }
}
