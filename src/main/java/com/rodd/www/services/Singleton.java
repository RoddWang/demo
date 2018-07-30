package com.rodd.www.services;

/**
 *  2.懒汉模式的线程安全单实例类
 *
 * @author rodd
 * @email 491836059@qq.com
 * @date 2018/7/29 00:17
 */
public class Singleton {
    /** 实列 */
    private static volatile Singleton instance;

    /**
     * 私有化构造方法
     */
    private Singleton() {
    }

    /**
     * 提供获取本实例的方法
     *
     * @return com.rodd.www.services.Singleton
     * @author rodd
     * @email 491836059@qq.com
     * @date 2018/7/29 00:19
     */
    public static Singleton getInstance(){
        if(instance == null){
            synchronized (Singleton.class){
                if(instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
