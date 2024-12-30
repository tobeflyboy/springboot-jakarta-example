package com.nutcracker.example.demo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * file utils
 *
 * @author 胡桃夹子
 * @date 2022/12/22 09:39
 */
public class FileUtils extends cn.hutool.core.io.FileUtil {

    /**
     * inputStream 转 File
     */
    public static File inputStreamToFile(InputStream ins, String name) throws IOException {
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + name);
        if (file.exists()) {
            return file;
        }
        OutputStream os = new FileOutputStream(file);
        int bytesRead;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return file;
    }
}
