package org.example;

import org.example.utils.SystemUtils;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Public IP address : " + SystemUtils.getPublicIp());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("IP socket : " + SystemUtils.getPublicIpBySocket());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
