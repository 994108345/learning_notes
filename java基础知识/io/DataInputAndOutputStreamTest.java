package io;/**
 * Created by wenzailong on 2018/5/22.
 */

import org.junit.Test;

import java.io.*;

/**
 * @author wenzailong
 * @create 2018-05-22 9:54
 **/
public class DataInputAndOutputStreamTest {
    /*
    * DataInputStream和DataInputStream,是数据的输入输出流，允许应用程序以机器无关的方式从底层输入流中读取基本java数据类型。
    * 也就是说，当去读一个数据时，不必再关心这个数值应当是什么字节。
    * 构造方法：DataInputStream(InputStream in);
    *          DataOutputStream(OutputStream out);
    * DataOutputStream提供了三种写入字符串的方法：
    * writeBytes(String s)
    * writeChars(String s)
    * writeUTF(String s)
    *
    * DataOutputStream提供了一个readUTF();读取字符串。
    * 在不知道输入流的长度和大小，writeUTF方法可以向目标设备写入字符串的长度。所以也只能准确的读回字符串。
    * */

    @Test
    public void DataTest() throws IOException {
        try {
            String path = "E:\\学习\\项目测试文件\\DataOutPutStream.txt";
            FileOutputStream fo = new FileOutputStream(path);
            DataOutputStream da = new DataOutputStream(fo);
           /* da.writeBytes("使用writeBytes()");*/
            /*da.writeChars("使用writeChars()");*/
            /*da.writeUTF("使用writeUTF()");*/
            da.close();
            FileInputStream fi = new FileInputStream(path);
            DataInputStream di = new DataInputStream(fi);
            String readStr = di.readUTF();
            /*byte readBt = di.readByte();*/
            /*char readCr = di.readChar();*/
            System.out.println(readStr);
            fo.close();
            da.close();
            fi.close();
            di.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
