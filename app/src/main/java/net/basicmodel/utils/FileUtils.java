package net.basicmodel.utils;

import android.content.Intent;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class FileUtils {

    public static void saveFile(String content) {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "a.testupdate.txt";
        File file = new File(filePath + File.separator + fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readrFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return "";
        } else {
            try {
                FileReader reader = new FileReader(filePath);
                BufferedReader r = new BufferedReader(reader);
                return r.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
