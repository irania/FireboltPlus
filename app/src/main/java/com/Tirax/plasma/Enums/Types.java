package com.Tirax.plasma.Enums;

/**
 * Created by a.irani on 11/1/2016.
 */
public enum Types {
   HF(0), LF(1), VACUUM(2), FRAC(3),LPG(4), CAVITATION(5);
   private int value;
   private Types(int value) {
      this.value = value;
   }
   public int getValue() {
      return value;
   }
}
