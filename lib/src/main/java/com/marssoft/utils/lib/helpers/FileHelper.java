package com.marssoft.utils.lib.helpers;

import android.graphics.Bitmap;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Alexey on 08.10.2015.
 */
public class FileHelper {
    public static boolean saveBitmapAndStoreUriToDb(String path, Bitmap bitmap) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
