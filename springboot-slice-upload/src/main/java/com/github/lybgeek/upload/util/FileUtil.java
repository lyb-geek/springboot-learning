package com.github.lybgeek.upload.util;


import com.github.lybgeek.common.exception.BizException;
import com.github.lybgeek.common.util.SystemUtil;
import com.github.lybgeek.upload.constant.FileConstant;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileUtil extends FileUtils {

  public static final String PATH_HEAD = "/vagrant/";
  public static final String USER_HEAD = "/data/User";
  public static final String DOT = ".";
  public static final String SLASH_ONE = "/";
  public static final String SLASH_TWO = "\\";
  public static final String HOME = "";
  private static String uploadWindowRoot;



  public static void moveFiles(String oldPath, String newPath) throws IOException {

    String[] filePaths = new File(oldPath).list();

    if (filePaths != null && filePaths.length > 0) {
      if (!new File(newPath).exists()) {
        new File(newPath).mkdirs();
      }

      for (int i = 0; i < filePaths.length; i++) {
        if (new File(oldPath + File.separator + filePaths[i]).isDirectory()) {
          moveFiles(oldPath + File.separator + filePaths[i],
              newPath + File.separator + filePaths[i]);
        } else if (new File(oldPath + File.separator + filePaths[i]).isFile()) {
          copyFile(oldPath + File.separator + filePaths[i],
              newPath + File.separator + filePaths[i]);
          new File(oldPath + File.separator + filePaths[i])
              .renameTo(new File(newPath + File.separator + filePaths[i]));
        }
      }
    }
  }

  public static void copyFile(String oldPath, String newPath) {

    try {
      File oldFile = new File(oldPath);
      File file = new File(newPath);
      FileInputStream in = new FileInputStream(oldFile);
      FileOutputStream out = new FileOutputStream(file);
      byte[] buffer = new byte[2097152];

      while ((in.read(buffer)) != -1) {
        out.write(buffer);
      }
    } catch (IOException e) {
      throw new BizException("复制文件错误", e);
    }
  }

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
        StringUtils.lastIndexOf(fileName, DOT) == -1 ? fileName.length() : StringUtils.lastIndexOf(fileName, DOT));
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

  /**
   * 去除首尾斜杠 path
   */
  public static String withoutHeadAndTailDiagonal(String path) {

    int start = 0;
    int end = 0;
    boolean existHeadDiagonal = path.startsWith(FileConstant.FILE_SEPARATORCHAR);
    boolean existTailDiagonal = path.endsWith(FileConstant.FILE_SEPARATORCHAR);
    if (existHeadDiagonal && existTailDiagonal) {
      start = StringUtils.indexOf(path, FileConstant.FILE_SEPARATORCHAR, 0) + 1;
      end = StringUtils.lastIndexOf(path, FileConstant.FILE_SEPARATORCHAR);
      return StringUtils.substring(path, start, end);
    } else if (existHeadDiagonal && !existTailDiagonal) {
      start = StringUtils.indexOf(path, FileConstant.FILE_SEPARATORCHAR, 0) + 1;
      return StringUtils.substring(path, start);
    } else if (!existHeadDiagonal && existTailDiagonal) {
      end = StringUtils.lastIndexOf(path, FileConstant.FILE_SEPARATORCHAR);
      return StringUtils.substring(path, 0, end);
    }
    return path;
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

  public static List<String> splitPath(String filePath) {

    List<String> pathList = new ArrayList<>();
    if (filePath.contains(FileConstant.FILE_SEPARATORCHAR)) {
      String[] arrPath = StringUtils.split(filePath, FileConstant.FILE_SEPARATORCHAR);
      StringBuilder sbPath = new StringBuilder();
      for (int i = 0; i < arrPath.length - 1; i++) {
        sbPath.append(FileConstant.FILE_SEPARATORCHAR).append(arrPath[i]);
        pathList.add(sbPath.toString());
      }

    }

    return pathList;
  }

  public static void main(String[] args) throws Exception {

  }

  public static String getParentPath(String filePath) {

    if (StringUtils.lastIndexOf(filePath, SLASH_ONE) == 0) {
      return SLASH_ONE;
    } else {
      String path = StringUtils.substring(filePath, 0,
          StringUtils.lastIndexOf(filePath, SLASH_ONE));
      return path;
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



  public static void downloadFile(String name, String path, HttpServletRequest request,
      HttpServletResponse response) throws FileNotFoundException {
    //获取所需文件资源
    if (SystemUtil.isWinOs()) {
      path = uploadWindowRoot + path;
    }
    File downloadFile = new File(path);
    String fileName = name;
    if (StringUtils.isBlank(fileName)) {
      fileName = downloadFile.getName();
    }
    String headerValue = String.format("attachment; filename=\"%s\"", fileName);
    response.addHeader(HttpHeaders.CONTENT_DISPOSITION, headerValue);
    response.addHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
    //获取文件大小
    long downloadSize = downloadFile.length();
    long fromPos = 0, toPos = 0;
    if (request.getHeader("Range") == null) {
      response.addHeader(HttpHeaders.CONTENT_LENGTH, downloadSize + "");
    } else {
      log.info("range:{}", response.getHeader("Range"));
      //如果为持续下载
      response.setStatus(HttpStatus.PARTIAL_CONTENT.value());
      String range = request.getHeader("Range");
      String bytes = range.replaceAll("bytes=", "");
      String[] ary = bytes.split("-");
      fromPos = Long.parseLong(ary[0]);
      log.info("fronPos:{}", fromPos);
      if (ary.length == 2) {
        toPos = Long.parseLong(ary[1]);
      }
      int size;
      if (toPos > fromPos) {
        size = (int) (toPos - fromPos);
      } else {
        size = (int) (downloadSize - fromPos);
      }
      response.addHeader(HttpHeaders.CONTENT_LENGTH, size + "");
      downloadSize = size;
    }

    try (RandomAccessFile in = new RandomAccessFile(downloadFile, "rw");
        OutputStream out = response.getOutputStream()) {
      if (fromPos > 0) {
        in.seek(fromPos);
      }
      int bufLen = (int) (downloadSize < 2048 ? downloadSize : 2048);
      byte[] buffer = new byte[bufLen];
      int num;
      //当前写入客户端大小
      int count = 0;
      while ((num = in.read(buffer)) != -1) {
        out.write(buffer, 0, num);
        count += num;
        if (downloadSize - count < bufLen) {
          bufLen = (int) (downloadSize - count);
          if (bufLen == 0) {
            break;
          }
          buffer = new byte[bufLen];
        }
      }
      response.flushBuffer();
    } catch (IOException e) {
      log.error("download error:" + e.getMessage(), e);
      throw new BizException("文件下载失败", 406);
    }
  }


  /**
   * 不确定是否能准确，待测试 判断文本文件的字符集，文件开头三个字节表明编码格式。 <a href="http://blog.163.com/wf_shunqiziran/blog/static/176307209201258102217810/">参考的博客地址</a>
   */
  public static String charset(String path) {

    String charset = "GBK";
    byte[] first3Bytes = new byte[3];
    try {
      boolean checked = false;
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
      bis.mark(0); // 读者注： bis.mark(0);修改为 bis.mark(100);我用过这段代码，需要修改上面标出的地方。
      // Wagsn注：不过暂时使用正常，遂不改之
      int read = bis.read(first3Bytes, 0, 3);
      if (read == -1) {
        bis.close();
        return charset; // 文件编码为 ANSI
      } else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
        charset = "UTF-16LE"; // 文件编码为 Unicode
        checked = true;
      } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
        charset = "UTF-16BE"; // 文件编码为 Unicode big endian
        checked = true;
      } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
          && first3Bytes[2] == (byte) 0xBF) {
        charset = "UTF-8"; // 文件编码为 UTF-8
        checked = true;
      }
      bis.reset();
      if (!checked) {
        while ((read = bis.read()) != -1) {
          if (read >= 0xF0) {
            break;
          }
          if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
          {
            break;
          }
          if (0xC0 <= read && read <= 0xDF) {
            read = bis.read();
            if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
            // (0x80 - 0xBF),也可能在GB编码内
            {
              continue;
            } else {
              break;
            }
          } else if (0xE0 <= read && read <= 0xEF) { // 也有可能出错，但是几率较小
            read = bis.read();
            if (0x80 <= read && read <= 0xBF) {
              read = bis.read();
              if (0x80 <= read && read <= 0xBF) {
                charset = "UTF-8";
                break;
              } else {
                break;
              }
            } else {
              break;
            }
          }
        }
      }
      bis.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return charset;
  }

  public static boolean isBase64(String str) {

    String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
    return Pattern.matches(base64Pattern, str);
  }

  @Value("${upload.window.root}")
  public void setUploadWindowRoot(String windowRoot) {

    uploadWindowRoot = windowRoot;
  }
}
