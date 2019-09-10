package common.utils;

import common.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class HttpUtils {
    public static String get(String url) {
        return get(url, "访问接口");
    }

    public static String get(String url, String season) {
        String result = null;
        java.net.URL uri = null;
        HttpURLConnection connection = null;
        try {
            uri = new java.net.URL(url);
            System.out.println(season + ":" + url);
            // 打开连接
            connection = (HttpURLConnection) uri
                    .openConnection();
            connection.setRequestProperty("referer", CommonUtils.getHost);
            connection.setRequestProperty("cookie", CommonUtils.getHost);
            connection.setConnectTimeout(30 * 1000);
            connection.setReadTimeout(60 * 1000);
            connection.setRequestMethod("GET");
//            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (android 6.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/22.0.0.0 Safari/537.36");
            // Post 请求不能使用缓存
            connection.setUseCaches(false);
            int code = connection.getResponseCode();

            InputStream inputStream = connection.getInputStream();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] data = new byte[8 * 1024];
            int len;
            while ((len = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
            result = outputStream.toString("utf-8");
            outputStream.close();
        } catch (Exception pE) {
            pE.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return result;

    }
}
