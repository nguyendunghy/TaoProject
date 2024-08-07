package org.example.monitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.monitor.config.MonitorConfig;
import org.example.monitor.config.ThreadConfig;
import org.example.script.RunShellScript;
import org.example.telegram.TeleGramMessageSender;
import org.example.utils.Constants;
import org.example.utils.PropertyUtils;
import org.example.utils.SystemUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitorImpl implements Monitor {
    private ObjectMapper objectMapper = new ObjectMapper();
    MonitorConfig monitorConfig = new MonitorConfig();

    public MonitorImpl() {
        try {
            initMonitorConfigFromFile();
            if (monitorConfig.getIp() == null || !monitorConfig.getIp().equals(getIpAddress())) {
                System.out.println("Ip address not correct " + monitorConfig.getIp() + ":" + getIpAddress());
                System.exit(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getIpAddress() throws Exception {
        return SystemUtils.getIpAddress();
    }

    @Override
    public Map<ThreadConfig, String> getInfo() {
        Map<ThreadConfig, String> infoMap = new HashMap<>();
        for (ThreadConfig threadConfig : this.monitorConfig.getThreadConfigs()) {
            String output = RunShellScript.runMonitorScript(threadConfig.getCommand());
            infoMap.put(threadConfig, output);
        }
        return infoMap;
    }

    @Override
    public List<String> findIssue(Map<ThreadConfig, String> infoMap) {
        List<String> listIssue = new ArrayList<>();
        for (ThreadConfig threadConfig : infoMap.keySet()) {
            String info = infoMap.get(threadConfig);
            switch (threadConfig.getOperator()) {
                case "NOT_CONTAIN":
                    if (!info.contains(threadConfig.getCheckedString())) {
                        listIssue.add(threadConfig.getMessage());
                    }
                    break;
                case "CONTAIN":
                    break;
                default:
                    break;
            }
        }
        return listIssue;
    }

    @Override
    public String doAction(List<String> issueList) {
        for (String issue : issueList) {
            String telegramChannelId = PropertyUtils.getProperty("monitor.threads.channel.chat.id");
            TeleGramMessageSender.sendMessage(telegramChannelId, issue);
        }

        return "SUCCESS";
    }

    @Override
    public void run() {
        while (true) {
            try {
                Map<ThreadConfig, String> info = getInfo();
                List<String> listIssue = findIssue(info);
                doAction(listIssue);
                Thread.sleep(this.monitorConfig.getTimeSleep() * 1000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private MonitorConfig initMonitorConfigFromFile() throws Exception {
        String ip = getIpAddress();
        String fileName = String.join("-", ip.split("\\.")) + ".json";
        String directory = Constants.PROJECT_LOCATION + PropertyUtils.getProperty("monitor.config.path");
        String fullFilePath = directory + "/" + fileName;
        File jsonFile = new File(fullFilePath);
        this.monitorConfig = objectMapper.readValue(jsonFile, MonitorConfig.class);
        return this.monitorConfig;
    }
}
