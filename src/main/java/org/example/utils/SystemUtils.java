package org.example.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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

    public static void genIndex(){
        HashSet<String> set = new HashSet<>();
        List<List<Integer>> combination = new ArrayList<>();
        List<Integer> listNumber = Arrays.asList(0,1,2,3,4,5,6,7,8,9);
        for(int i =0; i< listNumber.size(); i++){
            for(int j=i+1; j< listNumber.size();j++){
                String key = listNumber.get(i)>=listNumber.get(j)? listNumber.get(i)+ "_"+listNumber.get(j):listNumber.get(j)+ "_"+listNumber.get(i);
                if(!set.contains(key)){
                    combination.add(Arrays.asList(listNumber.get(i),listNumber.get(j)));
                }
            }
        }

        System.out.println(combination);
        System.out.println(combination.size());
        set = new HashSet<>();
        List<List<List<Integer>>> finaly_list = new ArrayList<>();
        for(List<Integer> a : combination){
            for(List<Integer> b : combination){
                if(b.contains(a.get(0)) || b.contains(a.get(1))){
                    continue;
                }
                String key1 = a.get(0) + "_" + a.get(1) + "_" + b.get(0);
                String key2 = a.get(0) + "_" + a.get(1) + "_" + b.get(1);
                String key3 = a.get(0) + "_" + b.get(0) + "_" + b.get(1);
                String key4 = a.get(1) + "_" + b.get(0) + "_" + b.get(1);

                List<String> keyList = Arrays.asList(key4,key3,key2,key1);
                boolean exists = false;
                for(String key: keyList){
                    if (set.contains(key)) {
                        exists = true;
                        break;
                    }
                }
                if(!exists){
                    finaly_list.add(Arrays.asList(a,b));
                    set.addAll(keyList);
                }
            }
        }
        System.out.println(finaly_list);
        System.out.println(finaly_list.size());

    }

    public static void main(String[] args) throws Exception {
        genIndex();
    }
}
