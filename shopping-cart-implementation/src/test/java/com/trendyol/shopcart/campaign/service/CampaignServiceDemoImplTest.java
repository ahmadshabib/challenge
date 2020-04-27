package com.trendyol.shopcart.campaign.service;

import com.trendyol.shopcart.campaign.model.Campaign;
import com.trendyol.shopcart.common.exception.ElementNotFoundException;
import com.trendyol.shopcart.common.model.DiscountType;
import com.trendyol.shopcart.product.model.Category;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class CampaignServiceDemoImplTest {


  private CampaignServiceDemoImpl campaignService;
  private Campaign foodCampaign, fashionCampaign;

  @Before
  public void setup() {
    campaignService = new CampaignServiceDemoImpl();
    Category fashionCategory = new Category("Fashion");
    Category foodCategory = new Category("Food");
    foodCampaign = new Campaign(100,foodCategory, new BigDecimal("10.00"), 2, DiscountType.RATIO);
    fashionCampaign = new Campaign(200,fashionCategory, new BigDecimal("10.00"), 1, DiscountType.AMOUNT);
  }

  @Test
  public void numberOfProductsTest() {
    Assert.assertEquals(4, campaignService.retrieveAll().size());
  }

  @Test
  public void addProductTest() throws ElementNotFoundException {
    campaignService.create(foodCampaign);
    Assert.assertEquals(foodCampaign, campaignService.retrieve(foodCampaign.getId()));
  }


  @Test(expected = ElementNotFoundException.class)
  public void retrieveNonExcitedProductTest() throws ElementNotFoundException {
    campaignService.delete(fashionCampaign);
    Assert.assertEquals(fashionCampaign, campaignService.retrieve(fashionCampaign.getId()));
  }

  @After
  public void removedAdditionalProducts(){
    campaignService.delete(foodCampaign);
    campaignService.delete(fashionCampaign);
  }

}
