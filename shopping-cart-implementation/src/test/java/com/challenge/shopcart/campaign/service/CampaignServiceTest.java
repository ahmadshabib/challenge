package com.challenge.shopcart.campaign.service;

import com.challenge.shopcart.campaign.model.Campaign;
import com.challenge.shopcart.campaign.repository.CampaignRepositoryDemoImpl;
import com.challenge.shopcart.common.exception.ElementNotFoundException;
import com.challenge.shopcart.common.model.DiscountType;
import com.challenge.shopcart.product.model.Category;
import com.challenge.shopcart.product.model.Product;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class CampaignServiceTest {

  private CampaignServiceImpl campaignService = new CampaignServiceImpl();

  private Product product;
  private Campaign foodCampaign, fashionCampaign;

  @Before
  public void setup() {
    campaignService.campaignRepositoryDemo = new CampaignRepositoryDemoImpl();
    Category fashionCategory = new Category("Fashion");
    Category foodCategory = new Category("Food");
    foodCampaign = new Campaign(100, foodCategory, new BigDecimal("10.00"), 2, DiscountType.RATIO);
    fashionCampaign =
        new Campaign(200, fashionCategory, new BigDecimal("10.00"), 1, DiscountType.AMOUNT);

    Category category = new Category("food");
    product = new Product(1, "bread", new BigDecimal(5), category);
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

  @Test
  public void campaignNotApplicableTest() {
    Campaign campaign =
        new Campaign(1, new Category("fashion"), new BigDecimal(50), 2, DiscountType.RATIO);
    Assert.assertFalse(campaignService.isApplicable(campaign, product, 2));
    campaign.setCategory(new Category(("food")));
    Assert.assertFalse(campaignService.isApplicable(campaign, product, 1));
  }

  @Test
  public void campaignApplicableTest() {
    Campaign campaign =
        new Campaign(1, new Category("food"), new BigDecimal(50), 2, DiscountType.RATIO);
    Assert.assertTrue(campaignService.isApplicable(campaign, product, 2));
  }

  @After
  public void removedAdditionalProducts() {
    campaignService.delete(foodCampaign);
    campaignService.delete(fashionCampaign);
  }
}
