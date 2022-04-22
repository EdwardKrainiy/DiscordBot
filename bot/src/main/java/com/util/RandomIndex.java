package com.util;

import java.util.Random;
import java.util.Set;

public class RandomIndex {
  private RandomIndex(){}

  public static <T> T getRandomElem(Set<T> setOfElements){
    if(setOfElements.isEmpty()){
      return null;
    }

    int size = setOfElements.size();
    int item = new Random().nextInt(size);
    int counter = 0;

    for(T obj : setOfElements)
    {
      if (counter == item){
        return obj;
      }
      counter++;
    }
    return null;
  }
}
