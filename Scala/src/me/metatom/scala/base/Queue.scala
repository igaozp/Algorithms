package me.metatom.scala.base

/**
  * 使用链表实现的 Queue 队列
  * Queue 队列是一种基于 FIFO 策略的集合类型
  *
  * @author igaozp
  * @param manifest Manifest
  * @tparam T 泛型
  */
class Queue[T >: Null <: AnyRef](implicit manifest: Manifest[T]) {
  /**
    * 队列的首节点
    */
  private var first: Node = _
  /**
    * 队列的最后一个节点
    */
  private var last: Node = _
  /**
    * 队列元素的数量
    */
  private var size = 0

  /**
    * 内部的链表节点类
    */
  class Node {
    var item = manifest.runtimeClass.getConstructor().newInstance().asInstanceOf[T]
    var next: Node = _
  }

  /**
    * 检查队列是否为空
    *
    * @return true 队列为空
    *         false 队列不为空
    */
  def isEmpty: Boolean = first == null

  /**
    * 获取队列的元素数量
    *
    * @return 队列的元素数量
    */
  def getSize: Int = size

  /**
    * 获取队列的头部节点元素
    *
    * @return 队列的头部节点元素
    */
  def peek: T = {
    if (isEmpty) {
      throw new NoSuchElementException("Queue underflow")
    }
    first.item
  }

  /**
    * 元素入队
    *
    * @param item 入队元素
    */
  def enqueue(item: T): Unit = {
    // 新增节点
    val oldLast = this.last
    this.last = new Node
    this.last.item = item
    this.last.next = null
    // 根据队列是否为空，选择插入队列的策略
    if (isEmpty) {
      this.first = this.last
    } else {
      oldLast.next = this.last
    }
    // 更新队列元素数量
    size += 1
  }

  /**
    * 元素出队
    *
    * @return 出队元素
    */
  def dequeue: T = {
    if (isEmpty) {
      throw new NoSuchElementException("Queue underflow")
    }
    // 获取队列首节点元素，并使下一个元素成为队列的首节点
    val item = this.first.item
    this.first = this.first.next
    // 队列若为空，队列尾节点置空
    if (isEmpty) {
      this.last = null
    }
    // 更新队列元素数量
    size -= 1
    item
  }
}

object QueueMain {
  def main(args: Array[String]): Unit = {
    val queue = new Queue[String]()
    queue.enqueue("Hello")
    queue.enqueue("World")
    println("Size of queue = " + queue.getSize)
    println("Head of queue = " + queue.peek)
  }
}
