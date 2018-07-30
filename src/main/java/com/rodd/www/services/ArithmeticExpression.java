package com.rodd.www.services;


import java.math.BigDecimal;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * 1.算数表达式
 *
 * @author rodd
 * @email 491836059@qq.com
 * @date 2018/7/28 22:06
 */
public class ArithmeticExpression {
    /**
     * 算术运算符: +
     */
    private static final char ADD = '+';
    /**
     * 算术运算符: -
     */
    private static final char SUBTRACT = '-';
    /**
     * 算术运算符: *
     */
    private static final char MULTIPLY = '*';
    /**
     * 算术运算符: /
     */
    private static final char DIVIDE = '/';
    /**
     * 左括号
     */
    private static final char LEFT_BRACKET = '(';
    /**
     * 右括号
     */
    private static final char RIGHT_BRACKET = ')';

    /**
     * 计算
     *
     * @param content
     * @return String
     * @author rodd
     * @email 491836059@qq.com
     * @date 2018/7/28 22:07
     */
    public String calculate(String content) {
        String re = infixToSuffix(content);
        return suffixToArithmetic(re).setScale(0, BigDecimal.ROUND_DOWN).toString();

    }

    /**
     * 将中缀表达式转化成后缀表达式
     *
     * @param exp
     * @return String
     * @author rodd
     * @email 491836059@qq.com
     * @date 2018/7/28 22:09
     */
    public static String infixToSuffix(String exp) {
        Stack<Character> stack = new Stack<Character>();
        String suffix = "";
        int len = exp.length();
        for (int i = 0; i < len; i++) {
            char ch = exp.charAt(i);
            //临时字符变量
            char temp;
            //忽略空格
            switch (ch) {
                case ' ':
                    break;
                //如果是左括号直接加入栈中
                case LEFT_BRACKET:
                    stack.push(ch);
                    break;

                //如果是+ - 那么则弹出所有字符至序列中，直到遇到左括号为止，然后再把该运算符压到堆栈中
                case ADD:
                case SUBTRACT:
                    while (!stack.isEmpty()) {
                        temp = stack.pop();
                        if (temp == LEFT_BRACKET) {
                            stack.push(temp);
                            break;
                        }
                        suffix += temp;
                    }
                    stack.push(ch);
                    break;

                //如果是* /，则弹出所有字符至序列中，直到遇到左括号、+、-号为止，然后把该运算符压到堆栈中
                case MULTIPLY:
                case DIVIDE:
                    while (!stack.isEmpty()) {
                        temp = stack.pop();
                        if (temp == '(' || temp == '+' || temp == '-') {
                            stack.push(temp);
                            break;
                        }
                        suffix += temp;
                    }
                    stack.push(ch);
                    break;

                //如果是* /，则弹出所有字符至序列中，直到遇到左括号、+、-号为止，然后把该运算符压到堆栈中
                case RIGHT_BRACKET:
                    while (!stack.isEmpty()) {
                        temp = stack.pop();
                        if (temp == LEFT_BRACKET) {
                            break;
                        }
                        suffix += temp;
                    }
                    break;
                //默认，如果是数字，直接加到输出队列中
                default:
                    suffix += ch;
                    break;
            }
        }
        //如果堆栈不为空，依次把所有的字符加入序列中
        while (!stack.isEmpty()) {
            suffix += stack.pop();
        }
        return suffix;
    }

    /**
     * 输入后缀表达式得到计算结果
     *
     * @param exp
     * @return java.math.BigDecimal
     * @author rodd
     * @email 491836059@qq.com
     * @date 2018/7/28 23:01
     */
    public static BigDecimal suffixToArithmetic(String exp) {
        Stack<BigDecimal> stack = new Stack<BigDecimal>();
        //使用正则表达式匹配数字
        Pattern pattern = Pattern.compile("\\d+||(\\d+\\.\\d+)");
        //将字符串分割成字符串数组
        String strs[] = exp.split("");
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].equals(""))
                continue;
            //如果是数字，则入栈
            if ((pattern.matcher(strs[i])).matches()) {
                stack.push(BigDecimal.valueOf(Double.parseDouble(strs[i])));
            } else {
                //如果是运算符，则弹出运算符，计算结果
                BigDecimal b = stack.pop();
                BigDecimal a = stack.pop();
                //将运算结果重新压入栈中
                stack.push(calculate(a, b, strs[i].toCharArray()[0]));
            }
        }
        return stack.pop();
    }

    /**
     * 根据符号计算最终结果
     *
     * @param a
     * @param b
     * @param op
     * @return java.math.BigDecimal
     * @author rodd
     * @email 491836059@qq.com
     * @date 2018/7/28 23:11
     */
    public static BigDecimal calculate(BigDecimal a, BigDecimal b, char op) {
        if (op == ADD) {
            return a.add(b);
        }
        if (op == SUBTRACT) {
            return a.subtract(b);
        }
        if (op == MULTIPLY) {
            return a.multiply(b);
        }
        if (op == DIVIDE) {
            if (b.equals(BigDecimal.ZERO)) {
                throw new IllegalArgumentException("算术表达式不正确,除数不能为0.");
            }
            return a.divide(b);
        }
        return BigDecimal.ZERO;
    }
}
