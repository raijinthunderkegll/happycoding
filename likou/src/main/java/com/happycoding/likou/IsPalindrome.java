package com.happycoding.likou;

import java.util.ArrayList;
import java.util.List;

/**
 * 如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。
 *
 * 字母和数字都属于字母数字字符。
 *
 * 给你一个字符串 s，如果它是 回文串 ，返回 true ；否则，返回 false 。
 *
 */
public class IsPalindrome {
    public static void main(String[] args) {
        //String s = "A man, a plan, a canal: Panama";
        String s = "0P";
        System.out.println(isPalindrome(s));

    }

    public static boolean isPalindrome(String s) {
        int length = s.length();

        List<Character> list = new ArrayList<>();

        for(int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                if (Character.isUpperCase(c)) {
                    c = Character.toLowerCase(c);
                }
                list.add(c);
            }
        }

        for(int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i));
        }
        System.out.println();

        for (int i = 0; i < list.size()/2; i++) {
            if (list.get(i) != list.get(list.size() - 1 - i)) {
                return false;
            }
        }

        return true;
    }

}
