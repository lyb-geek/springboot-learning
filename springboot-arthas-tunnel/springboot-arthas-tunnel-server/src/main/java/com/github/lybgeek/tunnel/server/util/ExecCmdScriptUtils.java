package com.github.lybgeek.tunnel.server.util;


import java.io.*;


public final class ExecCmdScriptUtils {

    private ExecCmdScriptUtils(){}

    public static void runbat(String serviceName,String batName) {
        try {
            long startTime = System.currentTimeMillis();
            Process ps = getProcessInfo(batName);
            long endTime = System.currentTimeMillis();
            String content = String.format(">>>>>>>>>>>>>>>>>>>> %s started ,cost 【%d】ms" ,serviceName,(endTime - startTime));
            System.out.println(content);
            ps.waitFor();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static Process getProcessInfo(String batName) throws IOException {
        Process ps = Runtime.getRuntime().exec(batName);
//        InputStream in = ps.getInputStream();
//        int c;
//        while ((c = in.read()) != -1) {
//            System.out.print(c);// 如果你不需要看输出，这行可以注销掉
//        }
//
//        in.close();
        return ps;
    }


}
