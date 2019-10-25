package com.github.lybgeek.preview.util;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;

@Slf4j
public class FileUtil extends FileUtils {


  public static final String DOT = ".";
  public static final String SLASH_ONE = "/";
  public static final String SLASH_TWO = "\\";
  public static final String PDF = "pdf";
  public static final String HTML = "html";




  /**
   * 获取含扩展名的文件名（不包含path路径）
   */
  public static String getFileName(String fileName) {

    String name = "";
    if (StringUtils.lastIndexOf(fileName, SLASH_ONE) >= StringUtils
        .lastIndexOf(fileName, SLASH_TWO)) {
      name = StringUtils
          .substring(fileName, StringUtils.lastIndexOf(fileName, SLASH_ONE) + 1,
              fileName.length());

    } else {
      name = StringUtils
          .substring(fileName, StringUtils.lastIndexOf(fileName, SLASH_TWO) + 1,
              fileName.length());
    }
    return StringUtils.trimToEmpty(name);
  }

  /**
   * 获取没有扩展名的文件名
   */
  public static String getWithoutExtension(String fileName) {

    String ext = StringUtils.substring(fileName, 0,
        StringUtils.lastIndexOf(fileName, DOT) == -1 ? fileName.length() : StringUtils
            .lastIndexOf(fileName, DOT));
    return StringUtils.trimToEmpty(ext);
  }

  /**
   * 获取扩展名
   */
  public static String getExtension(String fileName) {

    if (StringUtils.INDEX_NOT_FOUND == StringUtils.indexOf(fileName, DOT)) {
      return StringUtils.EMPTY;
    }
    String ext = StringUtils.substring(fileName,
        StringUtils.lastIndexOf(fileName, DOT) + 1);
    return StringUtils.trimToEmpty(ext);
  }


  /**
   * 获取扩展名
   */
  public static String getExtension(InputStream inputStream) {

    try {
      Tika tika = new Tika();
      String contentType = tika.detect(inputStream);
      MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
      MimeType mime = allTypes.forName(contentType);
      return mime.getExtension().substring(1);
    } catch (Exception e) {
      log.error(e.getMessage(),e);
    }
    return null;
  }

  /**
   * 判断是否同为扩展名
   */
  public static boolean isExtension(String fileName, String ext) {

    return StringUtils.equalsIgnoreCase(getExtension(fileName), ext);
  }

  /**
   * 判断是否存在扩展名
   */
  public static boolean hasExtension(String fileName) {

    return !isExtension(fileName, StringUtils.EMPTY);
  }

  /**
   * 得到正确的扩展名
   */
  public static String trimExtension(String ext) {

    return getExtension(DOT + ext);
  }

  /**
   * 向path中填充扩展名(如果没有或不同的话)
   */
  public static String fillExtension(String fileName, String ext) {

    fileName = replacePath(fileName + DOT);
    ext = trimExtension(ext);
    if (!hasExtension(fileName)) {
      return fileName + getExtension(ext);
    }
    if (!isExtension(fileName, ext)) {
      return getWithoutExtension(fileName) + getExtension(ext);
    }
    return fileName;
  }

  /**
   * 判断是否是文件PATH
   */
  public static boolean isFile(String fileName) {

    return hasExtension(fileName);
  }

  /**
   * 判断是否是文件夹PATH
   */
  public static boolean isFolder(String fileName) {

    return !hasExtension(fileName);
  }

  public static String replacePath(String path) {

    return StringUtils.replace(StringUtils.trimToEmpty(path), SLASH_ONE,
        SLASH_TWO);
  }

  /**
   * 链接PATH前处理
   */
  public static String trimLeftPath(String path) {

    if (isFile(path)) {
      return path;
    }
    path = replacePath(path);
    String top = StringUtils.left(path, 1);
    if (StringUtils.equalsIgnoreCase(SLASH_TWO, top)) {
      return StringUtils.substring(path, 1);
    }
    return path;
  }

  /**
   * 链接PATH后处理
   */
  public static String trimRightPath(String path) {

    if (isFile(path)) {
      return path;
    }
    path = replacePath(path);
    String bottom = StringUtils.right(path, 1);
    if (StringUtils.equalsIgnoreCase(SLASH_TWO, bottom)) {
      return StringUtils.substring(path, 0, path.length() - 2);
    }
    return path + SLASH_TWO;
  }

  /**
   * 链接PATH前后处理，得到准确的链接PATH
   */
  public static String trimPath(String path) {

    path = StringUtils.replace(StringUtils.trimToEmpty(path), SLASH_ONE,
        SLASH_TWO);
    path = trimLeftPath(path);
    path = trimRightPath(path);
    return path;
  }

  /**
   * 通过数组完整链接PATH
   */
  public static String bulidFullPath(String... paths) {

    StringBuffer sb = new StringBuffer();
    for (String path : paths) {
      sb.append(trimPath(path));
    }
    return sb.toString();
  }



  public static void delFile(String filepath) {

    File f = new File(filepath);//定义文件路径
    if (f.exists() && f.isDirectory()) {//判断是文件还是目录
      if (f.listFiles().length != 0) {
        //若有则把文件放进数组，并判断是否有下级目录
        File delFile[] = f.listFiles();
        int i = f.listFiles().length;
        for (int j = 0; j < i; j++) {
          if (delFile[j].isDirectory()) {
            delFile(delFile[j].getAbsolutePath());//递归调用del方法并取得子目录路径
          }
          delFile[j].delete();//删除文件
        }
      }
      f.delete();
    }
  }

  public static void delFileList(List<String> filePaths) {

    for (String filePath : filePaths) {
      delFile(filePath);
    }
  }



  /**
   * 获取路径下的所有文件/文件夹
   *
   * @param directoryPath 需要遍历的文件夹路径
   * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
   */
  public static List<String> getAllFile(String directoryPath, boolean isAddDirectory) {

    List<String> list = new ArrayList<String>();
    File baseFile = new File(directoryPath);
    if (baseFile.isFile() || !baseFile.exists()) {
      return list;
    }
    File[] files = baseFile.listFiles();
    for (File file : files) {
      if (file.isDirectory()) {
        if (isAddDirectory) {
          list.add(file.getAbsolutePath());
        }
        list.addAll(getAllFile(file.getAbsolutePath(), isAddDirectory));
      } else {
        list.add(file.getAbsolutePath());
      }
    }
    return list;
  }



}
