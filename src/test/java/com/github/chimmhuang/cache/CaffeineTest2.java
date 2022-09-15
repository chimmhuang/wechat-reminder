package com.github.chimmhuang.cache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CaffeineTest2 {

    @Test
    public void test1() throws ExecutionException, InterruptedException, TimeoutException {
        AsyncLoadingCache<String, String> cache = Caffeine.newBuilder()
                // 数量上限
                .maximumSize(2)
                // 失效时间
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .refreshAfterWrite(1, TimeUnit.MINUTES)
                //key的弱引用
//                .weakKeys()
                //value的软引用
                // .softValues()
                //value的弱引用
//                .weakValues()

                // 异步加载机制
                .buildAsync(new CacheLoader<String, String>() {
                    @Override
                    public String load(@NonNull String key) throws Exception {
                        return getValue(key);
                    }

                    private String getValue(String key) {
                        return key+1;
                    }
                });

        System.out.println(cache.get("username").get());//如果需要的话，等待完成，然后如果有结果返回其结果
        System.out.println(cache.get("password").get(10, TimeUnit.MINUTES));//如果需要的话，最多等待这个未来的给定时间完成，然后如果有结果返回其结果
        System.out.println(cache.get("username").get(10, TimeUnit.MINUTES));
        System.out.println(cache.get("blog").get());
        //System.out.println(cache.estimatedSize());//取缓存中的记录数的
    }

    @Test
    public void test2() throws InterruptedException {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .weakValues()
                .build();
        Object value = new Object();
        cache.put("key1", value);

        value = new Object();//原对象不再有强引用
        System.gc();
        System.out.println(cache.getIfPresent("key1"));
        //输出null
    }

    @Test
    public void test3() throws InterruptedException {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .weakValues()
                .build();
        Object key1 = new Object();
        Object value= new Object();
        cache.put(key1,value);

        System.out.println(cache.getIfPresent(key1));//java.lang.Object@67117f44


        key1 = new Object();//原对象不再有强引用
        System.gc();
        System.out.println(cache.getIfPresent(key1));//null
    }
    @Test
    public void test4() throws InterruptedException {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .build();
        Object key1 = new Object();
        Object value= new Object();
        cache.put(key1,value);

        System.out.println(cache.getIfPresent(key1));//java.lang.Object@2a098129
        value=new Object();
        System.gc();
        System.out.println(cache.getIfPresent(key1));//java.lang.Object@2a098129
        key1 = new Object();
        System.gc();
        System.out.println(cache.getIfPresent(key1));//null
    }

    @Test
    public void test5() throws InterruptedException {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .softValues()
                .build();
        Object key1 = new Object();
        Object value= new Object();
        cache.put(key1,value);

        System.out.println(cache.getIfPresent(key1));//java.lang.Object@67117f44
        value=new Object();
        System.gc();
        System.out.println(cache.getIfPresent(key1));//java.lang.Object@67117f44

    }

}