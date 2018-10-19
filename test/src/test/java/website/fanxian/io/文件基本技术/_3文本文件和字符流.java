package website.fanxian.io.文件基本技术;

import org.junit.Test;

import java.io.*;
import java.util.Scanner;

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

        OutputStreamWriter：
            public OutputStreamWriter(OutputStream out, String charsetName)
     */
        // 创建一个FileOutputStream，然后将其包在一个OutputStreamWriter中，
        // 就可以直接以字符串写入了。
        Writer writer = new OutputStreamWriter(
                new FileOutputStream("hello.txt"), "GB2312");
        try{
            String str = "hello, 123,老马";
            writer.write(str);
        }finally{
            writer.close();
        }

        /**
         * InputStreamReader
         *  public InputStreamReader(InputStream in)
         *  public InputStreamReader(InputStream in, String charsetName)
         *
         *  编码类型： InputStreamReader内部有一个类型为StreamDecoder的解码器
         *   能将字节根据编码转换为char。
         */
        Reader reader = new InputStreamReader(
                new FileInputStream("hello.txt"), "GB2312");
        try{
            char[] cbuf = new char[1024];
            int charsRead = reader.read(cbuf);
            System.out.println(new String(cbuf, 0, charsRead));
        }finally{
            reader.close();
        }

        /**
         * 这段代码假定一次read嗲用就读到了所有内容，且假定长度不超过1024。
         * 为了确保读到所有内容，可以记住待会儿介绍的
         * CharArrayWriter或StringWriter
         */
    }
    @Test
    public void _3FileReader_FileWriter() throws Exception{/*
        public FileReader(File file) throws FileNotFoundException
        public FileReader(String fileName) throws FileNotFoundException

        public FileWriter(File file) throws IOException
        public FileWriter(String fileName, boolean append) throws IOException
     */
        /**
         * 需要注意的是， FileReader/FileWriter不能指定编码类型，只能使用默认编码，
         * 如果需要指定编码类型，可以使用InputStreamReader/OutputStreamWriter
         */
    }
    @Test
    public void _4CharArrayReader_CharArrayWriter() throws Exception{/*
        CharArrayWriter与ByteArrayOutputStream类似，它的输出目标是char数组，
        这个数组的长度可以根据数组内容动态扩展。
            public char[] toCharArray()
            public String toString()
     */
        // 相比FileReader可以指定编码
        Reader reader = new InputStreamReader(
                new FileInputStream("hello.txt"), "GB2312");
        try{
            CharArrayWriter writer = new CharArrayWriter();
            char[] cbuf = new char[1024];
            int charsRead = 0;
            while((charsRead=reader.read(cbuf))!=-1){
                // 读入的数据先写入CharArrayWriter中，读完后，在调用其toString()获取完整数据
                writer.write(cbuf, 0, charsRead);
            }
            System.out.println(writer.toString());
        }finally{
            reader.close();
        }

        /**
         * CharArrayReader和ByteArrayInputStream类似，它将char数组包装为一个Reader，
         * 是一种【适配器模式】
         *     public CharArrayReader(char buf[])
         *     public CharArrayReader(char buf[], int offset, int length)
         */
    }
    @Test
    public void _5StringReader_StringWriter() throws Exception{/*
     */
        /**
         * 与CharArrayReader， 只是输入源为String，输出目标为StringBuffer
         */
    }
    @Test
    public void _6BufferedReader_BufferedWriter() throws Exception{/*
        // 提供了缓冲，【装饰器】
        public BufferedWriter(Writer out)
        public BufferedWriter(Writer out, int sz) // sz 缓冲大小，默认为8192
        public void newLine() throws IOException // 输出换行符

        // BufferedReader
        public BufferedReader(Reader in)
        public BufferedReader(Reader in, int sz) // sz 缓冲大小，默认为8192
        public String readLine() throws IOException // 读入一行

     */
        /**
         * FileReader/FileWriter是没有缓冲的，也不能按行读写，所以，一般应该在他们对外面包上对应的缓冲类。
         */
//        public static void writeStudents(List<Student> students) throws IOException{
//            BufferedWriter writer = null;
//            try{
//                writer = new BufferedWriter(new FileWriter("students.txt"));
//                for(Student s : students){
//                    writer.write(s.getName()+","+s.getAge()+","+s.getScore());
//                    writer.newLine();
//                }
//            }finally{
//                if(writer!=null){
//                    writer.close();
//                }
//            }
//        }

//        public static List<Student> readStudents() throws IOException{
//            BufferedReader reader = null;
//            try{
//                reader = new BufferedReader(
//                        new FileReader("students.txt"));
//                List<Student> students = new ArrayList<>();
//                String line = reader.readLine();
//                while(line!=null){
//                    String[] fields = line.split(",");
//                    Student s = new Student();
//                    s.setName(fields[0]);
//                    s.setAge(Integer.parseInt(fields[1]));
//                    s.setScore(Double.parseDouble(fields[2]));
//                    students.add(s);
//                    line = reader.readLine();
//                }
//                return students;
//            }finally{
//                if(reader!=null){
//                    reader.close();
//                }
//            }
//        }

    }
    @Test
    public void _7PrintWriter() throws Exception{/*
        有很多重载的print方法，如：
            public void print(int i)
            public void print(Object obj)
            public PrintWriter printf(String format, Object ... args) // 格式化输出

            public PrintWriter(File file) throws FileNotFoundException
            public PrintWriter(String fileName, String csn) // 表示编码类型
            public PrintWriter(OutputStream out, boolean autoFlush)
            public PrintWriter(Writer out)
     */
        PrintWriter writer = new PrintWriter("");
        writer.format("%.2f", 123.456f); // 格式化输出，保留两位小数

        /**
         *  PrintWriter有一个非常类似的类 PrintStream
         *      有共同的方法，但含义有区别
         *
         *   PrintStream$println 只要碰到一个换行符\n，就会自动同步缓冲区
         *   PrintStream$write(int b) 使用最低的8位，输出一个字节
         *   PrintWriter$write(int b) 使用最低的2位，输出一个char
         */
    }

    @Test
    public void _8Scanner() {/*
        一个简单的文本扫描器，需要一个分隔符将不同数据分开，默认是空格
        通过方法useDelimiter() 可以指定。 有很多next方法，用于读取基本类型或行：
            public float nextFloat()
            public int nextInt()
            public String nextLine()

        有很多构造器，可以接受File对象，InputStream，Reader作为参数
        也可以接受String，这是它会创建一个StringReader。
    */
//        public static List<Student> readStudents() throws IOException{
//            BufferedReader reader = new BufferedReader(
//                    new FileReader("students.txt"));
//            try{
//                List<Student> students = new ArrayList<Student>();
//                String line = reader.readLine();
//                while(line!=null){
//                    Student s = new Student();
//                    Scanner scanner = new Scanner(line).useDelimiter(",");
//                    s.setName(scanner.next());
//                    s.setAge(scanner.nextInt());
//                    s.setScore(scanner.nextDouble());
//                    students.add(s);
//                    line = reader.readLine();
//                }
//                return students;
//            }finally{
//                reader.close();
//            }
//        }
    }

    @Test
    public void _9标准流() throws Exception {/*
        键盘与屏幕
        System.out
        System.in
        System.err
    */
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        System.out.println(num);

        /**
         * 可以【重定向】
         * setIn
         * setOut
         * setErr
         */
        System.setIn(new ByteArrayInputStream("hello".getBytes("UTF-8")));
        System.setOut(new PrintStream("out.txt"));
        System.setErr(new PrintStream("err.txt"));
        try{
            Scanner in2 = new Scanner(System.in);
            System.out.println(in2.nextLine());
            System.out.println(in2.nextLine());
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}
