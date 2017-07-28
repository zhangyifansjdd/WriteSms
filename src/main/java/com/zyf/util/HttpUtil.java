package com.zyf.util;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangYifan on 2017/7/26.
 */
public class HttpUtil {
    private static final String DEFAULT_APIKEY = "96896c9fb06a4509bc6b4fedd7c17c89";
    private static final String DEFAULT_USERNAME = "cn.chinamobile.messageplus-81:02:D8:AD:3A:4A:00:BB:E9:04:F5:D3:18:99:5B:0A:D4:77:05:1C";
    private static final String DEFAULT_ICCID = "898600781614F0204986";
    private static final String SSL_PROTOCOL = "TLS";

    public static String postByHttp(String url, HashMap<String, String> params) throws Exception {
        String result = "";
        params.put("Apikey", DEFAULT_APIKEY);
        params.put("Username", DEFAULT_USERNAME);
        params.put("Iccid", DEFAULT_ICCID);

        if (url.startsWith("https")) {
            result = new String(HttpUtil.postUrlBytesWithHttps(url, params, "UTF-8"));
        } else {
            result = new String(HttpUtil.postUrlBytes(url, params, "UTF-8"));
        }

        return result;
    }

    protected static byte[] postUrlBytes(String urlStr, Map<String, String> params, String encode) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setReadTimeout(HttpSets.MAX_READ_TIME);
        conn.setConnectTimeout(HttpSets.MAX_CONNECT_TIME);

        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        try {
            if (params != null) {
                OutputStream outputStream = conn.getOutputStream();
                String query = getQueryString(params);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, encode));
                writer.write(query);
                writer.flush();
                writer.close();
                outputStream.close();
            }

            InputStream in = conn.getInputStream();
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            conn.disconnect();
        }

    }

    protected static byte[] postUrlBytesWithHttps(String urlStr, Map<String, String> params, String encode) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        URL url = new URL(urlStr);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        conn.setReadTimeout(HttpSets.MAX_READ_TIME);
        conn.setConnectTimeout(HttpSets.MAX_CONNECT_TIME);

        SSLContext ssl = SSLContext.getInstance(SSL_PROTOCOL);
        ssl.init(null, new TrustManager[]{new AllTrustManager()}, new SecureRandom());
        conn.setSSLSocketFactory(ssl.getSocketFactory());
        conn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(final String hostname, final SSLSession session) {
                return true;
            }
        });

        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        try {
            if (params != null) {
                String query = getQueryString(params);
                OutputStream outputStream = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, encode));
                writer.write(query);
                writer.flush();
                writer.close();
                outputStream.close();
            }

            InputStream in = conn.getInputStream();
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            StringBuilder builder=new StringBuilder();
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            conn.disconnect();
        }

    }

    private static String getQueryString(Map<String, String> params) {
        StringBuilder builder=new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            builder.append(key).append("=").append(value).append("&");
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }


    private static boolean isHttps(String url) {
        return url.indexOf("https://") > -1;
    }

    private static class AllTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
