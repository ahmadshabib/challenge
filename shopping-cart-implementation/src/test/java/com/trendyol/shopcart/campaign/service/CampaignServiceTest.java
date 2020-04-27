package com.trendyol.shopcart.campaign.service;

import com.trendyol.shopcart.campaign.model.Campaign;
import com.trendyol.shopcart.common.model.DiscountType;
import com.trendyol.shopcart.product.model.Category;
import com.trendyol.shopcart.product.model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class CampaignServiceTest {

  CampaignService campaignService = new CampaignService();
  private Product product;

  @Before
  public void setup() {
    Category category = new Category("food");
    product = new Product(1, "bread", new BigDecimal(5), category);
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
}
