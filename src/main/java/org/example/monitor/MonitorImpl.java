package org.example.monitor;

import org.example.script.RunShellScript;
import org.example.utils.PropertyUtils;
import org.example.utils.SystemUtils;

public class MonitorImpl implements Monitor
{
    @Override
    public String getIpAddress() throws Exception {
        return SystemUtils.getIpAddress();
    }

    @Override
    public String getInfo() {
        String command = PropertyUtils.getProperty("monitor.get.info.command");
        System.out.println(command);
        String linuxOutput = RunShellScript.runMonitorScript(command);
        return linuxOutput;
    }

    @Override
    public String findIssue(String info) {
        String[] outputLines = info.split("\n");
        for(String line : outputLines){
            String[] elementArray = line.split(" ");
        }

        return null;
    }

    @Override
    public String doAction(String issue) {
        return null;
    }
}
