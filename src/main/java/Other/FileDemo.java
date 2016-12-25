package Other;

import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2016/10/9.
 */
public class FileDemo {
    public static void main(String[] args) throws Exception {
        List<File> files = getFileInCurrentDirectory("D:/BigData", 3);
        int i = 0;
        for (File file : files) {
            System.out.println(file);
            i++;
        }
        System.out.println(i);
    }

    // 获取当前路径下的文件(不包含子目录底下文件)(1:目录,2:文件,其它:所有文件)
    public static List<File> getFileInCurrentDirectory(String path, int type) throws Exception {
        File[] files;
        try {
            File f = new File(path);
            files = f.listFiles();
            if (files == null || files.length < 1) {
                return new ArrayList<File>();
            }
        } catch (Exception e) {
            throw new Exception("文件路径无效!");
        }
        List<File> list;
        if (type == 1) {
            list = new ArrayList<File>();
            for (File file : files) {
                if (file.isDirectory()) {
                    list.add(file);
                }
            }
        } else if (type == 2) {
            list = new ArrayList<File>();
            for (File file : files) {
                if (file.isFile()) {
                    list.add(file);
                }
            }
        } else {
            list = Arrays.asList(files);
        }
        return list;
    }

    // 获取当前路径下的文件[递归获取](1:目录,2:文件,其它:所有文件)
    public static List<File> getFileInDirectory(String path, int type) throws Exception {
        List<File> list = new ArrayList<File>();
        if (type == 1) {
            // 获取当前路径下的目录
            list.addAll(getFileInCurrentDirectory(path, 1));
            // 递归获取当前路径下的目录 底下的目录 开始
            List<File> childList = new ArrayList<File>();
            List<File> child;
            for (File file : list) {
                child = getFileInDirectory(file.getAbsolutePath(), 1);
                if (child != null && child.size() > 0) {
                    childList.addAll(child);
                }
            }
            if (childList.size() > 0) {
                list.addAll(childList);
            }
            // 递归获取当前路径下的目录 底下的目录 结束
        } else if (type == 2) {
            // 获取当前路径下的文件 开始
            File f = new File(path);
            File[] files = f.listFiles();
            List<File> dirs = new ArrayList<File>();
            for (File file : files) {
                if (file.isFile()) {
                    list.add(file);
                } else if (file.isDirectory()) {
                    dirs.add(file);
                }
            }
            // 获取当前路径下的文件 结束
            // 递归获取当前路径下的目录 底下的文件 开始
            if (dirs.size() > 0) {
                List<File> child;
                for (File file : dirs) {
                    child = getFileInDirectory(file.getAbsolutePath(), 2);
                    if (child != null && child.size() > 0) {
                        list.addAll(child);
                    }
                }
            }
            // 递归获取当前路径下的目录 底下的文件 结束
        } else {
            // 获取当前路径下的所有文件 开始
            File f = new File(path);
            File[] files = f.listFiles();
            List<File> dirs = new ArrayList<File>();
            for (File file : files) {
                list.add(file);
                if (file.isDirectory()) {
                    dirs.add(file);
                }
            }
            // 获取当前路径下的所有文件 结束
            // 递归获取当前路径下的目录 底下的所有文件 开始
            if (dirs.size() > 0) {
                List<File> child;
                for (File file : dirs) {
                    child = getFileInDirectory(file.getAbsolutePath(), 3);
                    if (child != null && child.size() > 0) {
                        list.addAll(child);
                    }
                }
            }
            // 递归获取当前路径下的目录 底下的所有文件 结束
        }
        return list;
    }
}
