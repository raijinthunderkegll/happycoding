package com.happycoding.likou;

/**
 * 实现一个myAtoi(string s)函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）。
 * <p>
 * 函数myAtoi(string s) 的算法如下：
 * <p>
 * 读入字符串并丢弃无用的前导空格
 * 检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。 确定最终结果是负数还是正数。 如果两者都不存在，则假定结果为正。
 * 读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
 * 将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。如果没有读入数字，则整数为 0 。必要时更改符号（从步骤 2 开始）。
 * 如果整数数超过 32 位有符号整数范围 [−2^31, 2^31− 1] ，需要截断这个整数，使其保持在这个范围内。具体来说，小于 −2^31 的整数应该被固定为 −2^31 ，大于 2^31− 1 的整数应该被固定为 2^31− 1 。
 * 返回整数作为最终结果。
 */
public class MyAtoi {
    public static void main(String[] args) {
        String s = "+-12";
        System.out.println(myAtoi(s));

    }

    public static int myAtoi(String s) {
        int length = s.length();
        boolean readDigit = false;
        char flag = '\u0000';
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (c == '-' || c == '+') {
                if (flag == '-' || flag == '+') {
                    break;
                }
                flag = c;
            }

            if (c == ' ') {
                continue;
            }
            if (readDigit && !Character.isDigit(c)) {
                // 读取数字完毕
                break;
            }

            if (Character.isDigit(c)) {
                sb.append(c);
                if (readDigit == false) {
                    readDigit = true;
                }
            } else if (c != '-' && c != '+') {
                break;
            }
        }

        if (sb.length() > 0) {
            // 判断是否超过Integer范围
            if (flag == '-') {
                if (Long.valueOf("-" + sb.toString()) - Integer.MIN_VALUE < 0) {
                    return Integer.MIN_VALUE;
                }
                return Integer.valueOf("-" + sb.toString());
            } else {
                if (Long.valueOf(sb.toString()) - Integer.MAX_VALUE > 0) {
                    return Integer.MAX_VALUE;
                }
                return Integer.valueOf(sb.toString());
            }
        }

        return 0;
    }
}
