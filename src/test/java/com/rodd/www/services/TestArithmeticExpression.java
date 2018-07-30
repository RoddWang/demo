package com.rodd.www.services;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.算数表达式
 *
 * @author rodd
 * @email 491836059@qq.com
 * @date 2018/7/28 23:21
 */
public class TestArithmeticExpression {

    private static final List<String> contentList = new ArrayList<>();

    @Before
    public void testBefore() {
        contentList.add("3*0+3+8+9*1");
        contentList.add(" 3+(3-1) * 2");
    }

    @Test
    public void test() {
        for (String content : contentList) {
            System.out.println("----------------------------------------------------");
            System.out.println(new ArithmeticExpression().calculate(content));
        }
    }
}
