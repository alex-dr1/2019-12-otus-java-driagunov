package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {
  private static final Logger logger = LoggerFactory.getLogger(MyCache.class);
  private List<HwListener<K, V>> listenerList = new ArrayList<>();
  private Map<String, V> cacheWeakMap = new WeakHashMap<>();


  public MyCache() {
    logger.info("Create MyCache");
  }

  @Override
  public void put(K key, V value) {
    cacheWeakMap.put(keyToStringHash(key),value);

    for (HwListener<K, V> listener: listenerList){
      listener.notify(key, value, "add to cache");
    }
  }

  @Override
  public void remove(K key) {
    V value = this.get(key);
    for (HwListener<K, V> listener: listenerList){
      listener.notify(key, value, "removed from cache");
    }

    cacheWeakMap.remove(keyToStringHash(key));
  }

  @Override
  public V get(K key) {
    return cacheWeakMap.get(keyToStringHash(key));
  }

  @Override
  public void addListener(HwListener<K, V> listener) {
    this.listenerList.add(listener);
  }

  @Override
  public void removeListener(HwListener<K, V> listener) {
    this.listenerList.remove(listener);
  }

  private String keyToStringHash(K key){
    return String.valueOf(key.hashCode());
  }
}
