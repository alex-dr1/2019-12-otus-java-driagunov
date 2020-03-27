package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HWCacheDemo {
  private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

  public static void main(String[] args) {

   new HWCacheDemo().demo();
  }


  private void demo() {
    MyCache<Integer, BigObject> cache = new MyCache<>();

    // пример, когда Idea предлагает упростить код, при этом может появиться "спец"-эффект
    HwListener<Integer, BigObject> listener = new HwListener<Integer, BigObject>() {
      @Override
      public void notify(Integer key, BigObject value, String action) {
        logger.info("key:{}, value:{}, action: {}", key, value, action);
      }
    };

    cache.addListener(listener);
    for (int i = 0; i < 500; i++){
      cache.put(i, new BigObject());
    }

    cache.printCache();
    cache.removeListener(listener);

  }

  static class BigObject {
    final byte[] array = new byte[1024 * 1024];

    public byte[] getArray() {
      return array;
    }
  }
}
