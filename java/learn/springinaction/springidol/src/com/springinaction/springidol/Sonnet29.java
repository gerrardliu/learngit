package com.springinaction.springidol;

public class Sonnet29 implements Poem {

  private static String[] LINES = {"When, in disgrace with fortune and men's eyes", "I'm nothing"};

  public Sonnet29() {}

  @Override
  public void recite() {
    for (int i = 0; i < LINES.length; i++) {
      System.out.println(LINES[i]);
    }
  }
}
