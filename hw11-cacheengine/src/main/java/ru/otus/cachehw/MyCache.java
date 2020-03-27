package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
  private static final Logger logger = LoggerFactory.getLogger(MyCache.class);
  private HwListener<K, V> listener;
  private Map<String, V> cacheWeakMap = new WeakHashMap<>();


  public MyCache() {
    logger.info("Create MyCache");
  }

  @Override
  public void put(K key, V value) {
    if(listener == null){
      logger.error("listener should not be empty");
      return;
    }

    if(get(key) != null){
      remove(key);
    }
    cacheWeakMap.put(keyToStringHash(key),value);

    listener.notify(key, value, "add to cache");
  }

  @Override
  public void remove(K key) {
    if(listener == null){
      logger.error("listener should not be empty");
      return;
    }

    cacheWeakMap.remove(keyToStringHash(key));
    listener.notify(key, this.get(key), "removed from cache");
  }

  @Override
  public V get(K key) {
    return cacheWeakMap.get(keyToStringHash(key));
  }

  @Override
  public void addListener(HwListener<K, V> listener) {
    this.listener = listener;
  }

  @Override
  public void removeListener(HwListener<K, V> listener) {
    this.listener = null;
  }

  public void printCache(){

    System.out.println("+-----------------------------+");
    System.out.print("Map(size " + cacheWeakMap.size() + "): ");
    cacheWeakMap.forEach((k, v) -> System.out.println(k+"="+v));
    System.out.println();
  }

  private String keyToStringHash(K key){
    return String.valueOf(key.hashCode());
  }
}
