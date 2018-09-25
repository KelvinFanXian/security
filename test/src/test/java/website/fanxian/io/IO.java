package website.fanxian.io;

import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class IO {
    // 对应英文字母“abcddefghijklmnopqrsttuvwxyz”
    private static final byte[] ArrayLetters = {0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F,
            0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7A};

    /**
     * ByteArrayInputStream实际上是通过“字节数组”去保存数据。
     (01) 通过ByteArrayInputStream(byte buf[]) 或 ByteArrayInputStream(byte buf[], int offset, int length) ，我们可以根据buf数组来创建字节流对象。
     (02) read()的作用是从字节流中“读取下一个字节”。
     (03) read(byte[] buffer, int offset, int length)的作用是从字节流读取字节数据，并写入到字节数组buffer中。offset是将字节写入到
          buffer的起始位置，length是写入的字节的长度。
     (04) markSupported()是判断字节流是否支持“标记功能”。它一直返回true。
     (05) mark(int readlimit)的作用是记录标记位置。记录标记位置之后，某一时刻调用reset()则将“字节流下一个被读取的位置
          ”重置到“mark(int readlimit)所标记的位置”；也就是说，reset()之后再读取字节流时，是从mark(int readlimit)所标记的位置开始读取。
     */
    @Test
    public void ByteArrayInputStream(){

        String s = new String(ArrayLetters);
        System.out.println(">"+s);

        // 从字节流中读取5个字节
        ByteArrayInputStream bais = new ByteArrayInputStream(ArrayLetters);
        for (int i = 0; i <5 ; i++) {
            if (bais.available() >= 0) { // 若能继续读取下一个字节
                // 读取“字节流的下一个字节”
                int tmp = bais.read();
                System.out.printf("%d : 0x%s\n", i, Integer.toHexString(tmp));
            }
        }

        // 若改字节流不支持标记，则直接退出
        if (!bais.markSupported()) {
            System.out.println("mark not support");
            return;
        }

//      标记“字节流中下一个被读取的位置”。即--标记“0x66”，因为因为前面已经读取了5个字节，所以下一个被读取的位置是第6个字节”
//       (01), ByteArrayInputStream类的mark(0)函数中的“参数0”是没有实际意义的。
//       (02), mark()与reset()是配套的，reset()会将“字节流中下一个被读取的位置”重置为“mark()中所保存的位置”
        bais.mark(0); // mark g2
        bais.skip(5); // skip g2
        byte[] buf = new byte[5];
        bais.read(buf, 0, 5); // read g3
        String str1 = new String(buf);
        System.out.printf("str1=%s\n",str1); //0x6B, 0x6C, 0x6D, 0x6E, 0x6F,

        bais.reset(); // reset to g2
        bais.read(buf, 0, 5); //read g2
        String str2 = new String(buf);
        System.out.printf("str2=%s\n", str2); //0x66, 0x67, 0x68, 0x69, 0x6A

    }

    /**
     * ByteArrayOutputStream 是字节数组输出流。它继承于OutputStream。
       ByteArrayOutputStream 中的数据被写入一个 byte 数组。缓冲区会随着数据的不断写入而自动增长。可使用 toByteArray() 和 toString() 获取数据。

     ByteArrayOutputStream实际上是将字节数据写入到“字节数组”中去。
     (01) 通过ByteArrayOutputStream()创建的“字节数组输出流”对应的字节数组大小是32。
     (02) 通过ByteArrayOutputStream(int size) 创建“字节数组输出流”，它对应的字节数组大小是size。
     (03) write(int oneByte)的作用将int类型的oneByte换成byte类型，然后写入到输出流中。
     (04) write(byte[] buffer, int offset, int len) 是将字节数组buffer写入到输出流中，offset是从buffer中读取数据的起始偏移位置，len是读取的长度。
     (05) writeTo(OutputStream out) 将该“字节数组输出流”的数据全部写入到“输出流out”中。


     */
    @Test
    public void ByteArrayOutputStream(){
        // 创建ByteArrayOutputStream字节流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 依次写入ABC三个字母
        baos.write(0x41);
        baos.write(0x42);
        baos.write(0x43);
        System.out.printf("baos=%s\n", baos);

        // 将ArrayLetters数组中从“3”开始的后5个字节写入到baos中。
        // 即对应写入。。。 即”defgh“
        baos.write(ArrayLetters, 3, 5);
        System.out.printf("baos=%s\n", baos);

        // 计算长度
        int size = baos.size();
        System.out.printf("size=%s\n", size);

        // 转换成byte[]数组
        byte[] buf = baos.toByteArray();
        String str = new String(buf);
        System.out.printf("str=%s\n", str);

        // 将baos写入到另一个输出流
        try{
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            baos.writeTo((OutputStream) baos2);
            System.out.printf("baos2=%s\n", baos2);
        } catch(IOException e){
        }

    }

    /**
     * 在java中，PipedOutputStream和PipedInputStream分别是管道输出流和管道输入流。
     它们的作用是让多线程可以通过管道进行线程间的通讯。在使用管道通信时，必须将PipedOutputStream和PipedInputStream配套使用。
     使用管道通信时，大致的流程是：我们在线程A中向PipedOutputStream中写入数据，这些数据会自动的发送到与PipedOutputStream对应的PipedInputStream中，
     进而存储在PipedInputStream的缓冲中；此时，线程B通过读取PipedInputStream中的数据。就可以实现，线程A和线程B的通信。

     管道输入流的缓冲区默认大小是1024个字节。所以，最多只能写入1024个字节。
     */
    @Test
    public void PipedIOStream() throws IOException {
        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
        pipedOutputStream.write(ArrayLetters);
        pipedOutputStream.close();

        byte[] readBuf = new byte[1024];
        int len;
        while ((len = pipedInputStream.read(readBuf))!=-1) {
            System.out.println(new String(readBuf, 0, len));
        }
        pipedInputStream.close();
    }

    @Test
    public void ObjectIOStream() throws Exception{
        /// ObjectOutputStream
        String tmp = "box.tmp";
        ObjectOutputStream out
                = new ObjectOutputStream(new FileOutputStream(tmp));
        out.writeBoolean(true);
        out.writeByte((byte)65);
        out.writeChar('a');
        out.writeInt(20131015);
        out.writeFloat(3.14F);
        out.writeDouble(1.414D);
        // 写入HashMap对象
        HashMap map = new HashMap();
        map.put("one", "red");
        map.put("two", "green");
        map.put("three", "blue");
        out.writeObject(map);
        out.close();

        /// ObjectInputStream
        ObjectInputStream in
                = new ObjectInputStream(new FileInputStream(tmp));
        System.out.printf("boolean:%b\n" , in.readBoolean());
        System.out.printf("byte:%d\n" , (in.readByte()&0xff));
        System.out.printf("char:%c\n" , in.readChar());
        System.out.printf("int:%d\n" , in.readInt());
        System.out.printf("float:%f\n" , in.readFloat());
        System.out.printf("double:%f\n" , in.readDouble());
        // 读取HashMap对象
        HashMap map2 = (HashMap) in.readObject();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            System.out.printf("%-6s -- %s\n" , entry.getKey(), entry.getValue());
        }

    }


}
