package com.challenge.shopcart.shoppingcart.service;

import com.challenge.shopcart.campaign.model.Campaign;
import com.challenge.shopcart.campaign.service.CampaignServiceImpl;
import com.challenge.shopcart.common.model.DiscountType;
import com.challenge.shopcart.coupon.model.Coupon;
import com.challenge.shopcart.coupon.service.CouponServiceImpl;
import com.challenge.shopcart.product.model.Category;
import com.challenge.shopcart.product.model.Product;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.HashMap;

public class ShoppingCartServiceImplTest {
  private ShoppingCartServiceImpl shoppingCartService;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  private Product bread, pants, apple, fashionPants;
  private Campaign foodCampain, fashionCampain, electronicCampaign, pantsCampaign;
  private Coupon twentyTLCoupon, tenPercentCoupon, oneThousandCoupin;
  private Category foodCategory, fashionCategory, pantsCategory, electronicCategory;

  @Before
  public void setup() {
    shoppingCartService = new ShoppingCartServiceImpl();
    shoppingCartService.campaignService = new CampaignServiceImpl();
    shoppingCartService.couponServiceImpl = new CouponServiceImpl();
    foodCategory = new Category("food");
    fashionCategory = new Category("fashion");
    pantsCategory = new Category("pants", fashionCategory);
    electronicCategory = new Category("electronic");
    bread = new Product(1, "bread", new BigDecimal("10.00"), foodCategory);
    pants = new Product(2, "pants", new BigDecimal("100.00"), pantsCategory);
    fashionPants = new Product(3, "pants", new BigDecimal("100.00"), fashionCategory);
    apple = new Product(4, "apple", new BigDecimal("5.00"), foodCategory);
    foodCampain = new Campaign(1, foodCategory, new BigDecimal("10.00"), 2, DiscountType.RATIO);
    fashionCampain =
        new Campaign(2, fashionCategory, new BigDecimal("10.00"), 1, DiscountType.AMOUNT);
    pantsCampaign = new Campaign(3, pantsCategory, new BigDecimal("10.00"), 1, DiscountType.AMOUNT);
    electronicCampaign =
        new Campaign(4, electronicCategory, new BigDecimal("50.00"), 1, DiscountType.RATIO);
    twentyTLCoupon =
        new Coupon(1, new BigDecimal("50.00"), new BigDecimal("20.00"), DiscountType.AMOUNT);
    tenPercentCoupon =
        new Coupon(2, new BigDecimal("70.00"), new BigDecimal("10.00"), DiscountType.RATIO);
    oneThousandCoupin =
        new Coupon(3, new BigDecimal("1000.00"), new BigDecimal("1000.00"), DiscountType.AMOUNT);
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void cleanUp() {
    shoppingCartService.cleanUpCart();
  }

  @Test
  public void selectedItemsTest() {
    shoppingCartService.addItem(bread, 2);
    HashMap<Product, Integer> products = new HashMap<>();
    products.put(bread, 2);
    Assert.assertEquals(products, shoppingCartService.getSelectedItems());
  }

  @Test
  public void withoutDiscountTest() {
    shoppingCartService.addItem(bread, 2);
    shoppingCartService.addItem(apple, 2);
    shoppingCartService.addItem(pants, 1);
    Assert.assertEquals(
        new BigDecimal("130.00"), shoppingCartService.getTotalAmountAfterDiscounts());
  }

  @Test
  public void withoutDiscountAndQuantityIncrementTest() {
    shoppingCartService.addItem(bread, 2);
    shoppingCartService.addItem(apple, 2);
    shoppingCartService.addItem(pants, 1);
    shoppingCartService.addItem(pants, 1);
    Assert.assertEquals(
        new BigDecimal("230.00"), shoppingCartService.getTotalAmountAfterDiscounts());
  }

  @Test
  public void withoutDiscountAndExtraQuantityDecreaseTest() {
    shoppingCartService.addItem(bread, 2);
    shoppingCartService.addItem(apple, 2);
    shoppingCartService.removeItem(apple, 3);
    shoppingCartService.addItem(pants, 1);
    shoppingCartService.addItem(pants, 1);
    Assert.assertEquals(
        new BigDecimal("220.00"), shoppingCartService.getTotalAmountAfterDiscounts());
  }

  @Test
  public void withoutDiscountAndQuantityDecreaseTest() {
    shoppingCartService.addItem(bread, 2);
    shoppingCartService.addItem(apple, 2);
    shoppingCartService.removeItem(apple, 1);
    shoppingCartService.addItem(pants, 1);
    shoppingCartService.addItem(pants, 1);
    Assert.assertEquals(
        new BigDecimal("225.00"), shoppingCartService.getTotalAmountAfterDiscounts());
  }

  @Test
  public void withoutDiscountAndItemRemovalTest() {
    shoppingCartService.addItem(bread, 2);
    shoppingCartService.addItem(apple, 2);
    shoppingCartService.removeItem(apple, 2);
    shoppingCartService.addItem(pants, 1);
    shoppingCartService.addItem(pants, 1);
    Assert.assertEquals(
        new BigDecimal("220.00"), shoppingCartService.getTotalAmountAfterDiscounts());
  }

  @Test
  public void withOneRationBasedCampaignTest() {
    shoppingCartService.addItem(bread, 2);
    shoppingCartService.addItem(apple, 2);
    shoppingCartService.addItem(pants, 1);
    shoppingCartService.applyDiscounts(foodCampain);
    Assert.assertEquals(
        new BigDecimal("127.00"), shoppingCartService.getTotalAmountAfterDiscounts());
    Assert.assertEquals(new BigDecimal("3.00"), shoppingCartService.getCampaignDiscount());
  }

  @Test
  public void withTwoCampaignsTest() {
    shoppingCartService.addItem(bread, 2);
    shoppingCartService.addItem(apple, 2);
    shoppingCartService.addItem(pants, 1);
    shoppingCartService.applyDiscounts(foodCampain, fashionCampain);
    Assert.assertEquals(
        new BigDecimal("117.00"), shoppingCartService.getTotalAmountAfterDiscounts());
    Assert.assertEquals(new BigDecimal("13.00"), shoppingCartService.getCampaignDiscount());
  }

  @Test
  public void withOneCampaignsTest() {
    shoppingCartService.addItem(fashionPants, 1);
    shoppingCartService.applyDiscounts(pantsCampaign);
    Assert.assertEquals(
        new BigDecimal("90.00"), shoppingCartService.getTotalAmountAfterDiscounts());
    Assert.assertEquals(new BigDecimal("10.00"), shoppingCartService.getCampaignDiscount());
  }

  @Test
  public void withOneCouponTest() {
    shoppingCartService.addItem(bread, 2);
    shoppingCartService.addItem(apple, 2);
    shoppingCartService.addItem(pants, 1);
    shoppingCartService.applyCoupons(twentyTLCoupon);
    Assert.assertEquals(
        new BigDecimal("110.00"), shoppingCartService.getTotalAmountAfterDiscounts());
    Assert.assertEquals(new BigDecimal("20.00"), shoppingCartService.getCouponDiscount());
  }

  @Test
  public void withNonApplicableCouponTest() {
    shoppingCartService.addItem(bread, 2);
    shoppingCartService.addItem(apple, 2);
    shoppingCartService.addItem(pants, 1);
    shoppingCartService.applyCoupons(oneThousandCoupin);
    Assert.assertEquals(
        new BigDecimal("130.00"), shoppingCartService.getTotalAmountAfterDiscounts());
    Assert.assertEquals(new BigDecimal("0.00"), shoppingCartService.getCouponDiscount());
  }

  @Test
  public void withTwoCouponsTest() {
    shoppingCartService.addItem(bread, 2);
    shoppingCartService.addItem(apple, 2);
    shoppingCartService.addItem(pants, 1);
    shoppingCartService.applyCoupons(twentyTLCoupon, tenPercentCoupon);
    Assert.assertEquals(
        new BigDecimal("99.00"), shoppingCartService.getTotalAmountAfterDiscounts());
    Assert.assertEquals(new BigDecimal("31.00"), shoppingCartService.getCouponDiscount());
  }

  @Test
  public void withCouponsAndCampaignTest() {
    shoppingCartService.addItem(bread, 2);
    shoppingCartService.addItem(apple, 2);
    shoppingCartService.addItem(pants, 1);
    shoppingCartService.applyDiscounts(fashionCampain);
    shoppingCartService.applyCoupons(twentyTLCoupon);
    Assert.assertEquals(
        new BigDecimal("100.00"), shoppingCartService.getTotalAmountAfterDiscounts());
    Assert.assertEquals(new BigDecimal("20.00"), shoppingCartService.getCouponDiscount());
    Assert.assertEquals(new BigDecimal("10.00"), shoppingCartService.getCampaignDiscount());
  }

  @Test
  public void withOneItemWithOutputTest() {
    shoppingCartService.addItem(bread, 2);
    shoppingCartService.calculateDeliveryCost();
    shoppingCartService.print();
    String expected =
        "Category Title: food -- Product Name: bread -- Product Quantity: 2 -- Original Price: 20.00 -- Price After Discount: 20.00" + System.lineSeparator()
            + "Total Price: 23.11 -- Shipping Price: 3.11";
    Assert.assertEquals(expected, outContent.toString().trim());
  }

  @Test
  public void withItemsWithCouponOutputTest() {
    shoppingCartService.addItem(bread, 2);
    shoppingCartService.addItem(fashionPants, 2);
    shoppingCartService.applyCoupons(twentyTLCoupon);
    shoppingCartService.calculateDeliveryCost();
    shoppingCartService.print();
    String expected =
        "Category Title: food -- Product Name: bread -- Product Quantity: 2 -- Original Price: 20.00 -- Price After Discount: 18.18" + System.lineSeparator()
            + "Category Title: fashion -- Product Name: pants -- Product Quantity: 2 -- Original Price: 200.00 -- Price After Discount: 181.82" + System.lineSeparator()
            + "Total Price: 203.23 -- Shipping Price: 3.23";
    Assert.assertEquals(expected, outContent.toString().trim());
  }

  @Test
  public void withItemsWithCampaignOutputTest() {
    shoppingCartService.addItem(bread, 2);
    shoppingCartService.addItem(fashionPants, 2);
    shoppingCartService.applyDiscounts(foodCampain);
    shoppingCartService.calculateDeliveryCost();
    shoppingCartService.print();
    String expected =
        "Category Title: food -- Product Name: bread -- Product Quantity: 2 -- Original Price: 20.00 -- Price After Discount: 18.00" + System.lineSeparator()
            + "Category Title: fashion -- Product Name: pants -- Product Quantity: 2 -- Original Price: 200.00 -- Price After Discount: 200.00" + System.lineSeparator()
            + "Total Price: 221.23 -- Shipping Price: 3.23";
    Assert.assertEquals(expected, outContent.toString().trim());
  }

  @Test
  public void emptyBasketDeliveryCostTest() {
    shoppingCartService.calculateDeliveryCost();
    Assert.assertEquals(new BigDecimal("0.00"), shoppingCartService.getDeliveryCost());
  }

  @Test
  public void withFullFlowTest() {
    shoppingCartService.addItem(bread, 2);
    shoppingCartService.addItem(fashionPants, 2);
    shoppingCartService.applyDiscounts(foodCampain, fashionCampain);
    shoppingCartService.applyCoupons(twentyTLCoupon, tenPercentCoupon);
    shoppingCartService.calculateDeliveryCost();
    shoppingCartService.print();
    String expected =
        "Category Title: food -- Product Name: bread -- Product Quantity: 2 -- Original Price: 20.00 -- Price After Discount: 14.48" + System.lineSeparator()
            + "Category Title: fashion -- Product Name: pants -- Product Quantity: 2 -- Original Price: 200.00 -- Price After Discount: 154.72" + System.lineSeparator()
            + "Total Price: 172.43 -- Shipping Price: 3.23";
    Assert.assertEquals(expected, outContent.toString().trim());
  }
}
