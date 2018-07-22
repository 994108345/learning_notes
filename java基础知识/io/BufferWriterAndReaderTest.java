package io;/**
 * Created by wenzailong on 2018/5/22.
 */

import org.junit.Test;

import java.io.*;

/**
 * @author wenzailong
 * @create 2018-05-22 9:34
 **/
public class BufferWriterAndReaderTest {
    /*
    * 常用方法：
    * read():单个字符
    * readLIne()：读取一个文本行，并将其返回为字符串，若无数据可读，则返回null
    * write(String s,int off,int len):写入字符串的某个部分
    * flush：刷新该流的缓存
    * newLine:写入一个行分隔符
    * 在使用BufferWriter的write方法时，数据并没有马上写入输出流，而是首先写入缓存区中，如果想立刻将缓存的数据写入输入流中，一定要调用flush方法。
    * */
    @Test
    public void bufferWriter() throws IOException {
        String path = "E:\\学习\\项目测试文件\\bufferWriter.txt";
        FileWriter fw = new FileWriter(path);
        BufferedWriter bw = new BufferedWriter(fw);
        String txt = "努力的小码";
        bw.write(txt.toCharArray());//写入如果是字符流，肯定是写入字符，是字节就写入字节，所以要转换！
        bw.flush();
        fw.close();
        bw.close();
    }

    @Test
    public void bufferRead() throws IOException {
        String path = "E:\\学习\\项目测试文件\\bufferWriter.txt";
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        char[] c = new char[1024];
        int index = br.read(c);
        System.out.println(new String(c,0,index));
        fr.close();
        br.close();
    }

}
