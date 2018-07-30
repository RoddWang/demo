package com.rodd.www.services;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 3.大文件统计单词
 *
 * @author rodd
 * @email 491836059@qq.com
 * @date 2018/7/29 1:33
 */
public class LargeFile {

    /**
     * 统计单词分割符
     */
    private static final String WORD_SPLIT_REGEX = ",";

    /**
     * 执行统计
     *
     * @param filePath 文件路径
     * @param words    统计单词以英文逗号分割
     * @return java.util.Map
     * @author rodd
     * @email 491836059@qq.com
     * @date 2018/7/29 1:34
     */
    public Map<String, Integer> execute(String filePath, String words) {
        Map<String, Integer> count = new HashMap<>();
        if (StringUtils.isNoneBlank(words)) {
            String[] split = words.split(WORD_SPLIT_REGEX);
            for (int i = 0; i < split.length; i++) {
                count.put(split[i], 0);
            }
        }
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(filePath)))) {
            BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 10 * 1024 * 1024);// 10M缓存
            while (in.ready()) {
                String line = in.readLine();
                for (Map.Entry<String, Integer> entry : count.entrySet()) {
                    String key = entry.getKey();
                    int value = entry.getValue();
                    int index = line.indexOf(key);
                    while (index != -1) {
                        value++;
                        index = line.indexOf(key, index + 1);
                    }
                    if (value != count.get(key)) {
                        count.put(key, value);
                    }
                }
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return count;
    }
}
