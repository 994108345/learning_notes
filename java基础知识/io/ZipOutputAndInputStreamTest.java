package io;/**
 * Created by wenzailong on 2018/5/22.
 */

import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author wenzailong
 * @create 2018-05-22 10:27
 **/
public class ZipOutputAndInputStreamTest {
    /*
    * 压缩文件流。
    * ZipOutputStream(OutputStream out)
    * putNextEntry(ZipEntry e):开始写一个新的ZipEntry,并且将流内的位置移至此entry所指数据的开头
    * write(byte[] b,int off,int len):将字节数组写入当前ZIP条目数据
    * finish:完成写入ZIP输入流的内容，无须关闭它配合的OutputStream
    * setComment(String comment):可设置此ZIP文件的注释文字
    * */
    @Test
    public void zipTest(){
        String path1 = "E:\\学习\\项目测试文件\\zip1.txt";
        try {
            FileOutputStream fos1 = new FileOutputStream(path1);
            String str = "测试压缩";
            fos1.write(str.getBytes());
            /*以上步骤是在‘项目测试文件‘目录下新建一个文件*/
            String zoutPath = "E:\\学习\\项目测试文件\\ziptest.zip";
            ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zoutPath));//压缩后的文件
            FileInputStream fis = new FileInputStream(path1);
            File file = new File(path1);
            zout.putNextEntry(new ZipEntry(file.getName()));
            byte[] bytes = new byte[1024];
            int i ;
            while((i = fis.read(bytes))!=-1){
                zout.write(bytes,0,i);
            }
            fos1.close();
            fis.close();
            zout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    * 解压文件流
    * read(byte[] b,int off,int len):读取目标b数组内off偏移量的位置，长度是len字节。
    * available():判断是否已读完目前entry所指定的数据，已读完返回0.否则返回1。
    * closeEntry():关闭当前zip条目并定位流以读取下一个条目：
    * skip(long n):跳过当前zip条目指定的字节数
    * getNextEntry():读取下一个zipentry，并将流内的位置移至该entry所指数据的开头
    * createZipEntry(String name):以指定的name参数新建一个ZipEntry对象。
    *
    * */

    @Test
    public void zipOutPutTest(){
        try {
            String path = "E:\\学习\\项目测试文件\\ziptest.zip";
            ZipFile zip = new ZipFile(new File(path), Charset.forName("GBK"));
            Enumeration entries = zip.entries();
            entries.hasMoreElements();
            ZipEntry entry = (ZipEntry) entries.nextElement();
            InputStream in = zip.getInputStream(entry);
            String outPath = "E:\\学习\\项目测试文件\\ziptest\\" + entry.getName();
            File file = new File(outPath);
            file.createNewFile();
            OutputStream out = new FileOutputStream(outPath);
            byte[] bt = new byte[1024];
            int index;
            while((index = in.read(bt))!=-1){
                out.write(bt,0,index);
            }
            zip.close();
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
