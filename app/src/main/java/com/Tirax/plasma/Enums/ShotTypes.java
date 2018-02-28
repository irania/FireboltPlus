package com.Tirax.plasma.Enums;

/**
 * Created by a.irani on 11/1/2016.
 */
public enum ShotTypes {
   SPRAY(0), DOT(1), PULSE(2);
   private int value;
   private ShotTypes(int value) {
      this.value = value;
   }
   public int getValue() {
      return value;
   }
}
