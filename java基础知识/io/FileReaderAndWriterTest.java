package io;/**
 * Created by wenzailong on 2018/5/21.
 */

import org.junit.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author wenzailong
 * @create 2018-05-21 17:55
 **/
public class FileReaderAndWriterTest {
    /*
    * FileReader和FileWriter是字符流。分别实现Reader和Writer。
    * 因为FileInputStream和FileOutputStream是字节流，处理中文容易出现乱码的问题，
    * 所以采用FileReader和FileWriter字符流就可以避免这种情况
    *
    * */
    @Test
    public void writeText() throws IOException {
        String path = "E:\\学习\\项目测试文件\\fileWriter.txt";
        FileWriter writer = new FileWriter(path);
        writer.write("努力的小码");
        writer.close();
    }

    @Test
    public void readText() throws IOException {
        String path = "E:\\学习\\项目测试文件\\fileWriter.txt";
        FileReader reader = new FileReader(path);
        char[] c = new char[1024];
        int index = reader.read(c);//index为读取当前字符的最后一个字符的下标
        System.out.println(new String(c,0,index));
        reader.close();
    }


}
