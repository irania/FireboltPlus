package com.Tirax.plasma.Enums;

/**
 * Created by a.irani on 11/1/2016.
 */
public enum BodyPart {
   FACE(0), BODY(1), NECK(2), LEGS(3);
   private int value;
   private BodyPart(int value) {
      this.value = value;
   }
   public int getValue() {
      return value;
   }
}
