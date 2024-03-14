package org.example.monitor;

public interface Monitor {

    //Identify the ip address
    String getIpAddress() throws Exception;

    //Run the command to get the information about the thread
    String getInfo();

    //Process the output of the command to indentify whether it has issue
    String findIssue(String info);

    //Do action such as notify to telegram if there's any issues.
    String doAction(String issue);


}
