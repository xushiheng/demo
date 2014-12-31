package com.xsh.cache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class ImageFileCache {
	private static final String CACHDIR = "ImgCach";
	private static final String WHOLESALE_CONV = ".cach";
	
	//从文件缓存中取得图片
    public Bitmap getImage(final String url) {    
        final String path = getDirectory() + "/" + convertUrlToFileName(url);
        File file = new File(path);
        if (file.exists()) {
            Bitmap bmp = BitmapFactory.decodeFile(path);
            return bmp;
        }
        return null;
    }
	
	//将图片存入文件缓存
	public void saveBitmap(Bitmap bm, String url) {
		if (bm == null) {
            return;
        }
		String filename = convertUrlToFileName(url);
		String dir = getDirectory();
		File dirFile = new File(dir);
		if (!dirFile.exists()){
        	dirFile.mkdirs();
        }
		File file = new File(dir +"/" + filename);
		try {
            file.createNewFile();
            OutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	//把URL变成文件名
    private String convertUrlToFileName(String url) {
        String[] strs = url.split("/");
        return strs[strs.length - 1] + WHOLESALE_CONV;
    }
    //得到存储路径
    private String getDirectory() {
        String dir = getSDPath() + "/" + CACHDIR;
        return dir;
    }
    //得到SD卡路径
    private String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);  //判断SD卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory(); //获得根目录
        }
        if (sdDir != null) {
            return sdDir.toString();
        } else {
            return "";
        }
    } 
}
