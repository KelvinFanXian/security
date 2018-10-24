/**
 *  1.硬盘的访问延时，相比内存，是很慢的。
 *  2.一般读写文件需要两次数据复制：
 *      读文件：
 *          先从硬盘复制到操作系统内核
 *          再从内核复制到应用程序分配的内存中。
 *
 * 运行环境不同：
 *      操作系统--内核态
 *      应用程序--用户态
 *
 * 应用程序调用操作系统的功能
 *      需要两次环境的切换，
 *          用户态 --> 内核态，
 *          内核态 --> 用户态。
 * 这种切换是有开销的，应尽量避免。
 *
 *  操作系统操作文件一般有打开和关闭的概念。
 *      打开文件会在操作系统内核建立一个有关该文件的内存结构，这个结构一般通过一个整数索引
 *      来引用，称为【文件描述符】。 这个结构耗内存，OS能同时打开的文件是有限的。
 *      关闭文件，会同步缓冲区内容到硬盘，并释放占据的内存结构。
 *
 *  OS一般支持一种称为【内存映射文件】的高效的随机读写大文件的方法。
 *      将文件直接映射到内存，操作内存就是操作文件。 在内存映射文件中，只有访问到的数据才会被
 *      实际复制到内存，且数据只会复制一次，被操作系统以及多个应用程序共享。
 *
 *
 */
package website.fanxian.io;