package org.example.script;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RunShellScript {
    public static String run(String scriptPath, String subnetId) {
        try {
            // Create process builder
            ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath, subnetId);
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


    public static String register(String scriptPath, String subnetId, String hotkey) {
        try {
            // Create process builder
            ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath, subnetId, hotkey);
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


}