package com.trendyol.shopcart.shell.model;

public enum PromptColor {
  RED(1),
  GREEN(2),
  YELLOW(3),
  CYAN(6);

  private final int value;

  PromptColor(int value) {
    this.value = value;
  }

  public int toJlineAttributedStyle() {
    return this.value;
  }
}
