package AlgorithmNationalDay.LinkedListRecursionStack1;

import java.util.Stack;

/**
 * Created by Administrator on 2016/10/18.
 * 名称：最长括号匹配
 * 备注：
 * 参考：
 */
public class LongestBracketMatches {
    public static void main(String[] args) {
        getLongestParenthese2("(())))");//4
        getLongestParenthese2("(((()))");//6
        getLongestParenthese2("))(()))");//4
    }

    /**
     * @Author 王坤造
     * @Date 2016/10/19 10:51
     * 名称：最长括号匹配
     * 备注：时间复杂度为O(N);空间复杂度为O(N)
     */
    private static void getLongestParenthese(String str) {
        Stack<Integer> s = new Stack<>();
        int answer = 0;//最终解
        int start = -1;//左括号的前一个位置
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                s.push(i);
            } else {//str.charAt(i) == ')'
                if (s.empty()) {//")(())"这种情况的处理
                    start = i;
                } else {
                    s.pop();
                    if (s.empty()) {
                        answer = Math.max(answer, i - start);//字符串最后偏移量-开始角标
                    } else {
                        answer = Math.max(answer, i - s.peek());//字符串最后偏移量-栈中存储的最开始角标
                    }
                }
            }
        }
        System.out.println(answer);
    }

    /**
     * @Author 王坤造
     * @Date 2016/10/19 10:51
     * 名称：最长括号匹配
     * 备注：时间复杂度为O(N);空间复杂度为O(1)
     */
    private static void getLongestParenthese2(String str) {
        int answer = 0;//最终解
        int i;

        //这个是用来处理右括号比左括号多的情况
        int deep = 0;//遇到了多少左括号
        int start = -1;//左括号的前一个位置,,最深的左括号(deep=0)位置
        for (i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                deep++;
            } else {
                deep--;
                if (deep == 0) {
                    answer = Math.max(answer, i - start);
                } else if (deep < 0) {//右括号比左括号多,初始化为for循环前
                    deep = 0;
                    start = i;
                }
            }
        }

        //这个是用来处理左括号比右括号多的情况
        deep = 0;//遇到了多少右括号
        start = str.length();//右括号的后一个位置,,最深的右括号(deep=size-1)位置
        for (i = start - 1; i > -1; i--) {
            if (str.charAt(i) == ')') {
                deep++;
            } else {
                deep--;
                if (deep == 0) {
                    answer = Math.max(answer, start - i);
                } else if (deep < 0) {
                    deep = 0;
                    start = i;
                }
            }
        }
        System.out.println(answer);
    }


}
