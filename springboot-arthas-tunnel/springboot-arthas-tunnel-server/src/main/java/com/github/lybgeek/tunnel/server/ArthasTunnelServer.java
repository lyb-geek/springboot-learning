package com.github.lybgeek.tunnel.server;


import com.github.lybgeek.tunnel.server.util.ExecCmdScriptUtils;

public class ArthasTunnelServer {

    public static void main(String[] args) throws Exception{
      ExecCmdScriptUtils.runbat("ArthasTunnelServer",arthasTunnelServerCmdPath());
    }

    private static String getProjectPath() {
        String basePath = ArthasTunnelServer.class.getResource("").getPath();
        return basePath.substring(0, basePath.indexOf("/target"));
    }



    public static String arthasTunnelServerCmdPath() {
        return getProjectPath() + "/arthas-tunnel-server/bin/startup.bat";
    }
}
