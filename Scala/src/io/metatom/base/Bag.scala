package io.metatom.base

/**
  * Bag 的链表实现
  * Bag 是一种不支持删除其中元素的集合数据类型，可以用来收集和遍历元素
  *
  * @author igaozp
  * @since 2019-01-19
  * @version 1.0
  * @tparam T 泛型参数
  */
class Bag[T] {
  /**
    * 链表首节点
    */
  private var first = new Node[T]
  /**
    * Bag 的元素数量
    */
  private var size = 0

  /**
    * 检查是否为空
    *
    * @return true Bag 为空
    *         false Bag 不为空
    */
  def isEmpty: Boolean = size <= 0

  /**
    * 获取 Bag 的元素数量
    *
    * @return Bag 的元素数量
    */
  def getSize: Int = size

  /**
    * 添加元素
    *
    * @param item 添加的元素对象
    */
  def add(item: T): Unit = {
    val oldFirst: Node[T] = first
    first = new Node[T]
    first.item = item
    first.next = oldFirst
    size += 1
  }
}

/**
  * 节点类
  *
  * @tparam T 泛型参数
  */
private class Node[T] {
  var item: T = _
  var next: Node[T] = _
}


object Bag {
  /**
    * Bag 的单元测试
    *
    * @param args 命令行参数
    */
  def main(args: Array[String]): Unit = {
    val bag = new Bag[String]
    bag.add("Hello")
    bag.add("World")
    print("Size of bag = " + bag.getSize)
  }
}