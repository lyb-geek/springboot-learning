package com.github.lybgeek.desensitization.util;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.jar.JarFile;

/**
 * 敏感词处理工具 - DFA算法实现
 */
@Slf4j
public class SensitiveWordUtils {


    private static String SENSITIVE_WORD_PATH;

    public static final String DEFAULT_SENSITIVE_DIR = "sensitive/";
    
    /**
     * 敏感词匹配规则
     */
    public static final int MinMatchTYpe = 1;      //最小匹配规则，如：敏感词库["中国","中国人"]，语句："我是中国人"，匹配结果：我是[中国]人
    public static final int MaxMatchType = 2;      //最大匹配规则，如：敏感词库["中国","中国人"]，语句："我是中国人"，匹配结果：我是[中国人]

    /**
     * 敏感词集合
     */
    @SuppressWarnings("rawtypes")
    public static HashMap sensitiveWordMap;

    static {
        initDefault();
    }


    @SneakyThrows
    private static void initDefault(){
        URL url = SensitiveWordUtils.class.getClassLoader().getResource(DEFAULT_SENSITIVE_DIR);
        init(getPath(url));
    }

    @SneakyThrows
    private static String getPath(URL url){
        String path;
        URLConnection connection = url.openConnection();
        if(connection instanceof JarURLConnection) {
            JarFile jarFile = ((JarURLConnection) connection).getJarFile();
            loadSenstiveWordSet(jarFile);
            return null;
        } else {
            path = url.getPath();
        }
        return path;
    }

