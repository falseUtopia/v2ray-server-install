package com.falseu.autoinstallproxy;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.dialect.PropsUtil;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

public class AutoInstallProxy {

    public static void main(String[] args) {

        if (args.length == 2 && "release".equals(args[0])) {
            AutoInstallProxy.release(args[1]);
            return;

        }

        String configJson = "{\"log\":{\"loglevel\":\"warning\"},\"inbounds\":[{\"port\":16823,\"protocol\":\"vmess\",\"settings\":{\"clients\":[{\"id\":\"c033ee75-1b3f-45a6-a473-8f28dbf4aa27\",\"alterId\":0}]}}],\"outbounds\":[{\"protocol\":\"freedom\",\"settings\":{}}]}";
        // 下载zip
        // 解压
        // 替换json
        String url = "https://mirror.ghproxy.com/https://github.com/v2fly/v2ray-core/releases/download/v5.13.0/v2ray-windows-64.zip";
        String desktopPath = FileUtil.getUserHomePath() + "/Desktop";
        String fileName = "proxy-v2ray-windows-64-v5.13.0.zip";
        String fullFileName = desktopPath + "/" + fileName;
        String unZipFilePath = desktopPath + "/" + FileNameUtil.getPrefix(fullFileName);

        HttpUtil.downloadFile(url, FileUtil.file(fileName),

                new StreamProgress() {
                    @Override
                    public void start() {
                        System.out.println("开始下载。。。。");
                    }


                    @Override
                    public void progress(long total, long progressSize) {
                        System.out.printf("\r下载中: %s/%s", FileUtil.readableFileSize(progressSize), FileUtil.readableFileSize(total));
                    }

                    @Override
                    public void finish() {
                        System.out.println("\n下载完成！");
                    }
                });

        ZipUtil.unzip(fileName, unZipFilePath);
        FileUtil.del(fileName);

        String jsonFile = unZipFilePath + "/config.json";
        JSONObject jsonObject = JSONUtil.parseObj(configJson);

        JSONArray jsonArray = jsonObject.getJSONArray("inbounds").getJSONObject(0)
                .getJSONObject("settings")
                .getJSONArray("clients");

        Props props;
        try {
            props = PropsUtil.get("ip-uuid.txt");
        } catch (Exception e) {
            System.out.println("可以在当前目录指定ip-uuid.txt文件以指定关联");
            props = new Props();
        }
        String localhostStr = NetUtil.getLocalhostStr();
        if (props.containsKey(localhostStr)) {

            String uuid = props.getStr(localhostStr);
            jsonArray.getJSONObject(0).set("id", uuid);
            System.out.printf("从配置中获取到uuid %s: %s%n", localhostStr, uuid);
            FileUtil.writeString(jsonObject.toStringPretty(), jsonFile, "UTF-8");

        } else {

            String string = UUID.randomUUID().toString();
            // 可做成上报
            System.out.printf("需要记下来 %s: %s%n", localhostStr, string);
            jsonArray.getJSONObject(0).set("id", string);

            FileUtil.writeString(jsonObject.toStringPretty(), jsonFile, "UTF-8");
        }

        String startCmd = "%~dp0\\v2ray.exe run";
        FileUtil.writeString(startCmd, unZipFilePath + "/start.bat", "UTF-8");

    }

    /**
     * 此方法,通过脚本调用
     */
    private static void release(String outPath) {

        String[] fileNameArr = {
                "com",
                "hutool-all-5.8.28.jar",
                "ip-uuid.txt",
                "start.bat"};

        File[] fileArr = Arrays.stream(fileNameArr)
                .map(File::new)
                .distinct()
                .toArray(File[]::new);

        ZipUtil.zip(new File(outPath + "/v2ray-proxy-install.zip"), true, fileArr);
        FileUtil.del("com");

    }

}
