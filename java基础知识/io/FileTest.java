package io;/**
 * Created by wenzailong on 2018/5/21.
 */

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author wenzailong
 * @create 2018-05-21 16:24
 **/
public class FileTest {
    /*
    * 创建File有三种形式
    * 1：File file = new File(String pathname);
    *   pathname:是指路径名称（包含文件名）
    *
    *  2：File file = new File(String parentPath,String childPath);
    *  parentPath:父路劲  chldPath:子路径
    *
    *  3：File file = new File(File parentFile,String childPath);
    *  parentFile:父路径文件对象  childPath:子路劲字符串
    * */
    @Test
    public void newFileTest1(){
        String pathname = "E:\\学习\\项目测试文件\\file.txt";
        File file = new File(pathname);
        System.out.println(file.exists());
    }
    @Test
    public void newFileTest2(){
        String parentPath = "E:\\学习\\项目测试文件";
        String childPath = "file.txt";
        File file = new File(parentPath,childPath);
        System.out.println(file.exists());
    }
    @Test
    public void newFileTest3(){
        File parentFile = new File( "E:\\学习\\项目测试文件");
        String childPath = "file.txt";
        File file = new File(parentFile,childPath);
        System.out.println(file.exists());
    }

    /*
    * 如果file.txt文件不存在。则可以通过file的createNewFile()创建一个文件名为file.txt的文件,创建成功会返回true，反之，如果文件已经存在，则返回false
    * 如果file.txt存在，则可以通过delete()方法删除，删除成功返回true，文件不存在，删除失败返回false。
    *
    * */
    @Test
    public void createFileTest() throws IOException {
        String pathname = "E:\\学习\\项目测试文件\\wenzailong.txt";
        File file = new File(pathname);
        boolean isCreate = file.createNewFile();
        System.out.println(isCreate);
    }
    @Test
    public void deleteFileTest() throws IOException {
        String pathname = "E:\\学习\\项目测试文件\\wenzailong.txt";
        File file = new File(pathname);
        boolean isDelete = file.delete();
        System.out.println(isDelete);
    }

    @Test
    public void fileMethodTest() {
        String pathname = "E:\\学习\\项目测试文件\\file.txt";
        File file = new File(pathname);
        String fileName = file.getName();//获取文件的名称
        boolean isCanRead = file.canRead();//判断文件是否是可读的
        boolean isCanWrite = file.canWrite();//判断文件是否存在
        boolean isExits = file.exists();//文件是否存在
        long length = file.length();//文件的长度（以字节为单位）
        String filePath = file.getAbsolutePath();//获取文件的绝对路径
        String parentPath = file.getParent();//获得文件的父路径
        boolean isFile = file.isFile();//文件是否存在
        boolean isDirectory = file.isDirectory();//文件是否是一个目录
        boolean isHiddenFile = file.isHidden();//文件是否是隐藏文件
        long lastModifiedDate = file.lastModified();//返回文件的最后修改时间
        boolean isRename = file.renameTo(new File("E:\\学习\\项目测试文件\\file1.txt"));//重命名文件
        System.out.println();
    }

    /*
    *
    * boolean renameTo(File file);重命名文件，重命名成功返回true,反之false
    * 可以理解问file去替换原来的file。
    * */

    @Test
    public void renameFileTest(){
        String filePath = "E:\\学习\\项目测试文件\\file1.txt";
        File file = new File(filePath);
        boolean isReName = file.renameTo(new File("E:\\学习\\项目测试文件\\file2.java"));
        System.out.println(isReName);
    }

    @Test
    public void listFileTest(){
        String path = null;
        try {
            path = "E:\\学习\\项目测试文件";
            File file = new File(path);
            File[] files = file.listFiles();
            for (File file1 : files) {
                System.out.println(file1.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
