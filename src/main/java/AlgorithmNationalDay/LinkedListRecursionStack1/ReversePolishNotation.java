package AlgorithmNationalDay.LinkedListRecursionStack1;

import java.util.Stack;

/**
 * Created by Administrator on 2016/10/19.
 * 名称：逆波兰表达式(后缀表达式)
 * 备注：
 * 参考：
 */
public class ReversePolishNotation {
    public static void main(String[] args) {
        RPN(new String[]{"2", "1", "+", "3", "*"});
        RPN(new String[]{"4", "13", "5", "/", "+"});
    }

    private static void RPN(String[] arr) {
        Stack<Integer> s = new Stack<>();
        int a, b,temp;
        for (int i = 0; i < arr.length; i++) {
            String c = arr[i];
            if (isOperator(c)) {
                a = s.pop();
                b = s.pop();
                temp = calc(a, b, c);
                s.push(temp);
            } else {
                s.push(Integer.parseInt(c + ""));
            }
        }
        System.out.println(s.pop());
    }

    private static int calc(int a, int b, String c) {
        switch (c) {
            case "+":
                return b + a;
            //要用中序遍历,那就左中右.此时a是右,b是左,所以这里要用b-a,
            case "-":
                return b - a;
            case "*":
                return b * a;
            case "/":
                return b / a;
        }
        return 0;
    }

    private static boolean isOperator(String c) {
        switch (c) {
            case "+":
                return true;
            case "-":
                return true;
            case "*":
                return true;
            case "/":
                return true;
        }
        return false;
    }


}
