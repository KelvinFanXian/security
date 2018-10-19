package website.fanxian.io.文件基本技术;

import lombok.Data;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** 13.1二进制文件和字节流
 *  InputStream/OutputStream 抽象基类，很多面向流的代码，以它们为参数（提高代码的重用性）
 *  FileInputStream/FileOutputStream 流的源和目的地是文件
 *  ByteArrayInputStream/ByteArrayOutputStream 输入，【适配器】； 输出，封装了动态数组
 *  DataInputStream/DataOutputStream 【装饰器】 按基本类型和字符串读写流
 *  BufferedInputStream/BufferedOutputStream 【装饰器】,提供缓冲
 */

public class _2二进制文件和字节流 {
    @Test
    public void _1InputStream_OutputStream() throws Exception{/*
        flush方法确保数据传递到操作系统
        close方法一般会首先调用flush方法
        close方法一般应该放到finally语句内
     */
    }
    @Test
    public void _2FileInputStream_FileOutputStream() throws Exception{/*
        当用户没有写权限，会抛出异常SecurityException
     */
        /**
         * FileOutputStream
         */
        // 将字符串写到文件
        OutputStream output =  new FileOutputStream("hello.txt");
        try{
            String data = "hello, 123, 老马";
            byte[] bytes = data.getBytes(Charset.forName("UTF-8"));
            output.write(bytes);
        }finally{
            output.close();
        }

        /**
         * FileDescriptor$sync()
         *  public native void sync() throws SyncFailedException;
         *  本地方法，类似flush，区别：确保数据保存到磁盘
         */

        /**
         * FileInputStream
         */
        // 这里假定一次读完，要确保长度不超过1024
        InputStream input = new FileInputStream("hello.txt");
        byte[] buf = new byte[1024];
        try{
            int bytesRead = input.read(buf);
            String data = new String(buf, 0, bytesRead, "UTF-8");
            System.out.println(data);
        }finally{
            input.close();
        }

        // 为了确保读到所有内容，可以逐个字节读取直到文件结束：
        int b = -1;
        int bytesRead = 0;
        while ((b = input.read()) != -1) {
            buf[bytesRead++] = (byte)b;
        }

        // 在没有缓冲的情况下逐个字节读取性能很低，可以使用批量读入且确保读到结尾
//        byte[] buf = new byte[1024];
        int off=0;
//        int bytesRead = 0;
        while((bytesRead=input.read(buf, off, 1024-off ))!=-1){
            off += bytesRead;
        }
        String data = new String(buf, 0, off, "UTF-8");

        // 以上都是在一直文件大小不超过1024的情况下。 如果超过，则需要用ByteArrayOutputStream
    }
    @Test
    public void _3ByteArrayInputStream_ByteArrayOutputStream() throws Exception{/*
        ByteArrayOutputStream
            public synchronized byte[] toByteArray()
            public synchronized String toString()
            public synchronized String toString(String charsetName)
            public synchronized int size()
            public synchronized void reset()
     */
        InputStream input = new FileInputStream("hello.txt");
        try{
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int bytesRead = 0;
            while((bytesRead=input.read(buf))!=-1){
                // 读入的数据先写入ByteArrayOutputStream中，
                output.write(buf, 0, bytesRead);
            }
            // 读完后，在调用其toString方法获取完整数据
            String data = output.toString("UTF-8");
            System.out.println(data);
        }finally{
            input.close();
        }

        /**
         * ByteArrayInputStream 将byte数组包装为一个输入流，是一种【适配器模式】，它的构造方法有：
         *   public ByteArrayInputStream(byte buf[])
         *   public ByteArrayInputStream(byte buf[], int offset, int length)
         *
         * 为什么要将byte数组转换为InputStream呢？ 因为InputStream作为中间量进行操作，提高代码复用性
         */
    }
    @Test
    public void _4DataInputStream_DataOutputStream() throws Exception{/*
        上面介绍的类都只能以字节为单位读写，如何以其他类型读写呢？ 可以使用
        DataInputStream/DataOutputStream，他们都是【装饰类】

        DataInputStream < FilterOutputStream < OutputStream

        部分方法：
            void writeBoolean(boolean v) throws IOException; // 写入1个字节 true->1 false->0
            void writeInt(int v) throws IOException; // 写入4个字节，先高后低
            void writeUTF(String s) throws IOException; // 将字符串的UTF-8编码字节写入

        构造器:(同FilterOutputStream，接受一个已有的OutputStream)
            public DataOutputStream(OutputStream out)
     */
        /**
         * DataOutputStream
         * 保存一个学生列表到文件中
         */
        @Data
        class Student {
            String name;
            int age;
            double score;
            public Student() {}
            public Student(String name, int age, double score) {
                this.name = name;
                this.age = age;
                this.score = score;
            }
        }
        List<Student> students = Arrays.asList(new Student[]{
                new Student("张三", 18, 80.9d), new Student("李四", 17, 67.5d)
        });
        DataOutputStream output = new DataOutputStream(new FileOutputStream("students.dat"));
        try{
            output.writeInt(students.size()); // 文件长度
            for(Student s : students){
                output.writeUTF(s.getName());
                output.writeInt(s.getAge());
                output.writeDouble(s.getScore());
            }
        }finally{
            output.close();
        }

        /**
         * 学生列表从文件中读出来（逆过程）
         * DataInputStream
         *  boolean readBoolean() throws IOException;
         *  int readInt() throws IOException;
         *  String readUTF() throws IOException;
         *  public DataInputStream(InputStream in)
         */
        DataInputStream input = new DataInputStream(
                new FileInputStream("students.dat"));
        try{
            int size = input.readInt();
            List<Student> students1 = new ArrayList<>(size);
            for(int i=0; i<size; i++){
                Student s = new Student();
                s.setName(input.readUTF());
                s.setAge(input.readInt());
                s.setScore(input.readDouble());
                students.add(s);
            }
            System.out.println(students1);
        }finally{
            input.close();
        }

        /**
         * 使用DataInputStream_DataOutputStream是读写对象，灵活但麻烦
         *   所以，Java提供了序列化机制，下章介绍
         */
    }
    @Test
    public void _5BufferedInputStream_BufferedOutputStream() throws Exception{/*
        FileInputStream/FileOutputStream是无缓冲的。而BufferedInputStream有个字节数组作为缓冲区。
            public BufferedInputStream(InputStream in)
            public BufferedInputStream(InputStream in, int size) // 缓冲区大小，size默认8192
     */
        // 包装类
        InputStream input = new BufferedInputStream(new FileInputStream("hello.txt"));
        OutputStream output =  new BufferedOutputStream(new FileOutputStream("hello.txt"));
        // 二次包装
        DataInputStream dataInput = new DataInputStream(new BufferedInputStream(new FileInputStream("students.dat")));
        DataOutputStream dataOutput = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("students.dat")));
    }

    static class 实用封装{
        /**
         * 1.复制输入流的内容到输出流
         * Java9中，InputStream$transferTo(OutputStream out)
         */
        public static void copy(InputStream input,
                                OutputStream output) throws IOException{
            byte[] buf = new byte[4096];
            int bytesRead = 0;
            while((bytesRead = input.read(buf))!=-1){
                output.write(buf, 0, bytesRead);
            }
        }

        /**
         * 2.将文件读入字节数组-->copy(InputStream input, OutputStream output)
         */
        public static byte[] readFileToByteArray(String fileName) throws IOException{
            InputStream input = new FileInputStream(fileName);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            try{
                copy(input, output);
                return output.toByteArray();
            }finally{
                input.close();
            }
        }

        /**
         * 将字节数组写到文件
         */
        public static void writeByteArrayToFile(String fileName,
                                                byte[] data) throws IOException{
            OutputStream output = new FileOutputStream(fileName);
            try{
                output.write(data);
            }finally{
                output.close();
            }
        }

        /**
         * 开发中推荐实用Apache Commons IO
         *  http://commons.apache.org/proper/commons-io/
         */

        /**
         *  InputStream/OutputStream 抽象基类，很多面向流的代码，以它们为参数（提高代码的重用性）
         *  FileInputStream/FileOutputStream 流的源和目的地是文件
         *  ByteArrayInputStream/ByteArrayOutputStream 输入，【适配器】； 输出，封装了动态数组
         *  DataInputStream/DataOutputStream 【装饰器】 按基本类型和字符串读写流
         *  BufferedInputStream/BufferedOutputStream 【装饰器】,提供缓冲
         */
    }
}
