package me.metatom.scala.base

/**
  * Stack 链表的实现
  * Stack 是一种基于 LIFO 策略的集合类型
  *
  * @author igaozp
  * @since 2019-01-22
  * @param manifest Manifest
  * @tparam T 泛型参数
  */
class Stack[T >: Null <: AnyRef](implicit manifest: Manifest[T]) {
  /**
    * 栈的顶部节点
    */
  private var first: Node = _
  /**
    * 栈的节点元素数量
    */
  private var size = 0

  /**
    * 内部的链表节点类
    */
  class Node {
    var item: T = manifest.runtimeClass.getConstructor().newInstance().asInstanceOf[T]
    var next: Node = _
  }

  /**
    * 检查栈是否为空
    *
    * @return true  栈为空
    *         false 栈不为空
    */
  def isEmpty: Boolean = first == null

  /**
    * 检查栈的节点元素数量
    *
    * @return 栈元素的数量
    */
  def getSize: Int = size

  /**
    * 元素压入栈
    *
    * @param item 入栈元素
    */
  def push(item: T): Unit = {
    // 使用头插法将元素入栈
    val oldFirst = first
    first = new Node
    first.item = item
    first.next = oldFirst
    // 更新节点数量
    size += 1
  }

  /**
    * 元素出栈
    *
    * @return 出栈的元素
    */
  def pop(): T = {
    if (isEmpty) {
      throw new NoSuchElementException("Stack underflow")
    }
    // 获取头节点的元素，并更新头节点
    val item = first.item
    first = first.next
    // 更新节点的数量
    size -= 1
    item
  }

  /**
    * 获取栈的首节点的元素
    *
    * @return 首节点元素
    */
  def peek(): T = {
    if (isEmpty) {
      throw new NoSuchElementException("Stack underflow")
    }
    first.item
  }
}

object Stack {
  /**
    * 单元测试
    *
    * @param args 命令行参数
    */
  def main(args: Array[String]): Unit = {
    val stack = new Stack[String]()
    stack.push("1")
    stack.push("2")
    stack.push("3")
    println("Size of stack = " + stack.getSize)
    println("Top of stack = " + stack.peek())
  }
}
