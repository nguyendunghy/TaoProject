package org.example.monitor;

import org.example.monitor.config.ThreadConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Monitor {

    //Identify the ip address
    String getIpAddress() throws Exception;

    //Run the command to get the information about the thread
    Map<ThreadConfig,String> getInfo();

    //Process the output of the command to indentify whether it has issue
    List<String> findIssue(Map<ThreadConfig, String> infoMap);

    //Do action such as notify to telegram if there's any issues.
    String doAction(List<String> issue);

    void run();


}
