package org.example.script;

import org.example.utils.PropertyUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RunShellScript {
    public static String getPriceLinuxCommand(String scriptPath, String subnetId, String hotkey) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath, subnetId, hotkey, folderOfScript());
        return runScript(processBuilder);
    }

    public static String register(String scriptPath, String subnetId, String threshold, String hotkey) throws Exception{
        ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath, subnetId, threshold, hotkey, folderOfScript());
        return runScript(processBuilder);
    }

    public static String checkAllBalance() throws Exception {
        String scriptPath = PropertyUtils.checkBalanceScriptPath();
        ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath);
        return runScript(processBuilder);
    }

    public static String runMonitorScript(String command){
        List<String> commands = new ArrayList<>();
        commands.add("/bin/sh");
        commands.add("-c");
        commands.add(command);

        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        return runScript(processBuilder);
    }

    public static String getPrice(String netId, String coldKey, String hotKey) throws Exception {
        String command = String.format("btcli subnet register --netuid %s --wallet.name %s --wallet.hotkey %s", netId, coldKey, hotKey);
        String networkAnswer = "finney";
        String isContinueAnswer = "n";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();
            process.getOutputStream().write((networkAnswer + "\n").getBytes());
            process.getOutputStream().flush();
            process.getOutputStream().write((isContinueAnswer + "\n").getBytes());
            process.getOutputStream().flush();

            // Get input stream of the process
            InputStream inputStream = process.getInputStream();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            String[] array = output.toString().split("\n");
            return array[1].split("τ")[1];
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static String checkFreeBalance() throws Exception {
        String command = String.format("btcli subnet register --netuid %s --wallet.name %s --wallet.hotkey %s", "6", "default", "jackie_hotkey_22");
        String networkAnswer = "finney";
        String isContinueAnswer = "n";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();
            process.getOutputStream().write((networkAnswer + "\n").getBytes());
            process.getOutputStream().flush();
            process.getOutputStream().write((isContinueAnswer + "\n").getBytes());
            process.getOutputStream().flush();

            // Get input stream of the process
            InputStream inputStream = process.getInputStream();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            String[] array = output.toString().split("\n");
            return array[0].split("τ")[1];
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static String runScript(ProcessBuilder processBuilder ){
        try {
             // Redirect error stream to output stream
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Get input stream of the process
            InputStream inputStream = process.getInputStream();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            return output.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String folderOfScript() throws Exception {
        String path = PropertyUtils.getPriceRegisterScriptPath();
        int index = path.lastIndexOf("/");
        return path.substring(0, index);
    }
    public static void main(String[] args) throws Exception {
        System.out.println(checkAllBalance());
//        System.out.println(runMonitorScript("ps -ef|grep java"));
    }

}