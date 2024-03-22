package com.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class LoadDataEngine {

    private static int TOTAL_FILE = 1024;
    private static int NUM_THREAD = 32;

    private static final Logger logger = LogManager.getLogger(LoadDataEngine.class);

    public static void assignWorkAndStart() {
        int numFilePerThread = TOTAL_FILE / NUM_THREAD;
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < NUM_THREAD; i++) {
            int start = i * numFilePerThread;
            int end = start + numFilePerThread;
            LoadDataRunner runner = new LoadDataRunner(start, end);
            Thread thread = new Thread(runner);
            threadList.add(thread);
        }

        for (Thread thread : threadList) {
            thread.start();
        }
        logger.info("start successfully " + NUM_THREAD + " threads");
    }

    public static void main(String[] args) {
        assignWorkAndStart();
    }
}
