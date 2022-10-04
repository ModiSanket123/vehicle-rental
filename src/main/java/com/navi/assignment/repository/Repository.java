package com.navi.assignment.repository;

import java.util.HashMap;
import java.util.Map;

public abstract class Repository<K, V> {
    final Map<K, V> data;

    protected Repository() {
        data = new HashMap<>();
    }

    public V get(K key) {
        return data.get(key);
    }

    public boolean containsKey(K key){
        return data.containsKey(key);
    }

    public boolean save(V value) {
        K key = getKey(value);
        if (data.containsKey(key)) {
            return false;
        }
        data.put(key, value);
        return true;
    }

    abstract K getKey(V value);
}
