package io;/**
 * Created by wenzailong on 2018/5/21.
 */

import org.junit.Test;

import java.io.*;

/**
 * @author wenzailong
 * @create 2018-05-21 17:25
 **/
public class FileInputAndOutStreamTest {
    /*
    * FileInputStream类与FileOutPutStream类都是用来操做磁盘文件的，是字节流。
    * 创建FileInputStream常用的两种构造方法：
    * 1： new FileInputStream(String filePath);
    * filePath:文件路劲
    * 2：new FileInputStream(new File(...));
    * file:文件对象
    * */
    @Test
    public void newFileInputStreamTest1() throws IOException {

        FileInputStream fileInputStream = new FileInputStream("FileTest.java");
        System.out.println(fileInputStream);
        fileInputStream.close();
    }
    @Test
    public void newFileInputStreamTest2() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("E:\\学习\\项目测试文件\\file.java"));
        System.out.println(fileInputStream);
        fileInputStream.close();
    }

    /*
    * 写一个简单的文件读取和写入操作
    * 虽然java在程序结束时自动关闭所有打开的流，但是当使用完流后，显示的关闭任何打开的流是一个好习惯。
    * 不然很容易造成系统资源浪费，并影响其他人读取这个流
    * */
    @Test
    public void fileOutputStreanmWriteTest(){
        try{
            FileOutputStream out = new FileOutputStream("E:\\学习\\项目测试文件\\outputStream.txt");
            byte text[] = "nulidexiaoma".getBytes();
            out.write(text);
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void fileInoutStreamReadTest(){
        try{
            FileInputStream in = new FileInputStream("E:\\学习\\项目测试文件\\outputStream.txt");
            byte b[] = new byte[1024];//读取文件的速度，即每次读取1024个字节。
            int len = in.read(b);
            System.out.println("读取的信息是"+new String(b,0,len));
            in.close();
        }catch (Exception e){


        }
    }
}
