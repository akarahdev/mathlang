package net.realmofuz.util;

import java.util.HashMap;
import java.util.List;

public class StackMap<K, V> {
    public List<HashMap<K, V>> internal = List.of();

    public void push() {
        internal.add(new HashMap<>());
    }

    public void pop() {
        internal.removeLast();
    }

    public void put(K key, V value) {
        internal.getLast().put(key, value);
    }

    public V get(K key) {
        return internal.getLast().get(key);
    }
}
