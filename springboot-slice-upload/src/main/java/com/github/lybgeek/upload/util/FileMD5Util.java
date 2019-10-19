package com.github.lybgeek.upload.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

/**
 * 计算文件MD5工具类
 */
public class FileMD5Util {

  private final static Logger logger = LoggerFactory.getLogger(FileMD5Util.class);

  public static String getFileMD5(File file) throws FileNotFoundException {

    String value = null;
    FileInputStream in = new FileInputStream(file);
    MappedByteBuffer byteBuffer = null;
    try {
      byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(byteBuffer);
      BigInteger bi = new BigInteger(1, md5.digest());
      value = bi.toString(16);
      if (value.length() < 32) {
        value = "0" + value;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      FileUtil.close(in, byteBuffer);
    }
    return value;
  }

  public static String getFileMD5(MultipartFile file) {

    try {
      byte[] uploadBytes = file.getBytes();
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      byte[] digest = md5.digest(uploadBytes);
      String hashString = new BigInteger(1, digest).toString(16);
      return hashString;
    } catch (IOException e) {
      logger.error("get file md5 error!!!", e);
    } catch (NoSuchAlgorithmException e) {
      logger.error("get file md5 error!!!", e);
    }
    return null;
  }



  public static void main(String[] args) throws Exception {

    long start = System.currentTimeMillis();
    String filePath = "F:\\desktop\\uploads\\461ad6106d8253b94bd00546a4a1a8e4\\pycharm-professional-2019.1.3.exe";
    File file = new File(filePath);
    String md5 = FileMD5Util.getFileMD5(file);
    long end = System.currentTimeMillis();
    System.out.println("cost:" + (end - start) + "ms, md5:" + md5);
  }
}
