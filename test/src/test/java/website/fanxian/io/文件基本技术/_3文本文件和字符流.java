package website.fanxian.io.文件基本技术;

import org.junit.Test;

import java.io.IOException;
import java.io.Writer;

/**
 * 13.3文本文件和字符流
 *  1) Reader/Writer 字符流的基类，它们是抽象类
 *  2) InputStreamReader/OutputStreamWriter 【适配器】，将字节流转换为字符流
 *  3) FileReader/FileWriter 输入源和输出目标是文件的字符流
 *  4) CharArrayReader/CharArrayWriter 输入源和输出目标是char数组的字符流
 *  5) StringReader/StringWriter 输入源和输出目标是String的字符流
 *  6) BufferedReader/BufferedWriter 【装饰类】，对输入输出流提供缓冲，
 *  7) PrintWriter 【装饰类】，可将基本类型和对象装为其字符串形式输出的类。
 *
 *  还有一个Scanner类，类似于一个Reader，但不是Reader的子类，可以读取基本类型的字符串形式，
 *  类似PrintWriter的逆操作。
 *
 *  字符流按char读取
 *  一个char不完全等同于一个字符
 */
public class _3文本文件和字符流 {

    @Test
    public void _1Reader_Writer() throws Exception{/*
        Reader类似InputStream，抽象类
            public int read() throws IOException
            public int read(char cbuf[]) throws IOException // 读取的是char
            abstract public void close() throws IOException
            public long skip(long n) throws IOException
            public boolean ready() throws IOException // 对应InputStream里的available
        Writer类似OutputStream，抽象类
            public void write(int c)
            public void write(char cbuf[]) // 写的是char
            public void write(String str) throws IOException // String的内部就是char数组，处理时，会获取到char数组
            abstract public void close() throws IOException;
            abstract public void flush() throws IOException;
     */
    }
    @Test
    public void _2InputStreamReader_OutputStreamWriter() throws Exception{/*
        【适配器】将InputStream/OutputStream 转换为 Reader/Writer
     */
    }
    @Test
    public void _3FileReader_FileWriter() throws Exception{/*
     */
    }
    @Test
    public void _4CharArrayReader_CharArrayWriter() throws Exception{/*
     */
    }
    @Test
    public void _5StringReader_StringWriter() throws Exception{/*
     */
    }
    @Test
    public void _6BufferedReader_BufferedWriter() throws Exception{/*
     */
    }
    @Test
    public void _7PrintWriter() throws Exception{/*
     */
    }
}
