package com.boreworld.tsac.structures;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public class LinkedHashList<V> implements List<V> {

    private LinkedHashMap<String, V> map = new LinkedHashMap<>();
    private boolean isEditing = false;

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsValue(0);
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @NonNull
    @Override
    public Iterator<V> iterator() {
        return map.values().iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return map.values().toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] ts) {
        return map.values().toArray(ts);
    }

    @Override
    public boolean add(V value) {
        boolean result = false;
        if (!isEditing()) {
            isEditing = true;
            result = add(getUniqueKey(getClassNameKey(value), map), value);
            isEditing = false;
        }
        return result;
    }

    public boolean add(String key, V value) {
        boolean result = false;
        try {
            if (!isEditing()) {
                isEditing = true;
                map.put(key, value);
                isEditing = false;
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public boolean remove(Object value) {
        boolean result = false;
        if (!isEditing()) {
            isEditing = true;
            Set<Map.Entry<String, V>> set = map.entrySet();
            for (Map.Entry<String, V> e : set) {
                if (e.getValue()==value){
                    map.remove(e.getKey());
                    result = true;
                    break;
                }
            }
            isEditing = false;
        }
        return result;
    }

    public boolean remove(String key) {
        boolean result = false;
        if (!isEditing()) {
            isEditing = true;
            try {
                map.remove(key);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            isEditing = false;
        }
        return result;
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        return map.values().containsAll(collection);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends V> collection) {
        boolean result = false;
        if (!isEditing()) {
            isEditing = true;
            try {
                if (collection.size()>0) {
                    for (V var : collection) {
                        add(var);
                    }
                    result = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            isEditing = false;
        }
        return result;
    }

    @Override
    public boolean addAll(int i, @NonNull Collection<? extends V> collection) {
        boolean result = false;
        if (!isEditing()) {
            isEditing = true;
            try {
                if (i<map.size()) {
                    Map.Entry<String, V>[] entries = (Map.Entry<String, V>[]) map.entrySet().toArray().clone();

                    LinkedHashMap<String, V> clone = new LinkedHashMap<>();

                    for (int j = 0; j < i; j++) {
                        Map.Entry<String, V> entry = entries[j];
                        clone.put(entry.getKey(), map.remove(entry.getKey()));
                    }
                    V[] values = (V[]) collection.toArray();
                    for (int j = 0; j < values.length; j++) {
                        V var = values[i];
                        clone.put(getUniqueKey(getClassNameKey(var), clone), var);
                    }
                    for (int j = i; j < entries.length; j++) {
                        Map.Entry<String, V> entry = entries[j];
                        clone.put(entry.getKey(), map.remove(entry.getKey()));
                    }
                    map = clone;
                    result = true;
                }
                else {
                    throw new IndexOutOfBoundsException("error index="+i+", where size="+map.size());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            isEditing = false;
        }
        return result;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        boolean result = false;
        try {
            if (!isEditing()) {
                isEditing = true;
                Object[] col = collection.toArray();
                Map.Entry<String, V>[] entries = (Map.Entry<String, V>[]) map.entrySet().toArray().clone();
                for (int i = 0; i < col.length; i++) {
                    Object o = col[i];
                    for (int j = 0; j < entries.length; j++) {
                        Map.Entry entry = entries[j];
                        if (entry.getValue()==o) {
                            map.remove(entry.getKey());
                            result = true;
                        }
                    }
                }
                isEditing = false;
            }
            else {
                throw new InterruptedException("error map was interrupted while editing. Use isEditing() to for safe execution.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        return map.values().retainAll(collection);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public V get(int i) {
        V result = null;
        try {
            Iterator<V> val = map.values().iterator();
            int c = 0;
            while (val.hasNext()) {
                if (i==c) {
                    result = val.next();
                    break;
                }
                c++;
            }
        } catch (Exception e) {}
        return result;
    }

    public V get(String key) {
        return map.get(key);
    }

    @Override
    public V set(int i, V v) {
        V result = null;
        if (!isEditing) {
            isEditing = true;
            try {
                if (i<map.size()) {
                    Map.Entry<String, V> holder = null;
                    Map.Entry<String, V>[] entries = (Map.Entry<String, V>[]) map.entrySet().toArray().clone();
                    for (Map.Entry e : entries) {
                        if (v == e.getValue()) {
                            holder = e;
                            break;
                        }
                    }
                    if (holder!=null) {
                        LinkedHashMap<String, V> clone = new LinkedHashMap<>();
                        for (int j = 0; j < entries.length; j++) {
                            Map.Entry<String, V> entry = entries[j];
                            if (j==i) {
                                clone.put(holder.getKey(), map.remove(holder.getKey()));
                                result = holder.getValue();
                            }
                            else if (!entry.getKey().equals(holder.getKey())) {
                                clone.put(entry.getKey(), map.remove(entry.getKey()));
                            }
                        }
                        map = clone;
                    }
                }
                else {
                    throw new IndexOutOfBoundsException("error index="+i+", where size="+map.size());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            isEditing = false;
        }

        return result;
    }

    @Override
    public void add(int i, V v) {
        try {
            if (!isEditing()) {
                isEditing = true;
                if (i<map.size()) {
                    Map.Entry<String, V>[] entries = (Map.Entry<String, V>[]) map.entrySet().toArray().clone();

                    LinkedHashMap<String, V> clone = new LinkedHashMap<>();
                    for (int j = 0; j < i; j++) {
                        Map.Entry<String, V> entry = entries[j];
                        clone.put(entry.getKey(), map.remove(entry.getKey()));
                    }
                    clone.put(getUniqueKey(getClassNameKey(v), clone), v);
                    for (int j = i; j < entries.length; j++) {
                        Map.Entry<String, V> entry = entries[j];
                        clone.put(entry.getKey(), map.remove(entry.getKey()));
                    }
                    map = clone;
                }
                else {
                    throw new IndexOutOfBoundsException("error index="+i+", where size="+map.size());
                }
                isEditing = false;
            }
            else {
                throw new InterruptedException("error map was interrupted while editing. Use isEditing() to for safe execution.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public V remove(int i) {
        V result = null;
        try {
            if (!isEditing()) {
                isEditing = true;
                String[] keys = (String[]) map.keySet().toArray();
                result = map.remove(keys[i]);
                isEditing = false;
            }
            else {
                throw new InterruptedException("error map was interrupted while editing. Use isEditing() to for safe execution.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int indexOf(Object o) {
        ArrayList<V> arr = new ArrayList<V>(Arrays.asList((V[]) map.values().toArray()));
        return arr.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        ArrayList<V> arr = new ArrayList<V>(Arrays.asList((V[]) map.values().toArray()));
        return arr.lastIndexOf(o);
    }

    @NonNull
    @Override
    public ListIterator<V> listIterator() {
        ArrayList<V> arr = new ArrayList<V>(Arrays.asList((V[]) map.values().toArray()));
        return arr.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<V> listIterator(int i) {
        ArrayList<V> arr = new ArrayList<V>(Arrays.asList((V[]) map.values().toArray()));
        return arr.listIterator(i);
    }

    @NonNull
    @Override
    public List<V> subList(int fromIndex, int toIndex) {
        ArrayList<V> arr = new ArrayList<V>(Arrays.asList((V[]) map.values().toArray()));
        return arr.subList(fromIndex, toIndex);
    }

    public boolean isEditing() {
        return isEditing;
    }

    public static String getClassNameKey(Object o) {
        String key = null;
        try {
            key = o.getClass().getSimpleName()+"."+o.hashCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    private String getUniqueKey(String classNameKey, Map map) {
        return getUniqueKey(classNameKey, 1, map);
    }

    private String getUniqueKey(String classNameKey, int salt, Map map) {
        if (map==null) return classNameKey;
        String key = (map.containsKey(classNameKey))? classNameKey+"."+salt : classNameKey;
        try {
            if (map.containsKey(key)) {
                key = getUniqueKey(classNameKey, salt+1, map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }
}
