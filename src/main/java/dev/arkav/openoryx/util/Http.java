package dev.arkav.openoryx.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

@SuppressWarnings("ALL")
public class Http {
    public static String get(String uri, int timeout) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        return get(conn, timeout);
    }

    /**
     * Gets a specified url with a proxy
     * @param uri url to get
     * @param timeout in ms
     * @param proxy Proxy to use
     * @return Page response
     * @throws Exception
     */
    public static String proxiedGet(String uri, int timeout, Proxy proxy) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);

        return get(conn, timeout);
    }

    private static String get(HttpURLConnection conn, int timeout) throws IOException {
        StringBuilder result = new StringBuilder();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(timeout);
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        conn.disconnect();
        return result.toString();
    }

    /**
     * Gets a specified url
     * @param uri url to get
     * @return Page response
     * @throws Exception
     */
    public static String get(String uri) throws IOException {
        return get(uri, 5000);
    }

    public static InputStream getStream(String uri, int timeout) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        return conn.getInputStream();
    }
}