    private static void loadSenstiveWordSet(JarFile jarFile) {
        Set<String> sensitiveWordSet = new HashSet<>();
        jarFile.stream().filter(jarEntry -> jarEntry.getName().startsWith(DEFAULT_SENSITIVE_DIR)
                && !DEFAULT_SENSITIVE_DIR.equals(jarEntry.getName())).
                forEach(jarEntry -> {
                   try {
                       log.info("正在初始化系统默认敏感字库....{}",jarEntry.getName());
                       InputStream inputStream = jarFile.getInputStream(jarEntry);
                       loadSenitiveWordSet(sensitiveWordSet,inputStream);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
       });
        log.info("加载{}个默认敏感字",sensitiveWordSet.size());
        initSensitiveWordMap(sensitiveWordSet);
    }

    /**
     * 初始化敏感词库，构建DFA算法模型
     * @param filePath 字库路径
     */
    public static void init(String filePath){
        if(StringUtils.isBlank(filePath)){
            return;
        }
        SENSITIVE_WORD_PATH = filePath;
        Set<String> sensitiveWordSet = new HashSet<>();

        // 读取指定路径下的敏感字库
        try {
            log.info("正在初始化敏感字库....{}",SENSITIVE_WORD_PATH);
            File wordFileDir = new File(SENSITIVE_WORD_PATH);
            initSensitiveWordSet(sensitiveWordSet, wordFileDir);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("初始化敏感字库失败，未加载字库....");
        }
        log.info("加载{}个敏感字",sensitiveWordSet.size());
        initSensitiveWordMap(sensitiveWordSet);
    }


    /**
     * 初始化敏感词库，构建DFA算法模型
     * @param filePath 字库路径
     */
    public static void loadByClassPathFile(String filePath){
        SENSITIVE_WORD_PATH = filePath;
        Set<String> sensitiveWordSet = new HashSet<>();

        // 读取指定路径下的敏感字库
        try {
            log.info("正在初始化敏感字库....{}",SENSITIVE_WORD_PATH);
            URL url = SensitiveWordUtils.class.getClassLoader().getResource(SENSITIVE_WORD_PATH);
            File wordFile = new File(url.toURI());
            loadSenitiveWordSet(sensitiveWordSet, wordFile);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            log.error("初始化敏感字库失败，未加载字库....");
        }
        log.info("加载{}个敏感字",sensitiveWordSet.size());
        initSensitiveWordMap(sensitiveWordSet);
    }

    /**
     * 词库初始化
     * @param sensitiveWordSet
     * @param wordFileDir
     * @throws IOException
     */
    private static void initSensitiveWordSet(Set<String> sensitiveWordSet, File wordFileDir) throws IOException {
        if(wordFileDir.isDirectory()){
            File[] wordFiles = wordFileDir.listFiles();
            for (File wordFile : wordFiles) {
                loadSenitiveWordSet(sensitiveWordSet, wordFile);
            }
        }

    }

    /**
     * 加载词库
     * @param sensitiveWordSet
     * @param wordFile
     * @throws IOException
     */
    private static void loadSenitiveWordSet(Set<String> sensitiveWordSet, File wordFile) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(wordFile), "utf-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            sensitiveWordSet.add(line);
            log.trace("加载敏感字{}", line);
        }
        reader.close();
    }

    private static void loadSenitiveWordSet(Set<String> sensitiveWordSet, InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            sensitiveWordSet.add(line);
            log.trace("加载敏感字{}", line);
        }
        reader.close();
    }


    /**
     * 初始化敏感词库，构建DFA算法模型
     *
     * @param sensitiveWordSet 敏感词库
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void initSensitiveWordMap(Set<String> sensitiveWordSet) {
        //初始化敏感词容器，减少扩容操作
        if(MapUtil.isEmpty(sensitiveWordMap)) {
            sensitiveWordMap = new HashMap(sensitiveWordSet.size());
        }
        String key;
        Map nowMap;
        Map<String, String> newWorMap;
        //迭代sensitiveWordSet
        Iterator<String> iterator = sensitiveWordSet.iterator();
        while (iterator.hasNext()) {
            //关键字
            key = iterator.next();
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                //转换成char型
                char keyChar = key.charAt(i);
                //库中获取关键字
                Object wordMap = nowMap.get(keyChar);
                //如果存在该key，直接赋值，用于下一个循环获取
                if (wordMap != null) {
                    nowMap = (Map) wordMap;
                } else {
                    //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<>();
                    //不是最后一个
                    newWorMap.put("isEnd", "0");
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    //最后一个
                    nowMap.put("isEnd", "1");
                }
            }
        }
    }

    /**
     * 判断文字是否包含敏感字符
     *
     * @param txt       文字
     * @param matchType 匹配规则 1：最小匹配规则，2：最大匹配规则
     * @return 若包含返回true，否则返回false
     */
    public static boolean contains(String txt, int matchType) {
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            int matchFlag = checkSensitiveWord(txt, i, matchType); //判断是否包含敏感字符
            if (matchFlag > 0) {    //大于0存在，返回true
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 判断文字是否包含敏感字符
     *
     * @param txt 文字
     * @return 若包含返回true，否则返回false
     */
    public static boolean contains(String txt) {
        return contains(txt, MaxMatchType);
    }

    /**
     * 获取文字中的敏感词
     *
     * @param txt       文字
     * @param matchType 匹配规则 1：最小匹配规则，2：最大匹配规则
     * @return
     */
    public static Set<String> getSensitiveWord(String txt, int matchType) {
        Set<String> sensitiveWordList = new HashSet<>();

        for (int i = 0; i < txt.length(); i++) {
            //判断是否包含敏感字符
            int length = checkSensitiveWord(txt, i, matchType);
            if (length > 0) {//存在,加入list中
                sensitiveWordList.add(txt.substring(i, i + length));
                i = i + length - 1;//减1的原因，是因为for会自增
            }
        }

        return sensitiveWordList;
    }

    /**
     * 获取文字中的敏感词
     *
     * @param txt 文字
     * @return
     */
    public static Set<String> getSensitiveWord(String txt) {
        return getSensitiveWord(txt, MaxMatchType);
    }

    /**
     * 替换敏感字字符
     *
     * @param txt         文本
     * @param replaceChar 替换的字符，匹配的敏感词以字符逐个替换，如 语句：我爱中国人 敏感词：中国人，替换字符：*， 替换结果：我爱***
     * @param matchType   敏感词匹配规则
     * @return
     */
    public static String replaceSensitiveWord(String txt, char replaceChar, int matchType) {
        String resultTxt = txt;
        //获取所有的敏感词
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word;
        String replaceString;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }

    /**
     * 替换敏感字字符
     *
     * @param txt         文本
     * @param replaceChar 替换的字符，匹配的敏感词以字符逐个替换，如 语句：我爱中国人 敏感词：中国人，替换字符：*， 替换结果：我爱***
     * @return
     */
    public static String replaceSensitiveWord(String txt, char replaceChar) {
        return replaceSensitiveWord(txt, replaceChar, MaxMatchType);
    }

    /**
     * 替换敏感字字符
     *
     * @param txt        文本
     * @param replaceStr 替换的字符串，匹配的敏感词以字符逐个替换，如 语句：我爱中国人 敏感词：中国人，替换字符串：[屏蔽]，替换结果：我爱[屏蔽]
     * @param matchType  敏感词匹配规则
     * @return
     */
    public static String replaceSensitiveWord(String txt, String replaceStr, int matchType) {
        String resultTxt = txt;
        //获取所有的敏感词
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word;
        while (iterator.hasNext()) {
            word = iterator.next();
            resultTxt = resultTxt.replaceAll(word, replaceStr);
        }

        return resultTxt;
    }

    /**
     * 替换敏感字字符
     *
     * @param txt        文本
     * @param replaceStr 替换的字符串，匹配的敏感词以字符逐个替换，如 语句：我爱中国人 敏感词：中国人，替换字符串：[屏蔽]，替换结果：我爱[屏蔽]
     * @return
     */
    public static String replaceSensitiveWord(String txt, String replaceStr) {
        return replaceSensitiveWord(txt, replaceStr, MaxMatchType);
    }

    /**
     * 获取替换字符串
     *
     * @param replaceChar
     * @param length
     * @return
     */
    private static String getReplaceChars(char replaceChar, int length) {
        String resultReplace = String.valueOf(replaceChar);
        for (int i = 1; i < length; i++) {
            resultReplace += replaceChar;
        }

        return resultReplace;
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：<br>
     *
     * @param txt
     * @param beginIndex
     * @param matchType
     * @return 如果存在，则返回敏感词字符的长度，不存在返回0
     */
    @SuppressWarnings("rawtypes")
    private static int checkSensitiveWord(String txt, int beginIndex, int matchType) {
        //敏感词结束标识位：用于敏感词只有1位的情况
        boolean flag = false;
        //匹配标识数默认为0
        int matchFlag = 0;
        char word;
        Map nowMap = sensitiveWordMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);
            //获取指定key
            nowMap = (Map) nowMap.get(word);
            if (nowMap != null) {//存在，则判断是否为最后一个
                //找到相应key，匹配标识+1
                matchFlag++;
                //如果为最后一个匹配规则,结束循环，返回匹配标识数
                if ("1".equals(nowMap.get("isEnd"))) {
                    //结束标志位为true
                    flag = true;
                    //最小规则，直接返回,最大规则还需继续查找
                    if (MinMatchTYpe == matchType) {
                        break;
                    }
                }
            } else {//不存在，直接返回
                break;
            }
        }
        if (matchFlag < 2 || !flag) {//长度必须大于等于1，为词
            matchFlag = 0;
        }
        return matchFlag;
    }

    public static void main(String[] args) {
        System.out.println("敏感词的数量：" + SensitiveWordUtils.sensitiveWordMap.size());
        String string = "太多的伤感情怀也许只局限于饲养基地 荧幕中的情节。"
                + "然后我们的扮演的角色就是跟随着主人公的喜红客联盟 怒哀乐而过于牵强的把自己的情感也附加于银幕情节中，然后感动就流泪，"
                + "难过就躺在某一个人的怀里尽情的阐述心扉或者手机卡复制器一个贱人一杯红酒一部电影在夜 深人静的晚上，关上电话静静的发呆着。";
        System.out.println("待检测语句字数：" + string.length());

        //是否含有关键字
        boolean result = SensitiveWordUtils.contains(string);
        System.out.println(result);
        result = SensitiveWordUtils.contains(string, SensitiveWordUtils.MinMatchTYpe);
        System.out.println(result);

        //获取语句中的敏感词
        Set<String> set = SensitiveWordUtils.getSensitiveWord(string);
        System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
        set = SensitiveWordUtils.getSensitiveWord(string, SensitiveWordUtils.MinMatchTYpe);
        System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);

        //替换语句中的敏感词
        String filterStr = SensitiveWordUtils.replaceSensitiveWord(string, '*');
        System.out.println(filterStr);
        filterStr = SensitiveWordUtils.replaceSensitiveWord(string, '*', SensitiveWordUtils.MinMatchTYpe);
        System.out.println(filterStr);

        String filterStr2 = SensitiveWordUtils.replaceSensitiveWord(string, "[*敏感词*]");
        System.out.println(filterStr2);
        filterStr2 = SensitiveWordUtils.replaceSensitiveWord(string, "[*敏感词*]", SensitiveWordUtils.MinMatchTYpe);
        System.out.println(filterStr2);
    }

}

