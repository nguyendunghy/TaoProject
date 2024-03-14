package org.example.script;

import org.example.utils.PropertyUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RunShellScript {
    public static String getPriceLinuxCommand(String scriptPath, String subnetId, String hotkey) {
        try {
            // Create process builder
            String runScriptPath = folderOfScript();
            ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath, subnetId, hotkey, runScriptPath);
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


    public static String register(String scriptPath, String subnetId, String threshold, String hotkey) {
        try {
            // Create process builder
            ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath, subnetId, threshold, hotkey);
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

    public static String checkBalance() throws Exception {
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

    public static void main(String[] args) {

    }

}