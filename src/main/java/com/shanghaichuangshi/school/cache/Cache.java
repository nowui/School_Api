package com.shanghaichuangshi.school.cache;

import org.ehcache.UserManagedCache;

import java.util.*;

import static org.ehcache.config.builders.UserManagedCacheBuilder.newUserManagedCacheBuilder;

public abstract class Cache {

	protected static final UserManagedCache<String, List> ehcacheList = newUserManagedCacheBuilder(String.class, List.class).identifier("data-cache-list").build(true);
	protected static final UserManagedCache<String, Object> ehcacheObject = newUserManagedCacheBuilder(String.class, Object.class).identifier("data-cache-object").build(true);
	protected static final Map<String, Set<String>> ehcacheMap = new HashMap<String, Set<String>>();

	protected Set<String> getMapByKey(String key) {
		Set<String> set;

		if (ehcacheMap.containsKey(key)) {
			set = ehcacheMap.get(key);
		} else {
			set = new HashSet<String>();
		}

		return set;
	}

	protected void setMapByKeyAndId(String key, String id) {
		Set<String> set;

		if (ehcacheMap.containsKey(key)) {
			set = ehcacheMap.get(key);
		} else {
			set = new HashSet<String>();
		}

		set.add(key + "_" + id);

        ehcacheMap.put(key, set);
	}

	protected void removeMapByKeyAndId(String key, String id) {
		Set<String> set;

		if (ehcacheMap.containsKey(key)) {
			set = ehcacheMap.get(key);

			set.remove(key + "_" + id);
		} else {
			set = new HashSet<String>();
		}

        ehcacheMap.put(key, set);
	}

	protected void removeMapByKey(String key) {
		Set<String> set = new HashSet<String>();

        ehcacheMap.put(key, set);
	}

}
