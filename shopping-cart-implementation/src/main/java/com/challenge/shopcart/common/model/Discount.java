package com.challenge.shopcart.common.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Discount {
  private BigDecimal totalDiscount = new BigDecimal("0.00");
  private BigDecimal couponDiscount = new BigDecimal("0.00");
  private BigDecimal campaignDiscount = new BigDecimal("0.00");
  private BigDecimal deliveryCost = new BigDecimal("0.00");

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
