package com.facishare.openapi.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by sunsk on 2017/8/3.
 */
public class fileUntil {
    /**
     * 读取文件内容
     *
     * @param fileName
     *            待读取的完整文件名
     * @return 文件内容
     * @throws IOException
     */
    public static String read(String fileName) throws IOException {
        File f = new File(fileName);
        FileInputStream fs = new FileInputStream(f);
        String result = null;
        byte[] b = new byte[fs.available()];
        fs.read(b);
        fs.close();
        result = new String(b);
        return result;
    }
    /**
     * 当前目录路径
     */
    public static String  getCurrentWorkDir(){
        String result = null;
        if (OSUtils.isWindows()) {
            result = System.getProperty("user.dir") + "\\"
                    + "file"
                    + "\\";
        } else if (OSUtils.isMacOS() || OSUtils.isLinux()) {
            result = System.getProperty("user.dir") + "/"
                    + "file"
                    + "/";
        }
        return result;
    }
}
