package com.trendyol.shopcart.shell.utils;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartPromptProvider implements PromptProvider {

  @Value("${shell.starting.text}")
  public String startingTest;

  @Override
  public AttributedString getPrompt() {
    return new AttributedString(
        startingTest, AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE));
  }
}
