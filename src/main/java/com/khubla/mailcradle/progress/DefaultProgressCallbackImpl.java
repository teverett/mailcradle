package com.khubla.mailcradle.progress;

import java.text.NumberFormat;

public class DefaultProgressCallbackImpl implements ProgressCallback {
   private int currentCount = 0;
   private final int totalCount;
   private final NumberFormat defaultFormat = NumberFormat.getPercentInstance();

   public DefaultProgressCallbackImpl(int totalCount) {
      super();
      this.totalCount = totalCount;
      defaultFormat.setMinimumFractionDigits(3);
   }

   public int getCurrentCount() {
      return currentCount;
   }

   public int getTotalCount() {
      return totalCount;
   }

   @Override
   public void progress() {
      if (0 != totalCount) {
         if ((0 != currentCount) && ((currentCount % 40) == 0)) {
            System.out.println(".");
            System.out.print(currentCount + "/" + totalCount + " (" + defaultFormat.format((double) currentCount / totalCount) + ")");
         } else {
            System.out.print(".");
         }
         currentCount = currentCount + 1;
      }
   }

   public void setCurrentCount(int currentCount) {
      this.currentCount = currentCount;
   }
}
