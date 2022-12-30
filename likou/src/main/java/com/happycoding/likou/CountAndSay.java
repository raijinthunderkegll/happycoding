package com.happycoding.likou;

/**
 * 给定一个正整数 n ，输出外观数列的第 n 项。
 * <p>
 * 「外观数列」是一个整数序列，从数字 1 开始，序列中的每一项都是对前一项的描述。
 * <p>
 * 你可以将其视作是由递归公式定义的数字字符串序列：
 * <p>
 * countAndSay(1) = "1"
 * countAndSay(n) 是对 countAndSay(n-1) 的描述，然后转换成另一个数字字符串。
 * <p>
 * example:
 * 1.     1
 * 2.     11
 * 3.     21
 * 4.     1211
 * 5.     111221
 * 第一项是数字 1
 * 描述前一项，这个数是 1 即 “ 一 个 1 ”，记作 "11"
 * 描述前一项，这个数是 11 即 “ 二 个 1 ” ，记作 "21"
 * 描述前一项，这个数是 21 即 “ 一 个 2 + 一 个 1 ” ，记作 "1211"
 * 描述前一项，这个数是 1211 即 “ 一 个 1 + 一 个 2 + 二 个 1 ” ，记作 "111221"
 */
public class CountAndSay {
    public static void main(String[] args) {
        int n = 5;
        System.out.println(countAndSay(n));
    }

    public static String countAndSay(int n) {
        if (n == 1) {
            return "1";
        }
        String countAndSay = countAndSay(n - 1);

        StringBuilder stringBuffer = new StringBuilder();
        int count = 1;
        char c = '\u0000';
        for (int i = 0; i < countAndSay.length(); i++) {
            c = countAndSay.charAt(i);
            if (i > 0 && c == countAndSay.charAt(i - 1)) {
                count++;
            } else {
                if (i > 0 && c != countAndSay.charAt(i - 1)) {
                    stringBuffer.append(count).append(countAndSay.charAt(i - 1));
                }
                count = 1;
            }
        }
        stringBuffer.append(count).append(c);

        return stringBuffer.toString();
    }

}
