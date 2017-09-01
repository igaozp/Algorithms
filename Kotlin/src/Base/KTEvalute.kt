package Base

import edu.princeton.cs.algs4.StdIn
import edu.princeton.cs.algs4.StdOut
import java.util.Stack

/**
 * 使用 Dijkstra 双栈算法求算术表达式
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-08-30
 */
class KTEvalute {
    fun main(args: ArrayList<String>) {
        // 存放算术表达式的运算符
        val ops: Stack<String> = Stack()
        // 存放算术表达式的数值
        val vals: Stack<Double> = Stack()

        while (!StdIn.isEmpty()) {
            // 获取字符
            val s: String = StdIn.readString()
            // 匹配字符
            when (s) {
                // 忽略表达式的 "("，匹配到算数运算符时将运算符压入栈
                "(" -> {}
                "+" -> ops.push(s)
                "-" -> ops.push(s)
                "*" -> ops.push(s)
                "/" -> ops.push(s)
                "sqrt" -> ops.push(s)
                // 匹配到 ")" 时，进行相关运算
                ")" -> {
                    // 获取运算符栈的栈顶元素
                    val op = ops.pop()
                    // 获取数值栈的栈顶元素
                    var v = vals.pop()
                    /*
                      根据匹配的运算符进行相关运算
                      当匹配到双目运算符时，需要再从数值栈中取出数据进行双目运算
                     */
                    when (op) {
                        "+" -> v += vals.pop()
                        "-" -> v -= vals.pop()
                        "*" -> v *= vals.pop()
                        "/" -> v /= vals.pop()
                        "sqrt" -> v = Math.sqrt(v)
                    }
                    // 数值入栈
                    vals.push(v)
                }
                else -> {
                    vals.push(s.toDouble())
                }
            }
        }
        StdOut.println(vals.pop())
    }
}
