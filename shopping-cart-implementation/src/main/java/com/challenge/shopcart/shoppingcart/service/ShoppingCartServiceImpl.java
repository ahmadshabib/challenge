package com.challenge.shopcart.shoppingcart.service;

import com.challenge.shopcart.campaign.model.Campaign;
import com.challenge.shopcart.campaign.service.CampaignServiceImpl;
import com.challenge.shopcart.common.constants.Constants;
import com.challenge.shopcart.common.utils.PrintHelper;
import com.challenge.shopcart.coupon.model.Coupon;
import com.challenge.shopcart.coupon.service.CouponServiceImpl;
import com.challenge.shopcart.product.model.Product;
import com.challenge.shopcart.shoppingcart.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class ShoppingCartServiceImpl implements ShoppingCartService<BigDecimal> {
  private ShoppingCart shoppingCart = new ShoppingCart();

  @Autowired
  CampaignServiceImpl campaignService;

  @Autowired
  CouponServiceImpl couponServiceImpl;

  public void addItem(Product product, Integer quantity) {
    this.shoppingCart.addItem(product, quantity);
  }

  public void removeItem(Product product, Integer quantity) {
    this.shoppingCart.removeItem(product, quantity);
  }

  public Map<Product, Integer> getSelectedItems() {
    return this.shoppingCart.getProducts();
  }

  public void applyDiscounts(Campaign... discounts) {
    campaignService.applyDiscounts(shoppingCart, discounts);
  }

  public void applyCoupons(Coupon... coupons) {
    couponServiceImpl.applyCoupons(shoppingCart, coupons);
  }

  public void calculateDeliveryCost() {
    if (shoppingCart.getProducts().size() > 0) {
      DeliveryCostCalculatorServiceImpl deliveryCostCalculator =
          new DeliveryCostCalculatorServiceImpl(
              Constants.DELIVERY_COST_PER_DELIVERY,
              Constants.DELIVERY_COST_PER_PRODUCT,
              Constants.DELIVERY_FIXED_COST);
      BigDecimal newTotalAmount =
          shoppingCart.getTotalAmount().add(deliveryCostCalculator.calculate(shoppingCart));
      shoppingCart.setTotalAmount(newTotalAmount);
      shoppingCart
          .getDiscount()
          .increaseDeliveryCost(deliveryCostCalculator.calculate(shoppingCart));
    }
  }

  public BigDecimal getTotalAmountAfterDiscounts() {
    return shoppingCart.getTotalAmount().subtract(shoppingCart.getDiscount().getTotalDiscount());
  }

  public BigDecimal getCouponDiscount() {
    return shoppingCart.getDiscount().getCouponDiscount();
  }

  public BigDecimal getCampaignDiscount() {
    return shoppingCart.getDiscount().getCampaignDiscount();
  }

  public BigDecimal getDeliveryCost() {
    return shoppingCart.getDiscount().getDeliveryCost();
  }

  public void print() {
    PrintHelper.printShoppingCart(shoppingCart, getTotalAmountAfterDiscounts(), getDeliveryCost());
  }

  public void cleanUpCart() {
    shoppingCart.cleanUpCart();
  }
}
