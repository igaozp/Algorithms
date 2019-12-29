package io.metatom.scala.base

import scala.reflect.Manifest

/**
  * Bag 的链表实现
  * Bag 是一种不支持删除其中元素的集合数据类型，可以用来收集和遍历元素
  *
  * @author igaozp
  * @since 2019-01-19
  * @version 1.0
  * @tparam T 泛型参数
  */
class Bag[T >: Null <: AnyRef](implicit manifest: Manifest[T]) {
  /**
    * 链表首节点
    */
  private var first = new Node
  /**
    * Bag 的元素数量
    */
  private var size = 0

  /**
    * 内部节点类
    */
  class Node {
    var item: T = manifest.runtimeClass.getConstructor().newInstance().asInstanceOf[T]
    var next: Node = _
  }

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
    val oldFirst: Node = first
    first = new Node
    first.item = item
    first.next = oldFirst
    size += 1
  }
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