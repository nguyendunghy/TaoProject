package org.example.monitor.config;

import java.util.Objects;

public class ThreadConfig {
    private String command;
    private String location;
    private String operator;
    private String checkedString;
    private String logFilePath;

    private String message;

    public ThreadConfig() {
    }

    public ThreadConfig(String command, String location, String operator, String checkedString, String logFilePath, String message) {
        this.command = command;
        this.location = location;
        this.operator = operator;
        this.checkedString = checkedString;
        this.logFilePath = logFilePath;
        this.message = message;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCheckedString() {
        return checkedString;
    }

    public void setCheckedString(String checkedString) {
        this.checkedString = checkedString;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThreadConfig that = (ThreadConfig) o;
        return Objects.equals(command, that.command) && Objects.equals(location, that.location) && Objects.equals(operator, that.operator) && Objects.equals(checkedString, that.checkedString) && Objects.equals(logFilePath, that.logFilePath) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, location, operator, checkedString, logFilePath, message);
    }
}
