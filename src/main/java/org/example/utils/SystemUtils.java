package org.example.utils;

import java.net.InetAddress;

public class SystemUtils {

    public static String getIpAddress() throws Exception {
        InetAddress inetAddress = InetAddress.getLocalHost();

        String ipAddress = inetAddress.getHostAddress();

        System.out.println("IP Address of the running server: " + ipAddress);
        return ipAddress;
    }
}
