package com.happycoding.likou;

import com.sun.istack.internal.NotNull;

/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * <p>
 * 如果不存在公共前缀，返回空字符串 ""。
 */
public class LongestCommonPrefix {
    public static void main(String[] args) {
//        String[] strs = new String[]{"flower", "flow", "flight"};
        String[] strs = new String[]{"cir", "car"};
        System.out.println(longestCommonPrefix(strs));
    }

    public static String longestCommonPrefix(String[] strs) {
        int len = 0;
        char local = '\u0000';

        for (int i = 0; i < strs.length; i++) {
            if (strs[i].length() == 0) {
                return "";
            }
            char c = strs[i].charAt(0);
            if (local == '\u0000') {
                local = c;
            } else if (local != c) {
                return "";
            }
            len = len ==0 || len > strs[i].length() ? strs[i].length() : len;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(local);

        for (int j = 1; j < len; j++) {
            for (int i = 0; i < strs.length; i++) {
                char c = strs[i].charAt(j);
                if (i == 0){
                    local = c;
                }
                if (i > 0 && local != c) {
                    return sb.toString();
                }
                if (i == strs.length -1) {
                    sb.append(local);
                }
            }
        }

        return sb.toString();
    }

    /**
     * 别人写的，牛逼
     * @param strs
     * @return 前缀字符串
     */
    public static String longestCommonPrefixFromWeb(String[] strs){
        String ans = strs[0];
        for (int i = 1; i < strs.length; ++i) {
            while (strs[i].indexOf(ans) != 0)
                // 循环截取到当前记录包含的前缀
                ans = ans.substring(0, ans.length() - 1);
        }
        return ans;
    }
}
