package com.rodd.www.services;

import org.junit.Test;

import java.util.Map;

/**
 * 大文件统计单词
 *
 * @author rodd
 * @email 491836059@qq.com
 * @date 2018/7/29 2:22
 */
public class TestLargeFile {


    private static final String filePath = "D:\\dotest.txt";

    private static final String words = "hello,rodd,wangjian";

    @Test
    public void test() {
        Map<String, Integer> execute = new LargeFile().execute(filePath, words);
        if (!execute.isEmpty()) {
            for (String key : execute.keySet()) {
                System.out.println(String.format("统计单词：%s ,出现次数：%d .", key, execute.get(key)));
            }
        }
    }
}
