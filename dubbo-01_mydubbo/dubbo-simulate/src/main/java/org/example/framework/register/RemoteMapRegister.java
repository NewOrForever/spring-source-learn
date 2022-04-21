package org.example.framework.register;

import org.example.framework.URL;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 远程注册中心
 * 为了解决两个进程不能公用的问题：临时将数据写入本机File文件
 */
public class RemoteMapRegister {
    private static Map<String, List<URL>> remoteRegisterMap = new HashMap<>();

    public static void regist(String interfaceName, URL url) {
        remoteRegisterMap.computeIfAbsent(interfaceName, k -> new ArrayList<URL>()).add(url);

        // 将map输出到文件
        saveFile();
    }

    public static List<URL> get(String interfaceName) {
        remoteRegisterMap = getFile();
        return remoteRegisterMap.get(interfaceName);
    }

    public static void saveFile() {
        try {
            // 将map数据输出到文件
            FileOutputStream fileOutputStream = new FileOutputStream("./temp.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(remoteRegisterMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, List<URL>> getFile() {
        // 从文件读取数据
        try {
            FileInputStream fileInputStream = new FileInputStream("./temp.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Map<String, List<URL>>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
