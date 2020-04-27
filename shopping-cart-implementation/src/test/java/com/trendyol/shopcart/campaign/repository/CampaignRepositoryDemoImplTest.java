package com.trendyol.shopcart.campaign.repository;

import com.trendyol.shopcart.campaign.model.Campaign;
import com.trendyol.shopcart.common.exception.ElementNotFoundException;
import com.trendyol.shopcart.common.model.DiscountType;
import com.trendyol.shopcart.product.model.Category;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class CampaignRepositoryDemoImplTest {


  private CampaignRepositoryDemoImpl campaignRepositoryDemo;
  private Campaign foodCampaign, fashionCampaign;

  @Before
  public void setup() {
    campaignRepositoryDemo = new CampaignRepositoryDemoImpl();
    Category fashionCategory = new Category("Fashion");
    Category foodCategory = new Category("Food");
    foodCampaign = new Campaign(100,foodCategory, new BigDecimal("10.00"), 2, DiscountType.RATIO);
    fashionCampaign = new Campaign(200,fashionCategory, new BigDecimal("10.00"), 1, DiscountType.AMOUNT);
  }

  @Test
  public void numberOfProductsTest() {
    Assert.assertEquals(4, campaignRepositoryDemo.retrieveAll().size());
  }

  @Test
  public void addProductTest() throws ElementNotFoundException {
    campaignRepositoryDemo.create(foodCampaign);
    Assert.assertEquals(foodCampaign, campaignRepositoryDemo.retrieve(foodCampaign.getId()));
  }


  @Test(expected = ElementNotFoundException.class)
  public void retrieveNonExcitedProductTest() throws ElementNotFoundException {
    campaignRepositoryDemo.delete(fashionCampaign);
    Assert.assertEquals(fashionCampaign, campaignRepositoryDemo.retrieve(fashionCampaign.getId()));
  }

  @After
  public void removedAdditionalProducts(){
    campaignRepositoryDemo.delete(foodCampaign);
    campaignRepositoryDemo.delete(fashionCampaign);
  }

}
