package org.example.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

public class SystemUtils {

    public static String getIpAddress() throws Exception {
        return getPublicIp();
    }

    public static String getPublicIp() throws Exception {
        String urlString = "http://checkip.amazonaws.com/";
        URL url = new URL(urlString);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String ip = br.readLine();
            System.out.println("getPublicIp: " + ip);
            return ip;
        }
    }

    public static String getPublicIpBySocket() throws Exception {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("google.com", 80));
            return socket.getLocalAddress().getHostAddress();
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getPublicIp());
    }
}
