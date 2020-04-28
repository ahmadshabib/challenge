package com.challenge.shopcart.campaign.repository;

import com.challenge.shopcart.common.exception.ElementNotFoundException;
import com.challenge.shopcart.common.service.DaoRepository;
import com.challenge.shopcart.common.constants.Constants;
import com.challenge.shopcart.common.model.DiscountType;
import com.challenge.shopcart.campaign.model.Campaign;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class CampaignRepositoryDemoImpl implements DaoRepository<Campaign, Integer> {
  private static AtomicReference<HashSet<Campaign>> campaigns =
      new AtomicReference<>(new HashSet<>());

  static {
    campaigns
        .get()
        .add(
            new Campaign(
                1, Constants.FOOD_CATEGORY, new BigDecimal("10.00"), 2, DiscountType.RATIO));
    campaigns
        .get()
        .add(
            new Campaign(
                2, Constants.FASHION_CATEGORY, new BigDecimal("10.00"), 1, DiscountType.AMOUNT));
    campaigns
        .get()
        .add(
            new Campaign(
                3, Constants.PANTS_CATEGORY, new BigDecimal("10.00"), 1, DiscountType.AMOUNT));
    campaigns
        .get()
        .add(
            new Campaign(
                4, Constants.ELECTRONIC_CATEGORY, new BigDecimal("50.00"), 1, DiscountType.RATIO));
  }

  @Override
  public void create(Campaign campaign) {
    campaigns.get().add(campaign);
  }

  @Override
  public Campaign retrieve(Integer id) throws ElementNotFoundException {
    return campaigns.get().stream()
        .filter(campaign -> campaign.getId().equals(id))
        .findAny()
        .orElseThrow(() -> new ElementNotFoundException("Product Not Found"));
  }

  @Override
  public List<Campaign> retrieveAll() {
    return new ArrayList<>(campaigns.get());
  }

  @Override
  public void delete(Campaign campaign) {
    campaigns.get().remove(campaign);
  }
}
