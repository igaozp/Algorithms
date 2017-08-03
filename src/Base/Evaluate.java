package Base;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Stack;

/**
 * 使用 Dijkstra 双栈算法求算术表达式
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-6-30
 */
public class Evaluate {
    public static void main(String[] args) {
        // 存放算术表达式的运算符
        Stack<String> ops = new Stack<>();
        // 存放算术表达式的数值
        Stack<Double> vals = new Stack<>();

        while (!StdIn.isEmpty()) {
            // 获取字符
            String s = StdIn.readString();
            // 匹配字符
            switch (s) {
                // 忽略表达式的 "("，匹配到算数运算符时将运算符压入栈
                case "(":
                    break;
                case "+":
                    ops.push(s);
                    break;
                case "-":
                    ops.push(s);
                    break;
                case "*":
                    ops.push(s);
                    break;
                case "/":
                    ops.push(s);
                    break;
                case "sqrt":
                    ops.push(s);
                    break;
                // 匹配到 ")" 时，进行相关运算
                case ")":
                    // 获取运算符栈的栈顶元素
                    String op = ops.pop();
                    // 获取数值栈的栈顶元素
                    double v = vals.pop();
                    /*
                      根据匹配的运算符进行相关运算
                      当匹配到双目运算符时，需要再从数值栈中取出数据进行双目运算
                     */
                    switch (op) {
                        case "+":
                            v = vals.pop() + v;
                            break;
                        case "-":
                            v = vals.pop() - v;
                            break;
                        case "*":
                            v = vals.pop() * v;
                            break;
                        case "/":
                            v = vals.pop() / v;
                            break;
                        case "sqrt":
                            v = Math.sqrt(v);
                            break;
                    }
                    // 数值入栈
                    vals.push(v);
                    break;
                default:
                    vals.push(Double.parseDouble(s));
                    break;
            }
        }
        StdOut.println(vals.pop());
    }
}
