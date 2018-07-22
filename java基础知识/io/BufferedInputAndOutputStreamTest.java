package io;/**
 * Created by wenzailong on 2018/5/22.
 */

import org.junit.Test;

import java.io.*;

/**
 * @author wenzailong
 * @create 2018-05-22 9:04
 **/
public class BufferedInputAndOutputStreamTest {
    /*
    *
    *
    缓存是I/O的一种性能优化。缓存刘为I/O流增加了内存缓存区，有了缓存区，使得在流上执行skip，mark。和reset方法都成为可能！
    BufferedInputStream有两个构造函数
    BufferedInputStream(InputStream in)：创建带有32个字节的缓存流,
    BufferedInputStream(InputStream in,int size)：构造指定的size创建缓存流。
    一个最优缓存区的小取决于它所在的操作系统，
    BufferedOutputStream和OutputStream有一个flush方法来讲缓存区的数据强制输出完。
    BufferedOutputStream(OutputStream in)
    BufferedOutputStream(OutputStream in,int size)
    flush()就是用于即使缓存区没有满的情况下，也将缓存区的内容强制写入到外设。习惯上叫做刷新。
    从构造函数上可以看出来缓存流肯定是在其他普通输出输入流之后创建，因为创建缓存流需要其他的输入输出流。
    */
    @Test
    public void bufferInputTest() throws IOException {
        String path = "E:\\学习\\项目测试文件\\bufferOutputStream.txt";
        FileOutputStream out = new FileOutputStream(path);
        BufferedOutputStream bf = new BufferedOutputStream(out);
        String text = "nulidexiaoma";
        bf.write(text.getBytes());//都是覆盖原来的内容
        bf.flush();
        out.close();
        bf.close();
    }

    @Test
    public void bufferOutputStream() throws IOException {
        String path = "E:\\学习\\项目测试文件\\bufferOutputStream.txt";
        FileInputStream in = new FileInputStream(path);
        BufferedInputStream bf = new BufferedInputStream(in);
        byte[] b = new byte[1024];//是字节流就定义字节，是字符就定义字符
        int index = bf.read(b);
        System.out.println(new String(b,0,index));
        in.close();
        bf.close();
    }
}
