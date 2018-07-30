package com.rodd.www.services;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 懒汉模式的线程安全单实例类测试
 *
 * @author rodd
 * @email 491836059@qq.com
 * @date 2018/7/29 00:23
 */
public class TestSingleton {

    /**
     * 线程池大小
     */
    private static final int THREAD_POOL_SIZE = 15;

    @Test
    public void test() {
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("当前线程名：" + Thread.currentThread().getName() + ":" + Singleton.getInstance());
                }
            });
            //看结果时放开此段注释
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
    }
}
