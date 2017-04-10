/**
 * 
 */
package com.wasu.ptyw.galaxycrm.core.cache;

import java.util.concurrent.TimeUnit;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * 本地缓存类
 * 
 * @author wenguang
 * @date 2015年6月4日
 */
@Slf4j
public abstract class DbCache<T, V> {
	/** 缓存对象 */
	private LoadingCache<T, V> cache;

	/** 缓存大小 */
	@Setter
	private static Long cacheSize = 10000L;

	/** 缓存超时时间 ,单位秒 */
	@Setter
	private static Long expireTime = 600L;

	/**
	 * 加载数据
	 */
	protected abstract V fetch(T key);

	/**
	 * 获取对象
	 */
	public V get(T key) {
		if (key == null)
			return null;
		try {
			initCache();
			return cache.get(key);
		} catch (Exception e) {
			log.error("getCache error, key=" + key, e);
		}
		return null;
	}

	/**
	 * 增加缓存
	 */
	public void put(T key, V value) {
		initCache();
		cache.put(key, value);
	}

	public void initCache() {
		if (cache == null) {
			// .expireAfterAccess(expireTime, TimeUnit.SECONDS)
			cache = CacheBuilder.newBuilder().maximumSize(cacheSize).expireAfterWrite(expireTime, TimeUnit.SECONDS)
					.build(new CacheLoader<T, V>() {
						@Override
						public V load(T key) throws Exception {
							return fetch(key);
						}
					});
		}
	}

	/**
	 * 清除所有缓存
	 */
	public void clear(T key) {
		if (cache == null) {
			return;
		}
		cache.invalidate(key);
	}

	/**
	 * 清除所有缓存
	 */
	public void clearAll() {
		if (cache == null) {
			return;
		}
		cache.invalidateAll();
	}
}
