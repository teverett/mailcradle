package com.khubla.mailcradle.domain;

import java.util.ArrayList;
import java.util.List;

public class StringList {
   private String name;
   private List<String> list = new ArrayList<String>();

   public void addString(String string) {
      list.add(string.toLowerCase());
   }

   public boolean contains(String name) {
      for (final String item : list) {
         if (name.contains(item)) {
            return true;
         }
      }
      return false;
   }

   public List<String> getList() {
      return list;
   }

   public String getName() {
      return name;
   }

   public void setList(List<String> list) {
      this.list = list;
   }

   public void setName(String name) {
      this.name = name;
   }
}
