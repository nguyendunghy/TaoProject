package com.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class LoadDataEngine {

    private static int TOTAL_FILE = 1024;
    private static int NUM_THREAD = 32;
    private static final Logger logger = LogManager.getLogger(LoadDataEngine.class);

    public static void assignWork() {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < NUM_THREAD; i++) {
            LoadDataRunner runner = new LoadDataRunner(i);
            Thread thread = new Thread(runner);
            threadList.add(thread);
        }

        for (Thread thread : threadList) {
            thread.start();
        }
    }

    public static void main(String[] args) {
        assignWork();
    }
}
