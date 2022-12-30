package com.happycoding.likou;

/**
 * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0 开始）。
 * 如果 needle 不是 haystack 的一部分，则返回  -1 。
 *
 */
public class StrStr {
    public static void main(String[] args) {
        System.out.println(strStr("mississippi", "issipi"));
    }

    public static int strStr(String haystack, String needle) {
        char[] needleArray = needle.toCharArray();
        if (needleArray.length > haystack.length()) {
            return -1;
        }
        int index = -1;
        int cursor = 0;
        for (int i = 0; i < haystack.length() - needleArray.length + 1; i++) {
            char c = haystack.charAt(i);
            if (c == needleArray[cursor]) {
                index = i;
                for (int j = 1; j < needleArray.length; j++) {
                    cursor += 1;
                    if (haystack.charAt(i + j) != needleArray[cursor]) {
                        index = -1;
                        cursor = 0;
                        break;
                    }
                }
                if (cursor == needleArray.length - 1) {
                    return index;
                }
            }
        }
        return index;
    }
}
