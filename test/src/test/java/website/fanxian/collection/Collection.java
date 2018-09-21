package website.fanxian.collection;

import org.junit.Test;

import java.util.*;

/**
 * http://www.cnblogs.com/skywang12345/p/3308498.html
 * List列表
 * Set集合
 * Map映射
 * 工具类(Iterator迭代器、Enumeration枚举类、Arrays和Collections)
 */
public class Collection {


    @Test
    // ArrayList, LinkedList, Stack, Vector
    public void List() {
//         ArrayList继承于AbstractList，
//         实现了List, RandomAccess, Cloneable, java.io.Serializable
//
//         ArrayList中的操作不是线程安全的！所以，建议在单线程中才使用ArrayList，
//         在多线程中可以选择Vector或者CopyOnWriteArrayList。
//
//         ArrayList(int capacity)
//         capacity是ArrayList的默认容量大小。当由于增加数据导致容量不足时，
//         容量会添加上一次容量大小的一半。
//
//         遍历ArrayList时，使用随机访问(即，通过索引序号访问)效率最高，而使用迭代器的效率最低！
//
//         调用 toArray() 函数会抛出“java.lang.ClassCastException”异常，但是调用 toArray(T[] contents) 能正常返回 T[]
//
//         修改数的记录值。
//         每次新建Itr()对象时，都会保存新建该对象时对应的modCount；
//         以后每次遍历List中的元素的时候，都会比较expectedModCount和modCount是否相等；
//         若不相等，则抛出ConcurrentModificationException异常，产生fail-fast事件。
        ArrayList arrayList = new ArrayList();

//         LinkedList 是一个继承于AbstractSequentialList的双向链表。
//         它也可以被当作堆栈、队列或双端队列进行操作。
//         实现 List 接口，能对它进行队列操作。
//         实现 Deque 接口，即能将LinkedList当作双端队列使用。
//
//         LinkedList的本质是双向链表。
//         无论如何，千万不要通过随机访问去遍历LinkedList！
//         要用 for (Integer integ:list)
        LinkedList linkedList = new LinkedList();

//         继承于AbstractList，实现了List, RandomAccess, Cloneable这些接口。
//         和ArrayList不同，Vector中的操作是线程安全的。
        Vector vector = new Vector(); //----> Enumeration
    }

    @Test
    public void Map(){
//         HashMap，TreeMap，WeakHashMap都是继承于AbstractMap
//         Hashtable虽然继承于Dictionary，但它实现了Map接口。
        Map map;

//         Map.Entry是Map中内部的一个接口，Map.Entry是键值对
//         Map通过 entrySet() 获取Map.Entry的键值对集合，从而通过该集合实现对键值对的操作。
        Map.Entry entry;
        AbstractMap abstractMap;

//         SortedMap是一个继承于Map接口的接口。它是一个有序的SortedMap键值映射。
//         SortedMap的排序方式有两种：自然排序 或者 用户指定比较器。
//         插入有序 SortedMap 的所有元素都必须实现 Comparable 接口（或者被指定的比较器所接受）。
        SortedMap sortedMap;

//        NavigableMap是继承于SortedMap的接口
//        它是一个可导航的键-值对集合
//        NavigableMap分别提供了获取“键”、“键-值对”、“键集”、“键-值对集”的相关方法

//        NavigableMap除了继承SortedMap的特性外，它的提供的功能可以分为4类：
//        第1类，提供操作键-值对的方法。
//          lowerEntry、floorEntry、ceilingEntry 和 higherEntry 方法，它们分别返回与小于、小于等于、大于等于、大于给定键的键关联的 Map.Entry 对象。
//          firstEntry、pollFirstEntry、lastEntry 和 pollLastEntry 方法，它们返回和/或移除最小和最大的映射关系（如果存在），否则返回 null。
//        第2类，提供操作键的方法。这个和第1类比较类似
//          lowerKey、floorKey、ceilingKey 和 higherKey 方法，它们分别返回与小于、小于等于、大于等于、大于给定键的键。
//        第3类，获取键集。
//          navigableKeySet、descendingKeySet分别获取正序/反序的键集。
//        第4类，获取键-值对的子集。
        NavigableMap navigableMap;

//        NavigableMap是JDK 1.0定义的键值对的接口，它也包括了操作键值对的基本函数。
        Dictionary dictionary;

//        HashMap 是一个散列表，它存储的内容是键值对(key-value)映射。
//        HashMap 继承于AbstractMap，实现了Map、Cloneable、java.io.Serializable接口。
//        HashMap 的实现不是同步的，这意味着它不是线程安全的。它的key、value都可以为null。此外，HashMap中的映射不是有序的。

//        HashMap 的实例有两个参数影响其性能：“初始容量” 和 “加载因子”容量 是哈希表中桶的数量，
// 初始容量 只是哈希表在创建时的容量。
// 加载因子 是哈希表在其容量自动增加之前可以达到多满的一种尺度。
// 当哈希表中的条目数超出了加载因子与当前容量的乘积时，则要对该哈希表进行rehash 操作（即重建内部数据结构），从而哈希表将具有大约两倍的桶数。
//        通常，默认加载因子是 0.75, 这是在时间和空间成本上寻求一种折衷。加载因子过高虽然减少了空间开销，但同时也增加了查询成本
// （在大多数 HashMap 类的操作中，包括 get 和 put 操作，都反映了这一点）。

//        // 指定“容量大小”和“加载因子”的构造函数
//        HashMap(int capacity, float loadFactor)

//        (01) HashMap继承于AbstractMap类，实现了Map接口。Map是"key-value键值对"接口，AbstractMap实现了"键值对"的通用函数接口。
//        (02) HashMap是通过"拉链法"实现的哈希表。它包括几个重要的成员变量：table, size, threshold, loadFactor, modCount。
//    　　table是一个Entry[]数组类型，而Entry实际上就是一个单向链表。哈希表的"key-value键值对"都是存储在Entry数组中的。
//    　　size是HashMap的大小，它是HashMap保存的键值对的数量。
//    　　threshold是HashMap的阈值，用于判断是否需要调整HashMap的容量。threshold的值="容量*加载因子"，当HashMap中存储数据的数量达到threshold时，就需要将HashMap的容量加倍。
//    　　loadFactor就是加载因子。
//    　　modCount是用来实现fail-fast机制的。

//        在详细介绍HashMap的代码之前，我们需要了解：HashMap就是一个散列表，它是通过“拉链法”解决哈希冲突的。
        HashMap hashMap = new HashMap(1, (float) .75);

    }

    @Test
    public void Set(){
        // HastSet, TreeSet 分别依赖于 HashMap, TreeMap

    }

    @Test
    public void Iterator(){
        // 我们说Collection依赖于Iterator，是因为
        // Collection的实现类都要实现iterator()函数，返回一个Iterator对象
        // ListIterator是专门为遍历List而存在的。
    }

    @Test
    public void Arrays_Collections(){
        // 操作数组、集合的两个工具类
    }
}
