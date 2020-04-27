package com.trendyol.shopcart.common.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Discount {
  private BigDecimal totalDiscount = BigDecimal.ZERO;
  private BigDecimal couponDiscount = BigDecimal.ZERO;
  private BigDecimal campaignDiscount = BigDecimal.ZERO;
  private BigDecimal deliveryCost = BigDecimal.ZERO;

  public void increaseTotalDiscount(BigDecimal additionalValue) {
    this.totalDiscount = totalDiscount.add(additionalValue);
  }

  public void increaseCouponDiscount(BigDecimal additionalValue) {
    this.couponDiscount = couponDiscount.add(additionalValue);
  }

  public void increaseCampaignDiscount(BigDecimal additionalValue) {
    this.campaignDiscount = campaignDiscount.add(additionalValue);
  }

  public void increaseDeliveryCost(BigDecimal additionalValue) {
    this.deliveryCost = deliveryCost.add(additionalValue);
  }
}
