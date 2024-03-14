package org.example.monitor.config;

import java.util.List;

public class MonitorConfig {
    private String ip;
    private List<ThreadConfig> threadConfigs;

    private Long timeSleep;

    public MonitorConfig() {
    }

    public MonitorConfig(String ip, List<ThreadConfig> threadConfigs, Long timeSleep) {
        this.ip = ip;
        this.threadConfigs = threadConfigs;
        this.timeSleep = timeSleep;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<ThreadConfig> getThreadConfigs() {
        return threadConfigs;
    }

    public void setThreadConfigs(List<ThreadConfig> threadConfigs) {
        this.threadConfigs = threadConfigs;
    }

    public Long getTimeSleep() {
        return timeSleep;
    }

    public void setTimeSleep(Long timeSleep) {
        this.timeSleep = timeSleep;
    }
}
