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
        // ArrayList继承于AbstractList，
        // 实现了List, RandomAccess, Cloneable, java.io.Serializable

        // ArrayList中的操作不是线程安全的！所以，建议在单线程中才使用ArrayList，
        // 在多线程中可以选择Vector或者CopyOnWriteArrayList。

        // ArrayList(int capacity)
        // capacity是ArrayList的默认容量大小。当由于增加数据导致容量不足时，
        // 容量会添加上一次容量大小的一半。

        // 遍历ArrayList时，使用随机访问(即，通过索引序号访问)效率最高，而使用迭代器的效率最低！

        // 调用 toArray() 函数会抛出“java.lang.ClassCastException”异常，但是调用 toArray(T[] contents) 能正常返回 T[]

        // 修改数的记录值。
        // 每次新建Itr()对象时，都会保存新建该对象时对应的modCount；
        // 以后每次遍历List中的元素的时候，都会比较expectedModCount和modCount是否相等；
        // 若不相等，则抛出ConcurrentModificationException异常，产生fail-fast事件。
        ArrayList arrayList = new ArrayList();

        // LinkedList 是一个继承于AbstractSequentialList的双向链表。
        // 它也可以被当作堆栈、队列或双端队列进行操作。
        // 实现 List 接口，能对它进行队列操作。
        // 实现 Deque 接口，即能将LinkedList当作双端队列使用。

        // LinkedList的本质是双向链表。
        // 无论如何，千万不要通过随机访问去遍历LinkedList！
        // 要用 for (Integer integ:list)
        LinkedList linkedList = new LinkedList();

        // 继承于AbstractList，实现了List, RandomAccess, Cloneable这些接口。
        // 和ArrayList不同，Vector中的操作是线程安全的。
        Vector vector = new Vector(); //----> Enumeration
    }

    @Test
    public void Map(){
        // HashMap，TreeMap，WeakHashMap都是继承于AbstractMap
        // Hashtable虽然继承于Dictionary，但它实现了Map接口。
        Map map;

        // Map.Entry是Map中内部的一个接口，Map.Entry是键值对
        // Map通过 entrySet() 获取Map.Entry的键值对集合，从而通过该集合实现对键值对的操作。
        Map.Entry entry;
        AbstractMap abstractMap;
        SortedMap sortedMap;
        NavigableMap navigableMap;
        Dictionary dictionary;


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
